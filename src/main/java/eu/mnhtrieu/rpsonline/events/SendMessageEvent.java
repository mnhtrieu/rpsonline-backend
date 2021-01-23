package eu.mnhtrieu.rpsonline.events;

import com.google.gson.Gson;
import eu.mnhtrieu.rpsonline.dto.request.MessageRequest;
import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMessageEvent implements SocketEvent {

    private final GameService gameService;

    @Autowired
    public SendMessageEvent(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        MessageRequest json = new Gson().fromJson((String)args[0], MessageRequest.class);
        Client client = gameService.clients.get(socket.getId());
        if(client == null) return;
        System.out.println("user " + socket.getId() + " has send a message to room " + client.getRoom());
        socket.broadcast("room_" + client.getRoom(), SocketEventType.GET_MESSAGE.getEvent(), json.getMessage());
    }
}
