package simon.android.com.simon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //global button definitions to use in multiple functions
    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;

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

    private displayPattern updateTask;
    private loseAsync updateTask2;
    private AsyncTask cA;

    //array to hold the pattern, size set to the original limit of 31
    private int[] simon_pattern = new int[31];

    //count of guesses left to check
    private int count = 0;

    //the length of the current pattern/level
    private int pattern_length = 0;

    //random number generator
    private static final Random RNG = new Random();

    private final String DATA_FILENAME = "scores.txt";
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

        //Initialize high score
        try {
            FileInputStream fis = openFileInput(DATA_FILENAME);
            Scanner scanner = new Scanner(fis);

            //format: firstName lastName
            while (scanner.hasNext()) {
                String score = scanner.next();
                TextView tv = (TextView) findViewById(R.id.tv_highScore);
                tv.setText(score);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            Log.d("file", "onCreate: file not opening");
        }
        //Initialize buttons and text view
        btnGreen = (Button) findViewById(R.id.btn_green);
        btnRed = (Button) findViewById(R.id.btn_red);
        btnYellow = (Button) findViewById(R.id.btn_yellow);
        btnBlue = (Button) findViewById(R.id.btn_blue);

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
        Toast.makeText(getApplicationContext(), "Game started, wait for pattern!", Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
        count = pattern_length;
        executePattern();
    }

    //generates every pattern after the game is started
    private void generatePattern() {
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
        count = pattern_length;
        executePattern();
    }

    private void executePattern () {
        if (updateTask != null && updateTask.getStatus() == AsyncTask.Status.FINISHED) {
            updateTask = null;
        }
        if (updateTask == null) {
            updateTask = new displayPattern();
            updateTask.execute();
        } else {
            Log.i("START", "executePattern: task is already running");
        }
    }

    class displayPattern extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnGreen.setEnabled(false);
                        btnRed.setEnabled(false);
                        btnYellow.setEnabled(false);
                        btnBlue.setEnabled(false);
                    }
                });
                for (int i = 0; i < pattern_length; i++) {
                    switch (simon_pattern[i]) {
                        case 0: {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playSound(belldingId, .2f);
                                    btnGreen.setBackground(getDrawable(R.drawable.green_simon));
                                }
                            });

                            Thread.sleep(300);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnGreen.setBackground(getDrawable(R.drawable.green_button));
                                }
                            });
                            Thread.sleep(300);

                            break;
                        }
                        case 1: {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playSound(huhId, 2f);
                                    btnRed.setBackground(getDrawable(R.drawable.red_simon));
                                }
                            });

                            Thread.sleep(300);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnRed.setBackground(getDrawable(R.drawable.red_button));
                                }
                            });
                            Thread.sleep(300);

                            break;
                        }
                        case 2: {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playSound(laserId, .02f);
                                    btnYellow.setBackground(getDrawable(R.drawable.yellow_simon));
                                }
                            });

                            Thread.sleep(300);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnYellow.setBackground(getDrawable(R.drawable.yellow_button));
                                }
                            });
                            Thread.sleep(300);

                            break;
                        }
                        case 3: {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    playSound(teleportId, .02f);
                                    btnBlue.setBackground(getDrawable(R.drawable.blue_simon));
                                }
                            });

                            Thread.sleep(300);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    btnBlue.setBackground(getDrawable(R.drawable.blue_button));
                                }
                            });
                            Thread.sleep(300);

                            break;
                        }
                    }
                }
            } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnGreen.setEnabled(true);
                    btnRed.setEnabled(true);
                    btnYellow.setEnabled(true);
                    btnBlue.setEnabled(true);
                }
            });
            return null;
        }
    }

    //checks a player's guess against the pattern in the array
    public void checkAnswerFunc(int button) {
        if (cA != null && cA.getStatus() == AsyncTask.Status.FINISHED) {
            cA = null;
        }
        if (cA == null) {
            cA = new checkAnswer().execute(new Integer(button));
        } else {
            Log.i("START", "task is already running");
        }
    }

    class checkAnswer extends AsyncTask<Integer, Void, Void> {
        int index = pattern_length - count;
        @Override
        protected Void doInBackground(Integer... b) {
            int button = b[0].intValue();
            if (simon_pattern[index] == button) {
                count--;
                Log.i("checkAnswer: ", "count value: " + count);
                Log.i("checkAnswer: ", "pattern value: " + pattern_length);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnGreen.setEnabled(false);
                        btnRed.setEnabled(false);
                        btnYellow.setEnabled(false);
                        btnBlue.setEnabled(false);
                    }
                });
                loseFunction();
            }
            if (count == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                generatePattern();
               // Log.i("checkAnswer", "checkAnswer: GENERATING PATTERN IN ANSWSER CHECK");
            }
            return null;
        }
    }

    private void loseFunction() {
        //TODO:
        //Reset count and pattern
        //Stop button presses to reset game
        //Check to see if score is new high score
        if (updateTask2 != null && updateTask2.getStatus() == AsyncTask.Status.FINISHED) {
            updateTask2 = null;
        }
        if (updateTask2 == null) {
            updateTask2 = new loseAsync();
            updateTask2.execute();
        } else {
            Log.i("START", "executePattern: task is already running");
        }
    }

    class loseAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  Toast.makeText(getApplicationContext(), "You lost! HA HA HA, wait for game to restart!", Toast.LENGTH_SHORT).show();
                  updateSave(pattern_length-1);
              }
          });
               Thread.sleep(5000);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pattern_length = count = 0;
                    btnGreen.setEnabled(true);
                    btnRed.setEnabled(true);
                    btnYellow.setEnabled(true);
                    btnBlue.setEnabled(true);
                }
            });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
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
//                else if (count == 0) {
//                    playSound(belldingId, .2f);
//                    Log.i("onClick", "onClick: GENERATING PATTERN IN onClick");
//                    generatePattern();
//                }
                //else check the button that was pressed
                else {
                    playSound(belldingId, .2f);
                    Log.i("onClick: ", "Checking answer from onClick count value: " + count);
                    Log.i("onClick: ", "Checking answer from onClick pattern value: " + pattern_length);
                    checkAnswerFunc(GREEN_BUTTON);
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
//                else if (count == 0) {
//                    playSound(huhId, 2f);
//                    Log.i("onClick", "onClick: GENERATING PATTERN IN onClick");
//                    generatePattern();
//                }
                //else check the button that was pressed
                else {
                    playSound(huhId, 2f);
                    Log.i("onClick: ", "Checking answer from onClick count value: " + count);
                    Log.i("onClick: ", "Checking answer from onClick pattern value: " + pattern_length);
                    checkAnswerFunc(RED_BUTTON);
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
//                else if (count == 0) {
//                    playSound(laserId, .02f);
//                    Log.i("onClick", "onClick: GENERATING PATTERN IN onClick");
//                    generatePattern();
//                }
                //else check the button that was pressed
                else {
                    playSound(laserId, .02f);
                    Log.i("onClick: ", "Checking answer from onClick count value: " + count);
                    Log.i("onClick: ", "Checking answer from onClick pattern value: " + pattern_length);
                    checkAnswerFunc(YELLOW_BUTTON);
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
//                else if (count == 0) {
//                    playSound(teleportId, .02f);
//                    Log.i("onClick", "onClick: GENERATING PATTERN IN onClick");
//                    generatePattern();
//                }
                //else check the button that was pressed
                else {
                    playSound(teleportId, .02f);
                    Log.i("onClick: ", "Checking answer from onClick count value: " + count);
                    Log.i("onClick: ", "Checking answer from onClick pattern value: " + pattern_length);
                    checkAnswerFunc(BLUE_BUTTON);
                }
                Log.d("onClick :: ", v + "");
                break;
            }

            default:
                break;
        }
    }

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

    private void updateSave(int score) {
        TextView tv = (TextView) findViewById(R.id.tv_highScore);
        int highScore = Integer.parseInt(tv.getText().toString());
        if (score > highScore) {
            try {
                FileOutputStream fos = openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(score);
                pw.close();
            } catch (FileNotFoundException e) {

            }
            tv.setText(""+ score);
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

//        if (id == R.id.action_resetHighScore) {
//            //tv_Highscore.setText("0");
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        if (updateTask != null) {
            updateTask.cancel(true);
            updateTask = null;
        }
        if (cA != null) {
            cA.cancel(true);
            cA = null;
        }
        if (updateTask2 != null) {
            updateTask2.cancel(true);
            updateTask2 = null;
        }
        loseFunction();
        super.onPause();
    }
}
