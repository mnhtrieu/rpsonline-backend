package eu.mnhtrieu.rpsonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveResponse {

    private TileResponse from;
    private TileResponse to;
}
