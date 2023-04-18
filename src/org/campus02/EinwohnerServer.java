package org.campus02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EinwohnerServer {

    public static void main(String[] args) throws IOException, DataFileException {
        try (ServerSocket serverSocket = new ServerSocket(1111)) {
            // laufe durchgehend
            while (true) {
                System.out.println("warte auf Client");
                // listener -> warte auf den Client
                try (Socket client = serverSocket.accept()) {
                    System.out.println("client hat sich verbunden");
                    EinwohnerLogik logik = new EinwohnerLogik(client);
                    logik.process();
                }
            }
        }
    }
}
