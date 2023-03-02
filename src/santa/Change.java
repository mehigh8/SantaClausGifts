package santa;

import child.Child;
import child.ChildUpdate;
import enums.CityStrategyEnum;
import gift.Gift;

import java.util.List;

/**
 * Clasa care stocheaza informatiile unui annual change.
 */
public final class Change {
    private Double newSantaBudget;
    private List<Gift> newGifts;
    private List<Child> newChildren;
    private List<ChildUpdate> childrenUpdates;
    private CityStrategyEnum cityStrategy;

    public Change(final Double newSantaBudget, final List<Gift> newGifts,
                  final List<Child> newChildren, final List<ChildUpdate> childrenUpdates,
                  final CityStrategyEnum cityStrategy) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
        this.cityStrategy = cityStrategy;
    }

    public Double getNewSantaBudget() {
        return newSantaBudget;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public List<Child> getNewChildren() {
        return newChildren;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

    public CityStrategyEnum getCityStrategy() {
        return cityStrategy;
    }
}
