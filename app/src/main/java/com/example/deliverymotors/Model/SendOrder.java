package com.example.deliverymotors.Model;

public class SendOrder {
    String orderDetail;
    String clientNumber;
    String clientId;
    String captainNumber;
    String clientName;
    String to;
    String from;

    public SendOrder() {
    }

    public SendOrder(String orderDetail, String clientNumber, String clientId, String captainNumber, String clientName, String to, String from) {
        this.orderDetail = orderDetail;
        this.clientNumber = clientNumber;
        this.clientId = clientId;
        this.captainNumber = captainNumber;
        this.clientName = clientName;
        this.to = to;
        this.from = from;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getCaptainNumber() {
        return captainNumber;
    }

    public void setCaptainNumber(String captainNumber) {
        this.captainNumber = captainNumber;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
