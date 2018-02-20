import pandas as pd
from utils import *
import numpy as np
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential, load_model
from keras.layers import Dense, LSTM
from math import sqrt
from matplotlib import pyplot
import argparse



# The training data need to be transformed into a 3-D array [samples, timesteps, features]
def filter_data(data, threshold=0.2):
    correlations = data.corr(method="kendall")["Fuel_consumption"]
    cols = list(data)
    for i, corr in enumerate(correlations):
        if np.isnan(corr) or np.abs(corr) < threshold:
            if cols[i] != "Time(s)":
                data = data.drop(cols[i], axis=1)

    return data


def scale_data(data):
    scaler = MinMaxScaler((-1, 1))
    scaled_values = scaler.fit_transform(data.as_matrix())
    scaled_df = pd.DataFrame(scaled_values, columns=list(data))
    return scaled_df, scaler


def build_model(data, settings):
    y = data["Fuel_consumption"].as_matrix()
    X = data.drop("Fuel_consumption", axis=1).as_matrix()
    X = X.reshape(X.shape[0], 1, X.shape[1])
    model = Sequential()
    model.add(LSTM(settings["units"],
                   batch_input_shape=(settings["batch_size"], X.shape[1], X.shape[2]), stateful=True))
    model.add(Dense(1))
    model.compile(loss="mean_squared_error", optimizer="adam")

    for i in range(settings["num_epochs"]):
        model.fit(X, y, epochs=1, batch_size=settings["batch_size"], verbose=1, shuffle=False)
        model.reset_states()

    return model


def predict_fuel_consumption(model, state, settings):
    X = state.reshape(1, 1, state.size)
    prediction = model.predict(X, batch_size=settings["batch_size"])
    return prediction[0]


def predict_test(model, data, settings):
    # TODO investigate how to make it to multi-step predictions
    num_states = data.shape[0]
    predictions = []
    for i in range(num_states):
        state = data.iloc[[i]]
        state_values = state.drop("Fuel_consumption", axis=1).as_matrix()
        predicted_fuel = predict_fuel_consumption(model, state_values, settings)[0]
        predictions.append(predicted_fuel)
    return predictions


def evaluate(predictions, data, scaler):
    for i, pred in enumerate(predictions):
        state = data.iloc[[i]]
        y_true = scaler.inverse_transform(state.as_matrix())
        state["Fuel_consumption"] = pred
        y_pred = scaler.inverse_transform(state.as_matrix())
        rmse = sqrt(mean_squared_error(y_true, y_pred))
        print("RMSE: {}, for state {}".format(rmse, i))


def plot_predictions(predictions, true_values, time_series):
    pyplot.plot(time_series, predictions, label="Predicted")
    pyplot.plot(time_series, true_values, 'r--', label="Actual")
    pyplot.show()


settings = {"batch_size": 1,
            "lag": 1,
            "units": 1,
            "num_epochs": 30}


# ###############################################
# ###########          MAIN           ###########
# ###############################################

model_name = ""
train_new_model = False

data = pd.read_csv("../datasets/KIA_driving_data.csv")
data = data.drop("Class", axis=1)
data = filter_data(data)
training_drives, test_drives = split_data(data, concat=False)
train = pd.concat(training_drives)
train = train.drop("Time(s)", axis=1)
train, scaler = scale_data(train)

if train_new_model:
    model = build_model(data, settings)
    model.save(model_name)
else:
    model = load_model("lstm_model.h5")

for drive in test_drives:
    # This is just the timestamo, it shouldn't be part of the calculations,
    time_series = drive["Time(s)"].values
    drive = drive.drop("Time(s)", axis=1)
    scaled_drive = scaler.transform(drive.as_matrix())
    scaled_drive_df = pd.DataFrame(scaled_drive, columns=list(drive))
    predictions = predict_test(model, scaled_drive_df, settings)

    # Invert the transformation before plotting
    true_fuel_consumption = drive["Fuel_consumption"].values
    scaled_drive_df["Fuel_consumption"] = predictions
    unscaled = scaler.inverse_transform(scaled_drive_df.as_matrix())
    predicted_fuel_consumption = pd.DataFrame(unscaled, columns=list(drive))["Fuel_consumption"].values

    plot_predictions(predicted_fuel_consumption, true_fuel_consumption, time_series)
