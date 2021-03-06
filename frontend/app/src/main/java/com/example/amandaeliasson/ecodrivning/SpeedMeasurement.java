package com.example.amandaeliasson.ecodrivning;

import java.util.Date;

/**
 * Created by amandaeliasson on 2018-01-29.
 */

public class SpeedMeasurement extends Measurement {
    public SpeedMeasurement(Date d, double c1, double c2, double s, int distance, int duration, String markerReason) {
        super(d, c1, c2, s, distance, duration, markerReason);
    }
    public double getSpeed(){
        return super.data;
    }
    public boolean goodValue(){
        if(getSpeed() ==1){
            return true;
        }else{
            return false;
        }
    }
    public String typeOfMeasurment(){
        return "speedmeasurment";
    }

}
