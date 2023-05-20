package com.lidosta.Deikstras_Lidosta.processor.calculator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
//rename to path distance price class
public class PriceDistanceInfo {
    UUID flightId;
    float price;
    int distance;
    List<UUID> path;

    public PriceDistanceInfo(UUID flightId, float price, int distance) {
        this.flightId = flightId;
        this.price = price;
        this.distance = distance;
    }
}
