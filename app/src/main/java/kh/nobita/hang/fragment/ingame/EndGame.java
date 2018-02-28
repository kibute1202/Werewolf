package kh.nobita.hang.fragment.ingame;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.HistoryOne;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Roles.ListRoles;

public class EndGame extends Fragment implements OnClickListener {
    private String TAG = EndGame.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private TextView tvComment;
    private Button btnNewGame;
    private LinearLayout lnLayoutHistory;

    public EndGame() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_endgame, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        Resources resources = LocaleHelper.getLangResources(getActivity());

        tvComment = (TextView) view.findViewById(R.id.tv_endgame_comment);

        btnNewGame = (Button) view.findViewById(R.id.btn_new_game);
        btnNewGame.setText(resources.getString(R.string.text_btn_new_game));

        lnLayoutHistory = (LinearLayout) view.findViewById(R.id.layout_history_endgame);

        for (HistoryOne history : ((GamePlay) getActivity()).getListHistoryGame()) {
            LinearLayout ln = LayoutInit.createLinearLayoutHorizontal(getActivity());
            ln.setOrientation(LinearLayout.VERTICAL);
            if (history.getTvTime().getParent() != null)
                ((ViewGroup) history.getTvTime().getParent()).removeView(history.getTvTime());
            ln.addView(history.getTvTime());
            for (HorizontalScrollView hs : history.getListhistory()) {
                if (hs.getParent() != null)
                    ((ViewGroup) hs.getParent()).removeView(hs);
                ln.addView(hs);
            }
            if (history.isNight()) {
                ln.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorDetail));
            }
            lnLayoutHistory.addView(ln);
        }
        if (ListRoles.getInstance().getListWolf().size() == 0) {
            tvComment.setText(resources.getString(R.string.text_win_village));
        } else if (ListRoles.getInstance().getListWolf().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()) {
            tvComment.setText(resources.getString(R.string.text_win_wolf));
        }else if (((GamePlay) getActivity()).getListPlayerCouple().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()){
            tvComment.setText(resources.getString(R.string.text_win_couple));
        }
    }

    // Set Listeners

    private void setListeners() {
        btnNewGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_game:
                getActivity().onBackPressed();
                break;
        }
    }
}
