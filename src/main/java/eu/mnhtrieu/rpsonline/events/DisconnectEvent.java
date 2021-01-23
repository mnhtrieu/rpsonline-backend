package eu.mnhtrieu.rpsonline.events;

import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.services.GameService;
import io.socket.socketio.server.SocketIoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisconnectEvent implements SocketEvent{

    private final GameService gameService;

    @Autowired
    public DisconnectEvent(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void call(SocketIoSocket socket, Object... args) {
        Client client = gameService.clients.get(socket.getId());
        if(client == null) return;

        gameService.clients.remove(socket.getId());
        // todo vyprazdnit roomku
        System.out.println("An user " +  (client.getName() == null ? socket.getId() : client.getName()) + " has DISCONNECTED");
    }
}
