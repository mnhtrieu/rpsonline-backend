package eu.mnhtrieu.rpsonline.events;

import io.socket.socketio.server.SocketIoSocket;
import org.springframework.stereotype.Component;

@Component
public class PingEvent implements SocketEvent {
    @Override
    public void call(SocketIoSocket socket, Object... args) {
        invokeAck(args[0], (Object) null);
    }
}
