package eu.mnhtrieu.rpsonline.models;

import eu.mnhtrieu.rpsonline.models.tiles.EmptyTile;
import eu.mnhtrieu.rpsonline.models.tiles.OutOfBoundTile;
import eu.mnhtrieu.rpsonline.models.tiles.Tile;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Board {

    private Tile[][] tiles;

    private static int[][] RELATIVE = new int[][]{
        {0, -1},{1, 0},{0, 1},{-1, 0}
    };

    Board() {
        tiles = new Tile[7][6]; // width x height
        for(int i = 0; i < 7; ++ i) {
            for(int j = 0; j < 6; ++ j) {
                this.tiles[i][j] = new EmptyTile(i, j);
            }
        }
    }

    public Board withTiles(List<Tile> tiles) {
        Board board = new Board();
        for(int i = 0; i < 7; ++ i) board.tiles[i] = this.tiles[i].clone();
        for(Tile tile: tiles) {
            board.tiles[tile.getX()][tile.getY()] = tile;
        }
        return board;
    }

    private boolean checkPos(int x, int y) {
        if(x < 0 || x > 6) return false;
        return y >= 0 && y < 6;
    }

    public List<Tile> movement(int posX, int posY) {
        Tile current = tiles[posX][posY];

        List<Tile> res = new ArrayList<>();

        System.out.println("checking tile [" + posX + ", " + posY + "]");

        for (int i = 0; i < RELATIVE.length; ++ i) {
            int x = posX + RELATIVE[i][0];
            int y = posY + RELATIVE[i][1];
            if (checkPos(x, y) && tiles[x][y].canStepFrom(current)) {
                res.add(tiles[x][y]);
            }
        }

        return res;
    }

    public Tile getInverse(Tile tile) {
        int x = tile.getX(), y = tile.getY();

        x = 6 - x;
        y = 5 - y;

        return tiles[x][y];
    }


    public Tile getTile(int x, int y) {
        if (!checkPos(x, y)) {
            return new OutOfBoundTile(x, y);
        }

        return tiles[x][y];
    }

    public List<Tile> flatTiles() {
        List<Tile> res = new ArrayList<>();

        for(int i = 0; i < 7; ++ i) {
            res.addAll(Arrays.asList(tiles[i]).subList(0, 6));
        }

        return res;
    }

}
