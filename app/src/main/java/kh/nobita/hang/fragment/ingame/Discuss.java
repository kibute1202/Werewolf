package kh.nobita.hang.fragment.ingame;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.InGameHangedPlayerAdapter;
import kh.nobita.hang.model.HistoryOne;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;

public class Discuss extends Fragment implements OnClickListener {
    private String TAG = Discuss.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerDead;
    private TextView tvComment, tvNumnerDead, tvCountdown;
    private Button btnNext;
    private InGameHangedPlayerAdapter hangedPlayerAdapter;
    private LinearLayout lnHistory;

    private CountDownTimer countDownTimer;

    public Discuss() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discuss, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        fragmentManager = getActivity().getSupportFragmentManager();
        lnHistory = LayoutInit.createLinearLayoutHorizontal(getActivity());
        rvPlayerDead = (RecyclerView) view.findViewById(R.id.rv_player_dead);
        rvPlayerDead.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerInRoles = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerInRoles.setOrientation(LinearLayoutManager.HORIZONTAL);
        List<Player> listPlayer = ListPlayers.getInstance(getActivity()).getAllPlayerLiveDiscuss(((GamePlay) getActivity()).isIdiotDie());
        hangedPlayerAdapter = new InGameHangedPlayerAdapter(getActivity(), listPlayer);
        rvPlayerDead.setAdapter(hangedPlayerAdapter);
        rvPlayerDead.setLayoutManager(myLayoutManagerPlayerInRoles);

        tvComment = (TextView) view.findViewById(R.id.tv_discuss_comment);
        tvComment.setText(resources.getString(R.string.tv_discuss) + " " + ((GamePlay) getActivity()).getNight());

        tvNumnerDead = (TextView) view.findViewById(R.id.tv_numner_dead);
        tvNumnerDead.setText(resources.getString(R.string.tv_text_number_dead) + " " + ((GamePlay) getActivity()).getListPlayerDieInNight().size());

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(resources.getString(R.string.btn_discuss_text_start_timer));

        ((TextView) view.findViewById(R.id.tv_title_function)).setText(resources.getString(R.string.tv_select_player_die));

        tvCountdown = (TextView) view.findViewById(R.id.tv_countdown);
        long millis = AppController.getInstance().getPrefManager().getTimerDiscuss() * 60 * 1000;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        tvCountdown.setText(hms);//set text

        TextView tvDiscuss = LayoutInit.createTextViewWrap(getActivity());
        tvDiscuss.setText(resources.getString(R.string.tv_discuss_history));
        lnHistory.addView(tvDiscuss);
        ((GamePlay) getActivity()).updateListMadScientist();
        ((GamePlay) getActivity()).updateListKnightKill();

        //show PlayerGrandmotherSelect
        if (((GamePlay) getActivity()).getListPlayerGrandmotherSelect().size() > 0) {
            String content = LocaleHelper.getLangResources(getActivity()).getString(R.string.dialog_player_grandmother_select) + " ";
            for (Player player : ((GamePlay) getActivity()).getListPlayerGrandmotherSelect()) {
                content = content + player.getName() + ", ";
            }
            new MaterialDialog.Builder(getActivity())
                    .title(LocaleHelper.getLangResources(getActivity()).getString(R.string.dialog_player_grandmother_select_title))
                    .content(content.substring(0, content.length() - 2))
                    .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                    .show();
        }
    }

    // Set Listeners
    private void setListeners() {
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        switch (v.getId()) {
            case R.id.btn_next:
                if (btnNext.getText().equals(resources.getString(R.string.btn_discuss_text_start_timer))) {
                    btnNext.setText(resources.getString(R.string.btn_discuss_text) + " " + ((GamePlay) getActivity()).getNight());
                    //If CountDownTimer is null then start timer
                    if (countDownTimer == null) {
                        startTimer(AppController.getInstance().getPrefManager().getTimerDiscuss() * 60 * 1000);//start countdown
                    }
                } else {
                    for (Player player : hangedPlayerAdapter.getPlayerList()) {
                        if (player.isHanged()) {
                            switch (player.getRole().getId()) {
                                case ListRoles.FLAG_OLD_VILLAGE:
                                    ((GamePlay) getActivity()).setNumberOldVillageWolfKill(2);
                                    break;
                                case ListRoles.FLAG_HUNTER:
                                    ((GamePlay) getActivity()).setHunterDied(true);
                                    break;
                                case ListRoles.FLAG_MAD_SCIENTIST:
                                    ((GamePlay) getActivity()).setMadScientistDie(true);
                                case ListRoles.FLAG_KNIGHT:
                                    ((GamePlay) getActivity()).setKnightDie(true);
                                    break;
                                case ListRoles.FLAG_IDIOT:
                                    player.setHanged(false);
                                    TextView tvIdiotNotHanged = LayoutInit.createTextView(getActivity());
                                    tvIdiotNotHanged.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    tvIdiotNotHanged.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.idiot_no_hanged));
                                    tvIdiotNotHanged.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                                    lnHistory.addView(tvIdiotNotHanged);
                                    ((GamePlay) getActivity()).setIdiotDie(true);
                                    break;
                                case ListRoles.FLAG_WOLF_BABY:
                                    ((GamePlay) getActivity()).setWolfBabyDie(true);
                                    break;
                            }
                            lnHistory.addView(LayoutInit.addPlayerToLN(getActivity(), player));
                        }
                    }
                    LinearLayout ln = LayoutInit.createLinearLayoutHorizontal(getActivity());
                    ln.setOrientation(LinearLayout.VERTICAL);

                    TextView tv = LayoutInit.createTextViewWrap(getActivity());
                    tv.setText(resources.getString(R.string.tv_day) + " " + (((GamePlay) getActivity()).getNight()));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
                    ln.addView(tv);

                    HorizontalScrollView hs = LayoutInit.createHorizontalScrollView(getActivity());
                    hs.addView(lnHistory);
                    ln.addView(hs);
                    ((GamePlay) getActivity()).getListHistoryGame().add(new HistoryOne(tv, hs, false));
                    if (ListRoles.getInstance().getListWolf().size() == 0 || ListRoles.getInstance().getListWolf().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()
                            || ((GamePlay) getActivity()).getListPlayerCouple().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()) {
                        ((GamePlay) getActivity()).replaceEndGameFragment();
                    } else {
                        stopCountdown();
                        ((GamePlay) getActivity()).getListPlayerDieInNight().clear();
                        ((GamePlay) getActivity()).getListPlayerDieByPoisonInNight().clear();
                        ((GamePlay) getActivity()).getListPlayerNotDieInNight().clear();
                        ((GamePlay) getActivity()).updateListMadScientist();
                        ((GamePlay) getActivity()).updateListKnightKill();
                        ((GamePlay) getActivity()).nextRoles();
                    }
                }
                break;
        }
    }

    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    } //Start Countodwn method

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                tvCountdown.setText(hms);//set text
            }

            public void onFinish() {
                try {
                    tvCountdown.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_discuss_text_time_up)); //On finish change timer text
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownTimer = null;//set CountDownTimer to null
            }
        }.start();

    }
}
