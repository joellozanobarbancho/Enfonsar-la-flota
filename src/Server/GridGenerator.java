package Server;

import java.util.Random;

public class GridGenerator {

    private static final Random random = new Random();

    private static final int[] SHIP_SIZES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

    public static void placeShips(GameState state) {
        int size = state.getSize();

        for (int shipSize : SHIP_SIZES) {
            boolean placed = false;

            while (!placed) {
                int row = random.nextInt(size);
                int col = random.nextInt(size);
                boolean horizontal = random.nextBoolean();

                if (canPlace(state, row, col, shipSize, horizontal)) {
                    placeShip(state, row, col, shipSize, horizontal);
                    placed = true;
                }
            }
        }
    }

    private static boolean canPlace(GameState state, int row, int col, int shipSize, boolean horizontal) {
        int size = state.getSize();

        if (horizontal) {
            if (col + shipSize > size) return false;

            for (int c = col; c < col + shipSize; c++) {
                if (state.getCell(row, c) != 0) return false;
            }

        } else {
            if (row + shipSize > size) return false;

            for (int r = row; r < row + shipSize; r++) {
                if (state.getCell(r, col) != 0) return false;
            }
        }

        return true;
    }

    private static void placeShip(GameState state, int row, int col, int shipSize, boolean horizontal) {
        if (horizontal) {
            for (int c = col; c < col + shipSize; c++) {
                state.setCell(row, c, 1);
            }
        } else {
            for (int r = row; r < row + shipSize; r++) {
                state.setCell(r, col, 1);
            }
        }
    }
}
