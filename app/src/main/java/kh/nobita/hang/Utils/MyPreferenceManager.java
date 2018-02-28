package kh.nobita.hang.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

/**
 * Created by Lincoln on 07/01/16.
 */
public class MyPreferenceManager {

    private String TAG = MyPreferenceManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "werewolf_setting";

    // Constructor
    public MyPreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void storeLang(String lang) {
        editor.putString(AppController.LANGUAGE_USING, lang);
        editor.commit();
    }

    public String getLang() {
        return pref.getString(AppController.LANGUAGE_USING, Locale.getDefault().getLanguage());
    }

    public void storeNumberWolfBite(int number) {
        editor.putInt(AppController.NUMBER_WOLF_BITE, number);
        editor.commit();
    }

    public int getNumberWolfBite() {
        return pref.getInt(AppController.NUMBER_WOLF_BITE, 1);
    }

    public void storeNumberSecurityHelp(int number) {
        editor.putInt(AppController.NUMBER_SECURITY_HELP, number);
        editor.commit();
    }

    public int getNumberSecurityHelp() {
        return pref.getInt(AppController.NUMBER_SECURITY_HELP, 1);
    }

    public void storeNumberWitchPoison(int number) {
        editor.putInt(AppController.NUMBER_WITCH_POISON, number);
        editor.commit();
    }

    public int getNumberWitchPoison() {
        return pref.getInt(AppController.NUMBER_WITCH_POISON, 1);
    }

    public void storeNumberWitchAssist(int number) {
        editor.putInt(AppController.NUMBER_WITCH_ASSIST, number);
        editor.commit();
    }

    public int getNumberWitchAssist() {
        return pref.getInt(AppController.NUMBER_WITCH_ASSIST, 1);
    }

    public void storeNumberHanged(int number) {
        editor.putInt(AppController.NUMBER_HANGED, number);
        editor.commit();
    }

    public int getNumberHanged() {
        return pref.getInt(AppController.NUMBER_HANGED, 1);
    }

    public void storeNumberHunterSelect(int number) {
        editor.putInt(AppController.NUMBER_HUNTER_SELECT, number);
        editor.commit();
    }

    public int getNumberHunterSelect() {
        return pref.getInt(AppController.NUMBER_HUNTER_SELECT, 1);
    }

    public void storeNumberCupidSelect(int number) {
        editor.putInt(AppController.NUMBER_CUPID_SELECT, number);
        editor.commit();
    }

    public int getNumberCupidSelect() {
        return pref.getInt(AppController.NUMBER_CUPID_SELECT, 2);
    }

    public void storeNumberInversePersonSelect(int number) {
        editor.putInt(AppController.NUMBER_INVERSE_PERSON_SELECT, number);
        editor.commit();
    }

    public int getNumberInversePersonSelect() {
        return pref.getInt(AppController.NUMBER_INVERSE_PERSON_SELECT, 1);
    }

    public int getNumberNomadsSelect() {
        return pref.getInt(AppController.NUMBER_NOMADS_SELECT, 1);
    }

    public void storeNumberNomadsSelect(int number) {
        editor.putInt(AppController.NUMBER_NOMADS_SELECT, number);
        editor.commit();
    }

    public void storeNumberGrandmotherSelect(int number) {
        editor.putInt(AppController.NUMBER_GRANDMOTHER_SELECT, number);
        editor.commit();
    }

    public int getNumberGrandmotherSelect() {
        return pref.getInt(AppController.NUMBER_GRANDMOTHER_SELECT, 1);
    }

    public void storeTimerDiscuss(int number) {
        editor.putInt(AppController.TIMER_DISCUSS, number);
        editor.commit();
    }

    public int getTimerDiscuss() {
        return pref.getInt(AppController.TIMER_DISCUSS, 1);
    }
}

