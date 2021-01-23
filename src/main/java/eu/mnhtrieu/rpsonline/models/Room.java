package eu.mnhtrieu.rpsonline.models;

import lombok.Data;

@Data
public class Room {
    private String host;
    private String join;
    private Boolean [] loaded = {false, false};
    private Boolean [] ready = {false, false};
    private Boolean turn;
    private String id;

    private Board board = new Board();

    public Room(String host, String id) {
        this.host = host;
        this.id = id;
    }

    public boolean setLoaded(int idx, boolean state) {
        this.loaded[idx] = state;
        return this.loaded[0] && this.loaded[1];
    }
    public boolean setReady(int idx, boolean state) {
        this.ready[idx] = state;
        return this.ready[0] && this.ready[1];
    }

    public boolean isPlayerOnTurn(String socketId) {
        return (!turn && host.equals(socketId)) || (turn && join.equals(socketId));
    }

}
