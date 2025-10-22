package com.algaworks.algasensors.temperature.monitoring.api.model;

public record SensorAlertInput(
        Double minTemperature,
        Double maxTemperature
) {

    public SensorAlertInput{
        if(minTemperature != null && maxTemperature != null && minTemperature > maxTemperature){
            throw  new IllegalArgumentException("Min temperature cannot be greater than max temperature");
        }
    }
}
