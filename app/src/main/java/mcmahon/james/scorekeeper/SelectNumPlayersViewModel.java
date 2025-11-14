package mcmahon.james.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectNumPlayersViewModel extends ViewModel {

    private final MutableLiveData<Integer> numberOfPlayers = new MutableLiveData<>(2);

    /**
     * Exposes the currently selected number of players as observable LiveData.
     *
     * @return the LiveData wrapping the currently selected number of players
     */
    public LiveData<Integer> getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Sets the selected number of players.
     *
     * @param number the new number of players to use in the view model
     */
    public void setNumberOfPlayers(int number) {
        numberOfPlayers.setValue(number);
    }
}