package santa;

import child.Child;
import child.ChildUpdate;
import common.Constants;
import comparators.ChildIdComparator;
import comparators.GiftPriceComparator;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import enums.ElvesType;
import factories.ChildComparatorFactory;
import gift.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import factories.AverageScoreStrategyFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public final class Santa {
    private int numberOfYears;
    private Double santaBudget;
    private List<Child> children;
    private List<Gift> gifts;
    private List<Change> annualChanges;

    private Double budgetUnit;
    private CityStrategyEnum cityStrategy;

    public Santa(final int numberOfYears, final Double santaBudget, final List<Child> children,
                 final List<Gift> gifts, final List<Change> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.children = new ArrayList<>();
        this.children.addAll(children);
        this.gifts = gifts;
        this.annualChanges = annualChanges;
        cityStrategy = CityStrategyEnum.ID;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public List<Change> getAnnualChanges() {
        return annualChanges;
    }

    /**
     * Functie care simuleaza un an pentru Mos Craciun.
     * @param year anul simulat
     * @param output JSONArray-ul in care este stocat output-ul
     */
    public void simulate(final int year, final JSONArray output) {
        AverageScoreStrategyFactory scoreFactory = AverageScoreStrategyFactory.getInstance();
        ChildComparatorFactory comparatorFactory = ChildComparatorFactory.getInstance();
        // Sunt stersi din lista copiii care au mai mult de 18 ani.
        children.removeIf((child) -> child.getAge() > Constants.ADULT_AGE);
        // Copiii sunt sortati in functie de id, pentru calcularea bugeturilor.
        children.sort(new ChildIdComparator());
        Double scoreSum = 0d;
        for (Child child : children) {
            // Este actualizat scorul de cumintenie al fiecarui copil
            // folosind factory-ul de strategii.
            child.updateNiceScore(scoreFactory.createStrategy(child.getAge()));
            scoreSum += child.getCurrentNiceScore();
        }
        // Este calculat budget unit-ul lui Mos Craciun.
        budgetUnit = santaBudget / scoreSum;
        // Daca strategia de impartire a cadourilor este in functie de scorul de cumintenie al
        // orasului, atunci vor trebui calculate scorurile de cumintenie al oraselor.
        if (cityStrategy == CityStrategyEnum.NICE_SCORE_CITY) {
            Map<Cities, Double> niceScoreCity = new HashMap<>();
            for (Cities city : Cities.values()) {
                Double sum = 0d;
                int counter = 0;
                for (Child child : children) {
                    if (child.getCity() == city) {
                        sum += child.getCurrentNiceScore();
                        counter++;
                    }
                }
                // Daca nu exista copii intr-un oras, acel oras va avea scorul 0.
                if (counter == 0) {
                    niceScoreCity.put(city, 0d);
                } else {
                    niceScoreCity.put(city, sum / counter);
                }
            }
            // Se actualizeaza scorurile oraselor pentru fiecare copil.
            children.stream().forEach((child) -> child.setNiceScoreCity(niceScoreCity));
        }
        // Copiii sunt sortati dupa strategia aleasa.
        children.sort(comparatorFactory.createComparator(cityStrategy));

        JSONArray childrenArray = new JSONArray();
        for (Child child : children) {
            // Fiecarui copil ii este asignat bugetul ce poate fi folosit de Mos Craciun
            // pentru a-i cumpara cadouri.
            Double currentChildBudget = budgetUnit * child.getCurrentNiceScore();
            // Se aplica modificarile bugetului in cazul in care copilul are elf black sau pink.
            if (child.getElf() == ElvesType.BLACK) {
                currentChildBudget -= currentChildBudget * Constants.THIRTY / Constants.PERCENT;
            }
            if (child.getElf() == ElvesType.PINK) {
                currentChildBudget += currentChildBudget * Constants.THIRTY / Constants.PERCENT;
            }
            child.setAssignedBudget(currentChildBudget);
            // Sunt sterse cadourile din anul precedent (daca este cazul).
            child.resetGifts();
            // Pentru fiecare dintre categoriile de cadouri din lista de preferinte ale copilului
            // se cauta cadourile care au aceeasi categorie in lista de cadouri ale mosului.
            for (Category category : child.getGiftsPreferences()) {
                List<Gift> currentGifts = new ArrayList<>();
                for (Gift gift : gifts) {
                    if (gift.getCategory() == category && gift.getQuantity() > 0) {
                        currentGifts.add(gift);
                    }
                }
                if (currentGifts.isEmpty()) {
                    continue;
                }
                // Cadourile sunt sortate in functie de pret.
                currentGifts.sort(new GiftPriceComparator());
                // Se alege cel mai ieftin (daca se incadreaza in buget).
                if (Double.compare(currentGifts.get(0).getPrice(), currentChildBudget) <= 0) {
                    child.addGift(currentGifts.get(0));
                    currentChildBudget -= currentGifts.get(0).getPrice();
                    currentGifts.get(0).decreaseQuantity();
                }
            }
        }
        // Copiii sunt sortati in functie de id pentru elful yellow.
        children.sort(new ChildIdComparator());
        for (Child child : children) {
            // Verific pentru fiecare copil daca elful lui este yellow
            // si daca nu a primit niciu cadou.
            if (child.getElf() == ElvesType.YELLOW && child.getReceivedGifts().size() == 0) {
                // Caut cel mai ieftin cadou din categoria preferata a copilului.
                Category preferred = child.getGiftsPreferences().get(0);
                Gift currentGift = null;
                Double minPrice = Double.MAX_VALUE;
                for (Gift gift : gifts) {
                    if (gift.getCategory() == preferred && gift.getPrice() < minPrice) {
                        currentGift = gift;
                        minPrice = gift.getPrice();
                    }
                }
                // Daca a fost gasit un cadou care sa aiba o cantitate mai mare de 0,
                // acesta va fi dat copilului.
                if (currentGift != null) {
                    if (currentGift.getQuantity() > 0) {
                        child.addGift(currentGift);
                        currentGift.decreaseQuantity();
                    }
                }
            }
            // Este notificat observer-ul copilului.
            child.notifyChange(childrenArray);
        }
        JSONObject result = new JSONObject();
        result.put("children", childrenArray);
        output.add(result);
        // Daca s-au parcurs anii necesari simularea ia sfarsit.
        if (year == numberOfYears) {
            return;
        }
        // Daca nu, se incrementeaza varsta copiilor.
        for (Child child : children) {
            child.increaseAge();
        }
        // Se aplica schimbarile corespunzatoare anului.
        Change change = annualChanges.get(year);
        santaBudget = change.getNewSantaBudget();
        gifts.addAll(change.getNewGifts());
        children.addAll(change.getNewChildren());

        for (ChildUpdate childUpdate : change.getChildrenUpdates()) {
            for (Child child : children) {
                if (child.getId() == childUpdate.getId()) {
                    // Daca noul scor este -1 inseamna ca nu trebuie adaugat.
                    if (childUpdate.getNiceScore() != -1) {
                        child.addNiceScore(childUpdate.getNiceScore());
                    }
                    // Sunt sterse duplicatele din lista de categorii.
                    childUpdate.removePreferencesDuplicates();
                    for (int i = 0; i < childUpdate.getGiftsPreferences().size(); i++) {
                        child.addPreferences(childUpdate.getGiftsPreferences());
                    }
                    child.setElf(childUpdate.getElf());
                }
            }
        }
        cityStrategy = change.getCityStrategy();
        // Se reapeleaza functia pentru anul urmator.
        simulate(year + 1, output);
    }
}
