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

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LayoutInit;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.InGameSecurityPlayerAdapter;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Roles.Role;

public class Security extends Fragment implements OnClickListener {
    private String TAG = Security.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerInRoles, rvPlayerSelect;
    private CircleImageView profileImage;
    private TextView tvNameRole, tvComment;
    private Button btnNext;
    private Role role;
    private InGameSecurityPlayerAdapter adapterSecurity;
    private LinearLayout lnHistory;

    public Security() {

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
        role = ListRoles.getInstance().getListRolesUsing().get(ListRoles.getInstance().getRoleInListShuffle(ListRoles.FLAG_SECURITY));
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
        CircleImageView cvSecurity = LayoutInit.createCircleView(getActivity());
        cvSecurity.setImageResource(role.getPathImage());
        TextView tvSecurity = LayoutInit.createTextView(getActivity());
        tvSecurity.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvSecurity.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.text_security));
        lnHistory.addView(cvSecurity);
        lnHistory.addView(tvSecurity);

        tvNameRole = (TextView) view.findViewById(R.id.tv_name_role);
        tvNameRole.setText(role.getName());

        rvPlayerSelect = (RecyclerView) view.findViewById(R.id.rv_player_select);
        rvPlayerSelect.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerSelect = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerSelect.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterSecurity = new InGameSecurityPlayerAdapter(getActivity(), ListPlayers.getInstance(getActivity()).getListLimit(((GamePlay) getActivity()).getListPlayerSecurity()));
        rvPlayerSelect.setAdapter(adapterSecurity);
        rvPlayerSelect.setLayoutManager(myLayoutManagerPlayerSelect);
        ((GamePlay) getActivity()).getListPlayerSecurity().clear();

        if (role.getListPlayerLive().size() == 0) {
            rvPlayerSelect.setVisibility(View.GONE);
        }

        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvComment.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.wolf_tv_commend));

        btnNext = (Button) view.findViewById(R.id.btn_next);
        btnNext.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done));
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
                        .title(role.getName())
                        .content(role.getRole())
                        .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                        .show();
                break;
            case R.id.btn_next:
                for (Player player : adapterSecurity.getPlayerList()) {
                    if (player.isSecurity()) {
                        if (((GamePlay) getActivity()).getNumberOldVillageWolfKill() < 2) {
                            if (!((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_SECURITY)) {
                                ((GamePlay) getActivity()).getListPlayerSecurity().add(player);
                            }
                        }
                        lnHistory.addView(LayoutInit.addPlayerToLN(getActivity(), player));
                    }
                }
                if (((GamePlay) getActivity()).getNumberOldVillageWolfKill() >= 2) {
                    TextView tvOldVillageDied = LayoutInit.createTextView(getActivity());
                    tvOldVillageDied.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.old_village_died));
                    tvOldVillageDied.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tvOldVillageDied.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorRed));
                    lnHistory.addView(tvOldVillageDied);
                } else {
                    if (((GamePlay) getActivity()).checkRoleInverse(ListRoles.FLAG_SECURITY)) {
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
}
