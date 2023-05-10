package com.example.ex1.Logic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class sensorManager {
    private SensorManager sensorManager;
    private Sensor sensor;
    SensorEventListener sensorEventListener;

    public sensorManager(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                calculateTilt(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
            public void calculateTilt(float x,float y){
                if (x>6.0){
                    Log.d("TILT","X>6.0");
                }
                if (x<-6.0){
                    Log.d("TILT","X<-6.0");
                }
                if (y>6.0){
                    Log.d("TILT","Y>6.0");
                }
                if (y<-6.0){
                    Log.d("TILT","Y<-6.0");
                }
            }


        };
    }
}





