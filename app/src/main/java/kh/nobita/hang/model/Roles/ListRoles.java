package kh.nobita.hang.model.Roles;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kh.nobita.hang.R;
import kh.nobita.hang.model.Player;

public class ListRoles {
    public static final int FLAG_VILLAGERS = 1;
    public static final int FLAG_WOLF_BOSS = FLAG_VILLAGERS + 1; //Soi Trum`
    public static final int FLAG_THREE_BROTHERS = FLAG_WOLF_BOSS + 1; //Ba Anh Em
    public static final int FLAG_TWO_SISTERS = FLAG_THREE_BROTHERS + 1; //Hai Chi Em
    public static final int FLAG_BUILDER = FLAG_TWO_SISTERS + 1; //Tho Xay
    public static final int FLAG_OLD_VILLAGE = FLAG_BUILDER + 1; //Gia Lang
    public static final int FLAG_CONTROLLER = FLAG_OLD_VILLAGE + 1; //Ke Thao Tung
    public static final int FLAG_KNIGHT = FLAG_CONTROLLER + 1; //Hiep Si
    public static final int FLAG_SERVANT = FLAG_KNIGHT + 1; //Day To
    public static final int FLAG_SNOWFLAKES = FLAG_SERVANT + 1; //Soi Tuyet
    public static final int FLAG_WILD_CHILD = FLAG_SNOWFLAKES + 1; //Dua Tre Hoang
    public static final int FLAG_INVERSE_PERSON = FLAG_WILD_CHILD + 1; //InversePerson
    public static final int FLAG_NOMADS = FLAG_INVERSE_PERSON + 1; //Du Muc
    public static final int FLAG_CUPID = FLAG_NOMADS + 1; //..
    public static final int FLAG_SECURITY = FLAG_CUPID + 1; //Bao Ve
    public static final int FLAG_WOLF_SNIFFING = FLAG_SECURITY + 1; // Soi danh hoi
    public static final int FLAG_WOLF = FLAG_WOLF_SNIFFING + 1; // Soi
    public static final int FLAG_PROPHET = FLAG_WOLF + 1; //Tien Tri
    public static final int FLAG_BEAR = FLAG_PROPHET + 1; //Gau
    public static final int FLAG_FOXES = FLAG_BEAR + 1; //Cao
    public static final int FLAG_WITCH = FLAG_FOXES + 1; //Phu Thuy
    public static final int FLAG_HUNTER = FLAG_WITCH + 1; //Tho San
    public static final int FLAG_IDIOT = FLAG_HUNTER + 1; //Thang Kho`
    public static final int FLAG_GRANDMOTHER = FLAG_IDIOT + 1; //Ba Gia
    public static final int FLAG_HUI_SICK = FLAG_GRANDMOTHER + 1; //Thang Benh Hui
    public static final int FLAG_MAD_SCIENTIST = FLAG_HUI_SICK + 1; //Nha Khoa Hoc Dien - Mad Scientist
    public static final int FLAG_BULLY = FLAG_MAD_SCIENTIST + 1; //Đầu gấu
    public static final int FLAG_WOLF_BABY = FLAG_BULLY + 1; //Ma Soi Con

    private List<Role> listRoles;
    private List<Role> listRolesUsing;
    private List<Integer> listShuffle;

    private static class ListPlayersHelper {
        private static final ListRoles INSTANCE = new ListRoles();
    }

    public static ListRoles getInstance() {
        return ListPlayersHelper.INSTANCE;
    }

    private ListRoles() {
        listRoles = new ArrayList<>();
    }

