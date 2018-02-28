package kh.nobita.hang.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.MultiplayerWifi;
import kh.nobita.hang.activity.OneMachine;
import kh.nobita.hang.adapter.PlayersGridAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.widget.GridSpacingItemDecoration;

public class Players extends Fragment implements OnClickListener {
    public static final int NUMBER_PLAYER_MIN = 6;
    private String TAG = Players.class.getSimpleName();
    private View view;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private RecyclerView rvGrid;
    private TextView tvTextTitle;

    public Players() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_players, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((OneMachine) getActivity()).setSupportActionBar(toolbar);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        initCollapsingToolbar();

        tvTextTitle = (TextView) view.findViewById(R.id.tv_text_title);
        tvTextTitle.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.players_title));

        rvGrid = (RecyclerView) view.findViewById(R.id.gv_players);
        rvGrid.setLayoutManager( new GridLayoutManager(getActivity(), 2));
        rvGrid.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvGrid.setItemAnimator(new DefaultItemAnimator());
        rvGrid.setAdapter(new PlayersGridAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getAllListPlayer()));
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_app_bar));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    // Set Listeners
    private void setListeners() {
        (view.findViewById(R.id.btn_players_next)).setOnClickListener(this);
        ((Button) (view.findViewById(R.id.btn_players_next))).setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_players_next));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        (view.findViewById(R.id.fab_players)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        switch (v.getId()) {
            case R.id.btn_players_next:
                if (ListPlayers.getInstance(getActivity()).getAllListPlayer().size() < NUMBER_PLAYER_MIN) {
                    new MaterialDialog.Builder(getActivity())
                            .title(resources.getString(R.string.player_app_bar))
                            .content(resources.getString(R.string.player_error_small_number))
                            .positiveText(resources.getString(R.string.agree))
                            .show();
                } else {
                    ((OneMachine) getActivity()).replaceRolesFragment();
                }
                break;

            case R.id.fab_players:
                ((OneMachine) getActivity()).replacePlayersAddFragment();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_players, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.players_library:
                ((OneMachine) getActivity()).replacePlayersLibraryFragment();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
