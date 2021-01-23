package eu.mnhtrieu.rpsonline.events;

import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import eu.mnhtrieu.rpsonline.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EventUtils {

    protected final GameService gameService;

    public Room getRoom(String socketId) {

        Client client = gameService.clients.get(socketId);

        if (client == null) {
            System.out.println(SocketEventType.SEND_MOVE + ": Cannot find client");
            return null;
        }

        Room room = gameService.rooms.get(client.getRoom());
//
//        if (!room.isPlayerOnTurn(client.getSocketId())) {
//            return null;
//        }

        return room;
    }

    protected List<Tile> getNewTiles(Tile from, Tile to) {

        Tile newFrom = from.clone();
        newFrom.setX(to.getX());
        newFrom.setY(to.getY());

        Tile newTo = to.clone();
        newTo.setX(from.getX());
        newTo.setY(from.getY());

        return List.of(newFrom, newTo);
    }
}
