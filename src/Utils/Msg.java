package Utils;

import java.io.Serializable;

public record Msg(MsgType type, Object data) implements Serializable {
}

