package com.lidosta.Deikstras_Lidosta.processor.calculator.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
//rename to path distance price class
public class FlightsInternalInfo {
    UUID flightId;
    float price;
    int distance;
    List<UUID> path;

    public FlightsInternalInfo(UUID flightId, float price, int distance) {
        this.flightId = flightId;
        this.price = price;
        this.distance = distance;
    }

    public FlightsInternalInfo(UUID flightId, float price, int distance, List<UUID> path) {
        this.flightId = flightId;
        this.price = price;
        this.distance = distance;
        this.path = path;
    }
}
