package kh.nobita.hang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.GamePermissions;
import kh.nobita.hang.activity.OneMachine;
import kh.nobita.hang.model.Player;

public class PlayersGridAdapter extends RecyclerView.Adapter<PlayersGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Player> playerList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView namePlayer = null;
        private CircleImageView imageViewPlayer = null;

        public MyViewHolder(final View view) {
            super(view);
            namePlayer = (TextView) view.findViewById(R.id.name_player);
            imageViewPlayer = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }

    public PlayersGridAdapter(Context mContext, List<Player> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_players_gv_item, parent, false);
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
    }

    private void checkSelected(int position) {
        ((OneMachine) mContext).setPlayerEdit(playerList.get(position));
        ((OneMachine) mContext).replacePlayersEditFragment();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
