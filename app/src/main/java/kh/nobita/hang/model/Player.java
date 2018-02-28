package kh.nobita.hang.model;

import kh.nobita.hang.model.Roles.Role;

/**
 * Created by 11200 on 2/5/2018.
 */

public class Player {
    private long id;
    private String name;
    private String pathProfile;
    private boolean isCheck;
    private boolean isDelete;
    private Role role;
    private boolean isDead;
    private boolean isSecurity;
    private boolean isWolfBites;
    private boolean isPoison;
    private boolean isHanged;

    public Player() {
        this.id = -1;
    }

    public Player(String name, String pathProfile) {
        this.id = -1;
        this.name = name;
        this.pathProfile = pathProfile;
        this.isCheck = false;
        this.isDead = false;
        this.isSecurity = false;
        this.isWolfBites = false;
        this.isHanged = false;
    }

    public Player(String name, String pathProfile, boolean isCheck) {
        this.name = name;
        this.pathProfile = pathProfile;
        this.isCheck = isCheck;
        this.isDead = false;
        this.isSecurity = false;
        this.isWolfBites = false;
        this.isHanged = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathProfile() {
        return pathProfile;
    }

    public void setPathProfile(String pathProfile) {
        this.pathProfile = pathProfile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isSecurity() {
        return isSecurity;
    }

    public void setSecurity(boolean security) {
        isSecurity = security;
    }

    public boolean isWolfBites() {
        return isWolfBites;
    }

    public void setWolfBites(boolean wolfBites) {
        isWolfBites = wolfBites;
    }

    public boolean isPoison() {
        return isPoison;
    }

    public void setPoison(boolean poison) {
        isPoison = poison;
    }

    public boolean isHanged() {
        return isHanged;
    }

    public void setHanged(boolean hanged) {
        isHanged = hanged;
    }
}
