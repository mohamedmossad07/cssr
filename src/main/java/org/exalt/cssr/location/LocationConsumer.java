package org.exalt.cssr.location;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = LocationEventBrokerConfig.LOCATION_TOPIC_NAME, groupId = LocationEventBrokerConfig.OWNERS_GROUP_OF_LOCATION_EVENT)
    public void consumeLocationEvent(LocationEvent event) {
        notificationService.notifyOwner(event);
    }
}
