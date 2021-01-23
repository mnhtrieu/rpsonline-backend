package eu.mnhtrieu.rpsonline.dto.response;

import lombok.Data;

@Data
public class AttackMoveResponse {
    private boolean result;

    private String weapon;

    private MoveResponse host;

    private MoveResponse join;

    private boolean attackFrom;
}
