package eu.mnhtrieu.rpsonline.models.tiles;

public class TrapTile extends Tile {


    public TrapTile(int x, int y, boolean type, int idx) {
        super(x, y, type, idx);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        return tile.isType() != type;
    }

    @Override
    public Tile clone() {
        return new TrapTile(this.x, this.y, this.type, this.idx);
    }

    @Override
    public boolean moveable() {
        return false;
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return true;
    }

    @Override
    public String getWeaponName() {
        return "trap";
    }
}
