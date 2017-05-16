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
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordScoresActivity extends AppCompatActivity {

    private static final int CENTER_GRAVITY = 0x11;

    int roundsCount;
    ArrayList<int[]> scores = new ArrayList<int[]>();

    public defaultPlayerName defaultPlayerName(int playerNumber) {
        return new defaultPlayerName(String.format(getResources().getString(R.string.Player_number_label), playerNumber));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_scores);
        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);
        TableLayout scoresTable = (TableLayout) findViewById(R.id.player_score_table);
        TableRow tableHeaderRow = (TableRow) findViewById(R.id.player_score_table_header_row);


        Boolean customNames = intent.getBooleanExtra(SelectNumPlayersActivity.EXTRA_CUSTOM_NAMES, false);

        String playerNames[] = new String[numberOfPlayers];
        if (customNames) {
            playerNames = intent.getStringArrayExtra(CustomNamePlayersActivity.EXTRA_PLAYER_NAMES);
        }

        for (int n = 1; n <= numberOfPlayers; n++) {
            // Creation textView
            TextView textView = new TextView(this);
            String playerName = "";


            playerName = customNames ? playerNames[n - 1] : defaultPlayerName(n).getValue();

            textView.setText(playerName);
            textView.setGravity(CENTER_GRAVITY);
            // Add text view to row
            tableHeaderRow.addView(textView);
        }

        TableRow tableRow1 = (TableRow) findViewById(R.id.player_score_table_row1);

        for (int n = 1; n <= numberOfPlayers; n++) {
            // Creation editTexts
            EditText editTextScore = new EditText(this);
            editTextScore.setInputType(InputType.TYPE_CLASS_NUMBER);
            String hint = "0";
            editTextScore.setHint(hint);
            editTextScore.setText("0"); //TODO Remove this line and add a check for no score ("") later on
            editTextScore.setGravity(CENTER_GRAVITY);
            editTextScore.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            if (n == numberOfPlayers) {
                editTextScore.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
            editTextScore.setId(n);
            // Add text view to row
            tableRow1.addView(editTextScore);
        }

        this.roundsCount++;

        /**Give edit texts IDs
         have an add button which takes values from editi
         texts and sets them to be uneditable and then draws a
         new row of edit texts
         Array list!
         **/

    }

    public final void addScores(View view) {
        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);
        TableLayout scoresTable = (TableLayout) findViewById(R.id.player_score_table);
        //Add round array to arraylist
        int[] roundScores = new int[numberOfPlayers];

        //Store Player scores
        for (int n = 1; n <= numberOfPlayers; n++) {
            EditText editText = (EditText) findViewById(n);
            //turn off the edit text
            editText.setEnabled(false);
            //collect the scores into an array
            int score = Integer.parseInt(editText.getText().toString());
            roundScores[n - 1] = score;
        }
        //add the round scores to the overall score
        this.scores.add(new int[numberOfPlayers]);
        this.scores.add(this.scores.size() - 1, roundScores);  //FIXME
        //Draw some a new row of edit texts
        TableRow tableRow = new TableRow(this);
        for (int n = 1; n <= numberOfPlayers; n++) {
            // Creation editTexts
            EditText editTextScore = new EditText(this);
            String hint = "0";

            editTextScore.setHint(hint);
            String text = "";
            editTextScore.setText(text);
            editTextScore.setGravity(CENTER_GRAVITY);

            editTextScore.setId(n);
            // Add text view to row
            tableRow.addView(editTextScore);
            this.roundsCount++;
        }
        scoresTable.addView(tableRow, scoresTable.getChildCount() - 1);
    }

}