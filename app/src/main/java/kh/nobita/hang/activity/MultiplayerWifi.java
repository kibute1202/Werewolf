package kh.nobita.hang.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bvapp.arcmenulibrary.ArcMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.MyContextWrapper;
import kh.nobita.hang.adapter.RoomAdapter;
import kh.nobita.hang.model.Room;
import kh.nobita.hang.multicast.MultiCastThread;
import kh.nobita.hang.multicast.MulticastContain;

public class MultiplayerWifi extends AppCompatActivity {
    private Toolbar toolbar;
    public static final int[] LIST_IMAGE_WEREWOLF_GAME = {R.drawable.threebrothers, R.drawable.ba_dong,
            R.drawable.protector, R.drawable.fox, R.drawable.baby, R.drawable.village,
            R.drawable.maid, R.drawable.oldvillage, R.drawable.controller, R.drawable.twogirls,
            R.drawable.knight, R.drawable.idiot,
            R.drawable.bear, R.drawable.blackwolf, R.drawable.whitewolf};

    private String TAG = MultiplayerWifi.class.getSimpleName();

    private RecyclerView recyclerView;
    private RoomAdapter adapter;
    private List<Room> roomList;
    private List<Room> filterRoomList;
    private ArcMenu arcMainMenu;
    private MultiCastThread multiCastThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_wifi);
        Init();
        getRoom();
    }

    public void Init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        arcMainMenu = (ArcMenu) findViewById(R.id.arc_main_menu);
        initArcMenu(arcMainMenu);

        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        roomList = new ArrayList<>();
        filterRoomList = roomList;
        adapter = new RoomAdapter(this, filterRoomList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            Picasso.with(this).load(R.drawable.cover).fit().centerCrop().placeholder(android.R.drawable.ic_menu_report_image).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        multiCastThread = MultiCastThread.getInstance();
        multiCastThread.sendData(MulticastContain.GET_ALL_ROOM + "");
    }

    private void initArcMenu(final ArcMenu menu) {
        int[] items = {R.mipmap.ic_setting, R.mipmap.ic_add, R.mipmap.ic_tutorial, R.mipmap.ic_refresh};
        final String[] str = {"setting_content", "add", "tutorial", "refresh"};
        for (int i = 0; i < items.length; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(items[i]);

            final int position = i;
            menu.addItem(item, str[i], new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:
                            break;
                    }
                }
            });
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    private void getRoom() {

        Room a = new Room("True Romance", 13, 14, true);
        roomList.add(a);

        a = new Room("Xscpae", 13, 14, true);
        roomList.add(a);

        a = new Room("Maroon 5", 13, 14, true);
        roomList.add(a);

        a = new Room("Born to Die", 13, 14, true);
        roomList.add(a);

        a = new Room("Honeymoon", 13, 14, true);
        roomList.add(a);

        a = new Room("I Need a Doctor", 13, 14, true);
        roomList.add(a);

        a = new Room("Loud", 13, 14, true);
        roomList.add(a);

        a = new Room("Legend", 13, 14, true);
        roomList.add(a);

        a = new Room("Hello", 13, 14, true);
        roomList.add(a);

        a = new Room("Greatest Hits", 13, 14, true);
        roomList.add(a);

        try {
            roomList.add(parseJson(parseJson("kien", 1, 100, false)));
            roomList.add(parseJson(parseJson("kien2", 1, 50, false)));
            roomList.add(parseJson(parseJson("kien3", 1, 20, false)));
            roomList.add(parseJson(parseJson("kien4", 1, 10, false)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        filterRoomList = roomList;
        adapter.notifyDataSetChanged();
    }

    private String parseJson(String name, int numberMember, int numberMemberLimit, boolean isStart) throws JSONException {
        JSONObject room = new JSONObject();
        room.put("name_room", name);
        room.put("number_member", numberMember);
        room.put("number_member_limit", numberMemberLimit);
        room.put("is_start", isStart);

        return room.toString();
    }

    private Room parseJson(String data) throws JSONException {
        JSONObject room = new JSONObject(data);

        String nameRoom = room.getString("name_room");
        Log.e(TAG, "name_room: " + nameRoom);

        int numberMember = room.getInt("number_member");
        Log.e(TAG, "number_member: " + numberMember);

        int numberMemberLimit = room.getInt("number_member_limit");
        Log.e(TAG, "number_member_limit: " + numberMemberLimit);

        Boolean isStart = room.getBoolean("is_start");
        Log.e(TAG, "isStart: " + isStart);

        return new Room(nameRoom, numberMember, numberMemberLimit, isStart);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    filterRoomList = roomList;
                } else {

                    ArrayList<Room> filter = new ArrayList<>();

                    for (Room item : roomList) {

                        if (item.getNameRoom().toLowerCase().contains(newText)) {
                            filter.add(item);
                        }
                    }
                    filterRoomList = filter;
                }
                adapter.setItemList(filterRoomList);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase, AppController.getInstance().getPrefManager().getLang()));
    }
}
