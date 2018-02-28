package kh.nobita.hang.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.activity.GamePlay;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;

public class InGameWolfSniffingAdapter extends RecyclerView.Adapter<InGameWolfSniffingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Player> playerList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namePlayer;
        public CircleImageView profileImage;

        public MyViewHolder(final View view) {
            super(view);
            namePlayer = (TextView) view.findViewById(R.id.name_player);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }

    public List<Player> getItemList() {
        return playerList;
    }

    public void setItemList(List<Player> roomList) {
        this.playerList = roomList;
    }

    public InGameWolfSniffingAdapter(Context mContext, List<Player> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player_in_roles_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.namePlayer.setText(playerList.get(position).getName());

        if (playerList.get(position).getPathProfile().trim().equals("")) {
            holder.profileImage.setImageResource(R.drawable.ic_name_player);
        } else {
            if (GamePermissions.checkPermissionReadExternalStorage(mContext)) {
                holder.profileImage.setImageBitmap(BitmapFactory.decodeFile(playerList.get(position).getPathProfile()));
            } else {
                holder.profileImage.setImageResource(R.drawable.ic_name_player);
            }
        }
        holder.namePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
    }

    private void showRoleImfo(int position) {
        boolean isShowTrue;
        Resources resources = LocaleHelper.getLangResources(mContext);
        switch (playerList.get(position).getRole().getId()) {
            case ListRoles.FLAG_OLD_VILLAGE:
            case ListRoles.FLAG_PROPHET:
                isShowTrue = true;
                break;
            default:
                isShowTrue = false;
                break;
        }
        if (((GamePlay) mContext).checkRoleInverse(ListRoles.FLAG_WOLF_SNIFFING)) {
            isShowTrue = (isShowTrue ? false : true);
        }
        String content = "";
        if (isShowTrue) {
            content = resources.getString(R.string.wolf_sniffing_show_content);
        } else {
            content = resources.getString(R.string.wolf_sniffing_show_not_content);
        }
        new MaterialDialog.Builder(mContext)
                .title(resources.getString(R.string.wolf_sniffing_show_tile))
                .content(content)
                .positiveText(resources.getString(R.string.agree))
                .show();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
