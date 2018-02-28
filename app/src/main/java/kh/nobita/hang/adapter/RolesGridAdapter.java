package kh.nobita.hang.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kh.nobita.hang.R;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Roles.Role;

public class RolesGridAdapter extends RecyclerView.Adapter<RolesGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Role> listRoles;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameRole = null;
        private TextView numberPlayer = null;
        private CircleImageView imageViewRole = null;
        private Button btnSub = null;
        private Button btnAdd = null;

        public MyViewHolder(final View view) {
            super(view);
            nameRole = (TextView) view.findViewById(R.id.name_role);
            numberPlayer = (TextView) view.findViewById(R.id.number_player);
            imageViewRole = (CircleImageView) view.findViewById(R.id.profile_image);
            btnSub = (Button) view.findViewById(R.id.btn_sub);
            btnAdd = (Button) view.findViewById(R.id.btn_add);
        }
    }

    public RolesGridAdapter(Context mContext, List<Role> listRoles) {
        this.mContext = mContext;
        this.listRoles = listRoles;
    }

    public List<Role> getPlayerList() {
        return listRoles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_roles_gv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.nameRole.setText(listRoles.get(position).getName());
        holder.numberPlayer.setText(listRoles.get(position).getNumberPlayer() + "");
        holder.imageViewRole.setImageResource(listRoles.get(position).getPathImage());

        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (listRoles.get(position).getId()) {
                    case ListRoles.FLAG_THREE_BROTHERS:
                        listRoles.get(position).subNumberPlayers(3);
                        listRoles.get(0).addNumberPlayers(3);
                        break;
                    case ListRoles.FLAG_TWO_SISTERS:
                        listRoles.get(position).subNumberPlayers(2);
                        listRoles.get(0).addNumberPlayers(2);
                        break;
                    default:
                        listRoles.get(position).subNumberPlayers();
                        listRoles.get(0).addNumberPlayers();
                        break;
                }
                notifyDataSetChanged();
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (listRoles.get(position).getId()) {
                    case ListRoles.FLAG_THREE_BROTHERS:
                        if (listRoles.get(0).getNumberPlayer() >= 3) {
                            listRoles.get(position).addNumberPlayers(3);
                            listRoles.get(0).subNumberPlayers(3);
                        }
                        break;
                    case ListRoles.FLAG_TWO_SISTERS:
                        if (listRoles.get(0).getNumberPlayer() >= 2) {
                            listRoles.get(position).addNumberPlayers(2);
                            listRoles.get(0).subNumberPlayers(2);
                        }
                        break;
                    default:
                        listRoles.get(position).addNumberPlayers();
                        listRoles.get(0).subNumberPlayers();
                        break;
                }
                notifyDataSetChanged();
            }
        });
        holder.nameRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });
        holder.imageViewRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoleImfo(position);
            }
        });

        if (listRoles.get(position).getId() == ListRoles.FLAG_VILLAGERS) {
            holder.btnAdd.setVisibility(View.INVISIBLE);
            holder.btnSub.setVisibility(View.INVISIBLE);
        } else {
            if (listRoles.get(position).getNumberPlayer() == 0) {
                holder.btnSub.setVisibility(View.INVISIBLE);
            } else {
                holder.btnSub.setVisibility(View.VISIBLE);
            }

            if (listRoles.get(0).getNumberPlayer() == 0) {
                holder.btnAdd.setVisibility(View.INVISIBLE);
            } else {
                holder.btnAdd.setVisibility(View.VISIBLE);
            }

            if (listRoles.get(position).getId() == ListRoles.FLAG_THREE_BROTHERS) {
                if (listRoles.get(0).getNumberPlayer() < 3) {
                    holder.btnAdd.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                }
            }

            if (listRoles.get(position).getId() == ListRoles.FLAG_TWO_SISTERS) {
                if (listRoles.get(0).getNumberPlayer() < 2) {
                    holder.btnAdd.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnAdd.setVisibility(View.VISIBLE);
                }
            }
            switch (listRoles.get(position).getId()) {
                case ListRoles.FLAG_IDIOT:
                case ListRoles.FLAG_MAD_SCIENTIST:
                case ListRoles.FLAG_OLD_VILLAGE:
                case ListRoles.FLAG_HUI_SICK:
                case ListRoles.FLAG_KNIGHT:
                    if (listRoles.get(position).getNumberPlayer() == 1) {
                        holder.btnAdd.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    }

    private void showRoleImfo(int position) {
        new MaterialDialog.Builder(mContext)
                .title(listRoles.get(position).getName())
                .content(listRoles.get(position).getRole())
                .positiveText(LocaleHelper.getLangResources(mContext).getString(R.string.agree))
                .show();
    }

    @Override
    public int getItemCount() {
        return listRoles.size();
    }

}
