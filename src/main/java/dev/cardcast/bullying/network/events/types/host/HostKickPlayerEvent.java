package dev.cardcast.bullying.network.events.types.host;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HostKickPlayerEvent extends Event {

    @Getter
    private final int trackingId;

    @Getter
    private final String playerName;
}