package eu.mnhtrieu.rpsonline.models.tiles;

public class FlagTile extends Tile {

    public FlagTile(int x, int y, boolean type, int idx) {
        super(x, y, type, idx);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        System.out.println("flag tile");
        return tile.isType() != type;
    }

    @Override
    public boolean moveable() {
        return false;
    }

    @Override
    public Tile clone() {
        return new FlagTile(this.x, this.y, this.type, this.idx);
    }

    @Override
    public String getWeaponName() {
        return "flag";
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return false;
    }
}

