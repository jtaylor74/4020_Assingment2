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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;
    private TextView tvHighscore;
    private int mScore;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_green:
                Log.d("onClick :: ", "green");
                break;

            case R.id.btn_red:
                Log.d("onClick :: ", "red");
                break;

            case R.id.btn_yellow:
                Log.d("onClick :: ", "yellow");
                break;

            case R.id.btn_blue:
                Log.d("onClick :: ", "blue");
                break;

            default:
                break;
        }
    }
}
