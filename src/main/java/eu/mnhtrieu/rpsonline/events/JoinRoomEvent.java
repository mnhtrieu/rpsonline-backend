package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.CreateRoomRequest;
import eu.mnhtrieu.rpsonline.dto.response.PlayerNamesResponse;
import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoServer;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JoinRoomEvent implements SocketEvent {

    private final GameService gameService;
    private final SocketIoServer socketIoServer;

    @Autowired
    public JoinRoomEvent(GameService gameService, SocketIoServer socketIoServer) {
        this.gameService = gameService;
        this.socketIoServer = socketIoServer;
    }

    public void call(SocketIoSocket socket, Object... args){
        CreateRoomRequest json = new Gson().fromJson((String)args[0], CreateRoomRequest.class);
        String roomId = json.getRoom();
        String username = json.getName();
        Room room = gameService.rooms.get(roomId);


        if(room == null) {
            System.out.println("failed to find a room #" + roomId);
//            socket.emit(EventType.FAILED_JOIN.getEvent(), "The game wasn't found");
            invokeAck(args[1], "FAIL");
        }
        else {
            invokeAck(args[1], "SUCCESS");
            room.setJoin(socket.getId());
            System.out.println("successfully joined the room #" + roomId);
            socket.joinRoom("room_" + roomId);
            gameService.clients.computeIfPresent(socket.getId(), (s, client) -> new Client(username, roomId));
            String hostName = gameService.clients.get(room.getHost()).getName();
            socketIoServer.namespace("/").broadcast("room_" + roomId, SocketEventType.PREPARE_GAME.getEvent(), new Gson().toJson(new PlayerNamesResponse(hostName, username)));
        }

    }
}
