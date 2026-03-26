package Server;

public class GridPrinter {
    private static final String SEPARATOR = "========================================";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_RESET = "\u001B[0m";

    public static String toVisibleAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        sb.append(SEPARATOR).append("\n");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                if (!state.getShot(row, col)) {
                    sb.append("# ");
                } else {
                    int cell = state.getCell(row, col);

                    if (cell == 2) sb.append(COLOR_YELLOW + "1 " + COLOR_RESET);      // tocado
                    else if (cell == 3) sb.append(COLOR_BLUE + "0 " + COLOR_RESET);   // agua
                    else if (cell == 4) sb.append(COLOR_RED + "X " + COLOR_RESET);    // hundido
                    else sb.append(COLOR_RESET + "# " + COLOR_RESET);    // fallback
                }
            }
            sb.append("\n");
        }

        sb.append(COLOR_RESET + SEPARATOR).append("\n");

        return sb.toString();
    }

    public static String toDebugAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        sb.append(SEPARATOR).append("\n");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int cell = state.getCell(row, col);

                if (cell == 1) sb.append(COLOR_RED + "X " + COLOR_RESET);
                else sb.append(COLOR_BLUE + "0 " + COLOR_RESET);
            }
            sb.append("\n");
        }

        sb.append(COLOR_RESET + SEPARATOR).append("\n");

        return sb.toString();
    }
}
