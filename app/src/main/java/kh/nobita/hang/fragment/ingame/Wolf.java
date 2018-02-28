package kh.nobita.hang.fragment.ingame;

import android.content.res.Resources;
import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.InGameWolfKillPlayerAdapter;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Player;

public class Wolf extends Fragment implements OnClickListener {
    private String TAG = Wolf.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerInRoles, rvPlayerSelect;
    private CircleImageView profileImage;
    private TextView tvNameRole, tvComment;
    private Button btnNext;
    private LinearLayout lnHistory;

    private InGameWolfKillPlayerAdapter adapterKill;

    public Wolf() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_werewolf_roles, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        lnHistory = LayoutInit.createLinearLayoutHorizontal(getActivity());
        rvPlayerInRoles = (RecyclerView) view.findViewById(R.id.rv_player_in_roles);
        rvPlayerInRoles.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerInRoles = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerInRoles.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvPlayerInRoles.setAdapter(new WerewolfRolesAdapter(getActivity(), ListRoles.getInstance().getListWolf()));
        rvPlayerInRoles.setLayoutManager(myLayoutManagerPlayerInRoles);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setImageResource(ListRoles.getInstance().getListRoles().get(ListRoles.getInstance().getRoleInListRoles(ListRoles.FLAG_WOLF)).getPathImage());
        CircleImageView cvWolf = LayoutInit.createCircleView(getActivity());
        cvWolf.setImageResource(ListRoles.getInstance().getListRoles().get(ListRoles.getInstance().getRoleInListRoles(ListRoles.FLAG_WOLF)).getPathImage());
        TextView tvKill = LayoutInit.createTextView(getActivity());
        tvKill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvKill.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_bite));
        lnHistory.addView(cvWolf);
        lnHistory.addView(tvKill);


        tvNameRole = (TextView) view.findViewById(R.id.tv_name_role);
        tvNameRole.setText(ListRoles.getInstance().getListRoles().get(ListRoles.getInstance().getRoleInListRoles(ListRoles.FLAG_WOLF)).getName());

        rvPlayerSelect = (RecyclerView) view.findViewById(R.id.rv_player_select);
        rvPlayerSelect.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerSelect = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerSelect.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterKill = new InGameWolfKillPlayerAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getAllPlayerLive());
        rvPlayerSelect.setAdapter(adapterKill);
        rvPlayerSelect.setLayoutManager(myLayoutManagerPlayerSelect);
        ((GamePlay) getActivity()).getListPlayerWolfBite().clear();

        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvComment.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.wolf_tv_commend));

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done));

        if (((GamePlay) getActivity()).isWolfBabyDie()) {
            Resources resources = LocaleHelper.getLangResources(getActivity());
            new MaterialDialog.Builder(getActivity())
                    .title(resources.getString(R.string.wolf_tile))
                    .content(resources.getString(R.string.wolf_baby_die_content))
                    .positiveText(resources.getString(R.string.agree))
                    .show();
        }
    }

    // Set Listeners
    private void setListeners() {
        profileImage.setOnClickListener(this);
        tvNameRole.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
            case R.id.tv_name_role:
                new MaterialDialog.Builder(getActivity())
                        .title(ListRoles.getInstance().getListRoles().get(ListRoles.getInstance().getRoleInListRoles(ListRoles.FLAG_WOLF)).getName())
                        .content(ListRoles.getInstance().getListRoles().get(ListRoles.getInstance().getRoleInListRoles(ListRoles.FLAG_WOLF)).getRole())
                        .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                        .show();
                break;
            case R.id.btn_next:
                if (((GamePlay) getActivity()).isWolfBiteHuiSick()) {
                    ((GamePlay) getActivity()).setWolfBiteHuiSick(false);
                    TextView tvWolfBiteHui = LayoutInit.createTextView(getActivity());
                    tvWolfBiteHui.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tvWolfBiteHui.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.wolf_bite_sick_hui));
                    tvWolfBiteHui.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                    lnHistory.addView(tvWolfBiteHui);
                } else {
                    for (Player player : adapterKill.getPlayerList()) {
                        if (player.isWolfBites()) {
                            if (!checkWolfInverse()) {
                                if (player.getRole().getId() != ListRoles.FLAG_NOMADS || ((GamePlay) getActivity()).getPlayerNomads() == null) {
                                    ((GamePlay) getActivity()).getListPlayerWolfBite().add(player);
                                }
                            }
                            lnHistory.addView(LayoutInit.addPlayerToLN(getActivity(), player));
                        }
                    }
                }
                if (checkWolfInverse()) {
                    TextView tvInversePersonSelect = LayoutInit.createTextView(getActivity());
                    tvInversePersonSelect.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tvInversePersonSelect.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.inverse_person_select));
                    tvInversePersonSelect.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                    lnHistory.addView(tvInversePersonSelect);
                }
                HorizontalScrollView hs = LayoutInit.createHorizontalScrollView(getActivity());
                if (lnHistory.getParent() != null)
                    ((ViewGroup) lnHistory.getParent()).removeView(lnHistory);
                hs.addView(lnHistory);
                ((GamePlay) getActivity()).getListHistoryOne().add(hs);
                ((GamePlay) getActivity()).setWolfBabyDie(false);
                ((GamePlay) getActivity()).nextRoles();
                break;
        }
    }

    private boolean checkWolfInverse() {
        boolean resulrt = ((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WOLF) || ((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WOLF_BOSS)
                || ((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_SNOWFLAKES) || ((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WOLF_BABY);
        return resulrt;
    }
}
