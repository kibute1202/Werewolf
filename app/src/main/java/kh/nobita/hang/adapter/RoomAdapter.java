package kh.nobita.hang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import kh.nobita.hang.R;
import kh.nobita.hang.activity.MultiplayerWifi;
import kh.nobita.hang.model.Room;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    private Context mContext;
    private List<Room> roomList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameRoom, number;
        public ImageView thumbnail, isStart;

        public MyViewHolder(View view) {
            super(view);
            nameRoom = (TextView) view.findViewById(R.id.name_room);
            number = (TextView) view.findViewById(R.id.member);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            isStart = (ImageView) view.findViewById(R.id.is_start);
        }
    }

    public List<Room> getItemList() {
        return roomList;
    }

    public void setItemList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public RoomAdapter(Context mContext, List<Room> roomList) {
        this.mContext = mContext;
        this.roomList = roomList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Room room = roomList.get(position);
        holder.nameRoom.setText(room.getNameRoom());
        holder.number.setText(room.getNumberMember() + " / " + room.getNumberLimitMember());

        // loading Room cover using Glide library
        if (room.isStart()) {
            holder.isStart.setImageResource(R.drawable.ic_is_start);
        } else {
            holder.isStart.setImageResource(R.drawable.ic_non_start);
        }

        holder.nameRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(position, room.isStart());
            }
        });
        holder.number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(position, room.isStart());
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(position, room.isStart());
            }
        });
        int rdImage = (new Random()).nextInt(MultiplayerWifi.LIST_IMAGE_WEREWOLF_GAME.length - 1);
        Picasso.with(mContext).load(MultiplayerWifi.LIST_IMAGE_WEREWOLF_GAME[rdImage]).fit().centerCrop().placeholder(android.R.drawable.ic_menu_report_image).into(holder.thumbnail);
    }

    public void showDetail(int position, boolean isStart) {
        if (isStart) {
            new MaterialDialog.Builder(mContext)
            .title(R.string.main_room_is_start_title)
                    .content(R.string.main_room_is_start_content)
                    .positiveText(R.string.agree)
                    .show();
        } else {
//        Intent intent = new Intent(mContext, DetailPlace.class);
//        intent.putExtra("MyItem", itemList.get(position));
//        mContext.startActivity(intent);
        }

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
