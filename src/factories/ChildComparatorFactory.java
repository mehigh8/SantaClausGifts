package factories;

import child.Child;
import comparators.ChildIdComparator;
import comparators.ChildNiceScoreCityComparator;
import comparators.ChildNiceScoreComparator;
import enums.CityStrategyEnum;

import java.util.Comparator;

/**
 * Clasa factory pentru comparatoare folosite la modul impartirii cadourilor.
 */
public final class ChildComparatorFactory {
    private static ChildComparatorFactory instance = null;
    private ChildComparatorFactory() {
    }

    /**
     * Functie care returneaza instanta factory-ului.
     * @return instanta
     */
    public static ChildComparatorFactory getInstance() {
        if (instance == null) {
            instance = new ChildComparatorFactory();
        }
        return instance;
    }

    /**
     * Functie care genereaza un comparator corespunzator strategiei primite (enum).
     * @param cityStrategy strategia
     * @return comparatorul
     */
    public Comparator<Child> createComparator(final CityStrategyEnum cityStrategy) {
        return switch (cityStrategy) {
            case NICE_SCORE_CITY -> new ChildNiceScoreCityComparator();
            case ID -> new ChildIdComparator();
            case NICE_SCORE -> new ChildNiceScoreComparator();
        };
    }
}
