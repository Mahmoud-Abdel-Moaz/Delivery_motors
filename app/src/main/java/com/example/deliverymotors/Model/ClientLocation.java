package com.example.deliverymotors.Model;

public class ClientLocation {
    String clientNumber,name,image;
    double longitude,latitude;

    public ClientLocation() {
    }

    public ClientLocation(String clientNumber, String name, String image, double longitude, double latitude) {
        this.clientNumber = clientNumber;
        this.name = name;
        this.image = image;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
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
}
