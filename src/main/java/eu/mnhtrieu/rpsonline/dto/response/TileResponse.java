package eu.mnhtrieu.rpsonline.dto.response;

import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TileResponse {
    private int x;
    private int y;

    public TileResponse(Tile tile) {
        this.x = tile.getX();
        this.y = tile.getY();
    }
}
