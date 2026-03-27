package Server;

public class GridPrinter {

    public static final String COLOR_RED    = "\u001B[31m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE   = "\u001B[34m";
    public static final String COLOR_GREEN  = "\u001B[32m";
    public static final String COLOR_RESET  = "\u001B[0m";

    public static String toVisibleAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        sb.append("     ");
        for (int col = 1; col <= size; col++) {
            sb.append(COLOR_GREEN).append(col).append(" ").append(COLOR_RESET);
        }
        sb.append("\n     ");

        for (int col = 0; col < size; col++) {
            sb.append("─ ");
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) {
            sb.append(String.format(COLOR_GREEN + "%2d" + COLOR_RESET + " | ", row + 1));

            for (int col = 0; col < size; col++) {

                if (!state.getShot(row, col)) {
                    sb.append("# ");
                    continue;
                }

                int cell = state.getCell(row, col);

                switch (cell) {
                    case 2 -> sb.append(COLOR_YELLOW).append("1 ").append(COLOR_RESET); // tocado
                    case 3 -> sb.append(COLOR_BLUE).append("0 ").append(COLOR_RESET);   // agua
                    case 4 -> sb.append(COLOR_RED).append("X ").append(COLOR_RESET);    // hundido
                    default -> sb.append("# ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String toDebugAscii(GameState state) {
        int size = state.getSize();
        StringBuilder sb = new StringBuilder();

        sb.append("     ");
        for (int col = 1; col <= size; col++) {
            sb.append(COLOR_GREEN).append(col).append(" ").append(COLOR_RESET);
        }
        sb.append("\n     ");

        for (int col = 0; col < size; col++) {
            sb.append("─ ");
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) {
            sb.append(String.format(COLOR_GREEN + "%2d" + COLOR_RESET + " | ", row + 1));

            for (int col = 0; col < size; col++) {
                int cell = state.getCell(row, col);

                if (cell == 1)
                    sb.append(COLOR_RED).append("X ").append(COLOR_RESET);
                else
                    sb.append(COLOR_BLUE).append("0 ").append(COLOR_RESET);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
