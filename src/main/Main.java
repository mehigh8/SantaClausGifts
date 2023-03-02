package main;

import checker.Checker;
import common.Constants;
import common.Helpers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import santa.Change;
import santa.Santa;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa folosita pentru rularea codului.
 */
public final class Main {

    private Main() {
    }
    /**
     * Functie folosita pentru citirea input-ului, rularea simularilor lui Mos Craciun si
     * apelarea checker-ului.
     * @param args argumentele folosite la apelarea functiei main
     */
    public static void main(final String[] args) throws IOException, ParseException {
        // Crearea directorului de output.
        Path outputPath = Paths.get(Constants.OUTPUT);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }

        // Stergerea vechilor fisiere din directorul de output (daca exista).
        File[] outputFiles = (new File(Constants.OUTPUT)).listFiles();
        if (outputFiles != null) {
            for (File file : outputFiles) {
                if (!file.delete()) {
                    System.out.println("Failed to delete file");
                }
            }
        }

        // Parcurgerea fisierelor din directorului de teste pentru a fi rulate.
        File testsDir = new File(Constants.TESTS);
        File[] tests = testsDir.listFiles();
        if (tests != null) {
            for (File file : tests) {
                // Crearea caii fisierului de output.
                String outputFilePath = Constants.OUTPUT_PATH
                        + file.getName().substring(Constants.FOUR);
                File result = new File(outputFilePath);
                if (result.createNewFile()) {
                    runTest(file.getAbsolutePath(), outputFilePath);
                }
            }
        }
        // Apelarea checker-ului.
        Checker.calculateScore();
    }

    /**
     * Functie care ruleaza un test.
     * @param inputPath calea fisierului de input
     * @param outputPath calea fisierului de output
     */
    public static void runTest(final String inputPath, final String outputPath) {
        try {
            JSONParser jsonParser = new JSONParser();
            // Preluarea intregului continut al testului.
            JSONObject content = (JSONObject) jsonParser.parse(new FileReader(inputPath));
            // Mai intai sunt preluate annual change-urile.
            JSONArray annualChanges = (JSONArray) content.get("annualChanges");
            List<Change> changes = new ArrayList<>();
            for (Object jsonChange : annualChanges) {
                changes.add(new Change(Double.parseDouble((
                        (Long) ((JSONObject) jsonChange).get("newSantaBudget")).toString()),
                        Helpers.getJsonGifts(
                                (JSONArray) ((JSONObject) jsonChange).get("newGifts")),
                        Helpers.getJsonChildren(
                                (JSONArray) ((JSONObject) jsonChange).get("newChildren")),
                        Helpers.getJsonUpdates(
                                (JSONArray) ((JSONObject) jsonChange).get("childrenUpdates")),
                        Helpers.stringToCityStrategy(
                                (String) ((JSONObject) jsonChange).get("strategy"))));
            }
            // Apoi sunt preluate datele intiale si este creata variabila santa.
            JSONObject initialData = (JSONObject) content.get("initialData");
            Santa santa = new Santa(Integer.parseInt((
                            (Long) content.get("numberOfYears")).toString()),
                    Double.parseDouble(((Long) content.get("santaBudget")).toString()),
                    Helpers.getJsonChildren((JSONArray) (initialData.get("children"))),
                    Helpers.getJsonGifts((JSONArray) (initialData.get("santaGiftsList"))),
                    changes);

            // Output-ul va fi stocat intr-un JSONArray.
            JSONArray output = new JSONArray();
            santa.simulate(0, output);
            // Dupa ce simularea a luat sfarsit output-ul este adaugat intr-un JSONObject.
            JSONObject result = new JSONObject();
            result.put("annualChildren", output);
            // JSONObject-ul este afisat in fisierul de output.
            FileWriter writer = new FileWriter(outputPath);
            writer.write(result.toJSONString());
            writer.flush();
            writer.close();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
