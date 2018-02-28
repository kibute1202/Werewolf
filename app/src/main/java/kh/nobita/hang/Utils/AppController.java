package kh.nobita.hang.Utils;

import android.app.Application;
import android.content.Context;

//Class extending application
public class AppController extends Application {

    //Getting tag it will be used for displaying log and it is optional
    public static final String TAG = AppController.class.getSimpleName();
    public static final String LANGUAGE_USING = "lang";
    public static final String NUMBER_WOLF_BITE = "number_wolf_bite";
    public static final String NUMBER_SECURITY_HELP = "number_security_help";
    public static final String NUMBER_WITCH_POISON = "number_witch_poison";
    public static final String NUMBER_WITCH_ASSIST = "number_witch_assist";
    public static final String NUMBER_HANGED = "number_hanged";
    public static final String NUMBER_HUNTER_SELECT = "number_hunter_select";
    public static final String NUMBER_CUPID_SELECT = "number_cupid_select";
    public static final String NUMBER_INVERSE_PERSON_SELECT = "number_inverse_person_select";
    public static final String NUMBER_NOMADS_SELECT = "number_nomads_select";
    public static final String NUMBER_GRANDMOTHER_SELECT = "number_grandmother_select";
    public static final String TIMER_DISCUSS = "timer_discuss";

    //Creatting class object
    private static AppController mInstance;

    private MyPreferenceManager pref;

    //class instance will be initialized on app launch
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //Public static method to get the instance of this class
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }
}