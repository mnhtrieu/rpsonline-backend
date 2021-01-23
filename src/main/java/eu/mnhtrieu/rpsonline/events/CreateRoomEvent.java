package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.CreateRoomRequest;
import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomEvent implements SocketEvent {

    private final GameService gameService;

    @Autowired
    private CreateRoomEvent(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void call(SocketIoSocket socket, Object... args) {


            CreateRoomRequest createRoomRequest = new Gson().fromJson((String)args[0], CreateRoomRequest.class);

            String roomId = makeid(15);
            String username = createRoomRequest.getName();

            invokeAck(args[1], roomId);

            gameService.rooms.put(roomId, new Room(socket.getId(), roomId));
            gameService.clients.computeIfPresent(socket.getId(), (s, client) -> new Client(username, roomId));

            socket.joinRoom("room_" + roomId);
            System.out.println("successfully created the room #" + roomId);
    }

    private String makeid(long length) {
        StringBuilder stringBuilder = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; ++i) {
            stringBuilder.append(characters.charAt((int) Math.round(Math.floor(Math.random() * characters.length()))));
        }
        return stringBuilder.toString();
    }


}
