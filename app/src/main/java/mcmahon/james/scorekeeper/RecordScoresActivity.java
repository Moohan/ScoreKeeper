/*
 * Copyright (c) James McMahon 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package mcmahon.james.scorekeeper;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecordScoresActivity extends AppCompatActivity {


    private double[] scoreSums; //initialised once we know how many players we have
    private int[] sumsID;
    private int rounds;
    private ArrayList<ArrayList<Double>> scores = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> scoreID = new ArrayList<>();

    private defaultPlayerName defaultPlayerName(int playerNumber) {
        return new defaultPlayerName(String.format(getResources().getString(R.string.Default_Player_name_with_number), playerNumber));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_scores);

        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);

        Boolean customNames = intent.getBooleanExtra(SelectNumPlayersActivity.EXTRA_CUSTOM_NAMES, false);

        String playerNames[] = new String[numberOfPlayers];

        if (customNames) {
            playerNames = intent.getStringArrayExtra(CustomNamePlayersActivity.EXTRA_PLAYER_NAMES);
        }

        //Create the player names header row
        TableRow tableHeaderRow = (TableRow) findViewById(R.id.player_score_table_header_row);


        for (int n = 1; n <= numberOfPlayers; n++) {
            // Create textView
            TextView textView = new TextView(this);

            String playerName;
            playerName = customNames ? playerNames[n - 1] : defaultPlayerName(n).getValue();

            textView.setText(playerName);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTypeface(Typeface.DEFAULT_BOLD);

            // Add text view to row
            tableHeaderRow.addView(textView);
        }


        //Create bottom row of table to show score sums
        TableRow tableSumRow = (TableRow) findViewById(R.id.player_score_table_sum_row);
        scoreSums = new double[numberOfPlayers];
        sumsID = new int[numberOfPlayers];

        //initialise scores to zero
        for (int player = 1; player <= numberOfPlayers; player++) {
            scoreSums[player - 1] = 0;
            sumsID[player - 1] = 0;
        }


        rounds = 0;
        //Initialise the scores sum row
        for (int n = 1; n <= numberOfPlayers; n++) {
            // Create textView
            TextView textView = new TextView(this);
            textView.setText("0");

            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTypeface(Typeface.DEFAULT_BOLD);

            //Give each counter an ID and store it
            textView.setId(View.generateViewId());
            sumsID[n - 1] = textView.getId();


            if (savedInstanceState != null) {
                double savedScore = savedInstanceState.getDoubleArray("scoreSums")[n - 1];

                if (savedScore > 0) {
                    updateScoreSums(textView, savedScore);
                    scoreSums[n - 1] = savedScore;
                }

            } else {
                //Initialise the Array lists with number of players
                scores.add(new ArrayList<Double>());
            }

            // Add text view to row
            tableSumRow.addView(textView);
        }

        TableRow tableRow1 = (TableRow) findViewById(R.id.player_score_table_row1);
        //Create the first row of editTexts
        for (int i = 1; i <= numberOfPlayers; i++) {
            scoreID.add(new ArrayList<Integer>());
        }
        newScoreRow(tableRow1, numberOfPlayers);

        if (savedInstanceState != null) {
            scores = new ArrayList<>();
            rounds = savedInstanceState.getInt("rounds");
            for (int player = 1; player <= numberOfPlayers; player++) {
                scores.add((ArrayList<Double>) savedInstanceState.getSerializable(String.format("player" + player)));

                EditText editText = (EditText) findViewById(scoreID.get(player - 1).get(0));
                editText.setText(scoreFormat(scores.get(player - 1).get(0)));
            }
            endRound(findViewById(R.id.player_score_table));
            for (int round = 2; round <= rounds; round++) {
                for (int player = 1; player <= numberOfPlayers; player++) {
                    EditText editText = (EditText) findViewById(scoreID.get(player - 1).get(round - 1));
                    editText.setText(scoreFormat(scores.get(player - 1).get(round - 1)));
                }
                endRound(findViewById(R.id.player_score_table));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);

        super.onSaveInstanceState(outState);
        outState.putDoubleArray("scoreSums", scoreSums);
        outState.putInt("rounds", rounds);
        for (int player = 1; player <= numberOfPlayers; player++) {
            outState.putSerializable(String.format("player" + player), scores.get(player - 1));
        }


    }


    private void newScoreRow(TableRow tableRow, int numberOfPlayers) {
        for (int n = 1; n <= numberOfPlayers; n++) {
            // Create editTexts
            EditText editTextScore = new EditText(this);
            String hint = "0";
            editTextScore.setHint(hint);
            editTextScore.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editTextScore.setGravity(Gravity.CENTER_HORIZONTAL);
            editTextScore.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            if (n == numberOfPlayers) {
                editTextScore.setImeOptions(EditorInfo.IME_ACTION_DONE);
            }
            editTextScore.setId(View.generateViewId());

            scoreID.get(n - 1).add(editTextScore.getId());

            // Add text view to row
            tableRow.addView(editTextScore);
        }
    }


    public final void endRound(View view) {
        Intent intent = getIntent();
        int numberOfPlayers = intent.getIntExtra(SelectNumPlayersActivity.EXTRA_PLAYERS, 2);
        TableLayout scoresTable = (TableLayout) findViewById(R.id.player_score_table);

        //Store Player scores
        for (int n = 1; n <= numberOfPlayers; n++) {
            EditText editText = (EditText) findViewById(scoreID.get(n - 1).get(rounds));
            //turn off the edit text
            editText.setEnabled(false);
            //collect the scores into an array
            double score = 0;
            if (!(editText.getText().toString().equals(""))) {
                score = Double.parseDouble(editText.getText().toString());
            }
            scores.get(n - 1).add(score);
            scoreSums[n - 1] += score;

            updateScoreSums((TextView) (findViewById(sumsID[n - 1])), scoreSums[n - 1]);
        }
        //Draw a new row of edit texts
        TableRow tableRow = new TableRow(this);
        newScoreRow(tableRow, numberOfPlayers);
        scoresTable.addView(tableRow, scoresTable.getChildCount() - 2);

        //next round
        rounds++;

    }

    private void updateScoreSums(TextView textView, double scoreSum) {
        String scoreSumDisplayable = scoreFormat(scoreSum);
        textView.setText(scoreSumDisplayable);
    }

    private String scoreFormat(double score) {
        DecimalFormat decimalFormat = new DecimalFormat("0.###");
        return decimalFormat.format(score);
    }
}
