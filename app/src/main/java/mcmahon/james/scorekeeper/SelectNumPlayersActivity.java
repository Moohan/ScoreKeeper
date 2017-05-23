/*
 * Copyright (c) James McMahon 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.NumberPicker;


public class SelectNumPlayersActivity extends AppCompatActivity {
    public static final String EXTRA_PLAYERS = "mcmahon.james.scorekeeper.PLAYERS";
    public static final String EXTRA_CUSTOM_NAMES = "mcmahon.james.scorekeeper.CUSTOM_NAMES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_select_num_players);

        NumberPicker numberPicker = (NumberPicker) this.findViewById(R.id.playerSelector);

        int Min = 2;
        int Max = 6;
        int startValue = Math.round((Max + Min) / 2);

        numberPicker.setMinValue(Min);
        numberPicker.setMaxValue(Max);
        numberPicker.setWrapSelectorWheel(false);

        if (savedInstanceState != null) {
            //TODO extract key strings into Constants
            numberPicker.setValue(savedInstanceState.getInt("CurrentNumber"));
        } else {
            numberPicker.setValue(startValue);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        NumberPicker numberPicker = (NumberPicker) this.findViewById(R.id.playerSelector);
        //TODO extract key strings into Constants
        outState.putInt("CurrentNumber", numberPicker.getValue());
    }


    // Called when the user clicks the  Quick Start button
    public final void quickAcceptPlayers(View view) {
        Intent intent = new Intent(this, RecordScoresActivity.class);
        NumberPicker numberPicker = (NumberPicker) this.findViewById(R.id.playerSelector);
        int numberOfPlayers = numberPicker.getValue();

        intent.putExtra(EXTRA_PLAYERS, numberOfPlayers);
        intent.putExtra(EXTRA_CUSTOM_NAMES, false);
        this.startActivity(intent);
    }

    // Called when the user clicks the Name Players button
    public final void namePlayers(View view) {
        Intent intent = new Intent(this, CustomNamePlayersActivity.class);
        NumberPicker numberPicker = (NumberPicker) this.findViewById(R.id.playerSelector);
        int numberOfPlayers = numberPicker.getValue();

        intent.putExtra(EXTRA_PLAYERS, numberOfPlayers);
        this.startActivity(intent);
    }
}
