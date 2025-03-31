package org.exalt.cssr.location;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyOwner(LocationEvent event) {
        System.out.println(event);
    }
}
