package Server;

import Utils.Coordinate;
import Utils.ServerResponseType;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final int size;
    private final int[][] grid;
    private final boolean[][] shot;
    private final List<Ship> ships = new ArrayList<>();

    private static final int SHIP = 1;
    private static final int HIT = 2;
    private static final int MISS = 3;
    private static final int SUNK = 4;

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

    public boolean getShot(int row, int col) {
        return shot[row][col];
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public ServerResponseType fireShot(int row, int col) {

        if (shot[row][col]) return ServerResponseType.ALREADY;

        shot[row][col] = true;

        if (grid[row][col] == SHIP) {
            grid[row][col] = HIT;
            Ship ship = findShipAt(row, col);
            if (ship == null) return ServerResponseType.HIT;
            ship.registerHit(row, col);

            if (ship.isSunk()) {
                for (Coordinate c : ship.getCells()) grid[c.getRow()][c.getCol()] = SUNK;
                return allShipsSunk() ? ServerResponseType.WIN : ServerResponseType.SUNK;
            }

            return ServerResponseType.HIT;
        }

        grid[row][col] = MISS;
        return ServerResponseType.MISS;
    }

    private Ship findShipAt(int row, int col) {
        for (Ship s : ships) if (s.contains(row, col)) return s;
        return null;
    }

    public boolean allShipsSunk() {
        for (Ship s : ships) if (!s.isSunk()) return false;
        return true;
    }
}
