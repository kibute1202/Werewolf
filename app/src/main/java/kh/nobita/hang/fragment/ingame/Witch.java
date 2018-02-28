package kh.nobita.hang.fragment.ingame;

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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.InGameSecurityPlayerAdapter;
import kh.nobita.hang.adapter.InGameWitchPlayerAdapter;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Roles.Role;

public class Witch extends Fragment implements OnClickListener {
    private String TAG = Witch.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerInRoles, rvPlayerSelect;
    private CircleImageView profileImage, witchPoison, witchElixir;
    private TextView tvNameRole, tvComment, tvPoison, tvElixir;
    private Button btnNext;
    private Role role;
    private InGameWitchPlayerAdapter adapterWitch;
    private LinearLayout lnHistory;

    public Witch() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_witch_roles, container, false);
        initViews();
        setListeners();
        return view;
    }

    // Initiate Views
    private void initViews() {
        role = ListRoles.getInstance().getListRolesUsing().get(ListRoles.getInstance().getRoleInListShuffle(ListRoles.FLAG_WITCH));
        fragmentManager = getActivity().getSupportFragmentManager();
        lnHistory = LayoutInit.createLinearLayoutHorizontal(getActivity());
        rvPlayerInRoles = (RecyclerView) view.findViewById(R.id.rv_player_in_roles);
        rvPlayerInRoles.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerInRoles = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerInRoles.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPlayerInRoles.setAdapter(new WerewolfRolesAdapter(getActivity(), role.getListPlayerLive()));
        rvPlayerInRoles.setLayoutManager(myLayoutManagerPlayerInRoles);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setImageResource(role.getPathImage());
        CircleImageView cvWitch = LayoutInit.createCircleView(getActivity());
        cvWitch.setImageResource(role.getPathImage());
        lnHistory.addView(cvWitch);

        tvNameRole = (TextView) view.findViewById(R.id.tv_name_role);
        tvNameRole.setText(role.getName());

        rvPlayerSelect = (RecyclerView) view.findViewById(R.id.rv_player_select);
        rvPlayerSelect.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerSelect = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerSelect.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterWitch = new InGameWitchPlayerAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getAllPlayerLive(), 0);
        rvPlayerSelect.setAdapter(adapterWitch);
        rvPlayerSelect.setLayoutManager(myLayoutManagerPlayerSelect);
        rvPlayerSelect.setVisibility(View.GONE);

        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvComment.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.wolf_tv_commend));

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done));

        witchPoison = (CircleImageView) view.findViewById(R.id.witch_poison);
        witchElixir = (CircleImageView) view.findViewById(R.id.witch_elixir);
        tvPoison = (TextView) view.findViewById(R.id.tv_poison);
        tvPoison.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_poison));
        tvElixir = (TextView) view.findViewById(R.id.tv_elixir);
        tvElixir.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_elixir));
        if (((GamePlay) getActivity()).isPoison()) {
            tvPoison.setVisibility(View.GONE);
            witchPoison.setVisibility(View.GONE);
        }
        if (((GamePlay) getActivity()).isElixir()) {
            tvElixir.setVisibility(View.GONE);
            witchElixir.setVisibility(View.GONE);
        }
        if (role.getListPlayerLive().size() == 0) {
            tvPoison.setVisibility(View.GONE);
            witchPoison.setVisibility(View.GONE);
            tvElixir.setVisibility(View.GONE);
            witchElixir.setVisibility(View.GONE);
        }
    }

    // Set Listeners
    private void setListeners() {
        profileImage.setOnClickListener(this);
        tvNameRole.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        witchPoison.setOnClickListener(this);
        witchElixir.setOnClickListener(this);
        tvPoison.setOnClickListener(this);
        tvElixir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
            case R.id.tv_name_role:
                new MaterialDialog.Builder(getActivity())
                        .title(role.getName())
                        .content(role.getRole())
                        .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                        .show();
                break;
            case R.id.witch_poison:
            case R.id.tv_poison:
                clickPoison();
                break;
            case R.id.witch_elixir:
            case R.id.tv_elixir:
                clickElixir();
                break;
            case R.id.btn_next:
                TextView tvWitch = LayoutInit.createTextView(getActivity());
                tvWitch.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                switch (adapterWitch.getFlagWitch()) {
                    case InGameWitchPlayerAdapter.FLAG_POISON:
                        tvWitch.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_poison));
                        lnHistory.addView(tvWitch);
                        break;
                    case InGameWitchPlayerAdapter.FLAG_ELIXIR:
                        tvWitch.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_assist));
                        lnHistory.addView(tvWitch);
                        break;
                }
                boolean isCancel = true;
                for (Player player : adapterWitch.getPlayerList()) {
                    switch (adapterWitch.getFlagWitch()) {
                        case InGameWitchPlayerAdapter.FLAG_POISON:
                            if (player.isPoison()) {
                                isCancel = false;
                                if (((GamePlay) getActivity()).getNumberOldVillageWolfKill() < 2) {
                                    if (!((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WITCH)) {
                                        if (player.getRole().getId() != ListRoles.FLAG_NOMADS || ((GamePlay) getActivity()).getPlayerNomads() == null) {
                                            ((GamePlay) getActivity()).getListPlayerDieByPoisonInNight().add(player);
                                        }
                                    }
                                }
                                lnHistory.addView(LayoutInit.addPlayerToLN(getActivity(), player));
                            }
                            break;
                        case InGameWitchPlayerAdapter.FLAG_ELIXIR:
                            if (player.isSecurity()) {
                                isCancel = false;
                                if (((GamePlay) getActivity()).getNumberOldVillageWolfKill() < 2) {
                                    if (!((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WITCH)) {
                                        ((GamePlay) getActivity()).getListPlayerNotDieInNight().add(player);
                                    }
                                }
                                lnHistory.addView(LayoutInit.addPlayerToLN(getActivity(), player));
                            }
                            break;
                    }
                }
                if (isCancel && rvPlayerSelect.getVisibility() == View.VISIBLE) {
                    switch (adapterWitch.getFlagWitch()) {
                        case InGameWitchPlayerAdapter.FLAG_POISON:
                            clickPoison();
                            break;
                        case InGameWitchPlayerAdapter.FLAG_ELIXIR:
                            clickElixir();
                            break;
                    }
                }
                if (((GamePlay) getActivity()).getNumberOldVillageWolfKill() >= 2) {
                    TextView tvOldVillageDied = LayoutInit.createTextView(getActivity());
                    tvOldVillageDied.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tvOldVillageDied.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.old_village_died));
                    tvOldVillageDied.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                    lnHistory.addView(tvOldVillageDied);
                } else {
                    if (((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_WITCH)) {
                        TextView tvInversePersonSelect = LayoutInit.createTextView(getActivity());
                        tvInversePersonSelect.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        tvInversePersonSelect.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.inverse_person_select));
                        tvInversePersonSelect.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                        lnHistory.addView(tvInversePersonSelect);
                    }
                }
                HorizontalScrollView hs = LayoutInit.createHorizontalScrollView(getActivity());
                hs.addView(lnHistory);
                if (role.getListPlayerLive().size() > 0) {
                    ((GamePlay) getActivity()).getListHistoryOne().add(hs);
                }
                ((GamePlay) getActivity()).nextRoles();
                break;
        }
    }

    private void clickPoison() {
        ((GamePlay) getActivity()).setPoison(((GamePlay) getActivity()).isPoison() ? false : true);
        if (((GamePlay) getActivity()).isPoison()) {
            if (rvPlayerSelect.getVisibility() == View.VISIBLE) {
                clickElixir();
            }
            tvPoison.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
            ListPlayers.getInstance(getActivity()).clearFunction();
            adapterWitch = new InGameWitchPlayerAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getAllPlayerLive(), InGameWitchPlayerAdapter.FLAG_POISON);
            rvPlayerSelect.setAdapter(adapterWitch);
            rvPlayerSelect.setVisibility(View.VISIBLE);
        } else {
            tvPoison.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
            adapterWitch = new InGameWitchPlayerAdapter(getActivity(), new ArrayList<Player>(), InGameWitchPlayerAdapter.FLAG_POISON);
            rvPlayerSelect.setAdapter(adapterWitch);
            rvPlayerSelect.setVisibility(View.GONE);
        }
    }

    private void clickElixir() {
        ((GamePlay) getActivity()).setElixir(((GamePlay) getActivity()).isElixir() ? false : true);
        if (((GamePlay) getActivity()).isElixir()) {
            if (rvPlayerSelect.getVisibility() == View.VISIBLE) {
                clickPoison();
            }
            tvElixir.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
            ListPlayers.getInstance(getActivity()).clearFunction();
            adapterWitch = new InGameWitchPlayerAdapter(getActivity(), ((GamePlay) getActivity()).listPlayerWolfBiteDie(), InGameWitchPlayerAdapter.FLAG_ELIXIR);
            rvPlayerSelect.setAdapter(adapterWitch);
            rvPlayerSelect.setVisibility(View.VISIBLE);
        } else {
            tvElixir.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorBlack));
            adapterWitch = new InGameWitchPlayerAdapter(getActivity(), new ArrayList<Player>(), InGameWitchPlayerAdapter.FLAG_ELIXIR);
            rvPlayerSelect.setAdapter(adapterWitch);
            rvPlayerSelect.setVisibility(View.GONE);
        }
    }
}
