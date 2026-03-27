package Server;

import Utils.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final ServerMain server;
    private final Socket socket;
    private final GameState gameState;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(ServerMain server, Socket socket, GameState gameState) {
        this.server = server;
        this.socket = socket;
        this.gameState = gameState;
    }

    public void forceDisconnect() {
        try {
            if (!socket.isClosed()) socket.close();
        } catch (Exception ignored) {}
    }

    public void sendGameOver() {
        try {
            ServerResponse resp = new ServerResponse(ServerResponseType.WIN, "La partida ha terminado.");
            out.writeObject(new Msg(MsgType.GAME_OVER, resp));
            out.flush();
        } catch (Exception ignored) {}
    }

    private void sendGrid() throws Exception {
        String grid = GridPrinter.toVisibleAscii(gameState);
        out.writeObject(new Msg(MsgType.GRID_UPDATE, new GridUpdate(grid)));
        out.flush();
    }

    private void handleShot(Coordinate c) throws Exception {
        ServerResponseType resultType = gameState.fireShot(c.getRow(), c.getCol());
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
                out.flush();
                server.notifyGameOver();
                sendGrid();
                Thread.sleep(150);
                server.disconnectAllClients();
                server.setGameEnded(true);
                return;
            }

            default ->
                    result = new ServerResponse(ServerResponseType.MISS, "Error inesperado");
        }

        out.writeObject(new Msg(MsgType.SHOT_RESULT, result));
        out.flush();
        sendGrid();
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in  = new ObjectInputStream(socket.getInputStream());

            server.registerClient(this);

            sendGrid();

            while (true) {
                Msg msg = (Msg) in.readObject();

                if (msg.type() == MsgType.SHOT) {
                    Coordinate c = (Coordinate) msg.data();
                    handleShot(c);
                }
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado.");
        } finally {
            server.unregisterClient(this);
            server.onClientDisconnected();
        }
    }
}
