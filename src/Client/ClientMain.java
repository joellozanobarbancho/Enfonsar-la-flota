package Client;

import Utils.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Scanner scanner = new Scanner(System.in);

    private final int GRID_SIZE = 10;

    public void start(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to " + socket.getInetAddress().getHostName());

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Msg msg = (Msg) in.readObject();

                switch (msg.getType()) {

                    case GRID_UPDATE -> {
                        GridUpdate update = (GridUpdate) msg.getPayload();
                        System.out.println(update.getGrid());
                        sendShot();
                    }

                    case SHOT_RESULT -> {
                        ServerResponse response = (ServerResponse) msg.getPayload();
                        System.out.println(response.getMessage());

                        if (response.getType() == ServerResponseType.WIN) return;
                    }

                    default -> System.out.println("Unknown msg type...");
                }
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private void sendShot() throws Exception {

        int row;
        int col;

        while (true) {
            System.out.println("Seleccione una fila: (0-9)");
            row = scanner.nextInt();

            if (row >= 0 && row < GRID_SIZE) break;

            System.out.println("Fila fuera de rango. Inténtelo de nuevo.");
        }

        while (true) {
            System.out.println("Seleccione una columna: (0-9)");
            col = scanner.nextInt();

            if (col >= 0 && col < GRID_SIZE) break;

            System.out.println("Columna fuera de rango. Inténtelo de nuevo.");
        }

        Coordinate c = new Coordinate(row, col);
        Msg msg = new Msg(MsgType.SHOT, c);
        out.writeObject(msg);
    }

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        clientMain.start("localhost", 5000);
    }
}
