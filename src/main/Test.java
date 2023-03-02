package main;

import common.Constants;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clasa folosita pentru rularea unui singur test.
 */
public final class Test {
    private Test() {
    }

    /**
     * Functie care citeste numele unui fisier si, daca il gaseste printre teste, il ruleaza.
     * @param args argumentele folosite la apelarea functiei main
     */
    public static void main(final String[] args) {
        File testsDir = new File(Constants.TESTS);
        File[] tests = testsDir.listFiles();

        if (tests != null) {
            Arrays.sort(tests);
            Scanner scanner = new Scanner(System.in);
            String fileName = scanner.next();
            for (File test : tests) {
                if (test.getName().equalsIgnoreCase(fileName)) {
                    Main.runTest(test.getAbsolutePath(), "out.txt");
                    break;
                }
            }
        }
    }
}
