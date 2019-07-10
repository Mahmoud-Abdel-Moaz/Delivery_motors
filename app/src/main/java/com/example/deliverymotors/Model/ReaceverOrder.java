package com.example.deliverymotors.Model;

public class ReaceverOrder {
    String orderDetail;
    String clientNumber;
    String captainNumber;
    String clientName;
    String to;
    String from;
    String ClientImage;
    String OrderId;
    String clientId;

    public ReaceverOrder() {
    }

    public ReaceverOrder(String orderDetail, String clientNumber, String captainNumber, String clientName, String to, String from, String clientImage, String orderId, String clientId) {
        this.orderDetail = orderDetail;
        this.clientNumber = clientNumber;
        this.captainNumber = captainNumber;
        this.clientName = clientName;
        this.to = to;
        this.from = from;
        ClientImage = clientImage;
        OrderId = orderId;
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getClientImage() {
        return ClientImage;
    }

    public void setClientImage(String clientImage) {
        ClientImage = clientImage;
    }
}
