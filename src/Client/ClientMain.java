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

                switch (msg.type()) {

                    case GRID_UPDATE -> {
                        GridUpdate update = (GridUpdate) msg.data();
                        System.out.println();
                        System.out.println(update.getGrid());
                        sendShot();
                    }

                    case SHOT_RESULT -> {
                        ServerResponse response = (ServerResponse) msg.data();
                        System.out.println();
                        System.out.println(response.message());

                        if (response.type() == ServerResponseType.WIN) return;
                    }

                    default -> System.out.println("Unknown msg type...");
                }
            }

        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    private int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.matches("\\d+")) {
                return Integer.parseInt(input);
            }

            System.out.println("Entrada inválida. Introduce un número del 1 al 10.");
        }
    }


    private void sendShot() throws Exception {

        if (socket.isClosed() || !socket.isConnected()) {
            System.exit(0);
            return;
        }

        int row;
        int col;

        while (true) {
            row = readInt(scanner, "Seleccione una fila: ");
            if (row >= 1 && row <= GRID_SIZE) break;
            System.out.println("Fila fuera de rango. Introduce un número del 1 al 10.");
        }

        while (true) {
            col = readInt(scanner, "Seleccione una columna: ");
            if (col >= 1 && col <= GRID_SIZE) break;
            System.out.println("Columna fuera de rango. Introduce un número del 1 al 10.");
        }

        row -= 1;
        col -= 1;

        Coordinate c = new Coordinate(row, col);
        Msg msg = new Msg(MsgType.SHOT, c);

        try {
            out.writeObject(msg);
        } catch (IOException e) {
            System.out.println("El servidor ha cerrado la conexión. Fin de la partida.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        clientMain.start("localhost", 5000);
    }
}
