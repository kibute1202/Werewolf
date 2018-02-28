package kh.nobita.hang.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.Utils.MyContextWrapper;
import kh.nobita.hang.Utils.Utils;
import kh.nobita.hang.fragment.Players;
import kh.nobita.hang.fragment.PlayersAdd;
import kh.nobita.hang.fragment.PlayersLibrary;
import kh.nobita.hang.fragment.Roles;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;

public class OneMachine extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment playersAddFragment, playersEditFragment, playersLibraryFragment, rolesFragment;
    private Player playerEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_machine);
        fragmentManager = getSupportFragmentManager();

        ListPlayers.getInstance(this).cleanListPlayer();
        ListRoles.getInstance().cleanAllFunction();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.one_machine_frameContainer, new Players(), Utils.PLAYERS_FRAGMENT).commit();
        }
    }

    // Replace Players Fragment with animation
    public void replacePlayersFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_out).replace(R.id.one_machine_frameContainer, new Players(), Utils.PLAYERS_FRAGMENT).commit();
    }

    public void replacePlayersAddFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.one_machine_frameContainer, new PlayersAdd(), Utils.PLAYERS_ADD_FRAGMENT).commit();
    }

    public void replacePlayersEditFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.one_machine_frameContainer, new PlayersAdd(), Utils.PLAYERS_EDIT_FRAGMENT).commit();
    }

    public void replacePlayersLibraryFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.one_machine_frameContainer, new PlayersLibrary(), Utils.PLAYERS_LIBRARY_FRAGMENT).commit();
    }

    public void replaceRolesFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.one_machine_frameContainer, new Roles(), Utils.ROLES_FRAGMENT).commit();
    }

    @Override
    public void onBackPressed() {
        // Find the tag of signup and forgot password fragment
        playersAddFragment = fragmentManager.findFragmentByTag(Utils.PLAYERS_ADD_FRAGMENT);
        playersEditFragment = fragmentManager.findFragmentByTag(Utils.PLAYERS_EDIT_FRAGMENT);
        playersLibraryFragment = fragmentManager.findFragmentByTag(Utils.PLAYERS_LIBRARY_FRAGMENT);
        rolesFragment = fragmentManager.findFragmentByTag(Utils.ROLES_FRAGMENT);
        if (playersAddFragment != null) {
            replacePlayersFragment();
        } else if (playersEditFragment != null) {
            setPlayerEdit(null);
            replacePlayersFragment();
        } else if (playersLibraryFragment != null) {
            replacePlayersFragment();
        } else if (rolesFragment != null) {
            replacePlayersFragment();
        } else {
            super.onBackPressed();
        }
    }

    public Player getPlayerEdit() {
        return playerEdit;
    }

    public void setPlayerEdit(Player playerEdit) {
        this.playerEdit = playerEdit;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, AppController.getInstance().getPrefManager().getLang()));
    }
}
