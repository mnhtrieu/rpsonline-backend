package eu.mnhtrieu.rpsonline.listeners;

import eu.mnhtrieu.rpsonline.events.*;
import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.emitter.Emitter;
import io.socket.socketio.server.SocketIoSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConnectionListener implements Emitter.Listener {

    private final CreateRoomEvent createRoomEvent;
    private final JoinRoomEvent joinRoomEvent;
    private final DisconnectEvent disconnectEvent;
    private final SendMessageEvent sendMessageEvent;
    private final PingEvent pingEvent;
    private final GameService gameService;
    private final LoadedGameEvent loadedGameEvent;
    private final ChoseTeamEvent choseTeamEvent;
    private final SendPlayersEvent sendPlayersEvent;
    private final RequestMoveEvent requestMoveEvent;
    private final SendMoveEvent sendMoveEvent;
    private final RandomMoveEvent randomMoveEvent;
    private final RequestAttackEvent requestAttackEvent;


    @Override
    public void call(Object... objects) {
        SocketIoSocket socket = (SocketIoSocket) objects[0];
        gameService.clients.put(socket.getId(), new Client());
        //---------------------------------------------------
        socket.on(SocketEventType.PING.getEvent(), args -> pingEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.CREATE_ROOM.getEvent(), args -> createRoomEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.JOIN_ROOM.getEvent(), args -> joinRoomEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.DISCONNECT.getEvent(), args -> disconnectEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.SEND_MESSAGE.getEvent(), args -> sendMessageEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.LOADED.getEvent(), args -> loadedGameEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.CHOSE_TEAM.getEvent(), args -> choseTeamEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.SEND_PLAYERS.getEvent(), args -> sendPlayersEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.REQUEST_MOVE.getEvent(), args -> requestMoveEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.SEND_MOVE.getEvent(), args -> sendMoveEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.RANDOM_MOVE.getEvent(), args -> randomMoveEvent.call(socket, args));
        //---------------------------------------------------
        socket.on(SocketEventType.REQUEST_ATTACK_MOVE.getEvent(), args -> requestAttackEvent.call(socket, args));
        //---------------------------------------------------
        System.out.println("An user has connected");
    }
}