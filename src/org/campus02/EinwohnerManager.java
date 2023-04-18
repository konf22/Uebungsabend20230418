package org.campus02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EinwohnerManager {

    public static void main(String[] args) throws DataFileException {
        ArrayList<Einwohner> load = new EinwohnerManager().load();
        System.out.println(load);
    }

    public ArrayList<Einwohner> load() throws DataFileException {
        String file = "data/testdata-einwohner.csv";
        try (BufferedReader br = new BufferedReader(
                new FileReader(file)
        )) {
            ArrayList<Einwohner> einwohnerList = new ArrayList<>();
            String line;
            br.readLine(); // liest erste Zeile, wollen wir ignorieren (Header)

            // einlesen Zeile für Zeile
            while ((line = br.readLine()) != null) {
                String[] einwohnerData = line.split(";");
                // 1;Cirstoforo Heys;Steiermark;2002
                // -> .split(";")
                // einwohnerData[0] = 1
                // einwohnerData[1] = Cirstoforo Heys
                // einwohnerData[2] = Steiermark
                // einwohnerData[3] = 2002
                if (einwohnerData.length != 4) {
                    throw new DataFileException("datensatz nicht gültig, nicht vollständig");
                }
                // Wandle einen String in ein int um
                int id = Integer.parseInt(einwohnerData[0]);
                Einwohner einwohner = new Einwohner(id,
                        einwohnerData[1], //name
                        einwohnerData[2], // bundesland
                        Integer.parseInt(einwohnerData[3]));
                einwohnerList.add(einwohner);
            }
            return einwohnerList;
        } catch (FileNotFoundException e) {
            throw new DataFileException("File not found", e);
        } catch (IOException e) {
            throw new DataFileException("Cannot read file", e);
        }
    }
}
