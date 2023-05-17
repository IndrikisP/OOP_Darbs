package com.lidosta.Deikstras_Lidosta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Airplane {
    private UUID airplaneId;
    private String model;
    private String type;
    private int passengerCount;

    public Airplane(String type, String model, int passengerCount) {
        this.model = model;
        this.type = type;
        this.passengerCount = passengerCount;
    }
}
