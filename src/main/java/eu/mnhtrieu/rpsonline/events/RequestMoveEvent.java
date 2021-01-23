package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.RequestMoveRequest;
import eu.mnhtrieu.rpsonline.dto.response.RequestMoveResponse;
import eu.mnhtrieu.rpsonline.dto.response.TileResponse;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestMoveEvent implements SocketEvent {

    private final EventUtils utils;

    private final SocketIoServer socketIoServer;

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        try {

            Room room = utils.getRoom(socket.getId());

            RequestMoveRequest requestMoveRequest = new Gson().fromJson((String) args[0], RequestMoveRequest.class);
            int x = requestMoveRequest.getX(), y = requestMoveRequest.getY();

            if (!room.getHost().equals(socket.getId())) {
                x = 6 - x;
                y = 5 - y;
            }

            var arrows = room.getBoard().movement(x, y).stream();

            if (room.getTurn()) {
                arrows = arrows.map(tile -> room.getBoard().getInverse(tile));
            }
            List<TileResponse> res = arrows.map(TileResponse::new).collect(Collectors.toList());

            invokeAck(args[1], new Gson().toJson(new RequestMoveResponse(res)));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
