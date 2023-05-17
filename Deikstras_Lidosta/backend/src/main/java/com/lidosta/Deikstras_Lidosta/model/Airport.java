package com.lidosta.Deikstras_Lidosta.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Airport {
    UUID airportId;
    String name;
    String code;
    String cityName;

    public Airport(String name, String code, String cityName) {
        this.name = name;
        this.code = code;
        this.cityName = cityName;
    }
}
