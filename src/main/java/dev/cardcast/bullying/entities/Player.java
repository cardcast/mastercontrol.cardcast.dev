package dev.cardcast.bullying.entities;

import lombok.Getter;
import lombok.Setter;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.UUID;

@Getter @Setter
public class Player {

    @Getter
    private final UUID uuid;

    @Getter
    private Session session;

    @Getter
    private Hand hand;

    @Getter
    @Setter
    private String name;

    public Player(Session session) {
        this.hand = new Hand();
        this.uuid = UUID.randomUUID();
        this.session = session;
    }
}
