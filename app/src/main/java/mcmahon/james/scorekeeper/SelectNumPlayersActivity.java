package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SelectNumPlayersActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYERS = "mcmahon.james.scorekeeper.PLAYERS";
    public static final String EXTRA_CUSTOM_NAMES = "mcmahon.james.scorekeeper.CUSTOM_NAMES";

    private SelectNumPlayersViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_num_players);

        viewModel = new ViewModelProvider(this).get(SelectNumPlayersViewModel.class);

        NumberPicker numberPicker = findViewById(R.id.playerSelector);
        numberPicker.setMinValue(2);
        numberPicker.setMaxValue(6);
        numberPicker.setWrapSelectorWheel(false);

        viewModel.getNumberOfPlayers().observe(this, numberPicker::setValue);

        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> viewModel.setNumberOfPlayers(newVal));
    }

    public final void quickAcceptPlayers(View view) {
        Intent intent = new Intent(this, RecordScoresActivity.class);
        intent.putExtra(EXTRA_PLAYERS, viewModel.getNumberOfPlayers().getValue());
        intent.putExtra(EXTRA_CUSTOM_NAMES, false);
        startActivity(intent);
    }

    public final void namePlayers(View view) {
        Intent intent = new Intent(this, CustomNamePlayersActivity.class);
        intent.putExtra(EXTRA_PLAYERS, viewModel.getNumberOfPlayers().getValue());
        startActivity(intent);
    }
}
