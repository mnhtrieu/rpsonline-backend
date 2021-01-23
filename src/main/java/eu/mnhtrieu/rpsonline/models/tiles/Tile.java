package eu.mnhtrieu.rpsonline.models.tiles;

import eu.mnhtrieu.rpsonline.dto.request.PlayersRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Tile {

    protected int x;
    protected int y;
    protected boolean type;
    protected int idx;

    public abstract boolean canStepFrom(Tile tile);
    
    public static Tile of(boolean isJoinner, PlayersRequest.PlayerRequest playerRequest) {
        Tile tile;
        int x = playerRequest.getX(), y = playerRequest.getY();
        if(isJoinner) {
            x = 6 - x;
            y = 5 - y;
        }
        switch(playerRequest.getWeapon()) {
            case "rock":
                tile = new RockTile(x, y, isJoinner, playerRequest.getIdx());
                break;
            case "paper":
                tile = new PaperTile(x, y, isJoinner, playerRequest.getIdx());
                break;
            case "scissors":
                tile = new ScissorsTile(x, y, isJoinner, playerRequest.getIdx());
                break;
            case "trap":
                tile = new TrapTile(x, y, isJoinner, playerRequest.getIdx());
                break;
            case "flag":
                tile = new FlagTile(x, y, isJoinner, playerRequest.getIdx());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + playerRequest.getWeapon());
        }
        return tile;
    }

    public abstract Tile clone();

    public abstract boolean moveable();

    public boolean isNeighbour(Tile tile) {
        System.out.println("x distance: " + Math.abs(getX() - tile.getX()));
        System.out.println("y distance: " + Math.abs(getY() - tile.getY()));
        return ((Math.abs(getX() - tile.getX()) == 1 && Math.abs(getY() - tile.getY()) == 0) ||
                (Math.abs(getY() - tile.getY()) == 1 && Math.abs(getX() - tile.getX()) == 0));
    }

    public abstract boolean isAttacked(Tile tile);

    public abstract String getWeaponName();
}
