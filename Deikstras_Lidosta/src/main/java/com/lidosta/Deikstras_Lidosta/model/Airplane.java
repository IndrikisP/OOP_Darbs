package com.lidosta.Deikstras_Lidosta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Airplane {
    UUID airplaneId;
    String model;
    String type;
    int passengerCount;
}
