package comparators;

import gift.Gift;

import java.util.Comparator;

/**
 * Comparator folosit pentru sortarea cadourilor in functie de pret.
 */
public final class GiftPriceComparator implements Comparator<Gift> {
    @Override
    public int compare(final Gift o1, final Gift o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }
}
