package kh.nobita.hang.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import kh.nobita.hang.model.Player;

public class NightGridAdapter extends RecyclerView.Adapter<NightGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Player> playerList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView namePlayer = null;
        private TextView nameRole = null;
        private CircleImageView imageView = null;

        public MyViewHolder(final View view) {
            super(view);
            namePlayer = (TextView) view.findViewById(R.id.name_player);
            nameRole = (TextView) view.findViewById(R.id.name_role);
            imageView = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }

    public NightGridAdapter(Context mContext, List<Player> playerList) {
        this.mContext = mContext;
        this.playerList = playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_night_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.namePlayer.setText(playerList.get(position).getName());
        holder.nameRole.setText(playerList.get(position).getRole().getName());
        if (playerList.get(position).getPathProfile().trim().equals("")) {
            holder.imageView.setImageResource(R.drawable.ic_name_player);
        } else {
            if (GamePermissions.checkPermissionReadExternalStorage(mContext)){
                holder.imageView.setImageBitmap(BitmapFactory.decodeFile(playerList.get(position).getPathProfile()));
            }else{
                holder.imageView.setImageResource(R.drawable.ic_name_player);
            }
        }
        holder.namePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
        holder.nameRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
    }

    private void showRoleImfo(int position) {
        Resources resources = LocaleHelper.getLangResources(mContext);
        new MaterialDialog.Builder(mContext)
                .title(playerList.get(position).getRole().getName())
                .content(playerList.get(position).getRole().getRole())
                .positiveText(resources.getString(R.string.agree))
                .show();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

}
