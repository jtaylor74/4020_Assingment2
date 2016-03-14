package simon.android.com.simon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //global button definitions to use in multiple functions
    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;

    //global definition of textView
    private TextView tvHighscore;

    //const declarations of buttons to differentiate easily between guesses
    private final int GREEN_BUTTON = 0;
    private final int RED_BUTTON = 1;
    private final int YELLOW_BUTTON = 2;
    private final int BLUE_BUTTON = 3;

    //array to hold the pattern, size set to the original limit of 31
    private int[] simon_pattern = new int[31];

    //count of guesses left to check
    private int count = 0;

    //the length of the current pattern/level
    private int pattern_length = 0;

    //random number generator
    private static final Random RNG = new Random();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Code for action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //Initialize buttons and text view
        btnGreen = (Button) findViewById(R.id.btn_green);
        btnRed = (Button) findViewById(R.id.btn_red);
        btnYellow = (Button) findViewById(R.id.btn_yellow);
        btnBlue = (Button) findViewById(R.id.btn_blue);
        tvHighscore = (TextView) findViewById(R.id.tv_highScoreText);

        //set onClickListeners for buttons
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void checkAnswer(int button) {
        int index = pattern_length - count;
        if (simon_pattern[index] == button) {
            count--;
        } else {
            loseFunction();
        }
    }

    private void startGame() {
        int num = RNG.nextInt(4);
        //Log.d("NUM VALUE", "Value: " + num);
        simon_pattern[pattern_length] = num;
        pattern_length++;
        //displayPattern();
    }

    private void generatePattern() {
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
        //displayPattern();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://simon.android.com.simon/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://simon.android.com.simon/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

//   private void displayPattern () {
//        //TODO:
//        //Display array pattern back to user
//        //Use selector maybe?
//        btnBlue.setEnabled(false);
//        btnYellow.setEnabled(false);
//        btnRed.setEnabled(false);
//        btnGreen.setEnabled(false);
//
//        for (int i = 0; i < pattern_length; i++) {
//            switch (simon_pattern[i]) {
//                case 0:
//                    btnGreen.setBackground(getDrawable(R.drawable.red_button));
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    btnGreen.setBackground(getDrawable(R.drawable.green_button));
//                    break;
//
//                case 1:
//                    btnRed.setBackground(getDrawable(R.drawable.green_button));
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    btnRed.setBackground(getDrawable(R.drawable.red_button));
//                    break;
//
//                case 2:
//                    btnYellow.setBackground(getDrawable(R.drawable.blue_button));
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    btnYellow.setBackground(getDrawable(R.drawable.yellow_button));
//                    break;
//
//                case 3:
//                    btnBlue.setBackground(getDrawable(R.drawable.yellow_button));
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    btnBlue.setBackground(getDrawable(R.drawable.blue_button));
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//
//        btnBlue.setEnabled(true);
//        btnYellow.setEnabled(true);
//        btnRed.setEnabled(true);
//        btnGreen.setEnabled(true);
//
//    }

//    private class displayPattern extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            for (int i = 0; i < pattern_length; i++) {
//                switch (simon_pattern[i]) {
//                    case 0:
//                        btnGreen.setBackground(getDrawable(R.drawable.red_button));
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        btnGreen.setBackground(getDrawable(R.drawable.green_button));
//                        break;
//
//                    case 1:
//                        btnRed.setBackground(getDrawable(R.drawable.green_button));
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        btnRed.setBackground(getDrawable(R.drawable.red_button));
//                        break;
//
//                    case 2:
//                        btnYellow.setBackground(getDrawable(R.drawable.blue_button));
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        btnYellow.setBackground(getDrawable(R.drawable.yellow_button));
//                        break;
//
//                    case 3:
//                        btnBlue.setBackground(getDrawable(R.drawable.yellow_button));
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        btnBlue.setBackground(getDrawable(R.drawable.blue_button));
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//            return null;
//
//        }
//        @Override
//        protected void onProgressUpdate(Void... values) {
//
//        }
//    }

    private void loseFunction() {
        //TODO:
        //Reset count and pattern
        //Stop button presses to reset game
        //Check to see if score is new high score
        Toast.makeText(getApplicationContext(), "YOU LOST! HA HA", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_green: {

                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    generatePattern();
                }
                //else check the button that was pressed
                else
                    checkAnswer(GREEN_BUTTON);

                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_red: {

                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    count = pattern_length;
                    generatePattern();
                }
                //else check the button that was pressed
                else
                    checkAnswer(RED_BUTTON);

                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_yellow: {

                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    count = pattern_length;
                    generatePattern();
                }
                //else check the button that was pressed
                else
                    checkAnswer(YELLOW_BUTTON);

                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_blue: {

                //If there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    startGame();
                }
                //Else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    count = pattern_length;
                    generatePattern();
                }
                //else check the button that was pressed
                else
                    checkAnswer(BLUE_BUTTON);

                Log.d("onClick :: ", v + "");
                break;
            }

            default:
                break;
        }
    }
}
