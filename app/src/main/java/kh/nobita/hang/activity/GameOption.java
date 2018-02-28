package kh.nobita.hang.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.Utils.MyContextWrapper;
import kh.nobita.hang.databinding.ActivityGameOptionBinding;
import kh.nobita.hang.model.Roles.ListRoles;

public class GameOption extends AppCompatActivity implements View.OnClickListener {

    private ActivityGameOptionBinding binding;
    private Bundle save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        save = savedInstanceState;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_option);
        setListeners();
    }

    private void setListeners() {
        binding.btnOneMachine.setOnClickListener(this);
        binding.btnMultiMachine.setVisibility(View.GONE);
        binding.btnMultiMachine.setOnClickListener(this);
        binding.btnLibrary.setOnClickListener(this);
        binding.btnAbout.setOnClickListener(this);
        binding.btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOneMachine:
                Intent intent = new Intent(this, OneMachine.class);
                this.startActivity(intent);
                break;
            case R.id.btnMultiMachine:
                intent = new Intent(this, MultiplayerWifi.class);
                this.startActivity(intent);
                break;
            case R.id.btnLibrary:
                intent = new Intent(this, WerewolfLibrary.class);
                this.startActivity(intent);
                break;
            case R.id.btnAbout:
                Context ctx = LocaleHelper.setLocale(this, AppController.getInstance().getPrefManager().getLang());
                new MaterialDialog.Builder(this)
                        .title(ctx.getResources().getString(R.string.about_tile))
                        .content(ctx.getResources().getString(R.string.about_content))
                        .positiveText(ctx.getResources().getString(R.string.agree))
                        .show();
                break;
            case R.id.btnSettings:
                intent = new Intent(this, GameSetting.class);
                this.startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            updateView();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateView() {
        Resources resources = LocaleHelper.getLangResources(this);
        binding.btnOneMachine.setText(resources.getString(R.string.opt_one_machine));
        binding.btnMultiMachine.setText(resources.getString(R.string.opt_multi_machine));
        binding.btnLibrary.setText(resources.getString(R.string.opt_werewolf_library));
        binding.btnAbout.setText(resources.getString(R.string.opt_about));
        ListRoles.getInstance().createListRoles(resources);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, AppController.getInstance().getPrefManager().getLang()));
    }
}
