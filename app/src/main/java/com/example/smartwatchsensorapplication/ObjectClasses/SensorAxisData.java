package com.example.smartwatchsensorapplication.ObjectClasses;

public class SensorAxisData {
    String a_timer = "";
    float b_xValue = 0f;
    float c_yValue = 0f;
    float d_zValue = 0f;


    public SensorAxisData(String a_timer, float b_xValue, float c_yValue, float d_zValue) {
        this.a_timer = a_timer;
        this.b_xValue = b_xValue;
        this.c_yValue = c_yValue;
        this.d_zValue = d_zValue;
    }

    public String getA_timer() {
        return a_timer;
    }

    public void setA_timer(String a_timer) {
        this.a_timer = a_timer;
    }

    public float getB_xValue() {
        return b_xValue;
    }

    public void setB_xValue(float b_xValue) {
        this.b_xValue = b_xValue;
    }

    public float getC_yValue() {
        return c_yValue;
    }

    public void setC_yValue(float c_yValue) {
        this.c_yValue = c_yValue;
    }

    public float getD_zValue() {
        return d_zValue;
    }

    public void setD_zValue(float d_zValue) {
        this.d_zValue = d_zValue;
    }
}
