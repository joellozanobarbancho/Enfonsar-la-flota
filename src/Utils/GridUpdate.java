package Utils;

import java.io.Serializable;

public class GridUpdate implements Serializable {
    private final String Grid;

    public GridUpdate(String asciiGrid) {
        this.Grid = asciiGrid;
    }

    public String getGrid() {
        return Grid;
    }

}
