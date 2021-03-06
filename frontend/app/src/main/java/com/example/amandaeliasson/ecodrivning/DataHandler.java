package com.example.amandaeliasson.ecodrivning;

import android.content.res.AssetManager;
import android.util.Log;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataHandler {
    private CSVReader inputReader, outputReader;
    private AssetManager assetManager;
    private float output;
    private float[] inputRow;
    private int numRows, limitRow;

    public DataHandler(AssetManager assetManager, int dataIndex, int limit) {
        this.assetManager = assetManager;
        setDataset(dataIndex);
        this.limitRow = limit;
        numRows = 0;
    }

    /**
     * There are currently three available test sets.
     * @param index - 0, 1, or 2.
     */
    public void setDataset(int index) {
        String inputCSV = "test_input" + Integer.toString(index) + ".csv";
        String outputCSV = "test_output" + Integer.toString(index) + ".csv";
        try {
            inputReader = new CSVReader(new InputStreamReader(assetManager.open(inputCSV)));
            outputReader = new CSVReader(new InputStreamReader(assetManager.open(outputCSV)));
            // The first row is the header and we're not interested in that.
            inputReader.readNext();
            outputReader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return - The fuel consumption.
     */
    public float getOutput() {
        return output;
    }

    /**
     * @return - The input data to be fed to the Analyzer.classify method.
     */
    public float[] getInput() {
        return inputRow;
    }

    /**
     * Parses the next row of the chosen dataset. Should always be called before
     * getOutput() and getInput().
     * @return - True if there was a next row, false otherwise.
     */
    public boolean nextRow() {
        if (numRows == limitRow) {
            return false;
        }

        try {
            inputRow = toFloat(inputReader.readNext());
            output = toFloat(outputReader.readNext())[0];
            numRows += 1;
            Log.i("Datahandler", String.format("%d/%d rows handled", numRows, limitRow));
            return true;
        } catch (IOException | NullPointerException e) {
            return false;
        }
    }

    private float[] toFloat(String[] data) {
        float[] floatData = new float[data.length];
        int i = 0;
        for (String val : data) {
            floatData[i++] = Float.parseFloat(val);
        }
        return floatData;
    }
}