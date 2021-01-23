package eu.mnhtrieu.rpsonline.models.tiles;

public class EmptyTile extends Tile{

    public EmptyTile(int x, int y) {
        super(x, y, false, -1);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        return true;
    }

    @Override
    public Tile clone() {
        return new EmptyTile(this.x, this.y);
    }

    @Override
    public String getWeaponName() {
        return "empty";
    }

    @Override
    public boolean moveable() {
        return false;
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return false;
    }
}
