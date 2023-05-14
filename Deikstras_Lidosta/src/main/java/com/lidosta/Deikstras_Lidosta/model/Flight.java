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
    float distance;
    float price;
    Date timeOfArrival;
    Date timeOfDeparture;
    String timeZone;
    UUID airplaneId;
}
