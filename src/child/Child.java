package child;

import common.Constants;
import enums.Category;
import enums.Cities;
import enums.ElvesType;
import gift.Gift;
import observers.OutputMessage;
import org.json.simple.JSONArray;
import strategies.AverageScoreStrategy;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Map;

/**
 * Clasa care stocheaza informatiile despre un copil. Clasa este observable pentru a fi
 * folosita de catre observer pentru a genera output-ul.
 */
public final class Child extends Observable {
    private int id;
    private String lastName;
    private String firstName;
    private int age;
    private Cities city;
    private List<Double> niceScores;
    private List<Category> giftsPreferences;
    private Double niceScoreBonus;
    private ElvesType elf;

    private Double currentNiceScore;
    private Double assignedBudget;
    private List<Gift> receivedGifts;
    private Map<Cities, Double> niceScoreCity;

    public Child(final int id, final String lastName, final String firstName, final int age,
                 final Cities city, final double niceScore, final List<Category> giftsPreference,
                 final Double niceScoreBonus, final ElvesType elf) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        niceScores = new ArrayList<>();
        niceScores.add(niceScore);
        this.giftsPreferences = giftsPreference;
        this.niceScoreBonus = niceScoreBonus;
        this.elf = elf;
        this.receivedGifts = new ArrayList<>();
        addObserver(new OutputMessage());
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getAge() {
        return age;
    }

    public Cities getCity() {
        return city;
    }

    public List<Double> getNiceScores() {
        return niceScores;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public Double getCurrentNiceScore() {
        return currentNiceScore;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }

    public Map<Cities, Double> getNiceScoreCity() {
        return niceScoreCity;
    }

    public void setNiceScoreCity(final Map<Cities, Double> niceScoreCity) {
        this.niceScoreCity = niceScoreCity;
    }

    /**
     * Functie care adauga un nou scor in lista de scoruri.
     * @param score scorul ce trebuie adaugat
     */
    public void addNiceScore(final Double score) {
        niceScores.add(score);
    }

    /**
     * Functie care adauga un cadou in lista de cadouri primite.
     * @param gift cadoul primit
     */
    public void addGift(final Gift gift) {
        receivedGifts.add(gift);
    }

    /**
     * Functie care goleste lista de cadouri primite.
     */
    public void resetGifts() {
        receivedGifts.clear();
    }

    /**
     * Functie care actualizeaza scorul de cumintenie in functie de strategia primita.
     * @param strategy strategia folosita in actualizarea scorului
     */
    public void updateNiceScore(final AverageScoreStrategy strategy) {
        currentNiceScore = strategy.getAverageScore(niceScores);
        currentNiceScore += currentNiceScore * niceScoreBonus / Constants.PERCENT;
        if (currentNiceScore > Constants.PERFECT_SCORE) {
            currentNiceScore = Constants.PERFECT_SCORE;
        }
    }

    /**
     * Functie care trimite o notificare catre observer.
     * @param array array-ul folosit pentru output
     */
    public void notifyChange(final JSONArray array) {
        setChanged();
        notifyObservers(array);
    }

    /**
     * Functie care incrementeaza varsta copilului.
     */
    public void increaseAge() {
        age++;
    }

    /**
     * Functie care adauga in lista de preferinte categoriile din lista primita, cu conditia
     * sa fie adaugate la inceput.
     * @param preferences lista cu noile categorii
     */
    public void addPreferences(final List<Category> preferences) {
        giftsPreferences.removeIf(preferences::contains);
        List<Category> newPreferences = new ArrayList<>();
        newPreferences.addAll(preferences);
        newPreferences.addAll(giftsPreferences);
        giftsPreferences = newPreferences;
    }
}