    public void createListRoles(Resources resources) {
        if (listRoles != null) {
            listRoles.clear();
        } else {
            listRoles = new ArrayList<>();
        }
        listRoles.add(new Role(FLAG_VILLAGERS, resources.getString(R.string.villagers), R.drawable.village, resources.getString(R.string.village_info), 0));
        listRoles.add(new Role(FLAG_WOLF_BOSS, resources.getString(R.string.black_wolf), R.drawable.wolf, resources.getString(R.string.black_wolf_info), 0));
        listRoles.add(new Role(FLAG_THREE_BROTHERS, resources.getString(R.string.three_brothers), R.drawable.threebrothers, resources.getString(R.string.three_brothers_info), 0));
        listRoles.add(new Role(FLAG_TWO_SISTERS, resources.getString(R.string.two_girls), R.drawable.twogirls, resources.getString(R.string.two_girls_info), 0));
        listRoles.add(new Role(FLAG_BUILDER, resources.getString(R.string.builder), R.drawable.builder, resources.getString(R.string.builder_info), 0));
        listRoles.add(new Role(FLAG_OLD_VILLAGE, resources.getString(R.string.old_village), R.drawable.oldvillage, resources.getString(R.string.old_village_info), 0));
        //listRoles.add(new Role(FLAG_CONTROLLER, "Kẻ Thao Túng", R.drawable.controller, "Khi trò chơi bắt đầu, ngời chơi sẽ chia làm 2 nhóm. Kẻ Thao Túng thuộc một trong 2 nhóm. Khi tất cả người chơi khác nhóm đều chết thì Kẻ Thao Túng sẽ thắng", 0));
        listRoles.add(new Role(FLAG_KNIGHT, resources.getString(R.string.knight), R.drawable.knight, resources.getString(R.string.knight_info), 0));
        //listRoles.add(new Role(FLAG_SERVANT, "Đầy Tớ", R.drawable.maid, "Người đầy tớ hiến dâng chính mình cho một vai trò khác. Trước khi tiết lộ lá bài của người chơi bị giết bởi dân làng. Đây Tớ có thể tiết lộ lá bài của mình cho mọi người xem. Nếu như vậy, người bị xử vẫn bị chết và Đây Tớ sẽ chơi với vai trò mới cho đến hết game", 0));
        //listRoles.add(new Role(FLAG_SNOWFLAKES, "Sói Trắng", R.drawable.whitewolf, "Thức dậy mỗi tối cùng các Ma Sói khác để giết dân làng. Sau khi Ma Sói đã đi ngủ, Ma Sói Trắng thức giấc lần nữa và giết một Ma Sói khác. Mục tiêu của Ma Sói Trắng là trở thành người duy nhất sống sót. Chỉ trong trường hợp đó nhân vật này mới chiến thắng", 0));
        //listRoles.add(new Role(FLAG_WILD_CHILD, "Đứa Trẻ Hoang", R.drawable.baby, "Chọn 1 người chơi bất kì trong đêm đầu. Khi người được chọn chưa chết thì đứa trẻ hoang sẽ thắng cùng dân, nếu người được chọn chết thì đứa trẻ hoang hóa thành sói. 1% hiếm hoi sói băng ngủ trúng đứa trẻ hoang trong đêm đầu thì đứa trẻ hoang mãi mãi là dân", 0));
        listRoles.add(new Role(FLAG_INVERSE_PERSON, resources.getString(R.string.inverse_person), R.drawable.inverse_person, resources.getString(R.string.inverse_person_info), 0));
        listRoles.add(new Role(FLAG_NOMADS, resources.getString(R.string.nomads), R.drawable.nomads, resources.getString(R.string.nomads_info), 0));
        listRoles.add(new Role(FLAG_CUPID, resources.getString(R.string.cupid), R.drawable.cupid, resources.getString(R.string.cupid_info), 0));
        listRoles.add(new Role(FLAG_SECURITY, resources.getString(R.string.protector), R.drawable.protector, resources.getString(R.string.protector_info), 0));
        listRoles.add(new Role(FLAG_WOLF_SNIFFING, resources.getString(R.string.wolf_sniffing), R.drawable.blackwolf, resources.getString(R.string.wolf_sniffing_info), 0));
        listRoles.add(new Role(FLAG_WOLF, resources.getString(R.string.wolf), R.drawable.blackwolf, resources.getString(R.string.wolf_info), 0));
        listRoles.add(new Role(FLAG_PROPHET, resources.getString(R.string.prophet), R.drawable.prophet, resources.getString(R.string.prophet_info), 0));
        //listRoles.add(new Role(FLAG_BEAR, "Quản Gấu", R.drawable.bear, "Nếu 2 người ngồi bên cạnh gấu có ít nhất 1 sói thì quản trò sẽ gật đầu, nếu cả 2 là người thì quản trò sẽ lắc đầu. Nếu người bên cạnh gấu chết thì sẽ tính theo người kế bên người chết.", 0));
        //listRoles.add(new Role(FLAG_FOXES, "Cáo", R.drawable.fox, "Vào buổi tối khi được gọi dậy. Con Cáo có thể chỉ vào một người. Nếu người đó hoặc hay người bên cạnh người đó là Sói thì Cáo sẽ được thức dậy ở đêm tiếp theo. Nếu không thì Cáo mất chức năng", 0));
        listRoles.add(new Role(FLAG_WITCH, resources.getString(R.string.witch), R.drawable.witch, resources.getString(R.string.witch_info), 0));
        listRoles.add(new Role(FLAG_HUNTER, resources.getString(R.string.hunter), R.drawable.hunter, resources.getString(R.string.hunter_info), 0));
        listRoles.add(new Role(FLAG_IDIOT, resources.getString(R.string.idiot), R.drawable.idiot, resources.getString(R.string.idiot_info), 0));
        listRoles.add(new Role(FLAG_GRANDMOTHER, resources.getString(R.string.grandmother), R.drawable.grandmother, resources.getString(R.string.grandmother_info), 0));
        listRoles.add(new Role(FLAG_HUI_SICK, resources.getString(R.string.hui_sick), R.drawable.sick_hui, resources.getString(R.string.hui_sick_info), 0));
        listRoles.add(new Role(FLAG_MAD_SCIENTIST, resources.getString(R.string.mad_scientist), R.drawable.mad_scientist, resources.getString(R.string.mad_scientist_info), 0));
        listRoles.add(new Role(FLAG_BULLY, resources.getString(R.string.bully), R.drawable.mad_scientist, resources.getString(R.string.bully_info), 0));
        listRoles.add(new Role(FLAG_WOLF_BABY, resources.getString(R.string.wolf_baby), R.drawable.mad_scientist, resources.getString(R.string.wolf_baby_info), 0));
    }

