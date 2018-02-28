package kh.nobita.hang.fragment;

import android.content.Intent;
import android.content.res.Resources;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.activity.OneMachine;
import kh.nobita.hang.adapter.RolesGridAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.widget.GridSpacingItemDecoration;

public class Roles extends Fragment implements OnClickListener {
    private String TAG = Roles.class.getSimpleName();
    private View view;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private RecyclerView rvGrid;

    public Roles() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_roles, container, false);
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

        ((TextView) view.findViewById(R.id.tv_text_title)).setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.roles_title));

        ListRoles.getInstance().cleanAllFunction();
        ListRoles.getInstance().getListRoles().get(0).setNumberPlayer(ListPlayers.getInstance(getActivity()).getListPlayerFinal().size());

        rvGrid = (RecyclerView) view.findViewById(R.id.gv_roles);
        rvGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvGrid.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvGrid.setItemAnimator(new DefaultItemAnimator());
        rvGrid.setHasFixedSize(true);
        rvGrid.setAdapter(new RolesGridAdapter(getActivity(), ListRoles.getInstance().getListRoles()));
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
                    collapsingToolbar.setTitle(LocaleHelper.getLangResources(getActivity()).getString(R.string.roles_app_bar));
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
        (view.findViewById(R.id.btn_roles_start)).setOnClickListener(this);
        ((Button) (view.findViewById(R.id.btn_roles_start))).setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_role_start));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_roles_start:
                boolean isCancel = false;
                Resources resources = LocaleHelper.getLangResources(getActivity());
                if (ListRoles.getInstance().getNumberWolfRole() == 0) {
                    isCancel = true;
                    new MaterialDialog.Builder(getActivity())
                            .title(resources.getString(R.string.roles_dialog_error_title))
                            .content(resources.getString(R.string.roles_dialog_error_wolf_min_content) + " " + 1)
                            .positiveText(resources.getString(R.string.agree))
                            .show();
                }
                if (ListRoles.getInstance().getNumberWolfRole() == ListPlayers.getInstance(getActivity()).getListPlayerFinal().size()) {
                    isCancel = true;
                    new MaterialDialog.Builder(getActivity())
                            .title(resources.getString(R.string.roles_dialog_error_title))
                            .content(resources.getString(R.string.roles_dialog_error_wolf_max_content) + " " + (ListPlayers.getInstance(getActivity()).getListPlayerFinal().size() - 1))
                            .positiveText(resources.getString(R.string.agree))
                            .show();
                }
                if (!isCancel) {
                    ListRoles.getInstance().createListRolesUsing();
                    startActivity(new Intent(getActivity(), GamePlay.class));
                }
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