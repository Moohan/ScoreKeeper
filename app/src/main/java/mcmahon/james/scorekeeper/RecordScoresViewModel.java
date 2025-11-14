package mcmahon.james.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecordScoresViewModel extends ViewModel {

    private MutableLiveData<List<String>> playerNames = new MutableLiveData<>();
    private MutableLiveData<List<List<Double>>> scores = new MutableLiveData<>();
    private MutableLiveData<List<Double>> scoreSums = new MutableLiveData<>();
    private int numberOfPlayers;
    private int rounds = 0;

    /**
     * Initializes the view model for a game with the specified number of players and optional custom names.
     *
     * If `customNames` is non-null its entries are used as player names; otherwise names "Player 1" … "Player N" are generated.
     * This method sets the numberOfPlayers field, updates playerNames, initializes each player's score list to an empty list,
     * and initializes each player's cumulative sum to 0.0.
     *
     * @param numPlayers   the total number of players to initialize
     * @param customNames  an array of player names to use, or `null` to generate default names
     */
    public void init(int numPlayers, String[] customNames) {
        numberOfPlayers = numPlayers;
        List<String> names = new ArrayList<>();
        if (customNames != null) {
            for (String name : customNames) {
                names.add(name);
            }
        } else {
            for (int i = 1; i <= numberOfPlayers; i++) {
                names.add("Player " + i);
            }
        }
        playerNames.setValue(names);

        List<List<Double>> initialScores = new ArrayList<>();
        List<Double> initialSums = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            initialScores.add(new ArrayList<>());
            initialSums.add(0.0);
        }
        scores.setValue(initialScores);
        scoreSums.setValue(initialSums);
    }

    /**
     * Exposes the current list of player names.
     *
     * @return the LiveData containing the current list of player names
     */
    public LiveData<List<String>> getPlayerNames() {
        return playerNames;
    }

    /**
     * Provide access to the per-player scores collected each round.
     *
     * @return the LiveData holding, for each player, a list of that player's scores by round
     */
    public LiveData<List<List<Double>>> getScores() {
        return scores;
    }

    /**
     * Provide access to the cumulative scores for each player.
     *
     * @return a LiveData wrapping a list where element i is the total score for player i
     */
    public LiveData<List<Double>> getScoreSums() {
        return scoreSums;
    }

    /**
     * Retrieve the configured number of players.
     *
     * @return the total number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Returns the current count of completed rounds.
     *
     * @return the number of completed rounds
     */
    public int getRounds() {
        return rounds;
    }

    /**
     * Appends a single round of scores to each player's score history and updates their cumulative sums and the round counter.
     *
     * If the ViewModel's internal score lists or sums are not initialized, the method does nothing.
     *
     * @param roundScores a list of per-player scores for the round; element i is the score for player i and will be appended to that player's score list and added to their cumulative sum
     */
    public void endRound(List<Double> roundScores) {
        List<List<Double>> currentScores = scores.getValue();
        List<Double> currentSums = scoreSums.getValue();

        if (currentScores != null && currentSums != null) {
            for (int i = 0; i < numberOfPlayers; i++) {
                currentScores.get(i).add(roundScores.get(i));
                currentSums.set(i, currentSums.get(i) + roundScores.get(i));
            }
            scores.setValue(currentScores);
            scoreSums.setValue(currentSums);
            rounds++;
        }
    }
}