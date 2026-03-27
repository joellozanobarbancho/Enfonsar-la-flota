package Server;

public class GridPrinter {
    private static final String SEPARATOR = "========================================";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_RESET = "\u001B[0m";

    public static String toVisibleAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        System.out.println();
        sb.append("     ");
        for (int col = 1; col <= size; col++) {
            sb.append(COLOR_GREEN).append(col).append(" ").append(COLOR_RESET);
        }
        sb.append("\n");

        sb.append("     ");
        for (int col = 0; col < size; col++) {
            sb.append("_ ");
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) {
            sb.append(String.format(COLOR_GREEN + "%2d" + COLOR_RESET + " | ", row + 1));

            for (int col = 0; col < size; col++) {

                if (!state.getShot(row, col)) {
                    sb.append("# ");
                } else {
                    int cell = state.getCell(row, col);

                    if (cell == 2) sb.append(COLOR_YELLOW + "1 " + COLOR_RESET);      // tocado
                    else if (cell == 3) sb.append(COLOR_BLUE + "0 " + COLOR_RESET);   // agua
                    else if (cell == 4) sb.append(COLOR_RED + "X " + COLOR_RESET);    // hundido
                    else sb.append("# ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String toDebugAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        System.out.println();
        sb.append("     ");
        for (int col = 1; col <= size; col++) {
            sb.append(COLOR_GREEN).append(col).append(" ").append(COLOR_RESET);
        }
        sb.append("\n");

        sb.append("     ");
        for (int col = 0; col < size; col++) {
            sb.append("─ ");
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) {
            sb.append(String.format(COLOR_GREEN + "%2d" + COLOR_RESET + " | ", row + 1));

            for (int col = 0; col < size; col++) {
                int cell = state.getCell(row, col);

                if (cell == 1) sb.append(COLOR_RED + "X " + COLOR_RESET);
                else sb.append(COLOR_BLUE + "0 " + COLOR_RESET);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
