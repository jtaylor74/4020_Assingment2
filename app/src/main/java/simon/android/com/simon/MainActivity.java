package simon.android.com.simon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
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

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //global button definitions to use in multiple functions
    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;

    //global definition of textView
    private TextView tvHighscore;
    private int mScore;

    //const declarations of buttons to differentiate easily between guesses
    private final int GREEN_BUTTON = 0;
    private final int RED_BUTTON = 1;
    private final int YELLOW_BUTTON = 2;
    private final int BLUE_BUTTON = 3;

    //sound variables
    private SoundPool soundPool;
    private Set<Integer> soundsLoaded;
    private int belldingId; //green
    private int huhId; //red
    private int laserId; //yellow
    private int teleportId; //blue


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
        tvHighscore = (TextView) findViewById(R.id.tv_highScore);

        //Initialize high score
        mScore = 0;
        tvHighscore.setText("" + mScore);

        //set onClickListeners for buttons
        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnBlue.setOnClickListener(this);


        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    soundsLoaded.add(sampleId);
                } else {
                    Log.w("SOUND_POOL", "WARNING: status is " + status + "???????");
                }
            }
        });

        soundsLoaded = new HashSet<Integer>();

        belldingId = soundPool.load(this, R.raw.bell_ding1, 1);
        huhId = soundPool.load(this, R.raw.huh, 1);
        laserId = soundPool.load(this, R.raw.laser, 1);
        teleportId = soundPool.load(this, R.raw.teleport, 1);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //starts the game with an initial pattern
    private void startGame() {
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
        count++;
        Toast.makeText(getApplicationContext(), "Game started, wait for pattern!", Toast.LENGTH_SHORT).show();
        //displayPattern();
    }

    //generates every pattern after the game is started
    private void generatePattern() {
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
        count++;
        //displayPattern();
    }

    //  private void displayPattern () {
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
//}

    //generic function to play sounds
    private void playSound(int soundId, float volume) {
        if (soundsLoaded.contains(soundId)) {
        /*
            soundID        a soundID returned by the load() function
            leftVolume         left volume value (range = 0.0 to 1.0)
            rightVolume    right volume value (range = 0.0 to 1.0)
            priority       stream priority (0 = lowest priority)
            loop           loop mode (0 = no loop, -1 = loop forever)
            rate           playback rate (1.0 = normal playback, range 0.5 to 2.0)
         */
            soundPool.play(soundId, volume, volume, 0, 0, .5f);
        }
    }

    //checks a player's guess against the pattern in the array
    public void checkAnswer(int button) {
        int index = pattern_length - count;
        if (simon_pattern[index] == button) {
            count--;
        } else {
            loseFunction();
        }
    }

    //function for when player loses the game
    //resets count and pattern
    //checks for high score
    private void loseFunction() {
        //TODO:
        //Reset count and pattern
        //Stop button presses to reset game
        //Check to see if score is new high score
        count = 0;
        pattern_length = 0;
        btnGreen.setOnClickListener(null);
        btnRed.setOnClickListener(null);
        btnYellow.setOnClickListener(null);
        btnBlue.setOnClickListener(null);

        Toast.makeText(getApplicationContext(), "YOU LOST! HA HA, wait 10 seconds to play again.", Toast.LENGTH_LONG).show();

       //sleep to give game time to reset
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnGreen.setOnClickListener(this);
        btnRed.setOnClickListener(this);
        btnYellow.setOnClickListener(this);
        btnBlue.setOnClickListener(this);
    }

    //onClick listener function
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_green: {
                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    playSound(belldingId, .2f);
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    playSound(belldingId, .2f);
                    generatePattern();
                }
                //else check the button that was pressed
                else {
                    playSound(belldingId, .2f);
                    checkAnswer(GREEN_BUTTON);
                }
                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_red: {
                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    playSound(huhId, 2f);
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    playSound(huhId, 2f);
                    generatePattern();
                }
                //else check the button that was pressed
                else {
                    playSound(huhId, 2f);
                    checkAnswer(RED_BUTTON);
                }
                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_yellow: {
                //if there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    playSound(laserId, .02f);
                    startGame();
                }
                //else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    playSound(laserId, .02f);
                    generatePattern();
                }
                //else check the button that was pressed
                else {
                    playSound(laserId, .02f);
                    checkAnswer(YELLOW_BUTTON);
                }
                Log.d("onClick :: ", v + "");
                break;
            }

            case R.id.btn_blue: {
                //If there is no pattern(game hasn't started) start the game
                if (pattern_length == 0) {
                    playSound(teleportId, .02f);
                    startGame();
                }
                //Else if the count is 0, a new pattern must be generated.
                else if (count == 0) {
                    playSound(teleportId, .02f);
                    generatePattern();
                }
                //else check the button that was pressed
                else {
                    playSound(teleportId, .02f);
                    checkAnswer(BLUE_BUTTON);
                }
                Log.d("onClick :: ", v + "");
                break;
            }

            default:
                break;
        }
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

        if (id == R.id.action_resetHighScore) {
            mScore = 0;
            tvHighscore.setText("" + mScore);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
