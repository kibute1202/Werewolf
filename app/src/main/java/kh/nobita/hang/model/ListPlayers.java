package kh.nobita.hang.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import kh.nobita.hang.database.PlayersDBHelper;
import kh.nobita.hang.model.Roles.ListRoles;

public class ListPlayers {
    private PlayersDBHelper dbHelper;
    private List<Player> listPlayerAll;
    private List<Player> listPlayer;
    private List<Player> listPlayerLib;
    private static Context mContext;

    private static class ListPlayersHelper {
        private static final ListPlayers INSTANCE = new ListPlayers();
    }

    public static ListPlayers getInstance(Context context) {
        mContext = context;
        return ListPlayersHelper.INSTANCE;
    }

    private ListPlayers() {
        listPlayer = new ArrayList<>();
        dbHelper = new PlayersDBHelper(mContext);
        listPlayerLib = dbHelper.getPlayers();
    }

    public List<Player> getListPlayerFinal() {
        return listPlayerAll;
    }

    public List<Player> getAllListPlayer() {
        listPlayerAll = new ArrayList<>();
        for (Player player : listPlayerLib) {
            if (player.isCheck()) {
                listPlayerAll.add(player);
            }
        }
        listPlayerAll.addAll(listPlayer);
        return listPlayerAll;
    }

    public List<Player> getAllPlayerLive() {
        List<Player> list = new ArrayList<>();
        for (Player player : listPlayerAll) {
            if (!player.isDead() && !player.isHanged()) {
                list.add(player);
            }
        }
        return list;
    }

    public List<Player> getListLimit(List<Player> listLimit) {
        List<Player> list = getAllPlayerLive();
        for (Player player : listLimit) {
            list.remove(player);
        }
        return list;
    }

    public List<Player> getAllPlayerLiveDiscuss(boolean isIdiotDie) {
        List<Player> list = new ArrayList<>();
        for (Player player : listPlayerAll) {
            if (isIdiotDie && player.getRole().getId() == ListRoles.FLAG_IDIOT) {
                continue;
            }
            if (!player.isDead() && !player.isHanged()) {
                list.add(player);
            }
        }
        return list;
    }

    public List<Player> getListPlayerLib() {
        return listPlayerLib;
    }

    public void addPlayerToLib(Player player) {
        dbHelper.createPlayer(player);
        Player playerCreate = dbHelper.getPlayerLast();
        playerCreate.setCheck(player.isCheck());
        listPlayerLib.add(playerCreate);
    }

    public boolean isNameExist(String name) {
        for (Player player : listPlayerLib) {
            if (player.getName().equals(name.trim())) {
                return true;
            }
        }
        for (Player player : listPlayer) {
            if (player.getName().equals(name.trim())) {
                return true;
            }
        }
        return false;
    }

    public void addPlayer(Player player) {
        listPlayer.add(player);
    }

    public void cleanListPlayer() {
        this.listPlayer.clear();
    }

    public void cleanFlagDeletePlayers() {
        for (int i = 0; i < listPlayerLib.size(); i++) {
            listPlayerLib.get(i).setDelete(false);
        }
    }

    public void clearFunction() {
        for (Player player : getAllPlayerLive()) {
            player.setSecurity(false);
            player.setWolfBites(false);
            player.setPoison(false);
        }
    }

    public void cleanAllFunction() {
        for (Player player : listPlayerAll) {
            player.setDead(false);
            player.setSecurity(false);
            player.setWolfBites(false);
            player.setPoison(false);
            player.setHanged(false);
        }
    }

    public void editPlayers(Player playerOld, Player playerNew) {
        for (int i = 0; i < listPlayer.size(); i++) {
            if (playerOld.getName().equals(listPlayer.get(i).getName())) {
                listPlayer.set(i, playerNew);
            }
        }
    }

    public void deletePlayers(Player player) {
        for (int i = 0; i < listPlayer.size(); i++) {
            if (player.getName().equals(listPlayer.get(i).getName())) {
                listPlayer.remove(i);
            }
        }
    }

    public void editPlayersLib(Player player) {
        dbHelper.updatePlayer(player);
        for (int i = 0; i < listPlayerLib.size(); i++) {
            if (player.getId() == listPlayerLib.get(i).getId()) {
                listPlayerLib.set(i, player);
            }
        }
    }

    public void unCheckPlayerInPlayersLib(Player player) {
        for (int i = 0; i < listPlayerLib.size(); i++) {
            if (player.getId() == listPlayerLib.get(i).getId()) {
                listPlayerLib.get(i).setCheck(false);
            }
        }
    }

    public void deletePlayersLib() {
        for (Player player : listPlayerLib) {
            if (player.isDelete()) {
                dbHelper.deletePlayer(player.getId());
                listPlayerLib.remove(player);
            }
        }
    }
}
