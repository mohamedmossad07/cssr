package org.exalt.cssr.location;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Car Location tracker/Consumer
 */
@Service
@RequiredArgsConstructor
public class LocationConsumer {

    private final NotificationService notificationService;

    /**
     * Receive the last updated location and print it
     * @param event event
     */
    @KafkaListener(topics = LocationEventBrokerConfig.LOCATION_TOPIC_NAME, groupId = LocationEventBrokerConfig.OWNERS_GROUP_OF_LOCATION_EVENT)
    public void consumeLocationEvent(LocationEvent event) {
        notificationService.notifyOwner(event);
    }
}
