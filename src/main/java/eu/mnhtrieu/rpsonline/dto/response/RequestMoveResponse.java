package eu.mnhtrieu.rpsonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class RequestMoveResponse implements Serializable {
    private List<TileResponse> tiles;
}
