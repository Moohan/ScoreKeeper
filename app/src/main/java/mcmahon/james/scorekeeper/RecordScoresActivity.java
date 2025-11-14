package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordScoresActivity extends AppCompatActivity {

    private RecordScoresViewModel viewModel;
    private List<EditText> currentRoundEditTexts = new ArrayList<>();
    private List<TextView> sumTextViews = new ArrayList<>();

    /**
     * Initializes the activity: inflates the layout, prepares the ViewModel and player data, builds the player header,
     * sum row and initial score row, and starts observing score sums to update the UI.
     *
     * @param savedInstanceState system-supplied state bundle from a previous instance, or null if none
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_scores);

        viewModel = new ViewModelProvider(this).get(RecordScoresViewModel.class);

        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);
        boolean customNames = intent.getBooleanExtra(SelectNumPlayersActivity.EXTRA_CUSTOM_NAMES, false);
        String[] playerNames = customNames ? intent.getStringArrayExtra(CustomNamePlayersActivity.EXTRA_PLAYER_NAMES) : null;

        if (viewModel.getPlayerNames().getValue() == null) {
            viewModel.init(numberOfPlayers, playerNames);
        }

        setupPlayerHeader();
        setupSumRow();
        addNewScoreRow();

        viewModel.getScoreSums().observe(this, sums -> {
            DecimalFormat decimalFormat = new DecimalFormat("0.###");
            for (int i = 0; i < sums.size(); i++) {
                sumTextViews.get(i).setText(decimalFormat.format(sums.get(i)));
            }
        });
    }

    /**
     * Populates the header table row with a TextView for each player name provided by the ViewModel.
     *
     * If no player names are available, the header row is left unchanged.
     */
    private void setupPlayerHeader() {
        TableRow tableHeaderRow = findViewById(R.id.player_score_table_header_row);
        List<String> playerNames = viewModel.getPlayerNames().getValue();
        if (playerNames != null) {
            for (String playerName : playerNames) {
                TextView textView = new TextView(this);
                textView.setText(playerName);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                tableHeaderRow.addView(textView);
            }
        }
    }

    /**
     * Creates and inserts the per-player sum TextViews into the sum table row and initializes them to "0".
     *
     * Each created TextView is centered horizontally, uses a bold typeface, and is retained in {@code sumTextViews}
     * for later updates when score sums change.
     */
    private void setupSumRow() {
        TableRow tableSumRow = findViewById(R.id.player_score_table_sum_row);
        for (int i = 0; i < viewModel.getNumberOfPlayers(); i++) {
            TextView textView = new TextView(this);
            textView.setText("0");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            sumTextViews.add(textView);
            tableSumRow.addView(textView);
        }
    }

    /**
     * Appends a new score input row to the player scores table and prepares it for the next round.
     *
     * Each player receives a centered EditText configured for signed decimal numbers with a "0" hint;
     * IME Next is used for all but the last player which uses IME Done. The created EditTexts are
     * stored in `currentRoundEditTexts`. The new TableRow is inserted just before the table's last two rows.
     */
    private void addNewScoreRow() {
        TableLayout scoresTable = findViewById(R.id.player_score_table);
        TableRow tableRow = new TableRow(this);
        currentRoundEditTexts.clear();

        for (int n = 0; n < viewModel.getNumberOfPlayers(); n++) {
            EditText editTextScore = new EditText(this);
            editTextScore.setHint("0");
            editTextScore.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editTextScore.setGravity(Gravity.CENTER_HORIZONTAL);
            editTextScore.setImeOptions(n == viewModel.getNumberOfPlayers() - 1 ? EditorInfo.IME_ACTION_DONE : EditorInfo.IME_ACTION_NEXT);
            currentRoundEditTexts.add(editTextScore);
            tableRow.addView(editTextScore);
        }
        scoresTable.addView(tableRow, scoresTable.getChildCount() - 2);
    }

    /**
     * Finalizes the current scoring round by collecting current inputs, submitting them to the ViewModel, and preparing a new empty row.
     *
     * Disables each EditText in the current round, treats empty or non-numeric inputs as 0, passes the list of parsed scores to the ViewModel via endRound(...), and appends a fresh score entry row.
     */
    public final void endRound(View view) {
        List<Double> roundScores = new ArrayList<>();
        for (EditText editText : currentRoundEditTexts) {
            editText.setEnabled(false);
            double score = 0;
            if (!editText.getText().toString().isEmpty()) {
                try {
                    score = Double.parseDouble(editText.getText().toString());
                } catch (NumberFormatException e) {
                    // Keep score as 0 if input is invalid
                }
            }
            roundScores.add(score);
        }

        viewModel.endRound(roundScores);
        addNewScoreRow();
    }
}