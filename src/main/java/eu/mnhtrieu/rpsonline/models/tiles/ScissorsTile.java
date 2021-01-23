package eu.mnhtrieu.rpsonline.models.tiles;

public class ScissorsTile extends Tile{
    public ScissorsTile(int x, int y, boolean type, int idx) {
        super(x, y, type, idx);
    }

    @Override
    public boolean canStepFrom(Tile tile) {
        System.out.println("scissors tile");
        return tile.isType() != type && isNeighbour(tile);
    }

    @Override
    public Tile clone() {
        return new ScissorsTile(this.x, this.y, this.type, this.idx);
    }

    @Override
    public boolean isAttacked(Tile tile) {
        return tile instanceof RockTile;
    }

    @Override
    public String getWeaponName() {
        return "scissors";
    }

    @Override
    public boolean moveable() {
        return true;
    }
}