    public List<Role> getListRoles() {
        return listRoles;
    }

    public List<Role> getListRolesUsing() {
        return listRolesUsing;
    }

    public List<Integer> getListShuffle() {
        return listShuffle;
    }

    public int getRoleInListShuffle(int id) {
        for (int index = 0; index < listRolesUsing.size(); index++) {
            if (listRolesUsing.get(index).getId() == id) {
                return index;
            }
        }
        return -1;
    }

    public int getRoleInListRole(int id) {
        for (int index = 0; index < listRoles.size(); index++) {
            if (listRoles.get(index).getId() == id) {
                return index;
            }
        }
        return -1;
    }

    public int getRoleInListRoles(int id) {
        for (int index = 0; index < listRoles.size(); index++) {
            if (listRoles.get(index).getId() == id) {
                return index;
            }
        }
        return -1;
    }

    public void createListRolesUsing() {
        cleanListPlayerInListRoles();
        listRolesUsing = new ArrayList<>();
        listShuffle = new ArrayList<>();
        for (Role role : listRoles) {
            if (role.getNumberPlayer() > 0) {
                listRolesUsing.add(role);
                for (int i = 0; i < role.getNumberPlayer(); i++) {
                    listShuffle.add(role.getId());
                }
            }
        }
        Collections.shuffle(listShuffle);
    }

    public Role addPlayerToRole(Player player, int position) {
        int index = listShuffle.get(position);
        for (int i = 0; i < listRolesUsing.size(); i++) {
            if (listRolesUsing.get(i).getId() == index) {
                player.setRole(listRolesUsing.get(i));
                listRolesUsing.get(i).getListPlayer().add(player);
                return listRolesUsing.get(i);
            }
        }
        return null;
    }

    public List<Player> getListWolf() {
        List<Player> wolf = new ArrayList<>();

        int id = getRoleInListShuffle(ListRoles.FLAG_WOLF_BOSS);
        if (id > -1) {
            wolf.addAll(ListRoles.getInstance().getListRolesUsing().get(id).getListPlayerLive());
        }

        id = getRoleInListShuffle(ListRoles.FLAG_SNOWFLAKES);
        if (id > -1) {
            wolf.addAll(ListRoles.getInstance().getListRolesUsing().get(id).getListPlayerLive());
        }

        id = getRoleInListShuffle(ListRoles.FLAG_WOLF_SNIFFING);
        if (id > -1) {
            wolf.addAll(ListRoles.getInstance().getListRolesUsing().get(id).getListPlayerLive());
        }

        id = getRoleInListShuffle(ListRoles.FLAG_WOLF);
        if (id > -1) {
            wolf.addAll(ListRoles.getInstance().getListRolesUsing().get(id).getListPlayerLive());
        }

        id = getRoleInListShuffle(ListRoles.FLAG_WOLF_BABY);
        if (id > -1) {
            wolf.addAll(ListRoles.getInstance().getListRolesUsing().get(id).getListPlayerLive());
        }
        return wolf;
    }

    public int getNumberWolfRole() {
        int wolf = 0;

        int id = getRoleInListRole(ListRoles.FLAG_WOLF_BOSS);
        if (id > -1) {
            wolf = wolf + listRoles.get(id).getNumberPlayer();
        }

        id = getRoleInListRole(ListRoles.FLAG_SNOWFLAKES);
        if (id > -1) {
            wolf = wolf + listRoles.get(id).getNumberPlayer();
        }

        id = getRoleInListRole(ListRoles.FLAG_WOLF_SNIFFING);
        if (id > -1) {
            wolf = wolf + listRoles.get(id).getNumberPlayer();
        }

        id = getRoleInListRole(ListRoles.FLAG_WOLF);
        if (id > -1) {
            wolf = wolf + listRoles.get(id).getNumberPlayer();
        }

        id = getRoleInListRole(ListRoles.FLAG_WOLF_BABY);
        if (id > -1) {
            wolf = wolf + listRoles.get(id).getNumberPlayer();
        }
        return wolf;
    }

    public int getNumberOldVillageAndProphetRole() {
        int number = 0;

        int id = getRoleInListRole(ListRoles.FLAG_OLD_VILLAGE);
        if (id > -1) {
            number = number + listRoles.get(id).getNumberPlayer();
        }

        id = getRoleInListRole(ListRoles.FLAG_PROPHET);
        if (id > -1) {
            number = number + listRoles.get(id).getNumberPlayer();
        }
        return number;
    }

    public void cleanListPlayerInListRoles() {
        for (Role role : listRoles) {
            role.setListPlayer(new ArrayList<Player>());
        }
    }

    public void cleanNumberPlayerInListRoles() {
        for (Role role : listRoles) {
            role.setNumberPlayer(0);
        }
    }

    public void cleanAllFunction() {
        cleanListPlayerInListRoles();
        cleanNumberPlayerInListRoles();
    }
}
