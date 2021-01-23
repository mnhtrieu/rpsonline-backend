package eu.mnhtrieu.rpsonline;

import io.socket.engineio.server.EngineIoServer;
import io.socket.socketio.server.SocketIoServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SocketConfig {

    @Bean
    public EngineIoServer getEngineIoServer(){
        return new EngineIoServer();
    }

    @Bean
    public SocketIoServer getSocketIoServer(){
        return new SocketIoServer(getEngineIoServer());
    }


}
