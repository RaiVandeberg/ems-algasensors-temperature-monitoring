package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;


import com.algaworks.algasensors.temperature.monitoring.api.model.TempetureLogData;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

import static com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListerner {

    @RabbitListener(queues = QUEUE)
    @SneakyThrows
    public void handle(@Payload TempetureLogData temperatureLogData,
                       @Headers Map<String, Object> headers
                       ){
        TSID sensorID = temperatureLogData.getSensorID();
        Double temperature = temperatureLogData.getValue();
        log.info("Temperature update: SensorId {} Temp {}", sensorID, temperature);
        log.info("headers{}", headers);

    }

}
