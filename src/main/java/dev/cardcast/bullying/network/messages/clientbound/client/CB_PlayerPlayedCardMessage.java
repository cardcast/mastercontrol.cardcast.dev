package dev.cardcast.bullying.network.messages.clientbound.client;

import dev.cardcast.bullying.network.messages.clientbound.ClientBoundWSMessage;

public class CB_PlayerPlayedCardMessage extends ClientBoundWSMessage {
    public CB_PlayerPlayedCardMessage(int trackingId) {
        this.setTrackingId(trackingId);
    }
}
