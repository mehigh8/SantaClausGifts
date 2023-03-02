package comparators;

import child.Child;

import java.util.Comparator;

/**
 * Comparator folosit pentru sortarea copiilor in functie de id.
 */
public final class ChildIdComparator implements Comparator<Child> {
    @Override
    public int compare(final Child o1, final Child o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
