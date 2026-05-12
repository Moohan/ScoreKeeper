package mcmahon.james.scorekeeper;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;

public class RecordScoresViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RecordScoresViewModel viewModel;

    @Mock
    private Observer<List<Double>> scoreSumsObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new RecordScoresViewModel();
        viewModel.getScoreSums().observeForever(scoreSumsObserver);
    }

    @Test
    public void testInit() {
        viewModel.init(2, new String[]{"Player 1", "Player 2"});
        assert viewModel.getPlayerNames().getValue().size() == 2;
        assert viewModel.getScores().getValue().size() == 2;
        assert viewModel.getScoreSums().getValue().size() == 2;
        assert viewModel.getScoreSums().getValue().get(0) == 0.0;
    }

    @Test
    public void testEndRound() {
        viewModel.init(2, new String[]{"Player 1", "Player 2"});
        viewModel.endRound(Arrays.asList(10.0, 20.0));

        List<Double> expectedSums = Arrays.asList(10.0, 20.0);
        verify(scoreSumsObserver).onChanged(expectedSums);

        assert viewModel.getRounds() == 1;
        assert viewModel.getScores().getValue().get(0).get(0) == 10.0;
    }
}
