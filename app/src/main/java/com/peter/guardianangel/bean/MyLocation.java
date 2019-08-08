package com.peter.guardianangel.bean;

import java.io.Serializable;

public class MyLocation implements Serializable {

    private static final long serialVersionUID = 5097427414667213363L;

    private double accuracy;

    private double direction;

    private double latitude;

    private double longitude;

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
