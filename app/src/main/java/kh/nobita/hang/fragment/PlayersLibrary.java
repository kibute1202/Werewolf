package kh.nobita.hang.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.GridView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.OneMachine;
import kh.nobita.hang.adapter.PlayersLibraryGridAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.widget.GridSpacingItemDecoration;

public class PlayersLibrary extends Fragment implements OnClickListener {
    private String TAG = PlayersLibrary.class.getSimpleName();
    private View view;
    private Toolbar toolbar;
    private RecyclerView rvGrid;
    private FragmentManager fragmentManager;

    public PlayersLibrary() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_players_library, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        setHasOptionsMenu(true);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(LocaleHelper.getLangResources(getActivity()).getString(R.string.player_library_app_bar));
        ((OneMachine) getActivity()).setSupportActionBar(toolbar);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((OneMachine) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ListPlayers.getInstance(getActivity()).cleanFlagDeletePlayers();
        rvGrid = (RecyclerView) view.findViewById(R.id.gv_players_library);
        rvGrid.setLayoutManager( new GridLayoutManager(getActivity(), 2));
        rvGrid.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvGrid.setItemAnimator(new DefaultItemAnimator());
        rvGrid.setHasFixedSize(true);
        rvGrid.setAdapter(new PlayersLibraryGridAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getListPlayerLib()));
    }

    // Set Listeners
    private void setListeners() {
        // Set check listener over checkbox for showing and hiding password
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        (view.findViewById(R.id.fab_players_library)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        switch (v.getId()) {
            case R.id.fab_players_library:
                if (isCheckDelete()) {
                    new MaterialDialog.Builder(getActivity())
                            .title(resources.getString(R.string.dialog_delete_player_lib_title))
                            .content(resources.getString(R.string.dialog_delete_player_lib_content))
                            .positiveText(resources.getString(R.string.agree))
                            .negativeText(resources.getString(R.string.disagree))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                    ListPlayers.getInstance(getActivity()).deletePlayersLib();
                                    getActivity().onBackPressed();
                                }
                            })
                            .show();
                } else {
                    getActivity().onBackPressed();
                }
                break;
        }
    }

    private boolean isCheckDelete() {
        for (Player player : ListPlayers.getInstance(getActivity()).getListPlayerLib()) {
            if (player.isDelete()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_players_library, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resources resources = LocaleHelper.getLangResources(getActivity());
        switch (item.getItemId()) {
            case R.id.players_library_help:
                new MaterialDialog.Builder(getActivity())
                        .title(resources.getString(R.string.menu_title_player_lib))
                        .content(resources.getString(R.string.lib_dialog_help_content))
                        .positiveText(android.R.string.ok)
                        .show();
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