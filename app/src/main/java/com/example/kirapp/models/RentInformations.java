package com.example.kirapp.models;

public class RentInformations {
    private String lessorId;
    private String renterId;
    private String advertId;
    private String timeStamp;

    public RentInformations() {
    }

    public RentInformations(String lessorId, String renterId, String advertId, String timeStamp) {
        this.lessorId = lessorId;
        this.renterId = renterId;
        this.advertId = advertId;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "RentInformations{" +
                "lessorId='" + lessorId + '\'' +
                ", renterId='" + renterId + '\'' +
                ", advertId='" + advertId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }

    public String getLessorId() {
        return lessorId;
    }

    public void setLessorId(String lessorId) {
        this.lessorId = lessorId;
    }

    public String getRenterId() {
        return renterId;
    }

    public void setRenterId(String renterId) {
        this.renterId = renterId;
    }

    public String getAdvertId() {
        return advertId;
    }

    public void setAdvertId(String advertId) {
        this.advertId = advertId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
