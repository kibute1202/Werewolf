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

public class InGameWitchPlayerAdapter extends RecyclerView.Adapter<InGameWitchPlayerAdapter.MyViewHolder> {
    public static final int FLAG_POISON = 1;
    public static final int FLAG_ELIXIR = FLAG_POISON + 1;

    private Context mContext;
    private List<Player> playerList;
    private int flagWitch = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namePlayer;
        public CircleImageView profileImage;

        public MyViewHolder(final View view) {
            super(view);
            namePlayer = (TextView) view.findViewById(R.id.name_player);
            profileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }

    public InGameWitchPlayerAdapter(Context mContext, List<Player> playerList, int flag) {
        this.mContext = mContext;
        this.playerList = playerList;
        this.flagWitch = flag;
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
            if (GamePermissions.checkPermissionReadExternalStorage(mContext)){
                holder.profileImage.setImageBitmap(BitmapFactory.decodeFile(playerList.get(position).getPathProfile()));
            }else{
                holder.profileImage.setImageResource(R.drawable.ic_name_player);
            }
        }

        switch (flagWitch) {
            case FLAG_POISON:
                if (playerList.get(position).isPoison()) {
                    holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                } else {
                    holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
                }
                break;
            case FLAG_ELIXIR:
                if (playerList.get(position).isSecurity()) {
                    holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                } else {
                    holder.namePlayer.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlack));
                }
                break;
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
                selectPlayer(position);
                return false;
            }
        });
        holder.profileImage.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                selectPlayer(position);
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

    private void selectPlayer(int position) {
        Resources resources = LocaleHelper.getLangResources(mContext);
        switch (flagWitch) {
            case FLAG_POISON:
                if (playerList.get(position).isPoison()) {
                    playerList.get(position).setPoison(false);
                } else {
                    if (getNumberWitchPoison() < AppController.getInstance().getPrefManager().getNumberWitchPoison()) {
                        playerList.get(position).setPoison(true);
                    } else {
                        new MaterialDialog.Builder(mContext)
                                .title(resources.getString(R.string.witch_poison_error_title))
                                .content(resources.getString(R.string.witch_poison_error_content) + " " + AppController.getInstance().getPrefManager().getNumberWitchPoison())
                                .positiveText(resources.getString(R.string.agree))
                                .show();
                    }
                }
                break;
            case FLAG_ELIXIR:
                if (playerList.get(position).isSecurity()) {
                    playerList.get(position).setSecurity(false);
                } else {
                    if (getNumberWitchAssist() < AppController.getInstance().getPrefManager().getNumberWitchAssist()) {
                        playerList.get(position).setSecurity(true);
                    } else {
                        new MaterialDialog.Builder(mContext)
                                .title(resources.getString(R.string.witch_elixir_error_title))
                                .content(resources.getString(R.string.witch_elixir_error_content) + " " + AppController.getInstance().getPrefManager().getNumberWitchAssist())
                                .positiveText(resources.getString(R.string.agree))
                                .show();
                    }
                }
                break;
        }
        this.notifyDataSetChanged();
    }

    private int getNumberWitchPoison() {
        int number = 0;
        for (Player player : playerList) {
            if (player.isWolfBites()) {
                number = number + 1;
            }
        }
        return number;
    }

    private int getNumberWitchAssist() {
        int number = 0;
        for (Player player : playerList) {
            if (player.isSecurity()) {
                number = number + 1;
            }
        }
        return number;
    }

    public int getFlagWitch() {
        return flagWitch;
    }

    public void setFlagWitch(int flagWitch) {
        this.flagWitch = flagWitch;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
