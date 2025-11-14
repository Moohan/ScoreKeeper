package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class CustomNamePlayersActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_NAMES = "mcmahon.james.scorekeeper.PLAYER_NAMES";
    private CustomNamePlayersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_name_players);

        viewModel = new ViewModelProvider(this).get(CustomNamePlayersViewModel.class);

        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);

        if (viewModel.getPlayerNames().getValue() == null) {
            viewModel.init(numberOfPlayers);
        }

        TableLayout tableLayout = findViewById(R.id.player_name_table);

        for (int n = 0; n < viewModel.getNumberOfPlayers(); n++) {
            TableRow tableRow = new TableRow(this);
            TextView textViewPlayerNumber = new TextView(this);
            String text = "Player " + (n + 1);
            textViewPlayerNumber.setText(text);
            textViewPlayerNumber.setGravity(Gravity.CENTER_HORIZONTAL);

            EditText editTextPlayerName = new EditText(this);
            editTextPlayerName.setHint(text);
            editTextPlayerName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            editTextPlayerName.setImeOptions(n == viewModel.getNumberOfPlayers() - 1 ? EditorInfo.IME_ACTION_DONE : EditorInfo.IME_ACTION_NEXT);

            final int playerIndex = n;
            editTextPlayerName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    viewModel.setPlayerName(playerIndex, s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            tableRow.addView(textViewPlayerNumber);
            tableRow.addView(editTextPlayerName);
            tableLayout.addView(tableRow);
        }
    }

    public final void acceptPlayers(View view) {
        Intent intent = new Intent(this, RecordScoresActivity.class);
        intent.putExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, viewModel.getNumberOfPlayers());

        String[] playerNames = new String[viewModel.getNumberOfPlayers()];
        for (int i = 0; i < viewModel.getNumberOfPlayers(); i++) {
            String name = viewModel.getPlayerNames().getValue().get(i);
            playerNames[i] = name.isEmpty() ? "Player " + (i + 1) : name;
        }

        intent.putExtra(EXTRA_PLAYER_NAMES, playerNames);
        intent.putExtra(SelectNumPlayersActivity.EXTRA_CUSTOM_NAMES, true);
        startActivity(intent);
    }
}
