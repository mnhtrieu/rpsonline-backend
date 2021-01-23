package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.response.GetRandomMoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.MoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.TileResponse;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RandomMoveEvent implements SocketEvent {

    private final SocketIoServer socketIoServer;

    private final EventUtils utils;

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Room room = utils.getRoom(socket.getId());

        if (room == null) {
            return;
        }

        var userTiles = room.getBoard().flatTiles().stream()
                .filter(Tile::moveable)
                .filter(tile -> tile.isType() == room.getTurn())
                .filter(tile -> room.getBoard().movement(tile.getX(), tile.getY()).size() > 0)
                .collect(Collectors.toList());

        Random rand = new Random();

        var tile = userTiles.get(rand.nextInt(userTiles.size()));
        var movements = room.getBoard().movement(tile.getX(), tile.getY());
        var randomMove = movements.get(rand.nextInt(movements.size()));

        var response = new GetRandomMoveResponse();

        if (room.getTurn()) {
            tile = room.getBoard().getInverse(tile);
            randomMove = room.getBoard().getInverse(randomMove);
        }

        response.setPlayer(new MoveResponse(
                new TileResponse(tile),
                new TileResponse(randomMove)));

        response.setEnemy(new MoveResponse(
                new TileResponse(room.getBoard().getInverse(tile)),
                new TileResponse(room.getBoard().getInverse(randomMove))));

        room.setTurn(!room.getTurn());

        socketIoServer.namespace("/").broadcast("room_" + room.getId(), SocketEventType.GET_RANDOM_MOVE.getEvent(), new Gson().toJson(response));

    }
}
