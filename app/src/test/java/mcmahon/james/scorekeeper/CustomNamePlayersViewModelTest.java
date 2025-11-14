package mcmahon.james.scorekeeper;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomNamePlayersViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CustomNamePlayersViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new CustomNamePlayersViewModel();
    }

    @Test
    public void testInit() {
        viewModel.init(3);
        assertEquals(3, viewModel.getNumberOfPlayers());
        assertEquals(3, viewModel.getPlayerNames().getValue().size());
        assertEquals("", viewModel.getPlayerNames().getValue().get(0));
    }

    @Test
    public void testSetPlayerName() {
        viewModel.init(2);
        viewModel.setPlayerName(0, "Player 1");
        assertEquals("Player 1", viewModel.getPlayerNames().getValue().get(0));
    }
}
