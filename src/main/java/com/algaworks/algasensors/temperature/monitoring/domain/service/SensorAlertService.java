package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TempetureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorAlertService {

    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlert(TempetureLogData tempetureLogData){
    sensorAlertRepository.findById(new SensorId(tempetureLogData.getSensorID()))
            .ifPresentOrElse(alert -> {
                if(alert.getMaxTemperature() != null
                && tempetureLogData.getValue().compareTo(alert.getMaxTemperature()) >= 0 ){
                    log.info("Alert Max Tempeture: SensorId {} Temp {}",
                            tempetureLogData.getSensorID(), tempetureLogData.getValue());
                } else if(alert.getMinTemperature() != null
                        && tempetureLogData.getValue().compareTo(alert.getMinTemperature()) <= 0 ){
                    log.info("Alert Min Tempeture: SensorId {} Temp {}",
                            tempetureLogData.getSensorID(), tempetureLogData.getValue());
                } else {

                }
            }, () -> {
                logIgnoredAlert(tempetureLogData);
            });
    }
    public static void logIgnoredAlert(TempetureLogData tempetureLogData) {
        log.info("Alert Ignored: SensorId {} Temp {}",
                tempetureLogData.getSensorID(), tempetureLogData.getValue());
    }
}
