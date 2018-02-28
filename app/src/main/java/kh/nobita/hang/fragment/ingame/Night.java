package kh.nobita.hang.fragment.ingame;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.NightGridAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.widget.GridSpacingItemDecoration;

public class Night extends Fragment implements OnClickListener {
    private String TAG = Night.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerInNight;
    private TextView tvNightComment;
    private Button btnNext;

    public Night() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_night, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        Resources resources = LocaleHelper.getLangResources(getActivity());
        rvPlayerInNight = (RecyclerView) view.findViewById(R.id.gv_player_in_night);
        rvPlayerInNight.setLayoutManager( new GridLayoutManager(getActivity(), 2));
        rvPlayerInNight.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvPlayerInNight.setItemAnimator(new DefaultItemAnimator());
        rvPlayerInNight.setHasFixedSize(true);
        rvPlayerInNight.setAdapter(new NightGridAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getAllPlayerLive()));

        tvNightComment = (TextView) view.findViewById(R.id.tv_night_comment);
        tvNightComment.setText(resources.getString(R.string.tv_night) + " " + ((GamePlay) getActivity()).getNight());

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(resources.getString(R.string.btn_night_text) + " " + ((GamePlay) getActivity()).getNight());
    }

    // Set Listeners
    private void setListeners() {
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                ((GamePlay) getActivity()).nextRoles();
                break;
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
