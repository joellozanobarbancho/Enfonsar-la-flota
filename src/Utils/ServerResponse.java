package Utils;

import java.io.Serializable;

public record ServerResponse(ServerResponseType type, String message) implements Serializable {
}

