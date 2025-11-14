package mcmahon.james.scorekeeper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectNumPlayersViewModel extends ViewModel {

    private final MutableLiveData<Integer> numberOfPlayers = new MutableLiveData<>(2);

    public LiveData<Integer> getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int number) {
        numberOfPlayers.setValue(number);
    }
}
