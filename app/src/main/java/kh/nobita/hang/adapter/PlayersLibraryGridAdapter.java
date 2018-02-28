package kh.nobita.hang.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.model.Player;

public class PlayersLibraryGridAdapter extends RecyclerView.Adapter<PlayersLibraryGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Player> playerList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView namePlayer = null;
        private CircleImageView imageViewPlayer = null;
        private ImageView isCheck = null;

        public MyViewHolder(final View view) {
            super(view);
            namePlayer = (TextView) view.findViewById(R.id.name_player);
            imageViewPlayer = (CircleImageView) view.findViewById(R.id.profile_image);
            isCheck = (ImageView) view.findViewById(R.id.is_check);
        }
    }

    public PlayersLibraryGridAdapter(Context mContext, List<Player> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_players_library_gv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.namePlayer.setText(playerList.get(position).getName());
        if (playerList.get(position).getPathProfile().trim().equals("")) {
            holder.imageViewPlayer.setImageResource(R.drawable.ic_name_player);
        } else {
            if (GamePermissions.checkPermissionReadExternalStorage(mContext)){
                holder.imageViewPlayer.setImageBitmap(BitmapFactory.decodeFile(playerList.get(position).getPathProfile()));
            }else{
                holder.imageViewPlayer.setImageResource(R.drawable.ic_name_player);
            }
        }

        if (playerList.get(position).isDelete()) {
            holder.isCheck.setVisibility(View.VISIBLE);
            holder.isCheck.setImageResource(android.R.drawable.ic_delete);
        } else if (playerList.get(position).isCheck()) {
            holder.isCheck.setVisibility(View.VISIBLE);
            holder.isCheck.setImageResource(R.mipmap.ic_check);
        } else {
            holder.isCheck.setVisibility(View.GONE);
        }
        holder.namePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelected(position);
            }
        });
        holder.imageViewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelected(position);
            }
        });
        holder.isCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSelected(position);
            }
        });


        holder.namePlayer.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                checkDelete(position);
                return false;
            }
        });
        holder.imageViewPlayer.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                checkDelete(position);
                return false;
            }
        });
        holder.isCheck.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                checkDelete(position);
                return false;
            }
        });
    }

    private void checkSelected(int position) {
        playerList.get(position).setCheck(playerList.get(position).isCheck() ? false : true);
        this.notifyDataSetChanged();
    }

    private void checkDelete(int position) {
        playerList.get(position).setDelete(playerList.get(position).isDelete() ? false : true);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
