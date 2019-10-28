package dev.cardcast.bullying.network.events.types;

import dev.cardcast.bullying.network.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HostKickPlayerEvent extends Event {

    @Getter
    private final String playerName;
}