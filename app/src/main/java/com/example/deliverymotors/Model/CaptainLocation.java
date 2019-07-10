package com.example.deliverymotors.Model;

public class CaptainLocation {
    String captienNumber,name,image;
    boolean state;
    double longitude,latitude,rate;

    public CaptainLocation() {
    }

    public CaptainLocation(String captienNumber, String name, String image, boolean state, double longitude, double latitude, double rate) {
        this.captienNumber = captienNumber;
        this.name = name;
        this.image = image;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rate = rate;
    }

    public String getCaptienNumber() {
        return captienNumber;
    }

    public void setCaptienNumber(String captienNumber) {
        this.captienNumber = captienNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
