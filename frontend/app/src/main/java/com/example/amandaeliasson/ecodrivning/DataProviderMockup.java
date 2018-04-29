package com.example.amandaeliasson.ecodrivning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by amandaeliasson on 2018-01-29.
 */

public class DataProviderMockup extends DataProvider {
    private Measurement latestAdd = null;
    private int counter;
    List<Measurement> measurements;


    public DataProviderMockup() {
        counter = 0;
    }
    @Override
    public List<Measurement> getData() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar c=Calendar.getInstance();
        c.set(2018+1900,3,1,12,30,6);
        List<Measurement> measurements = new LinkedList<>();
        try {

            Measurement m1 = new SpeedMeasurement(df.parse("20180401"), 55.60457822286884, 13.001237567514181, 1);
            measurements.add(m1);
            latestAdd = m1;

            c.set(0, 0, 0);
            Measurement m2 = new SpeedMeasurement(df.parse("20180401"), 55.60329635732146, 13.001317111775279, 1);
            measurements.add(m2);
            latestAdd = m2;

            Measurement m3 = new SpeedMeasurement(df.parse("20180401"), 55.60299634018647, 12.998828021809459, 1);
            measurements.add(m3);
            latestAdd = m3;

            Measurement m4 = new SpeedMeasurement(df.parse("20180401"), 55.60367819399276, 12.99830767326057, 1);
            measurements.add(m4);
            latestAdd = m4;

            Measurement m5 = new SpeedMeasurement(df.parse("20180401"), 55.603563037292865, 12.996188728138804, 0);
            measurements.add(m5);
            latestAdd = m5;

            Measurement m6 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m6);
            latestAdd = m6;

            Measurement m7 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m7);
            latestAdd = m7;

            Measurement m8 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m8);
            latestAdd = m8;

            Measurement m9 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 0);
            measurements.add(m9);
            latestAdd = m9;

            Measurement m10 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 0);
            measurements.add(m10);
            latestAdd = m10;


            Measurement m11 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 0);
            measurements.add(m11);
            latestAdd = m11;

            Measurement m12 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 0);
            measurements.add(m12);
            latestAdd = m12;

            Measurement m13 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m13);
            latestAdd = m13;

            Measurement m14 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m14);
            latestAdd = m14;

            Measurement m15 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m15);
            latestAdd = m15;

            Measurement m16 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m16);
            latestAdd = m16;


            Measurement m17 = new SpeedMeasurement(df.parse("20180401"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m17);
            latestAdd = m17;

            Measurement m18 = new SpeedMeasurement(df.parse("20180501"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m18);
            latestAdd = m18;

            Measurement m19 = new SpeedMeasurement(df.parse("20180601"), 55.60439033975849, 12.995669301599264, 1);
            measurements.add(m19);
            latestAdd = m19;
            return measurements;
        }catch(Exception e){
         e.printStackTrace();
        }
        return measurements;
    }
    public List<Measurement> getFilteredData(Date start, Date end){
        List<Measurement> filteredData = new LinkedList<>();
        for(Measurement m : getData()){
            if(m.date.after(start) && m.date.before(end)) filteredData.add(m);
        }
        return filteredData;
    }
    public Measurement getMeasurement(){
        List<Measurement> data = getData();
        Measurement m = data.get(counter % data.size());
        counter++;
        return m;
    }
    public Measurement getLatestAdd(){
        return latestAdd;
    }
}