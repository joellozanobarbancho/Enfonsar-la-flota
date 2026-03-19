package Utils;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    private final ServerResponseType type;
    private final String message;

    public ServerResponse(ServerResponseType type, String message) {
        this.type = type;
        this.message = message;
    }

    public ServerResponseType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}

