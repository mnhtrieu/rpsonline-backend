package eu.mnhtrieu.rpsonline.dto.response;

import lombok.Data;

@Data
public class GetMoveResponse {
    private boolean join;
    private TileResponse from;
    private TileResponse to;
}
