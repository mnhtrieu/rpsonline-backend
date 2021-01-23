package eu.mnhtrieu.rpsonline.servlet;

import eu.mnhtrieu.rpsonline.listeners.ConnectionListener;
import io.socket.client.Socket;
import io.socket.engineio.server.EngineIoServer;
import io.socket.socketio.server.SocketIoServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/socket.io/*")
public class SocketIoServlet extends HttpServlet {

    private final EngineIoServer engineIoServer;

    private final ConnectionListener connectionListener;

    @Autowired
    public SocketIoServlet(SocketIoServer socketIoServer, EngineIoServer engineIoServer, ConnectionListener connectionListener){
        this.engineIoServer = engineIoServer;
        this.connectionListener = connectionListener;

        socketIoServer.namespace("/").on(Socket.EVENT_CONNECT, this.connectionListener);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        engineIoServer.handleRequest(request, response);
    }
}