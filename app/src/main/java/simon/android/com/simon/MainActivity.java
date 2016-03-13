package simon.android.com.simon;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
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

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;
    private TextView tvHighscore;

    private final int GREEN_BUTTON = 0;
    private final int RED_BUTTON = 1;
    private final int YELLOW_BUTTON = 2;
    private final int BLUE_BUTTON = 3;

    private int [] simon_pattern = new int [31];
    private int count = 0;
    private int pattern_length = 0;
    private static final Random RNG = new Random();




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

    }

    public void checkAnswer(int button) {
        int index = pattern_length - count;
        if (simon_pattern[index] == button) {
            //TODO::
            //FIGURE OUT HOW TO HANDLE
            //MULTIPLE INPUTS QUICKLY????
        } else {
            loseFunction();
        }
    }

    public void startGame() {
        int num = RNG.nextInt(4);
        //Log.d("NUM VALUE", "Value: " + num);
        simon_pattern[pattern_length] = num;
        pattern_length++;
    }

    public void generatePattern() {
        int num = RNG.nextInt(4);
        simon_pattern[pattern_length] = num;
        for (int i = 0; i <= pattern_length; i++)
            Log.d("Value: " + i, " = " + simon_pattern[i]);
        pattern_length++;
    }

    public void loseFunction() {
        //TODO:
        //Reset count and pattern
        //Stop button presses for 2s
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
            case R.id.btn_green:
                if (pattern_length == 0)
                    startGame();
                else if (count == 0)
                    generatePattern();
                else
                    checkAnswer(GREEN_BUTTON);
                Log.d("onClick :: ", v + "");
                break;

            case R.id.btn_red:
                if (pattern_length == 0)
                    startGame();
                else if (count == 0)
                    generatePattern();
                else
                    checkAnswer(RED_BUTTON);
                Log.d("onClick :: ", v + "");
                break;

            case R.id.btn_yellow:
                if (pattern_length == 0)
                    startGame();
                else if (count == 0)
                    generatePattern();
                else
                    checkAnswer(YELLOW_BUTTON);
                Log.d("onClick :: ", v + "");
                break;

            case R.id.btn_blue:
                if (pattern_length == 0)
                    startGame();
                else if (count == 0)
                    generatePattern();
                else
                    checkAnswer(BLUE_BUTTON);
                Log.d("onClick :: ", v + "");
                break;

            default:
                break;
        }
    }
}
