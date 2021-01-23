package eu.mnhtrieu.rpsonline.events;

import lombok.Getter;

@Getter
public enum SocketEventType {
    CREATE_ROOM("create a room"),
    JOIN_ROOM("join a room"),
    DISCONNECT("disconnect"),
    GET_MESSAGE("get a message"),
    SEND_MESSAGE("send a message"),
    FAILED_JOIN("failed to join"),
    PING("ping"),
    PREPARE_GAME("prepare a game"),
    LOADED("loaded game"),
    BOTH_LOADED("both loaded"),
    CHOSE_TEAM("chose a team"),
    GET_TEAM("get a team"),
    SEND_PLAYERS("send players"),
    BOTH_READY("both ready"),
    REQUEST_MOVE("request a move"),
    SEND_MOVE("send a move"),
    GET_MOVE("get a move"),
    RANDOM_MOVE("request random move"),
    GET_RANDOM_MOVE("get random move"),
    REQUEST_ATTACK_MOVE("request attack move"),
    GET_ATTACK_MOVE("get attack move");


    private final String event;

    SocketEventType(String event) {
        this.event = event;
    }

}
