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
import kh.nobita.hang.model.Room;

public class WerewolfLibraryRolesGridAdapter extends RecyclerView.Adapter<WerewolfLibraryRolesGridAdapter.MyViewHolder> {

    private Context mContext;
    private List<Role> listRoles;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameRole = null;
        private CircleImageView imageViewRole = null;

        public MyViewHolder(final View view) {
            super(view);
            nameRole = (TextView) view.findViewById(R.id.name_role);
            imageViewRole = (CircleImageView) view.findViewById(R.id.profile_image);
        }
    }

    public void setItemList(List<Role> listRoles) {
        this.listRoles = listRoles;
    }

    public WerewolfLibraryRolesGridAdapter(Context mContext, List<Role> listRoles) {
        this.mContext = mContext;
        this.listRoles = listRoles;
    }

    public List<Role> getPlayerList() {
        return listRoles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.werewolf_lib_roles_gv_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.nameRole.setText(listRoles.get(position).getName());
        holder.imageViewRole.setImageResource(listRoles.get(position).getPathImage());

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
    }

    private void showRoleImfo(int position) {
        Resources resources = LocaleHelper.getLangResources(mContext);
        new MaterialDialog.Builder(mContext)
                .title(listRoles.get(position).getName())
                .content(listRoles.get(position).getRole())
                .positiveText(resources.getString(R.string.agree))
                .show();
    }

    @Override
    public int getItemCount() {
        return listRoles.size();
    }

}
