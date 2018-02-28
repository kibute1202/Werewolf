package kh.nobita.hang.activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.Utils.MyContextWrapper;
import kh.nobita.hang.Utils.Utils;
import kh.nobita.hang.fragment.ShuffleRoles;
import kh.nobita.hang.fragment.ingame.Builder;
import kh.nobita.hang.fragment.ingame.Grandmother;
import kh.nobita.hang.fragment.ingame.InversePerson;
import kh.nobita.hang.fragment.ingame.Cupid;
import kh.nobita.hang.fragment.ingame.Day;
import kh.nobita.hang.fragment.ingame.Discuss;
import kh.nobita.hang.fragment.ingame.EndGame;
import kh.nobita.hang.fragment.ingame.Hunter;
import kh.nobita.hang.fragment.ingame.Night;
import kh.nobita.hang.fragment.ingame.Nomads;
import kh.nobita.hang.fragment.ingame.Prophet;
import kh.nobita.hang.fragment.ingame.Security;
import kh.nobita.hang.fragment.ingame.ThreeBrother;
import kh.nobita.hang.fragment.ingame.TwoSisters;
import kh.nobita.hang.fragment.ingame.Witch;
import kh.nobita.hang.fragment.ingame.Wolf;
import kh.nobita.hang.fragment.ingame.WolfSniffing;
import kh.nobita.hang.model.HistoryOne;
import kh.nobita.hang.model.ListPlayers;
import kh.nobita.hang.model.Player;
import kh.nobita.hang.model.Roles.ListRoles;

