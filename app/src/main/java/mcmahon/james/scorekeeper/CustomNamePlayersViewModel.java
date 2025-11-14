package mcmahon.james.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomNamePlayersViewModel extends ViewModel {

    private MutableLiveData<List<String>> playerNames = new MutableLiveData<>();
    private int numberOfPlayers;

    public void init(int numPlayers) {
        numberOfPlayers = numPlayers;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            names.add("");
        }
        playerNames.setValue(names);
    }

    public LiveData<List<String>> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerName(int index, String name) {
        if (playerNames.getValue() != null && index < playerNames.getValue().size()) {
            playerNames.getValue().set(index, name);
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
