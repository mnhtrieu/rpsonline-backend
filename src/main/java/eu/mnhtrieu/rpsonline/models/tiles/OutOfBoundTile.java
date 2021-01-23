package eu.mnhtrieu.rpsonline.models.tiles;

import lombok.Data;

public class OutOfBoundTile extends Tile{

    public OutOfBoundTile(int x, int y) {
        super(x, y, false, -1);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        return false;
    }

    @Override
    public Tile clone() {
        return null;
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
        return null;
    }
}
