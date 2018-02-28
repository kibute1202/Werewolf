package kh.nobita.hang.fragment.ingame;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.HistoryOne;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Roles.ListRoles;

public class Day extends Fragment implements OnClickListener {
    private String TAG = Day.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerDead;
    private TextView tvComment, tvNumnerDead;
    private Button btnNext;
    private LinearLayout lnLayoutHistory;
    private LinearLayout ln;

    public Day() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_day, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        Resources resources = LocaleHelper.getLangResources(getActivity());
        rvPlayerDead = (RecyclerView) view.findViewById(R.id.rv_player_dead);
        rvPlayerDead.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerInRoles = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerInRoles.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPlayerDead.setAdapter(new WerewolfRolesAdapter(getActivity(), ((GamePlay) getActivity()).updateDead()));
        rvPlayerDead.setLayoutManager(myLayoutManagerPlayerInRoles);

        tvComment = (TextView) view.findViewById(R.id.tv_day_comment);
        tvComment.setText(resources.getString(R.string.tv_day) + " " + ((GamePlay) getActivity()).getNight());

        tvNumnerDead = (TextView) view.findViewById(R.id.tv_numner_dead);
        tvNumnerDead.setText(resources.getString(R.string.tv_text_number_dead) + " " + ((GamePlay) getActivity()).getListPlayerDieInNight().size());

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(resources.getString(R.string.btn_day_text));

        lnLayoutHistory = (LinearLayout) view.findViewById(R.id.layout_history);

        ln = LayoutInit.createLinearLayoutHorizontal(getActivity());
        ln.setOrientation(LinearLayout.VERTICAL);

        TextView tv = LayoutInit.createTextViewWrap(getActivity());
        tv.setText(resources.getString(R.string.tv_night) + " " + (((GamePlay) getActivity()).getNight() - 1));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
        ln.addView(tv);
        for (HorizontalScrollView hs : ((GamePlay) getActivity()).getListHistoryOne()) {
            ln.addView(hs);
        }
        lnLayoutHistory.addView(ln);
        ((GamePlay) getActivity()).getListHistoryGame().add(new HistoryOne(tv, ((GamePlay) getActivity()).getListHistoryOne(), true));
        ((GamePlay) getActivity()).setListHistoryOne(new ArrayList<HorizontalScrollView>());
    }

    // Set Listeners
    private void setListeners() {
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                ln.removeAllViews();
                lnLayoutHistory.removeAllViews();
                if (ListPlayers.getInstance(getActivity()).getAllPlayerLive().size() == 1 || ListRoles.getInstance().getListWolf().size() == 0
                        || ListRoles.getInstance().getListWolf().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()
                        || ((GamePlay) getActivity()).getListPlayerCouple().size() == ListPlayers.getInstance(getActivity()).getAllPlayerLive().size()) {
                    ((GamePlay) getActivity()).replaceEndGameFragment();
                } else {
                    ((GamePlay) getActivity()).replaceDiscussFragment();
                }
                break;
        }
    }
}
