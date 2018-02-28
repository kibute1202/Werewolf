package kh.nobita.hang.fragment.ingame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.adapter.WerewolfRolesAdapter;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Roles.Role;

public class Builder extends Fragment implements OnClickListener {
    private String TAG = Builder.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private RecyclerView rvPlayerInRoles, rvPlayerSelect;
    private CircleImageView profileImage;
    private TextView tvNameRole, tvComment;
    private Button btnNext;
    private Role role;

    public Builder() {

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
        role = ListRoles.getInstance().getListRolesUsing().get(ListRoles.getInstance().getRoleInListShuffle(ListRoles.FLAG_BUILDER));
        fragmentManager = getActivity().getSupportFragmentManager();
        rvPlayerInRoles = (RecyclerView) view.findViewById(R.id.rv_player_in_roles);
        rvPlayerInRoles.setHasFixedSize(true);
        LinearLayoutManager myLayoutManagerPlayerInRoles = new LinearLayoutManager(getActivity());
        myLayoutManagerPlayerInRoles.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPlayerInRoles.setAdapter(new WerewolfRolesAdapter(getActivity(), role.getListPlayerLive()));
        rvPlayerInRoles.setLayoutManager(myLayoutManagerPlayerInRoles);

        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        profileImage.setImageResource(role.getPathImage());

        tvNameRole = (TextView) view.findViewById(R.id.tv_name_role);
        tvNameRole.setText(role.getName());

        rvPlayerSelect = (RecyclerView) view.findViewById(R.id.rv_player_select);
        rvPlayerSelect.setVisibility(View.GONE);

        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        tvComment.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.brother_sister));

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
                ((GamePlay) getActivity()).nextRoles();
                break;
        }
    }
}
