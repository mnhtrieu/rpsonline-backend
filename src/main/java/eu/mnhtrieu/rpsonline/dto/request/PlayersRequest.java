package eu.mnhtrieu.rpsonline.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class PlayersRequest {
    List<PlayerRequest> players;

    @Data
    public static class PlayerRequest {
        private int x;
        private int y;
        private String weapon;
        private int idx;
    }
}
