package com.lidosta.Deikstras_Lidosta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Flight {
    UUID flightId;
    UUID fromId;
    UUID toId;
    int distance;
    float price;
    Date timeOfArrival;
    Date timeOfDeparture;
    String timeZone;
    UUID airplaneId;
    String company;

    public Flight() {
    }

    public Flight(UUID fromId, UUID toId, int distance, float price, Date timeOfArrival, Date timeOfDeparture, String timeZone, UUID airplaneId, String company) {
        this.fromId = fromId;
        this.toId = toId;
        this.distance = distance;
        this.price = price;
        this.timeOfArrival = timeOfArrival;
        this.timeOfDeparture = timeOfDeparture;
        this.timeZone = timeZone;
        this.airplaneId = airplaneId;
        this.company = company;
    }
}
