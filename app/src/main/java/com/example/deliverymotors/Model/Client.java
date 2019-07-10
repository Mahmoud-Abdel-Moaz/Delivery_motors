package com.example.deliverymotors.Model;

import android.graphics.Bitmap;

public class Client {
    String clientNumber,clientName,email,address,nationalId;
    Bitmap image;

    public Client() {
    }

    public Client(String clientNumber, String clientName, String email, String address, String nationalId, Bitmap image) {
        this.clientNumber = clientNumber;
        this.clientName = clientName;
        this.email = email;
        this.address = address;
        this.nationalId = nationalId;
        this.image = image;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
