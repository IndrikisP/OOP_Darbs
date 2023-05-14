package com.lidosta.Deikstras_Lidosta.service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

public class FlightService {
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
