package Utils;

import java.io.Serializable;

public record GridUpdate(String Grid) implements Serializable {

    public String getGrid() {
        return Grid;
    }

}
