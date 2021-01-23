package eu.mnhtrieu.rpsonline.models.tiles;

public class PaperTile extends Tile{

    public PaperTile(int x, int y, boolean type, int idx) {
        super(x, y, type, idx);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        System.out.println("paper tile");
        return tile.isType() != type && isNeighbour(tile);
    }

    @Override
    public boolean moveable() {
        return true;
    }

    @Override
    public Tile clone() {
        return new PaperTile(this.x, this.y, this.type, this.idx);
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return tile instanceof ScissorsTile;
    }

    @Override
    public String getWeaponName() {
        return "paper";
    }
}

