package kh.nobita.hang.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kh.nobita.hang.R;
import kh.nobita.hang.Utils.AppController;
import kh.nobita.hang.Utils.LocaleHelper;
import kh.nobita.hang.Utils.MyContextWrapper;
import kh.nobita.hang.adapter.WerewolfLibraryRolesGridAdapter;
import kh.nobita.hang.model.Roles.ListRoles;
import kh.nobita.hang.model.Roles.Role;
import kh.nobita.hang.widget.GridSpacingItemDecoration;

public class WerewolfLibrary extends AppCompatActivity {
    public static String TAG = WerewolfLibrary.class.getSimpleName();

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private WerewolfLibraryRolesGridAdapter adapter;
    private List<Role> rolesList;
    private List<Role> filterRolesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_werewolf_lib);
        initView();
        setListeners();
    }

    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(LocaleHelper.getLangResources(this).getString(R.string.opt_werewolf_library));
        recyclerView = (RecyclerView) findViewById(R.id.gv_werewolf_library);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rolesList = ListRoles.getInstance().getListRoles();
        filterRolesList = rolesList;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new WerewolfLibraryRolesGridAdapter(this, filterRolesList);
        recyclerView.setAdapter(adapter);
    }

    // Set Listeners
    private void setListeners() {
        // Set check listener over checkbox for showing and hiding password
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                    filterRolesList = rolesList;
                } else {

                    List<Role> filter = new ArrayList<>();

                    for (Role item : rolesList) {

                        if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                            filter.add(item);
                        }
                    }
                    filterRolesList = filter;
                }
                adapter.setItemList(filterRolesList);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
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
