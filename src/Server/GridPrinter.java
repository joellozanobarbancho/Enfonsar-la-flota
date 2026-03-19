package Server;

public class GridPrinter {
    private static final String SEPARATOR = "========================================";

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

                    if (cell == 2) sb.append("1 ");         // tocado
                    else if (cell == 3) sb.append("0 ");    // agua
                    else if (cell == 4) sb.append("X ");    // hundido
                    else sb.append("# ");                   // fallback
                }
            }
            sb.append("\n");
        }

        sb.append(SEPARATOR).append("\n");

        return sb.toString();
    }
}
