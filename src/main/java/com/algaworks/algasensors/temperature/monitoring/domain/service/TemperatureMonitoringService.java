package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TempetureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TempetureLog;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TempetureLogId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {


    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TempetureLogData tempetureLogData) {

        sensorMonitoringRepository.findById(new SensorId(tempetureLogData.getSensorID()))
                .ifPresentOrElse(sensor-> handleSensorMonitoring(tempetureLogData, sensor),
                        () -> logIgnoredTemperature(tempetureLogData));

    }



    private void handleSensorMonitoring(TempetureLogData tempetureLogData, SensorMonitoring sensor) {
       if(sensor.isEnabled()){
        sensor.setLastTemperature(tempetureLogData.getValue());
        sensor.setUpdatedAt(OffsetDateTime.now());
        sensorMonitoringRepository.save(sensor);

        TempetureLog temperatureLog = TempetureLog.builder()
                .id(new TempetureLogId(tempetureLogData.getId()))
                .registerAt(tempetureLogData.getRegisteredAt())
                .value(tempetureLogData.getValue())
                .sensorId(new SensorId(tempetureLogData.getSensorID()))
                .build();

        temperatureLogRepository.save(temperatureLog);
           log.info("Temperature Update: SensorId {} Temp {}", tempetureLogData.getSensorID(),tempetureLogData.getValue());
       } else {
           logIgnoredTemperature(tempetureLogData);
       }


    }

    private void logIgnoredTemperature(TempetureLogData tempetureLogData) {
        log.info("Temperature Ignored: SensorId {} Temp {}", tempetureLogData.getSensorID(),tempetureLogData.getValue());
    }
}
