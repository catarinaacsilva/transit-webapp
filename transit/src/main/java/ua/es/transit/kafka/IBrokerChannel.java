package ua.es.transit.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface IBrokerChannel {
    @Input("delay")
    SubscribableChannel inbound();
}
