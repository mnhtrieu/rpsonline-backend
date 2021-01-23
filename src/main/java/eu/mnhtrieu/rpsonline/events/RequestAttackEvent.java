package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.SendMoveRequest;
import eu.mnhtrieu.rpsonline.dto.response.AttackMoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.MoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.TileResponse;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.models.tiles.EmptyTile;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestAttackEvent implements SocketEvent {

    private final EventUtils utils;

    private final SocketIoServer socketIoServer;

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Room room = utils.getRoom(socket.getId());

        if (room == null) {
            return;
        }

        SendMoveRequest req = new Gson().fromJson((String)args[0], SendMoveRequest.class);

        Tile from = room.getBoard().getTile(req.getFrom().getX(), req.getFrom().getY());
        Tile to = room.getBoard().getTile(req.getTo().getX(), req.getTo().getY());

        if (room.getTurn()) {
            from = room.getBoard().getInverse(from);
            to = room.getBoard().getInverse(to);
        }

        if (!to.canStepFrom(from)) {
            return;
        }

        // detect who wins
        boolean attackResult = to.isAttacked(from);
        System.out.println("Attack result: " + attackResult);
        var response = new AttackMoveResponse();

        // the attacker lost -- remove him from the board
        if (!attackResult) {
            var newFrom = new EmptyTile(from.getX(), from.getY());
            room.setBoard(room.getBoard().withTiles(List.of(newFrom)));
            response.setWeapon(to.getWeaponName());
        }
        else {
            room.setBoard(room.getBoard().withTiles(utils.getNewTiles(from, to)));
            response.setWeapon(from.getWeaponName());
        }

        room.setTurn(!room.getTurn());

        response.setResult(attackResult);
        response.setAttackFrom(room.getTurn());

        response.setHost(new MoveResponse(
                new TileResponse(from),
                new TileResponse(to)));

        response.setJoin(new MoveResponse(
                new TileResponse(room.getBoard().getInverse(from)),
                new TileResponse(room.getBoard().getInverse(to))));


        socketIoServer.namespace("/").broadcast("room_" + room.getId(), SocketEventType.GET_ATTACK_MOVE.getEvent(), new Gson().toJson(response));
    }
}
