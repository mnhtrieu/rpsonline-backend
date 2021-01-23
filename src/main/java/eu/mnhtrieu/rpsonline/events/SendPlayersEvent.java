package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.PlayersRequest;
import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SendPlayersEvent implements SocketEvent {

    private final GameService gameService;
    private final SocketIoServer socketIoServer;

    @Autowired
    public SendPlayersEvent(GameService gameService, SocketIoServer socketIoServer) {
        this.gameService = gameService;
        this.socketIoServer = socketIoServer;
    }


    @Override
    public void call(SocketIoSocket socket, Object... args) {
        PlayersRequest playersRequest = new Gson().fromJson((String)args[0], PlayersRequest.class);
        Client client = gameService.clients.get(socket.getId());
        if(client == null) {
            System.out.println(SocketEventType.SEND_PLAYERS + ": Couldn't find client");
            return;
        }
        Room room = gameService.rooms.get(client.getRoom());
        ArrayList<Tile> newTiles = new ArrayList<>();
        try {
            playersRequest.getPlayers().forEach(t -> newTiles.add(Tile.of(!room.getHost().equals(socket.getId()), t)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        room.setBoard(room.getBoard().withTiles(newTiles));
        int idx = room.getHost().equals(socket.getId()) ? 0 : 1;
        if(room.setReady(idx, true)) { // both ready
            socketIoServer.namespace("/").broadcast("room_" + client.getRoom(), SocketEventType.BOTH_READY.getEvent(), room.getTurn());
        }
        else {
            room.setTurn(!room.getHost().equals(socket.getId())); // false => host, true => join
            System.out.println("Setting turn to " + room.getTurn());
        }
    }
}
