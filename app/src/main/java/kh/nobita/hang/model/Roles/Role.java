package kh.nobita.hang.model.Roles;

import java.util.ArrayList;
import java.util.List;

import kh.nobita.hang.model.Player;

/**
 * Created by 11200 on 2/6/2018.
 */

public class Role {
    private int id;
    private String name;
    private int pathImage;
    private String role;
    private int numberPlayer;
    private List<Player> listPlayer;

    public Role(int id, String name, int pathImage, String role, int numberPlayer) {
        this.id = id;
        this.name = name;
        this.pathImage = pathImage;
        this.role = role;
        this.numberPlayer = numberPlayer;
        listPlayer = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPathImage() {
        return pathImage;
    }

    public void setPathImage(int pathImage) {
        this.pathImage = pathImage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNumberPlayer() {
        return numberPlayer;
    }

    public void setNumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public void subNumberPlayers() {
        this.numberPlayer--;
    }

    public void subNumberPlayers(int number) {
        this.numberPlayer = this.numberPlayer - number;
    }

    public void addNumberPlayers() {
        this.numberPlayer++;
    }

    public void addNumberPlayers(int number) {
        this.numberPlayer = this.numberPlayer + number;
    }

    public List<Player> getListPlayer() {
        return listPlayer;
    }

    public List<Player> getListPlayerLive() {
        List<Player> list = new ArrayList<>();
        for (Player player : listPlayer) {
            if (!player.isDead() && !player.isHanged()) {
                list.add(player);
            }
        }
        return list;
    }

    public void setListPlayer(List<Player> listPlayer) {
        this.listPlayer = listPlayer;
    }
}
