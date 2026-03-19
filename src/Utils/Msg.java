package Utils;

import java.io.Serializable;

public class Msg implements Serializable {

    private final MsgType type;
    private final Object payload;

    public Msg(MsgType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public MsgType getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}

