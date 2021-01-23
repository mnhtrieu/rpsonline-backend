package eu.mnhtrieu.rpsonline.events;

import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChoseTeamEvent implements SocketEvent {

    private final GameService gameService;
    private final SocketIoServer socketIoServer;

    @Autowired
    public ChoseTeamEvent(GameService gameService, SocketIoServer socketIoServer) {
        this.gameService = gameService;
        this.socketIoServer = socketIoServer;
    }

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Client client = gameService.clients.get(socket.getId());
        if(client == null) {
            System.out.println(SocketEventType.CHOSE_TEAM + ": Couldn't find a client");
            return;
        }

        String color = (String) args[0];
        if(color.equals("red")) color = "blue";
        else color = "red";
        socketIoServer.namespace("/").broadcast("room_" + client.getRoom(), SocketEventType.GET_TEAM.getEvent(), color);
    }
}
