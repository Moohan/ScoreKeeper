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

    public LiveData<List<String>> getPlayerNames() {
        return playerNames;
    }

    public LiveData<List<List<Double>>> getScores() {
        return scores;
    }

    public LiveData<List<Double>> getScoreSums() {
        return scoreSums;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getRounds() {
        return rounds;
    }

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
