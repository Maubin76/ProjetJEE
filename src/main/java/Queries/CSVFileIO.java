package Queries;

import java.io.*;
import java.util.ArrayList;

/**
 * Permet de lire et d'écrire dans un fichier CSV
 * (Séparateurs "," et RetourChariot)
 */
public class CSVFileIO {

    /**
     * Modification du fichier CSV par le array avec :
     * - chaque String[] une ligne du fichier
     * - chaque élément de chaque String[] un mot séparé du prochain par ","
     * @param array Un tableau à 2 dimensions d'éléments à insérer dans le fichier CSV
     * @param fileName le chemin d'accès au fichier
     * @throws IOException
     */
    public void write(ArrayList<String[]> array, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);

        try (fileWriter; BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String[] line : array) {
                for (String elt : line) {
                    bufferedWriter.write(elt + ",");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        }
    }

    /**
     * Permet de lire un fichier csv
     * @param fileName le chemin d'accès au fichier csv
     * @return le contenu avec chaque ligne un String[] split par ","
     * @throws IOException
     */
    public ArrayList<String[]> readCSV(String fileName) throws IOException {
        ArrayList<String[]> tab = new ArrayList<>();

        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            tab.add(line.split(","));
        }

        return tab;
    }

}

