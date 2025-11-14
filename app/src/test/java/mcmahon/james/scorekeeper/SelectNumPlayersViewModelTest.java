package mcmahon.james.scorekeeper;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class SelectNumPlayersViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private SelectNumPlayersViewModel viewModel;

    @Mock
    private Observer<Integer> numberOfPlayersObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new SelectNumPlayersViewModel();
        viewModel.getNumberOfPlayers().observeForever(numberOfPlayersObserver);
    }

    @Test
    public void testInitialValue() {
        verify(numberOfPlayersObserver).onChanged(2);
    }

    @Test
    public void testSetNumberOfPlayers() {
        viewModel.setNumberOfPlayers(4);
        verify(numberOfPlayersObserver).onChanged(4);
    }
}
