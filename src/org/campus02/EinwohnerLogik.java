package org.campus02;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class EinwohnerLogik {

    private Socket client;

    public EinwohnerLogik(Socket client) {
        this.client = client;
    }

    public void process() throws IOException, DataFileException {
        // vom client lesen -> inputStream
        // an den client senden -> outputStream

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                client.getInputStream()
        ));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                     client.getOutputStream()
             ))
        ) {
            // lade alle Einwohner aus dem file
            ArrayList<Einwohner> einwohnerList = new EinwohnerManager().load();

            String clientInput;
            while ((clientInput = br.readLine()) != null) {
                // bei GET bundesland -> retourniere Einwohner für das bundesland
                // bei GET geburtsjahr order by name -> retourniere Einwohner für das Geburtsjahr
                // bei EXIT -> schließe Verbindung
                // bei anderem Input -> schicke "falscher Input"

                // variante 1:
//                String[] cmd = clientInput.split(" ");
//                if (cmd.length > 1)

                // variante 2:
                if (clientInput.startsWith("GET ")) {
                    ArrayList<Einwohner> einwohnerFiltered = new ArrayList<>();
                    String[] cmd = clientInput.split(" ");
                    if (cmd.length == 2) {
                        // GET bundesland, zB: GET Steiermark
                        einwohnerFiltered = getByBundesland(cmd[1], einwohnerList);
                    } else {
                        // GET geburtsjahr order by name, zB: GET 1990 order by name
                        einwohnerFiltered = getByGeburtsjahr(Integer.parseInt(cmd[1]), einwohnerList);
                    }
                    // schicke die einwohner an den client retour
                    for (Einwohner einwohner : einwohnerFiltered) {
                        bw.write(einwohner.toString());
                        bw.newLine();
                    }
                } else if (clientInput.equalsIgnoreCase("exit")) {// EXIT, Exit, exit, eXIT
                    break;
                } else {
                    // schicke "falscher Input"
                    bw.write("falscher Input");
                }

                bw.newLine();
                bw.flush();
            }
        }

    }

    private ArrayList<Einwohner> getByBundesland(String bundesland, ArrayList<Einwohner> einwohners) {
        ArrayList<Einwohner> einwohnerFiltered = new ArrayList<>();
        for (Einwohner einwohner : einwohners) {
            if (einwohner.getBundesland().equalsIgnoreCase(bundesland)) {
                einwohnerFiltered.add(einwohner);
            }
        }
        return einwohnerFiltered;
    }

    private ArrayList<Einwohner> getByGeburtsjahr(int geburtsjahr, ArrayList<Einwohner> einwohners) {
        ArrayList<Einwohner> einwohnerFiltered = new ArrayList<>();
        for (Einwohner einwohner : einwohners) {
            if (einwohner.getGeburtsjahr() == geburtsjahr) {
                einwohnerFiltered.add(einwohner);
            }
        }
        // sortiere nach natürlicher Ordnung (compareTo in Einwohner Klasse)
        Collections.sort(einwohnerFiltered);
        return einwohnerFiltered;
    }
}
