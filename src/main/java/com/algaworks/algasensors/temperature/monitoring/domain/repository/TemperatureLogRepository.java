package com.algaworks.algasensors.temperature.monitoring.domain.repository;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TempetureLog;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TempetureLogId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureLogRepository extends JpaRepository<TempetureLog, TempetureLogId> {
    Page<TempetureLog> findAllBySensorId(SensorId sensorId, Pageable pageable);
}
