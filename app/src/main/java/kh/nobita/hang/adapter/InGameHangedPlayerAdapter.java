package kh.nobita.hang.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.model.Player;

public class InGameHangedPlayerAdapter extends RecyclerView.Adapter<InGameHangedPlayerAdapter.MyViewHolder> {

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

    public InGameHangedPlayerAdapter(Context mContext, List<Player> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_player_in_roles_item, parent, false);

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
        if (playerList.get(position).isHanged()) {
            holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else {
            holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
        }
        holder.namePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleInfo(position);
            }
        });
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleInfo(position);
            }
        });

        holder.namePlayer.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                checkDie(position);
                return false;
            }
        });
        holder.profileImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                checkDie(position);
                return false;
            }
        });
    }

    private void showRoleInfo(int position) {
//        Resources resources = LocaleHelper.getLangResources(mContext);
//        new MaterialDialog.Builder(mContext)
//                .title(playerList.get(position).getRole().getName())
//                .content(playerList.get(position).getRole().getRole())
//                .positiveText(resources.getString(R.string.agree))
//                .show();
    }

    private void checkDie(int position) {
        Resources resources = LocaleHelper.getLangResources(mContext);
        if (playerList.get(position).isHanged()) {
            playerList.get(position).setHanged(false);
        } else {
            if (getNumberHanged() < AppController.getInstance().getPrefManager().getNumberHanged()) {
                playerList.get(position).setHanged(true);
            } else {
                new MaterialDialog.Builder(mContext)
                        .title(resources.getString(R.string.hanged_error_title))
                        .content(resources.getString(R.string.hanged_error_content) + " " + AppController.getInstance().getPrefManager().getNumberHanged())
                        .positiveText(resources.getString(R.string.agree))
                        .show();
            }
        }
        this.notifyDataSetChanged();
    }

    private int getNumberHanged() {
        int number = 0;
        for (Player player : playerList) {
            if (player.isHanged()) {
                number = number + 1;
            }
        }
        return number;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
