package child;

import enums.Category;
import enums.ElvesType;

import java.util.List;

/**
 * Functie care stocheaza informatiile unui child update.
 */
public final class ChildUpdate {
    private int id;
    private Double niceScore;
    private List<Category> giftsPreferences;
    private ElvesType elf;

    public ChildUpdate(final int id, final Double niceScore,
                       final List<Category> giftsPreferences, final ElvesType elf) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
        this.elf = elf;
    }

    public int getId() {
        return id;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public ElvesType getElf() {
        return elf;
    }

    /**
     * Functie care sterge dublurile categoriilor din lista.
     */
    public void removePreferencesDuplicates() {
        for (int i = 0; i < giftsPreferences.size() - 1; i++) {
            for (int j = i + 1; j < giftsPreferences.size(); j++) {
                if (giftsPreferences.get(i) == giftsPreferences.get(j)) {
                    giftsPreferences.remove(j);
                    j--;
                }
            }
        }
    }
}
