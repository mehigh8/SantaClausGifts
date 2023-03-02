package observers;

import child.Child;
import common.Helpers;
import enums.Category;
import gift.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Observable;
import java.util.Observer;

/**
 * Clasa care implementeaza interfata Observer. Este folosita pentru a adauga in output
 * schimbarile fiecarui copil.
 */
public final class OutputMessage implements Observer {
    public OutputMessage() {
    }
    @Override
    public void update(final Observable o, final Object arg) {
        // Crearea JSONObject-ului ce va fi folosit in output si adaugarea tuturor
        // informatiilor copilului (Observable o).
        JSONObject result = new JSONObject();
        result.put("id", ((Child) o).getId());
        result.put("lastName", ((Child) o).getLastName());
        result.put("firstName", ((Child) o).getFirstName());
        result.put("city", Helpers.cityToString(((Child) o).getCity()));
        result.put("age", ((Child) o).getAge());
        JSONArray preferences = new JSONArray();
        for (Category category : ((Child) o).getGiftsPreferences()) {
            preferences.add(Helpers.categoryToString(category));
        }
        result.put("giftsPreferences", preferences);
        result.put("averageScore", ((Child) o).getCurrentNiceScore());
        JSONArray scores = new JSONArray();
        for (Double score : ((Child) o).getNiceScores()) {
            scores.add(score);
        }
        result.put("niceScoreHistory", scores);
        result.put("assignedBudget", ((Child) o).getAssignedBudget());
        JSONArray receivedGifts = new JSONArray();
        for (Gift gift : ((Child) o).getReceivedGifts()) {
            JSONObject receivedGift = new JSONObject();
            receivedGift.put("productName", gift.getProductName());
            receivedGift.put("price", gift.getPrice());
            receivedGift.put("category", Helpers.categoryToString(gift.getCategory()));
            receivedGifts.add(receivedGift);
        }
        result.put("receivedGifts", receivedGifts);
        // Dupa ce sunt adaugate toate informatiile, JSONObject-ul este adaugat in
        // JSONArray-ul folosit pentru output (arg).
        ((JSONArray) arg).add(result);
    }
}
