package eu.mnhtrieu.rpsonline.dto.response;

import lombok.Data;

@Data
public class GetRandomMoveResponse {

    private MoveResponse player;

    private MoveResponse enemy;
}
