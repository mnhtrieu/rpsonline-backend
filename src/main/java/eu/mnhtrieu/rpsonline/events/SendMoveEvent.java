package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.SendMoveRequest;
import eu.mnhtrieu.rpsonline.dto.response.GetMoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.TileResponse;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMoveEvent implements SocketEvent{

    private final EventUtils utils;

    private final SocketIoServer socketIoServer;

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Room room = utils.getRoom(socket.getId());

        if (room == null) {
            invokeAck(args[1], "FAIL");
            return;
        }

        // player move
        SendMoveRequest req = new Gson().fromJson((String)args[0], SendMoveRequest.class);

        Tile from = room.getBoard().getTile(req.getFrom().getX(), req.getFrom().getY());
        Tile to = room.getBoard().getTile(req.getTo().getX(), req.getTo().getY());

        if (room.getTurn()) {
            from = room.getBoard().getInverse(from);
            to = room.getBoard().getInverse(to);
        }

        if (!to.canStepFrom(from)) {
            invokeAck(args[1], "FAIL");
            return;
        }

        var newTiles = new java.util.ArrayList<>(utils.getNewTiles(from, to));

        room.setBoard(room.getBoard().withTiles(newTiles));
        invokeAck(args[1], "TRUE");

        var response = new GetMoveResponse();

        response.setJoin(room.getHost().equals(socket.getId()));

        if (!room.getTurn()) {
            from = room.getBoard().getInverse(from);
            to = room.getBoard().getInverse(to);
        }

        response.setFrom(new TileResponse(from));
        response.setTo(new TileResponse(to));

        socketIoServer.namespace("/").broadcast("room_" + room.getId(), SocketEventType.GET_MOVE.getEvent(), new Gson().toJson(response));
        room.setTurn(!room.getTurn());
    }


}
