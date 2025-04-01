package org.exalt.cssr.location;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void notifyOwner(LocationEvent event) {
        log.info("Update: Car with id '{}' exists in '{}' , '{}'", event.getCarId(), event.getLongitude(), event.getLatitude());
    }
}
