package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    private final int port;
    private GameState gameState;

    private final List<ClientHandler> clients = new ArrayList<>();
    private boolean gameEnded = false;

    public ServerMain(int port) {
        this.port = port;
    }

    public synchronized void registerClient(ClientHandler handler) {
        clients.add(handler);
    }

    public synchronized void unregisterClient(ClientHandler handler) {
        clients.remove(handler);
    }

    public synchronized void setGameEnded(boolean value) {
        this.gameEnded = value;
    }

    public synchronized void notifyGameOver() {
        for (ClientHandler handler : clients) {
            handler.sendGameOver();
        }
    }

    public synchronized void disconnectAllClients() {
        for (ClientHandler handler : clients) {
            handler.forceDisconnect();
        }
    }

    public synchronized void onClientDisconnected() {
        if (clients.isEmpty() && gameEnded) {
            resetGame();
            gameEnded = false;
        }
    }

    public synchronized void resetGame() {
        this.gameState = new GameState(10);
        GridGenerator.placeShips(gameState);
        System.out.println();
        System.out.println(GridPrinter.toDebugAscii(gameState));
    }

    public void start() {
        System.out.println("Servidor iniciado en el puerto: " + port);
        System.out.println();
        resetGame();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                ClientHandler handler = new ClientHandler(this, clientSocket, gameState);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerMain server = new ServerMain(5000);
        server.start();
    }
}
