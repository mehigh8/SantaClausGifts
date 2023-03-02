package comparators;

import child.Child;
import common.Helpers;

import java.util.Comparator;

/**
 * Comparator folosit pentru sortarea copiilor in functie de scorul de cumintenie al
 * orasului din care fac parte.
 */
public final class ChildNiceScoreCityComparator implements Comparator<Child> {
    @Override
    public int compare(final Child o1, final Child o2) {
        int res = Double.compare(o2.getNiceScoreCity().get(o2.getCity()),
                o1.getNiceScoreCity().get(o1.getCity()));
        if (res == 0) {
            int res2 = Helpers.cityToString(o1.getCity())
                    .compareTo(Helpers.cityToString(o2.getCity()));
            if (res2 == 0) {
                return Integer.compare(o1.getId(), o2.getId());
            }
            return res2;
        }
        return res;
    }
}
