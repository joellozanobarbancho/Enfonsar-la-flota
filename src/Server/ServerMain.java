package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private final int port;
    private GameState gameState;

    public ServerMain(int port) {
        this.port = port;
        this.gameState = new GameState(10);
        GridGenerator.placeShips(gameState);
    }

    public void start() {
        System.out.println("Servidor iniciado en el puerto: " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado en el puerto: " + port);

                ClientHandler handler = new ClientHandler(clientSocket, gameState);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();        }
    }

    public static void main(String[] args) {
        ServerMain server = new ServerMain(5000);
        server.start();
    }
}
