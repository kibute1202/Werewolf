package kh.nobita.hang.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    private String nameRoom;
    private int numberMember;
    private int numberLimitMember;
    private boolean isStart;

    public Room(String nameRoom, int numberMember, int numberLimitMember, boolean isStart) {
        this.nameRoom = nameRoom;
        this.numberMember = numberMember;
        this.numberLimitMember = numberLimitMember;
        this.isStart = isStart;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public int getNumberMember() {
        return numberMember;
    }

    public void setNumberMember(int numberMember) {
        this.numberMember = numberMember;
    }

    public int getNumberLimitMember() {
        return numberLimitMember;
    }

    public void setNumberLimitMember(int numberLimitMember) {
        this.numberLimitMember = numberLimitMember;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }
}
