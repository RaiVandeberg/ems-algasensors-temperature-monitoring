package com.algaworks.algasensors.temperature.monitoring.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TempetureLog {
    @Id
    @AttributeOverride(name = "value",
    column = @Column(name = "id", columnDefinition = "uuid"))
    private TempetureLogId id;

    @Column(name = "\"value\"")
    private Double value;

    private OffsetDateTime registerAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "bigint"))
    private SensorId sensorId;


}
