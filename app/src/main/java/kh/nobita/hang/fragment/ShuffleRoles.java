package kh.nobita.hang.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.Role;

public class ShuffleRoles extends Fragment implements OnClickListener {
    private String TAG = ShuffleRoles.class.getSimpleName();
    private View view;
    private FragmentManager fragmentManager;
    private Button btnShuffle;
    private TextView tvName, tvComment;
    private CircleImageView profileImage;

    private int position;
    private Role role;

    public ShuffleRoles() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shuffle_roles, container, false);
        initViews();
        setListeners();
        showPlayer();
        return view;
    }

    private void showPlayer() {
        profileImage.setAnimation(null);
        Player player = ListPlayers.getInstance(getActivity()).getListPlayerFinal().get(position);
        if (player.getPathProfile().equals("")) {
            profileImage.setImageResource(R.drawable.ic_name_player);
        } else {
            if (GamePermissions.checkPermissionReadExternalStorage(getActivity())) {
                profileImage.setImageBitmap(BitmapFactory.decodeFile(player.getPathProfile()));
            } else {
                profileImage.setImageResource(R.drawable.ic_name_player);
            }
        }
        tvName.setText(player.getName());
        tvComment.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.shuffle_player_help));
        btnShuffle.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_show_role));
    }

    private void showRole() {
        role = ListRoles.getInstance().addPlayerToRole(ListPlayers.getInstance(getActivity()).getListPlayerFinal().get(position), position);
        profileImage.setImageResource(role.getPathImage());
        profileImage.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.image_show));

        tvName.setText(role.getName());
        tvComment.setText(role.getRole());
        if (position == ListRoles.getInstance().getListShuffle().size() - 1) {
            btnShuffle.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_start_game));
        } else {
            btnShuffle.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done));
        }
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvComment = (TextView) view.findViewById(R.id.tv_comment);
        btnShuffle = (Button) view.findViewById(R.id.btn_shuffle);
        btnShuffle.setText(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_role_start));
        position = 0;
    }

    // Set Listeners
    private void setListeners() {
        profileImage.setOnClickListener(this);
        tvName.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        btnShuffle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
            case R.id.tv_name:
            case R.id.tv_comment:
                if (btnShuffle.getText().equals(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done))) {
                    new MaterialDialog.Builder(getActivity())
                            .title(role.getName())
                            .content(role.getRole())
                            .positiveText(LocaleHelper.getLangResources(getActivity()).getString(R.string.agree))
                            .show();
                }
                break;
            case R.id.btn_shuffle:
                if (btnShuffle.getText().equals(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_done))) {
                    position++;
                    showPlayer();
                } else if (btnShuffle.getText().equals(LocaleHelper.getLangResources(getActivity()).getString(R.string.btn_shuffle_text_show_role))) {
                    showRole();
                } else {
                    ListPlayers.getInstance(getActivity()).cleanAllFunction();
                    ((GamePlay) getActivity()).replaceNightFragment();
                }
                break;
        }
    }
}
