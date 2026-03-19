package Server;

import Utils.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {

    private final List<Coordinate> cells;     // casillas que ocupa el barco
    private final List<Coordinate> hits;      // casillas tocadas

    public Ship() {
        this.cells = new ArrayList<>();
        this.hits = new ArrayList<>();
    }

    public void addCell(int row, int col) {
        cells.add(new Coordinate(row, col));
    }

    public void registerHit(int row, int col) {
        Coordinate c = new Coordinate(row, col);
        if (cells.contains(c) && !hits.contains(c)) {
            hits.add(c);
        }
    }

    public boolean isSunk() {
        return hits.size() == cells.size();
    }

    public boolean contains(int row, int col) {
        return cells.contains(new Coordinate(row, col));
    }

    public List<Coordinate> getCells() {
        return cells;
    }
}

