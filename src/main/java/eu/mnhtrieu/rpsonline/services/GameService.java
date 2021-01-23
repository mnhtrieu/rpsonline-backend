package eu.mnhtrieu.rpsonline.services;

import eu.mnhtrieu.rpsonline.models.Client;
import eu.mnhtrieu.rpsonline.models.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class GameService {
    public final HashMap<String, Room> rooms = new HashMap<>();
    public final HashMap<String, Client> clients = new HashMap<>();
}
