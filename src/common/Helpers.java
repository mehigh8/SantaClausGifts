package common;

import child.Child;
import child.ChildUpdate;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import enums.ElvesType;
import gift.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa care contine functii ajutatoare pentru citirea input-ului
 * si pentru transformarea strin-urilor in enum-uri si invers.
 */
public final class Helpers {
    private Helpers() {
    }
    /**
     * Functie care transforma un string intr-un elf (enum).
     * @param elf string-ul ce trebuie transformat
     * @return elful corespunzator string-ului
     */
    public static ElvesType stringToElf(final String elf) {
        return switch (elf) {
            case "white" -> ElvesType.WHITE;
            case "yellow" -> ElvesType.YELLOW;
            case "black" -> ElvesType.BLACK;
            case "pink" -> ElvesType.PINK;
            default -> null;
        };
    }

    /**
     * Functie care transforma un string intr-o strategie (enum).
     * @param cityStrategy string-ul ce trebuie transformat
     * @return strategia corespunzatoare string-ului
     */
    public static CityStrategyEnum stringToCityStrategy(final String cityStrategy) {
        return switch (cityStrategy) {
            case "id" -> CityStrategyEnum.ID;
            case "niceScore" -> CityStrategyEnum.NICE_SCORE;
            case "niceScoreCity" -> CityStrategyEnum.NICE_SCORE_CITY;
            default -> null;
        };
    }

    /**
     * Functie care transforma un string intr-o categorie (enum).
     * @param category string-ul ce trebuie transformat
     * @return categoria corespunzatoare string-ului
     */
    public static Category stringToCategory(final String category) {
        return switch (category) {
            case "Board Games" -> Category.BOARD_GAMES;
            case "Books" -> Category.BOOKS;
            case "Clothes" -> Category.CLOTHES;
            case "Sweets" -> Category.SWEETS;
            case "Technology" -> Category.TECHNOLOGY;
            case "Toys" -> Category.TOYS;
            default -> null;
        };
    }

    /**
     * Functie care transforma o categorie (enum) intr-un string.
     * @param category categoria ce trebuie transformata
     * @return string-ul obtinut
     */
    public static String categoryToString(final Category category) {
        return switch (category) {
            case BOARD_GAMES -> "Board Games";
            case BOOKS -> "Books";
            case CLOTHES -> "Clothes";
            case SWEETS -> "Sweets";
            case TECHNOLOGY -> "Technology";
            case TOYS -> "Toys";
        };
    }

    /**
     * Functie care transforma un string intr-un oras (enum).
     * @param city string-ul ce trebuie transformat
     * @return orasul corespunzator string-ului
     */
    public static Cities stringToCity(final String city) {
        return switch (city) {
            case "Bucuresti" -> Cities.BUCURESTI;
            case "Constanta" -> Cities.CONSTANTA;
            case "Buzau" -> Cities.BUZAU;
            case "Timisoara" -> Cities.TIMISOARA;
            case "Cluj-Napoca" -> Cities.CLUJ;
            case "Iasi" -> Cities.IASI;
            case "Craiova" -> Cities.CRAIOVA;
            case "Brasov" -> Cities.BRASOV;
            case "Braila" -> Cities.BRAILA;
            case "Oradea" -> Cities.ORADEA;
            default -> null;
        };
    }

    /**
     * Functie care transforma un oras (enum) intr-un string.
     * @param city orasul ce trebuie transformat
     * @return string-ul obtinut
     */
    public static String cityToString(final Cities city) {
        return switch (city) {
            case BUCURESTI -> "Bucuresti";
            case CONSTANTA -> "Constanta";
            case BUZAU -> "Buzau";
            case TIMISOARA -> "Timisoara";
            case CLUJ -> "Cluj-Napoca";
            case IASI -> "Iasi";
            case CRAIOVA -> "Craiova";
            case BRASOV -> "Brasov";
            case BRAILA -> "Braila";
            case ORADEA -> "Oradea";
        };
    }

    /**
     * Functie care preia categoriile dintr-un JSONArray sub forma unei liste.
     * @param jsonPreferences JSONArray-ul din care este preluata informatia
     * @return lista de categorii obtinuta
     */
    public static List<Category> getJsonPreferences(final JSONArray jsonPreferences) {
        List<Category> preferences = new ArrayList<>();
        for (Object jsonCategory : jsonPreferences) {
            preferences.add(stringToCategory((String) jsonCategory));
        }
        return preferences;
    }

    /**
     * Functie care preia cadourile dintr-un JSONArray sub forma unei liste.
     * @param jsonGifts JSONArray-ul din care este preluata informatia
     * @return lista de cadouri obtinuta
     */
    public static List<Gift> getJsonGifts(final JSONArray jsonGifts) {
        List<Gift> gifts = new ArrayList<>();
        for (Object jsonGift : jsonGifts) {
            gifts.add(new Gift((String) ((JSONObject) jsonGift).get("productName"),
                    Double.parseDouble(((Long) ((JSONObject) jsonGift).get("price")).toString()),
                    stringToCategory((String) ((JSONObject) jsonGift).get("category")),
                    Integer.parseInt(
                            ((Long) ((JSONObject) jsonGift).get("quantity")).toString())));
        }
        return gifts;
    }

    /**
     * Functie care preia copiii dintr-un JSONArray sub forma unei liste.
     * @param jsonChildren JSONArray-ul din care este preluata informatia
     * @return lista de copii obtinuta
     */
    public static List<Child> getJsonChildren(final JSONArray jsonChildren) {
        List<Child> children = new ArrayList<>();
        for (Object jsonChild: jsonChildren) {
            children.add(new Child(Integer.parseInt((
                            (Long) ((JSONObject) jsonChild).get("id")).toString()),
                    (String) ((JSONObject) jsonChild).get("lastName"),
                    (String) ((JSONObject) jsonChild).get("firstName"),
                    Integer.parseInt(((Long) ((JSONObject) jsonChild).get("age")).toString()),
                    stringToCity((String) ((JSONObject) jsonChild).get("city")),
                    Double.parseDouble((
                            (Long) ((JSONObject) jsonChild).get("niceScore")).toString()),
                    getJsonPreferences(
                            (JSONArray) ((JSONObject) jsonChild).get("giftsPreferences")),
                    Double.parseDouble(
                            ((Long) ((JSONObject) jsonChild).get("niceScoreBonus")).toString()),
                    stringToElf((String) ((JSONObject) jsonChild).get("elf"))));
        }
        return children;
    }

    /**
     * Functie care preia child update-urile dintr-un JSONArray sub forma unei liste.
     * @param jsonUpdates JSONArray-ul din care este preluata informatia
     * @return lista de child update-uri obtinuta
     */
    public static List<ChildUpdate> getJsonUpdates(final JSONArray jsonUpdates) {
        List<ChildUpdate> childUpdates = new ArrayList<>();
        for (Object jsonUpdate : jsonUpdates) {
            childUpdates.add(new ChildUpdate(Integer.parseInt((
                    (Long) ((JSONObject) jsonUpdate).get("id")).toString()),
                    // Daca niceScore-ul preluat este null, in lista se va adauga -1.
                    Double.parseDouble(((Long) (((JSONObject) jsonUpdate).get("niceScore") == null
                            ? -1L : ((JSONObject) jsonUpdate).get("niceScore"))).toString()),
                    getJsonPreferences(
                            (JSONArray) ((JSONObject) jsonUpdate).get("giftsPreferences")),
                    stringToElf((String) ((JSONObject) jsonUpdate).get("elf"))));
        }
        return childUpdates;
    }
}
