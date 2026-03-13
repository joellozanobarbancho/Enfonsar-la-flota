package Server;

public class GameState {
    private final int size;
    private final int[][] grid;
    private final boolean[][] shot;

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

    public boolean applyShot(int row, int col) {
        fireShot(row, col);

        if (grid[row][col] == 1) {   // había barco
            grid[row][col] = 2;      // barco tocado
            return true;
        } else {
            grid[row][col] = 3;      // agua
            return false;
        }
    }

}
