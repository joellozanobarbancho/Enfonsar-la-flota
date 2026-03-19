package Server;

import Utils.Coordinate;
import Utils.ServerResponseType;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int size;
    private final int[][] grid;
    private final boolean[][] shot;
    private List<Ship> ships = new ArrayList<>();


    public GameState(int size) {
        this.size = size;
        this.grid = new int[size][size];
        this.shot = new boolean[size][size];
    }

    public int getSize() {
        return size;
    }

    public int getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, int value) {
        grid[row][col] = value;
    }

    public void fireShot(int row, int col) {
        shot[row][col] = true;
    }

    public boolean getShot(int row, int col) {
        return shot[row][col];
    }

    public ServerResponseType applyShot(int row, int col) {
        if (shot[row][col]) {
            return ServerResponseType.ALREADY;
        }

        shot[row][col] = true;

        if (grid[row][col] == 1) {
            grid[row][col] = 2; // tocado

            Ship ship = findShipAt(row, col);
            if (ship == null) return ServerResponseType.HIT;
            ship.registerHit(row, col);

            if (ship.isSunk()) {
                for (Coordinate c : ship.getCells()) {
                    grid[c.getRow()][c.getCol()] = 4;
                }

                if (allShipsSunk()) {
                    return ServerResponseType.WIN;
                }

                return ServerResponseType.SUNK;
            }



            return ServerResponseType.HIT;
        }

        grid[row][col] = 3; // agua
        return ServerResponseType.MISS;
    }

    private Ship findShipAt(int row, int col) {
        for (Ship s : ships) {
            if (s.contains(row, col)) {
                return s;
            }
        }
        return null;
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public boolean allShipsSunk() {
        for (Ship s : ships) {
            if (!s.isSunk()) return false;
        }
        return true;
    }

}
