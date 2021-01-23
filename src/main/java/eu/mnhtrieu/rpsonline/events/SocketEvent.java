package eu.mnhtrieu.rpsonline.events;

import io.socket.socketio.server.SocketIoSocket;

import java.lang.reflect.Method;
import java.util.Arrays;

public interface SocketEvent {
    void call(SocketIoSocket socket, Object... args);

    default void invokeAck(Object ack, Object ... objects) {
        Method[] methods = ack.getClass().getMethods();
        methods[0].setAccessible(true);
        try {
            methods[0].invoke(ack, new Object[]{objects});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
