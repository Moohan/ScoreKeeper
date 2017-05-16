/*
 * Copyright (c) James McMahon 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class CustomNamePlayersActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER_NAMES = "mcmahon.james.scorekeeper.PLAYER_NAMES";
    private static final int CENTER_GRAVITY = 0x11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_custom_name_players);

        Intent intent = this.getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);


        TableLayout tableLayout = (TableLayout) this.findViewById(R.id.player_name_table);

        for (int n = 1; n <= numberOfPlayers; n++) {
            //Create  tableRow
            TableRow tableRow = new TableRow(this);

            // Create textView
            TextView textViewPlayerNumber = new TextView(this);
            Resources res = this.getResources();
            String text = defaultPlayerName(n).getValue();
            textViewPlayerNumber.setText(text);
            textViewPlayerNumber.setGravity(CustomNamePlayersActivity.CENTER_GRAVITY);

            //Create editText
            EditText editTextPlayerName = new EditText(this);
            String playerName = defaultPlayerName(n).getValue();
            editTextPlayerName.setHint(playerName);

            //Give them IDs
            editTextPlayerName.setId(n);
            editTextPlayerName.setImeActionLabel("Next", n);
            editTextPlayerName.setImeOptions(EditorInfo.IME_ACTION_NEXT);


            // Add textView to row
            tableRow.addView(textViewPlayerNumber);

            //Add editText to row
            tableRow.addView(editTextPlayerName);

            //Add tableRow to table
            tableLayout.addView(tableRow);
        }
    }

    public defaultPlayerName defaultPlayerName(int playerNumber){
        return new defaultPlayerName(String.format(getResources().getString(R.string.Player_number_label), playerNumber));
    }

    public final void acceptPlayers(View view) {
        Intent intentFromNum = this.getIntent();

        int numberOfPlayers = intentFromNum.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);
        String[] playerNames = new String[numberOfPlayers];

        for (int n = 1; n <= numberOfPlayers; n++) {
            EditText editText = (EditText) this.findViewById(n);
            String playerName = editText.getText().toString();
            if (playerName.isEmpty()) {
                Resources res = this.getResources();
                playerNames[n - 1] = defaultPlayerName(n).getValue();
            } else {
                playerNames[n - 1] = playerName;

            }
        }


        Intent intent = new Intent(this, RecordScoresActivity.class);
        intent.putExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, numberOfPlayers);
        intent.putExtra(CustomNamePlayersActivity.EXTRA_PLAYER_NAMES, playerNames);
        intent.putExtra(SelectNumPlayersActivity.EXTRA_CUSTOM_NAMES, true);
        this.startActivity(intent);
    }



}