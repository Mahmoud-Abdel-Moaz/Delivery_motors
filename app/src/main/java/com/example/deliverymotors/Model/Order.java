package com.example.deliverymotors.Model;

public class Order {
    String captainName,clientName,captainNumber,clientNumber,orderDetail,from,to,startDate,endDate,price;
    boolean completed;

    public Order() {
    }

    public Order(String captainName, String clientName, String captainNumber, String clientNumber, String orderDetail, String from, String to, String startDate, String endDate, String price, boolean completed) {
        this.captainName = captainName;
        this.clientName = clientName;
        this.captainNumber = captainNumber;
        this.clientNumber = clientNumber;
        this.orderDetail = orderDetail;
        this.from = from;
        this.to = to;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.completed = completed;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getCaptainNumber() {
        return captainNumber;
    }

    public void setCaptainNumber(String captainNumber) {
        this.captainNumber = captainNumber;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
