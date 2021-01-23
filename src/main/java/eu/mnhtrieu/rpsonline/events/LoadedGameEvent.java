package eu.mnhtrieu.rpsonline.events;

import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoadedGameEvent implements SocketEvent {

    private final GameService gameService;
    private final SocketIoServer socketIoServer;

    @Autowired
    public LoadedGameEvent(GameService gameService, SocketIoServer socketIoServer) {
        this.gameService = gameService;
        this.socketIoServer = socketIoServer;
    }

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Client client = this.gameService.clients.get(socket.getId());
        if(client == null) {
            System.out.println("Cannot find an user " + socket.getId());
            return;
        }
        Room room = this.gameService.rooms.get(client.getRoom());
        int idx = room.getHost().equals(socket.getId()) ? 0 : 1;
        if(room.setLoaded(idx, true)) { // both ready
            socketIoServer.namespace("/").broadcast("room_" + client.getRoom(), SocketEventType.BOTH_LOADED.getEvent());
        }
    }
}
