package kh.nobita.hang.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.Utils.MyContextWrapper;

public class GameSetting extends AppCompatActivity implements View.OnClickListener {
    private static final int FLAG_NUMBER_WOLFBITE = 1;
    private static final int FLAG_NUMBER_SECURITY_HELP = FLAG_NUMBER_WOLFBITE + 1;
    private static final int FLAG_NUMBER_WITCH_POISON = FLAG_NUMBER_SECURITY_HELP + 1;
    private static final int FLAG_NUMBER_WITCH_ASSIST = FLAG_NUMBER_WITCH_POISON + 1;
    private static final int FLAG_NUMBER_HUNTER_SELECT = FLAG_NUMBER_WITCH_ASSIST + 1;
    private static final int FLAG_NUMBER_HANGED = FLAG_NUMBER_HUNTER_SELECT + 1;
    private static final int FLAG_NUMBER_GRANDMOTHER_SELECT = FLAG_NUMBER_HANGED + 1;
    private static final int FLAG_TIMER = FLAG_NUMBER_GRANDMOTHER_SELECT + 1;

    private Toolbar toolbar;
    private TextView numberWolfBite, numberSecurityHelp, numberWitchPoison, numberWitchAssist, numberHunterSelect, numberHanged, numberGrandmotherSelect, tvTimerDiscuss;
    private TextView tvLanguage;
    private Context context;
    private int itemSelectLanguage = -1;
    private int itemSelectNumberWolfBite = -1;
    private int itemSelectNumberSecurityHelp = -1;
    private int itemSelectNumberWitchPoison = -1;
    private int itemSelectNumberWitchAssist = -1;
    private int itemSelectNumberHunterSelect = -1;
    private int itemSelectNumberHanged = -1;
    private int itemSelectNumberGrandmotherSelect = -1;
    private int timer_min = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        setListeners();
    }

    private void initViews() {
        context = this;
        Resources resources = LocaleHelper.getLangResources(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(resources.getString(R.string.werewolf_setting));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        numberWolfBite = (TextView) findViewById(R.id.number_wolf_bite);
        //numberWolfBite.setFilters(new InputFilter[]{new InputFilterMinMax("1", "75")});
        numberSecurityHelp = (TextView) findViewById(R.id.number_security_help);
        numberWitchPoison = (TextView) findViewById(R.id.number_witch_poison);
        numberWitchAssist = (TextView) findViewById(R.id.number_witch_assist);
        numberHunterSelect = (TextView) findViewById(R.id.number_hunter_select);
        numberHanged = (TextView) findViewById(R.id.number_hanged);
        numberGrandmotherSelect = (TextView) findViewById(R.id.number_grandmother_select);
        tvTimerDiscuss = (TextView) findViewById(R.id.tv_timer_discuss);

        itemSelectNumberWolfBite = AppController.getInstance().getPrefManager().getNumberWolfBite() - 1;
        numberWolfBite.setText(resources.getString(R.string.number_wolf_bite) + " " + (itemSelectNumberWolfBite + 1));

        itemSelectNumberSecurityHelp = AppController.getInstance().getPrefManager().getNumberSecurityHelp() - 1;
        numberSecurityHelp.setText(resources.getString(R.string.number_security_help) + " " + (itemSelectNumberSecurityHelp + 1));

        itemSelectNumberWitchPoison = AppController.getInstance().getPrefManager().getNumberWitchPoison() - 1;
        numberWitchPoison.setText(resources.getString(R.string.number_witch_poison) + " " + (itemSelectNumberWitchPoison + 1));

        itemSelectNumberWitchAssist = AppController.getInstance().getPrefManager().getNumberWitchAssist() - 1;
        numberWitchAssist.setText(resources.getString(R.string.number_witch_assist) + " " + (itemSelectNumberWitchAssist + 1));

        itemSelectNumberHunterSelect = AppController.getInstance().getPrefManager().getNumberHunterSelect() - 1;
        numberHunterSelect.setText(resources.getString(R.string.number_hunter_select) + " " + (itemSelectNumberHunterSelect + 1));

        itemSelectNumberHanged = AppController.getInstance().getPrefManager().getNumberHanged() - 1;
        numberHanged.setText(resources.getString(R.string.number_hanged) + " " + (itemSelectNumberHanged + 1));

        itemSelectNumberGrandmotherSelect = AppController.getInstance().getPrefManager().getNumberGrandmotherSelect() - 1;
        numberGrandmotherSelect.setText(resources.getString(R.string.number_grandmother_select) + " " + (itemSelectNumberGrandmotherSelect + 1));

        timer_min = AppController.getInstance().getPrefManager().getTimerDiscuss() - 1;
        tvTimerDiscuss.setText(resources.getString(R.string.timer_discuss) + " " + (timer_min + 1) + " " + resources.getString(R.string.min));

        tvLanguage = (TextView) findViewById(R.id.tv_language);
        itemSelectLanguage = getIdLanguageShow(AppController.getInstance().getPrefManager().getLang());
        tvLanguage.setText(resources.getStringArray(R.array.language_chose_show)[itemSelectLanguage]);
    }

    private void setListeners() {
        tvLanguage.setOnClickListener(this);
        numberWolfBite.setOnClickListener(this);
        numberSecurityHelp.setOnClickListener(this);
        numberWitchPoison.setOnClickListener(this);
        numberWitchAssist.setOnClickListener(this);
        numberHunterSelect.setOnClickListener(this);
        numberHanged.setOnClickListener(this);
        numberGrandmotherSelect.setOnClickListener(this);
        tvTimerDiscuss.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private int getIdLanguageShow(String lang) {
        CharSequence[] list = getResources().getStringArray(R.array.language_chose);
        for (int index = 0; index < list.length; index++) {
            if (list[index].equals(lang)) {
                return index;
            }
        }
        for (int index = 0; index < list.length; index++) {
            if (list[index].equals("en")) {
                return index;
            }
        }
        return 0;
    }

    private void dialogMarialLanguage() {
        final Resources resources = LocaleHelper.getLangResources(this);
        new MaterialDialog.Builder(this)
                .title(resources.getString(R.string.dialog_language_title))
                .items(resources.getStringArray(R.array.language_chose_show))
                .itemsCallbackSingleChoice(itemSelectLanguage, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        itemSelectLanguage = which;
                        tvLanguage.setText(text);
                        return true;
                    }
                })
                .positiveText(resources.getString(R.string.agree))
                .show();
    }

    private void dialogShowSelectNumber(String title, int index, final int flagShow) {
        final Resources resources = LocaleHelper.getLangResources(this);
        new MaterialDialog.Builder(this)
                .title(title)
                .items(R.array.number_chose_show)
                .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (flagShow) {
                            case FLAG_NUMBER_WOLFBITE:
                                itemSelectNumberWolfBite = which;
                                numberWolfBite.setText(resources.getString(R.string.number_wolf_bite) + " " + (itemSelectNumberWolfBite + 1));
                                break;
                            case FLAG_NUMBER_SECURITY_HELP:
                                itemSelectNumberSecurityHelp = which;
                                numberSecurityHelp.setText(resources.getString(R.string.number_security_help) + " " + (itemSelectNumberSecurityHelp + 1));
                                break;
                            case FLAG_NUMBER_WITCH_POISON:
                                itemSelectNumberWitchPoison = which;
                                numberWitchPoison.setText(resources.getString(R.string.number_witch_poison) + " " + (itemSelectNumberWitchPoison + 1));
                                break;
                            case FLAG_NUMBER_WITCH_ASSIST:
                                itemSelectNumberWitchAssist = which;
                                numberWitchAssist.setText(resources.getString(R.string.number_witch_assist) + " " + (itemSelectNumberWitchAssist + 1));
                                break;
                            case FLAG_NUMBER_HUNTER_SELECT:
                                itemSelectNumberHunterSelect = which;
                                numberHunterSelect.setText(resources.getString(R.string.number_hunter_select) + " " + (itemSelectNumberHunterSelect + 1));
                                break;
                            case FLAG_NUMBER_HANGED:
                                itemSelectNumberHanged = which;
                                numberHanged.setText(resources.getString(R.string.number_hanged) + " " + (itemSelectNumberHanged + 1));
                                break;
                            case FLAG_NUMBER_GRANDMOTHER_SELECT:
                                itemSelectNumberGrandmotherSelect = which;
                                numberGrandmotherSelect.setText(resources.getString(R.string.number_grandmother_select) + " " + (itemSelectNumberGrandmotherSelect + 1));
                                break;
                            case FLAG_TIMER:
                                timer_min = which;
                                tvTimerDiscuss.setText(resources.getString(R.string.timer_discuss) + " " + (timer_min + 1) + " " + resources.getString(R.string.min));
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(resources.getString(R.string.agree))
                .show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, AppController.getInstance().getPrefManager().getLang()));
    }

    @Override
    public void onClick(View v) {
        Resources resources = LocaleHelper.getLangResources(this);
        switch (v.getId()) {
            case R.id.tv_language:
                dialogMarialLanguage();
                break;
            case R.id.number_wolf_bite:
                dialogShowSelectNumber(resources.getString(R.string.number_wolf_bite), itemSelectNumberWolfBite, FLAG_NUMBER_WOLFBITE);
                break;
            case R.id.number_security_help:
                dialogShowSelectNumber(resources.getString(R.string.number_security_help), itemSelectNumberSecurityHelp, FLAG_NUMBER_SECURITY_HELP);
                break;
            case R.id.number_witch_poison:
                dialogShowSelectNumber(resources.getString(R.string.number_witch_poison), itemSelectNumberWitchPoison, FLAG_NUMBER_WITCH_POISON);
                break;
            case R.id.number_witch_assist:
                dialogShowSelectNumber(resources.getString(R.string.number_witch_assist), itemSelectNumberWitchAssist, FLAG_NUMBER_WITCH_ASSIST);
                break;
            case R.id.number_hunter_select:
                dialogShowSelectNumber(resources.getString(R.string.number_hunter_select), itemSelectNumberHunterSelect, FLAG_NUMBER_HUNTER_SELECT);
                break;
            case R.id.number_hanged:
                dialogShowSelectNumber(resources.getString(R.string.number_hanged), itemSelectNumberHanged, FLAG_NUMBER_HANGED);
                break;
            case R.id.number_grandmother_select:
                dialogShowSelectNumber(resources.getString(R.string.number_grandmother_select), itemSelectNumberGrandmotherSelect, FLAG_NUMBER_GRANDMOTHER_SELECT);
                break;
            case R.id.tv_timer_discuss:
                dialogShowSelectNumber(resources.getString(R.string.number_hanged) + " (" + resources.getString(R.string.min) + ")", timer_min, FLAG_TIMER);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resources resources = LocaleHelper.getLangResources(this);
        switch (item.getItemId()) {
            case R.id.setting_save:
                new MaterialDialog.Builder(this)
                        .title(resources.getString(R.string.dialog_title_save_setting))
                        .content(resources.getString(R.string.dialog_content_save_setting))
                        .positiveText(resources.getString(R.string.agree))
                        .negativeText(resources.getString(R.string.disagree))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                try {
                                    if (!numberWolfBite.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberWolfBite((itemSelectNumberWolfBite + 1));
                                    }
                                    if (!numberSecurityHelp.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberSecurityHelp((itemSelectNumberSecurityHelp + 1));
                                    }
                                    if (!numberWitchPoison.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberWitchPoison((itemSelectNumberWitchPoison + 1));
                                    }
                                    if (!numberWitchAssist.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberWitchAssist((itemSelectNumberWitchAssist + 1));
                                    }
                                    if (!numberHunterSelect.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberHunterSelect((itemSelectNumberHunterSelect + 1));
                                    }
                                    if (!numberHanged.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberHanged((itemSelectNumberHanged + 1));
                                    }
                                    if (!numberGrandmotherSelect.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeNumberGrandmotherSelect((itemSelectNumberGrandmotherSelect + 1));
                                    }
                                    if (!tvTimerDiscuss.getText().toString().trim().equals("")) {
                                        AppController.getInstance().getPrefManager().storeTimerDiscuss((timer_min + 1));
                                    }
                                    AppController.getInstance().getPrefManager().storeLang(getResources().getStringArray(R.array.language_chose)[itemSelectLanguage]);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                onBackPressed();
                            }
                        })
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
