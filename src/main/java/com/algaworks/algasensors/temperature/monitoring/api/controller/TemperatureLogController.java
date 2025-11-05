package com.algaworks.algasensors.temperature.monitoring.api.controller;


import com.algaworks.algasensors.temperature.monitoring.api.model.TempetureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TempetureLog;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogRepository temperatureLogRepository;

    @GetMapping
    public Page<TempetureLogData> search(@PathVariable TSID sensorId,
                                         @PageableDefault Pageable pageable){
        Page<TempetureLog> tempetureLogs = temperatureLogRepository.findAllBySensorId(new SensorId(sensorId),
                pageable);
        return tempetureLogs.map(tempetureLog ->
                TempetureLogData.builder()
                        .id(tempetureLog.getId().getValue())
                        .value(tempetureLog.getValue())
                        .registeredAt(tempetureLog.getRegisterAt())
                        .sensorID(tempetureLog.getSensorId().getValue())
                        .build());
    }

}
