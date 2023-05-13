package com.example.ex1.Logic;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;

import com.example.ex1.Interface.CallBackTilt;

public class StepDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private CallBackTilt callBackTilt;
    private int playerPos;
    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, CallBackTilt callBackTilt) {
        sensorManager = (android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBackTilt = callBackTilt;
        initEventListener();
        playerPos=2;
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

            public void calculateTilt(float x, float y) {
                if (x > 4) {
                    playerPos=4;

                    Log.d("Tilt :","left 0");
                } else if (x < 4 && x > 2) {
                    playerPos=3;
                    Log.d("Tilt :","left 1");
                } else if (x < 2 && x > -2) {
                    playerPos=2;
                    Log.d("Tilt :","mid 2");
                } else if (x < -2 && x > -4) {
                    playerPos=1;
                    Log.d("Tilt :","right 3");
                } else {
                    playerPos=0;
                    Log.d("Tilt :","right 4");
                }
                if(callBackTilt!=null){
                    callBackTilt.updatePlayerPos();
                }
            }
        };
            ;
        };
    public int getPlayerPos(){
        return this.playerPos;
    }



    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );


    }

    ;
}










