package simon.android.com.simon;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by JordanTaylor on 2/24/16.
 */

/*
Colors : Number
    Green : 0
    Red : 1
    Yellow : 2
    Blue : 3
*/
    
public class Simon {
    private ArrayList<String> simonSequence;
    private ArrayList<String> playerSequence;
    private static Random random = new Random();


    public static int generateRandomNumber() {
        return random.nextInt(4);
    }
}
