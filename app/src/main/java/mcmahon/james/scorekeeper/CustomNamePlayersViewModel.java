package mcmahon.james.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomNamePlayersViewModel extends ViewModel {

    private MutableLiveData<List<String>> playerNames = new MutableLiveData<>();
    private int numberOfPlayers;

    /**
     * Initialize the view model for a given number of players.
     *
     * Sets the internal player count and updates the internal LiveData with a List of
     * empty player name strings sized to match the specified count.
     *
     * @param numPlayers the number of players to initialize (zero creates an empty list)
     */
    public void init(int numPlayers) {
        numberOfPlayers = numPlayers;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            names.add("");
        }
        playerNames.setValue(names);
    }

    /**
     * Exposes the LiveData holding the list of player names.
     *
     * @return the LiveData containing the current list of player names, or `null` if the view model has not been initialized
     */
    public LiveData<List<String>> getPlayerNames() {
        return playerNames;
    }

    /**
     * Updates the stored player name at the given position when the backing list exists and the index is valid.
     *
     * @param index the zero-based position of the player name to update
     * @param name  the new name to set for the specified player
     */
    public void setPlayerName(int index, String name) {
        if (playerNames.getValue() != null && index < playerNames.getValue().size()) {
            playerNames.getValue().set(index, name);
        }
    }

    /**
     * Provides the configured number of players for this ViewModel.
     *
     * @return the number of players configured for this ViewModel
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}