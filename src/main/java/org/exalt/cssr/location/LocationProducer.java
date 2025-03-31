package org.exalt.cssr.location;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationProducer {

    private final KafkaTemplate<String, LocationEvent> kafkaTemplate;

    public void sendLocationEvent(LocationEvent event) {
        kafkaTemplate.send(LocationEventBrokerConfig.LOCATION_TOPIC_NAME, event);
    }
}
