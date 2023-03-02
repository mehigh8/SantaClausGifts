package comparators;

import child.Child;

import java.util.Comparator;

/**
 * Comparator folosit pentru sortarea copiilor in functie de scorul de cumintenie,
 * respectiv in functie de id.
 */
public final class ChildNiceScoreComparator implements Comparator<Child> {
    @Override
    public int compare(final Child o1, final Child o2) {
        int res = Double.compare(o2.getCurrentNiceScore(), o1.getCurrentNiceScore());
        if (res == 0) {
            return Integer.compare(o1.getId(), o2.getId());
        }
        return res;
    }
}