public class GamePlay extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private int position;
    private int night;
    private List<Player> listPlayerNotDieInNight;
    private List<Player> listPlayerSecurity;
    private List<Player> listPlayerDieInNight;
    private List<Player> listPlayerWolfBite;
    private List<Player> listPlayerDieByPoisonInNight;
    private List<Player> listPlayerDieWithHunter;
    private List<Player> listPlayerDieWithMadScientist;
    private List<Player> listPlayerCouple;
    private List<Player> listPlayerInverse;
    private List<Player> listPlayerGrandmotherSelect;
    private List<Player> listPlayerBullyDie;
    private List<Player> listPlayerDieWithKight;
    private Player playerNomads;
    private boolean isHunterDied = false;
    private int numberOldVillageWolfKill = 0;
    private boolean isCallWolf = false;
    private boolean isPoison = false;
    private boolean isElixir = false;
    private boolean isIdiotDie = false;
    private boolean isWolfBiteHuiSick = false;
    private boolean isMadScientistDie = false;
    private boolean isWolfBabyDie = false;
    private boolean isKnightDie = false;
    private List<HorizontalScrollView> listHistoryOne;
    private List<HistoryOne> listHistoryGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        fragmentManager = getSupportFragmentManager();

        position = 1;
        night = 1;
        numberOldVillageWolfKill = 0;
        isCallWolf = false;
        isPoison = false;
        isElixir = false;
        isHunterDied = false;
        isIdiotDie = false;
        isWolfBiteHuiSick = false;
        isMadScientistDie = false;
        isWolfBabyDie = false;
        isKnightDie = false;
        listPlayerNotDieInNight = new ArrayList<>();
        listPlayerSecurity = new ArrayList<>();
        listPlayerDieInNight = new ArrayList<>();
        listPlayerWolfBite = new ArrayList<>();
        listPlayerDieByPoisonInNight = new ArrayList<>();
        listPlayerDieWithHunter = new ArrayList<>();
        listPlayerDieWithMadScientist = new ArrayList<>();
        listPlayerCouple = new ArrayList<>();
        listPlayerInverse = new ArrayList<>();
        playerNomads = null;
        listPlayerGrandmotherSelect = new ArrayList<>();
        listPlayerBullyDie = new ArrayList<>();
        listPlayerDieWithKight = new ArrayList<>();

        listHistoryOne = new ArrayList<>();
        listHistoryGame = new ArrayList<>();

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.gameplay_frameContainer, new ShuffleRoles(), Utils.SHUFFLE_ROLES_FRAGMENT).commit();
        }
    }

    public void replaceThreeBrothersFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new ThreeBrother(), Utils.THREE_BROTHERS_FRAGMENT)
                .commit();
    }

    public void replaceTwoSistersFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new TwoSisters(), Utils.TWO_SISTERS_FRAGMENT)
                .commit();
    }

    public void replaceBuilderFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Builder(), Utils.BUILDER_FRAGMENT)
                .commit();
    }

    public void replaceInversePersonFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new InversePerson(), Utils.INVERSE_PERSON_FRAGMENT)
                .commit();
    }

    public void replaceNomadsFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Nomads(), Utils.NOMADS_FRAGMENT)
                .commit();
    }


    public void replaceCupidFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Cupid(), Utils.CUPID_FRAGMENT)
                .commit();
    }

    public void replaceSecurityFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Security(), Utils.SECURITY_FRAGMENT)
                .commit();
    }

    public void replaceWolfSniffingFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new WolfSniffing(), Utils.WOLF_SNIFFING_FRAGMENT)
                .commit();
    }

    public void replaceWerewolfFragment() {
        isCallWolf = true;
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Wolf(), Utils.WOLF_ROLES_FRAGMENT)
                .commit();
    }

    public void replaceProphetFragment() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.gameplay_frameContainer, new Prophet(), Utils.PROPHET_FRAGMENT)
                .commit();
    }

    public void replaceWitchFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Witch(), Utils.WITCH_FRAGMENT).commit();
    }

    public void replaceHunterFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Hunter(), Utils.HUNTER_FRAGMENT).commit();
    }

    public void replaceGrandmotherFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Grandmother(), Utils.GRANDMOTHER_FRAGMENT).commit();
    }

    public void replaceNightFragment() {
        updateListMadScientist();
        updateListKnightKill();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Night(), Utils.NIGHT_FRAGMENT).commit();
    }

    public void replaceDayFragment() {
        night++;
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Day(), Utils.DAY_FRAGMENT).commit();
    }

    public void replaceDiscussFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new Discuss(), Utils.DISCUSS_FRAGMENT).commit();
    }

    public void replaceEndGameFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out).replace(R.id.gameplay_frameContainer, new EndGame(), Utils.END_GAME_FRAGMENT).commit();
    }

    public List<Player> getListPlayerDieInNight() {
        return listPlayerDieInNight;
    }

    public List<Player> getListPlayerDieByPoisonInNight() {
        return listPlayerDieByPoisonInNight;
    }

    public List<Player> getListPlayerNotDieInNight() {
        return listPlayerNotDieInNight;
    }

    public List<Player> getListPlayerDieWithHunter() {
        return listPlayerDieWithHunter;
    }

    public List<Player> getListPlayerCouple() {
        return listPlayerCouple;
    }

    public List<Player> getListPlayerInverse() {
        return listPlayerInverse;
    }

    public List<Player> getListPlayerSecurity() {
        return listPlayerSecurity;
    }

    public List<Player> getListPlayerGrandmotherSelect() {
        return listPlayerGrandmotherSelect;
    }

    public List<Player> getListPlayerWolfBite() {
        return listPlayerWolfBite;
    }

    public Player getPlayerNomads() {
        return playerNomads;
    }

    public void setPlayerNomads(Player playerNomads) {
        this.playerNomads = playerNomads;
    }

    public boolean isCallWolf() {
        return isCallWolf;
    }

    public void setCallWolf(boolean callWolf) {
        isCallWolf = callWolf;
    }

    public int getNumberOldVillageWolfKill() {
        return numberOldVillageWolfKill;
    }

    public void setNumberOldVillageWolfKill() {
        this.numberOldVillageWolfKill++;
    }

    public void setNumberOldVillageWolfKill(int numberOldVillageWolfKill) {
        this.numberOldVillageWolfKill = numberOldVillageWolfKill;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public boolean isElixir() {
        return isElixir;
    }

    public void setElixir(boolean elixir) {
        isElixir = elixir;
    }

    public boolean isHunterDied() {
        return isHunterDied;
    }

    public void setHunterDied(boolean hunterDied) {
        isHunterDied = hunterDied;
    }

    public boolean isMadScientistDie() {
        return isMadScientistDie;
    }

    public void setMadScientistDie(boolean madScientistDie) {
        isMadScientistDie = madScientistDie;
    }

    public boolean isKnightDie() {
        return isKnightDie;
    }

    public void setKnightDie(boolean knightDie) {
        isKnightDie = knightDie;
    }

    public boolean isWolfBabyDie() {
        return isWolfBabyDie;
    }

    public void setWolfBabyDie(boolean wolfBabyDie) {
        isWolfBabyDie = wolfBabyDie;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public boolean isIdiotDie() {
        return isIdiotDie;
    }

    public void setIdiotDie(boolean idiotDie) {
        isIdiotDie = idiotDie;
    }

    public boolean isWolfBiteHuiSick() {
        return isWolfBiteHuiSick;
    }

    public void setWolfBiteHuiSick(boolean wolfBiteHuiSick) {
        isWolfBiteHuiSick = wolfBiteHuiSick;
    }

    public List<HistoryOne> getListHistoryGame() {
        return listHistoryGame;
    }

    public void setListHistoryGame(List<HistoryOne> listHistoryGame) {
        this.listHistoryGame = listHistoryGame;
    }

    public List<HorizontalScrollView> getListHistoryOne() {
        return listHistoryOne;
    }

    public void setListHistoryOne(List<HorizontalScrollView> listHistoryOne) {
        this.listHistoryOne = listHistoryOne;
    }

    public void updateSecurity() {
        for (Player player : listPlayerNotDieInNight) {
            player.setSecurity(true);
        }
        for (Player player : listPlayerSecurity) {
            player.setSecurity(true);
        }
    }

    public void checkNomads() {
        if (playerNomads != null && playerNomads.isDead()) {
            for (Player player : ListRoles.getInstance().getListRolesUsing().get(ListRoles.getInstance().getRoleInListShuffle(ListRoles.FLAG_NOMADS)).getListPlayerLive()) {
                if (!player.isDead()) {
                    player.setDead(true);
                    listPlayerDieInNight.add(player);
                    if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                        this.numberOldVillageWolfKill = 2;
                    }
                }
            }
            checkHunter();
            checkCoupple();
        }
    }

    public void checkCoupple() {
        boolean coupleDie = false;
        for (Player player : listPlayerCouple) {
            if (player.isDead() || player.isHanged()) {
                coupleDie = true;
            }
        }
        if (coupleDie) {
            for (Player player : listPlayerCouple) {
                if (!player.isDead()) {
                    player.setDead(true);
                    listPlayerDieInNight.add(player);
                    if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                        this.numberOldVillageWolfKill = 2;
                    }
                }
            }
            checkHunter();
        }
    }

    public void checkHunter() {
        if (isHunterDied() && getNumberOldVillageWolfKill() < 2) {
            for (Player player : listPlayerDieWithHunter) {
                if (!player.isDead()) {
                    player.setDead(true);
                    listPlayerDieInNight.add(player);
                    if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                        this.numberOldVillageWolfKill = 2;
                    }
                }
            }
        }
        for (int i = 0; i < listPlayerDieInNight.size(); i++) {
            switch (listPlayerDieInNight.get(i).getRole().getId()) {
                case ListRoles.FLAG_HUNTER:
                    for (Player player : listPlayerDieWithHunter) {
                        if (!player.isDead()) {
                            player.setDead(true);
                            listPlayerDieInNight.add(player);
                            if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                                this.numberOldVillageWolfKill = 2;
                            }
                        }
                    }
                    listPlayerDieWithHunter.clear();
                    break;
            }
        }
    }

    public void checkMadScientist() {
        boolean isCancel = true;
        if (isMadScientistDie() && getNumberOldVillageWolfKill() < 2) {
            isCancel = false;
        }
        for (int i = 0; i < listPlayerDieInNight.size(); i++) {
            switch (listPlayerDieInNight.get(i).getRole().getId()) {
                case ListRoles.FLAG_MAD_SCIENTIST:
                    setMadScientistDie(true);
                    isCancel = false;
                    break;
            }
        }
        if (!isCancel) {
            for (Player player : listPlayerDieWithMadScientist) {
                if (!player.isDead()) {
                    player.setDead(true);
                    listPlayerDieInNight.add(player);
                    if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                        this.numberOldVillageWolfKill = 2;
                    }
                }
            }
            listPlayerDieWithMadScientist.clear();
            checkHunter();
            checkCoupple();
            checkNomads();
        }
    }

    public void checkKnight() {
        boolean isCancel = true;
        if (isKnightDie() && getNumberOldVillageWolfKill() < 2) {
            isCancel = false;
        }
        for (int i = 0; i < listPlayerDieInNight.size(); i++) {
            switch (listPlayerDieInNight.get(i).getRole().getId()) {
                case ListRoles.FLAG_KNIGHT:
                    setKnightDie(true);
                    isCancel = false;
                    break;
            }
        }
        if (!isCancel) {
            for (Player player : listPlayerDieWithKight) {
                if (!player.isDead()) {
                    player.setDead(true);
                    listPlayerDieInNight.add(player);
                    if (player.getRole().getId() == ListRoles.FLAG_OLD_VILLAGE) {
                        this.numberOldVillageWolfKill = 2;
                    }
                }
            }
            listPlayerDieWithKight.clear();
            checkHunter();
            checkCoupple();
            checkNomads();
        }
    }

    public void checkWitch() {
        for (int i = 0; i < listPlayerDieByPoisonInNight.size(); i++) {
            listPlayerDieByPoisonInNight.get(i).setDead(true);
            listPlayerDieInNight.add(listPlayerDieByPoisonInNight.get(i));
            switch (listPlayerDieByPoisonInNight.get(i).getRole().getId()) {
                case ListRoles.FLAG_OLD_VILLAGE:
                    this.numberOldVillageWolfKill = 2;
                    break;
            }
        }
    }

    public List<Player> listPlayerWolfBiteDie() {
        List<Player> list = new ArrayList<>();
        updateSecurity();
        for (int i = 0; i < listPlayerDieInNight.size(); i++) {
            if (!listPlayerDieInNight.get(i).isSecurity()) {
                list.add(listPlayerDieInNight.get(i));
            }
        }
        return list;
    }

    public List<Player> updateDead() {
        updateSecurity();
        for (Player player : listPlayerBullyDie) {
            player.setSecurity(false);
            if (!player.isDead()) {
                listPlayerDieInNight.add(player);
            }
        }
        listPlayerBullyDie.clear();

        for (Player player : listPlayerWolfBite) {
            switch (player.getRole().getId()) {
                case ListRoles.FLAG_HUI_SICK:
                    setWolfBiteHuiSick(true);
                    break;
                case ListRoles.FLAG_BULLY:
                    if (!player.isSecurity()) {
                        if (!checkRoleInverse(ListRoles.FLAG_BULLY)) {
                            if (this.numberOldVillageWolfKill < 2) {
                                listPlayerWolfBite.remove(player);
                                listPlayerBullyDie.add(player);
                            }
                        }
                    }
                    break;
            }
        }
        listPlayerDieInNight.addAll(listPlayerWolfBite);
        for (int i = 0; i < listPlayerDieInNight.size(); i++) {
            if (!listPlayerDieInNight.get(i).isSecurity()) {
                switch (listPlayerDieInNight.get(i).getRole().getId()) {
                    case ListRoles.FLAG_OLD_VILLAGE:
                        if (checkRoleInverse(ListRoles.FLAG_OLD_VILLAGE)) {
                            setNumberOldVillageWolfKill(2);
                        } else {
                            setNumberOldVillageWolfKill();
                        }
                        if (getNumberOldVillageWolfKill() >= 2) {
                            listPlayerDieInNight.get(i).setDead(true);
                        } else {
                            listPlayerDieInNight.remove(i);
                        }
                        break;
                    default:
                        listPlayerDieInNight.get(i).setDead(true);
                        break;
                }
            } else {
                listPlayerDieInNight.remove(i);
            }
        }
        checkWitch();
        checkHunter();
        checkCoupple();
        checkNomads();
        checkMadScientist();
        checkKnight();

        checkWolfBaby();
        return listPlayerDieInNight;
    }

    public void checkWolfBaby() {
        for (Player player : listPlayerDieInNight) {
            if (player.getRole().getId() == ListRoles.FLAG_WOLF_BABY) {
                setWolfBabyDie(true);
            }
        }
    }

    public boolean checkRoleInverse(int flag) {
        for (Player player : getListPlayerInverse()) {
            if (player.getRole().getId() == flag) {
                return true;
            }
        }
        return false;
    }

    public void updateListMadScientist() {
        if (!isMadScientistDie()) {
            listPlayerDieWithMadScientist.clear();
            if (getNumberOldVillageWolfKill() < 2) {
                for (int i = 0; i < ListPlayers.getInstance(this).getAllPlayerLive().size(); i++) {
                    if (ListPlayers.getInstance(this).getAllPlayerLive().get(i).getRole().getId() == ListRoles.FLAG_MAD_SCIENTIST) {
                        if (i == 0) {
                            listPlayerDieWithMadScientist.add(ListPlayers.getInstance(this).getAllPlayerLive().get(ListPlayers.getInstance(this).getAllPlayerLive().size() - 1));
                        } else {
                            listPlayerDieWithMadScientist.add(ListPlayers.getInstance(this).getAllPlayerLive().get(i - 1));
                        }
                        if (i == ListPlayers.getInstance(this).getAllPlayerLive().size() - 1) {
                            listPlayerDieWithMadScientist.add(ListPlayers.getInstance(this).getAllPlayerLive().get(0));
                        } else {
                            listPlayerDieWithMadScientist.add(ListPlayers.getInstance(this).getAllPlayerLive().get(i + 1));
                        }
                    }
                }
            }
        }
    }

    public void updateListKnightKill() {
        if (!isKnightDie()) {
            listPlayerDieWithKight.clear();
            if (getNumberOldVillageWolfKill() < 2) {
                int idKnight = -1;
                int idWolfFirst = -1;
                int idWolfFinal = -1;
                for (int i = 0; i < ListPlayers.getInstance(this).getAllPlayerLive().size(); i++) {
                    switch (ListPlayers.getInstance(this).getAllPlayerLive().get(i).getRole().getId()) {
                        case ListRoles.FLAG_KNIGHT:
                            if (idKnight == -1) {
                                idKnight = i;
                            }
                            break;
                        case ListRoles.FLAG_WOLF_BOSS:
                        case ListRoles.FLAG_SNOWFLAKES:
                        case ListRoles.FLAG_WOLF_SNIFFING:
                        case ListRoles.FLAG_WOLF:
                        case ListRoles.FLAG_WOLF_BABY:
                            if (idWolfFirst == -1) {
                                idWolfFirst = i;
                            }
                            if (i > idKnight && idWolfFinal == -1 && idKnight != -1) {
                                idWolfFinal = i;
                            }
                            break;
                    }
                }
                if (idWolfFinal == -1) {
                    idWolfFinal = idWolfFirst;
                }
                listPlayerDieWithKight.add(ListPlayers.getInstance(this).getAllPlayerLive().get(idWolfFinal));
            }
        }
    }

    public void nextRoles() {
        ListPlayers.getInstance(this).clearFunction();
        if (position > ListRoles.getInstance().getListRolesUsing().size() - 1) {
            if (!isCallWolf) {
                replaceWerewolfFragment();
            } else {
                position = 1;
                replaceDayFragment();
            }
            return;
        }
        int id = ListRoles.getInstance().getListRolesUsing().get(position).getId();
        if (!isCallWolf && id > ListRoles.FLAG_WOLF) {
            replaceWerewolfFragment();
            return;
        }
        if (id < ListRoles.FLAG_WOLF) {
            isCallWolf = false;
        }
        position++;
        switch (id) {
            case ListRoles.FLAG_WOLF_BOSS:
                nextRoles();
                break;
            case ListRoles.FLAG_THREE_BROTHERS:
                if (night == 1) {
                    replaceThreeBrothersFragment();
                } else {
                    nextRoles();
                }
                break;
            case ListRoles.FLAG_TWO_SISTERS:
                if (night == 1) {
                    replaceTwoSistersFragment();
                } else {
                    nextRoles();
                }
                break;
            case ListRoles.FLAG_BUILDER:
                if (night == 1) {
                    replaceBuilderFragment();
                } else {
                    nextRoles();
                }
                break;
            case ListRoles.FLAG_OLD_VILLAGE:
                nextRoles();
                break;
            case ListRoles.FLAG_CONTROLLER:
                nextRoles();
                break;
            case ListRoles.FLAG_KNIGHT:
                nextRoles();
                break;
            case ListRoles.FLAG_SERVANT:
                nextRoles();
                break;
            case ListRoles.FLAG_SNOWFLAKES:
                nextRoles();
                break;
            case ListRoles.FLAG_WILD_CHILD:
                nextRoles();
                break;
            case ListRoles.FLAG_INVERSE_PERSON:
                replaceInversePersonFragment();
                break;
            case ListRoles.FLAG_NOMADS:
                replaceNomadsFragment();
                break;
            case ListRoles.FLAG_CUPID:
                if (night == 1) {
                    replaceCupidFragment();
                } else {
                    nextRoles();
                }
                break;
            case ListRoles.FLAG_SECURITY:
                replaceSecurityFragment();
                break;
            case ListRoles.FLAG_WOLF_SNIFFING:
                if (ListRoles.getInstance().getNumberOldVillageAndProphetRole() > 0) {
                    replaceWolfSniffingFragment();
                } else {
                    nextRoles();
                }
                break;
            case ListRoles.FLAG_WOLF:
                replaceWerewolfFragment();
                break;
            case ListRoles.FLAG_PROPHET:
                replaceProphetFragment();
                break;
            case ListRoles.FLAG_BEAR:
                break;
            case ListRoles.FLAG_FOXES:
                break;
            case ListRoles.FLAG_WITCH:
                replaceWitchFragment();
                break;
            case ListRoles.FLAG_HUNTER:
                replaceHunterFragment();
                break;
            case ListRoles.FLAG_IDIOT:
                nextRoles();
                break;
            case ListRoles.FLAG_GRANDMOTHER:
                replaceGrandmotherFragment();
                break;
            default:
                nextRoles();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Resources resources = LocaleHelper.getLangResources(this);
        new MaterialDialog.Builder(this)
                .title(resources.getString(R.string.dialog_title_outgame))
                .content(resources.getString(R.string.dialog_content_outgame))
                .positiveText(resources.getString(R.string.agree))
                .negativeText(resources.getString(R.string.disagree))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        GamePlay.super.onBackPressed();
                    }
                })
                .show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, AppController.getInstance().getPrefManager().getLang()));
    }
}
