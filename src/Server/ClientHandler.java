package Server;

import Utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final GameState gameState;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, GameState gameState) {
        this.socket = socket;
        this.gameState = gameState;
    }

    private void handleShot(Coordinate c) throws IOException {
        ServerResponseType resultType = gameState.applyShot(c.getRow(), c.getCol());

        ServerResponse result;

        switch (resultType) {
            case HIT ->
                    result = new ServerResponse(ServerResponseType.HIT, "¡Tocado!");

            case MISS ->
                    result = new ServerResponse(ServerResponseType.MISS, "Fallaste...");

            case SUNK ->
                    result = new ServerResponse(ServerResponseType.SUNK, "¡Hundido!");

            case ALREADY ->
                    result = new ServerResponse(ServerResponseType.ALREADY, "Ya disparaste ahí.");

            case WIN -> {
                result = new ServerResponse(ServerResponseType.WIN, "¡Ganaste! Game Over");
                out.writeObject(new Msg(MsgType.SHOT_RESULT, result));
                sendGrid();
                socket.close();
                return;
            }

            default ->
                    result = new ServerResponse(ServerResponseType.MISS, "Error inesperado");
        }
        out.writeObject(new Msg(MsgType.SHOT_RESULT, result));
        sendGrid();
    }

    private void sendGrid() throws IOException {
        String grid = GridPrinter.toVisibleAscii(gameState);
        GridUpdate gridUpdate = new GridUpdate(grid);
        out.writeObject(new Msg(MsgType.GRID_UPDATE, gridUpdate));
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());

            ServerResponse start = new ServerResponse(
                    ServerResponseType.MISS,
                    "Comienza la partida, dispara."
            );
            out.writeObject(new Msg(MsgType.SHOT_RESULT, start));

            sendGrid();

            while (true) {
                Msg msg = (Msg) in.readObject();

                if (msg.getType() == MsgType.SHOT) {
                    Coordinate c = (Coordinate) msg.getPayload();
                    handleShot(c);
                }
            }

        } catch (Exception e) {
            System.out.println("El cliente se ha desconectado.");
        }
    }
}
