package eu.mnhtrieu.rpsonline.models.tiles;

public class RockTile extends Tile{

    public RockTile(int x, int y, boolean type, int idx) {
        super(x, y, type, idx);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        System.out.println("rock tile");
        return tile.isType() != type && isNeighbour(tile);
    }

    @Override
    public Tile clone() {
        return new RockTile(this.x, this.y, this.type, this.idx);
    }

    @Override
    public boolean moveable() {
        return true;
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return tile instanceof PaperTile;
    }

    @Override
    public String getWeaponName() {
        return "rock";
    }
}
