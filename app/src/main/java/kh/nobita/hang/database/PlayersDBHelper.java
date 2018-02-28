package kh.nobita.hang.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kh.nobita.hang.database.PlayersContract.PlayerContract;
import kh.nobita.hang.model.Player;

public class PlayersDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "players.db";

    private static final String SQL_CREATE_PlayerContract = "CREATE TABLE "
            + PlayerContract.TABLE_NAME + " (" + PlayerContract._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PlayerContract.COLUMN_NAME_PLAYER_NAME + " TEXT,"
            + PlayerContract.COLUMN_NAME_PLAYER_PATH_PROFILE + " TEXT" + " )";

    private static final String SQL_DELETE_PLAYERS = "DROP TABLE IF EXISTS "
            + PlayerContract.TABLE_NAME;

    public PlayersDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PlayerContract);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PLAYERS);
        onCreate(db);
    }

    private Player populateModel(Cursor c) {
        Player model = new Player();
        model.setId(c.getLong(c.getColumnIndex(PlayerContract._ID)));
        model.setName(c.getString(c.getColumnIndex(PlayerContract.COLUMN_NAME_PLAYER_NAME)));
        model.setPathProfile(c.getString(c.getColumnIndex(PlayerContract.COLUMN_NAME_PLAYER_PATH_PROFILE)));
        return model;
    }

    private ContentValues populateContent(Player model) {
        ContentValues values = new ContentValues();
        values.put(PlayerContract.COLUMN_NAME_PLAYER_NAME, model.getName());
        values.put(PlayerContract.COLUMN_NAME_PLAYER_PATH_PROFILE, model.getPathProfile());
        return values;
    }

    public long createPlayer(Player model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(PlayerContract.TABLE_NAME, null, values);
    }

    public long updatePlayer(Player model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(PlayerContract.TABLE_NAME, values, PlayerContract._ID + " = ?", new String[]{String.valueOf(model.getId())});
    }

    public Player getPlayerLast() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + PlayerContract.TABLE_NAME + " WHERE " + PlayerContract._ID + " = (SELECT MAX(" + PlayerContract._ID + ") FROM " + PlayerContract.TABLE_NAME + ")";
        Cursor c = db.rawQuery(select, null);
        if (c.moveToNext()) {
            return populateModel(c);
        }
        return null;
    }

    public Player getPlayer(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + PlayerContract.TABLE_NAME + " WHERE " + PlayerContract._ID + " = " + id;
        Cursor c = db.rawQuery(select, null);
        if (c.moveToNext()) {
            return populateModel(c);
        }
        return null;
    }

    public List<Player> getPlayers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + PlayerContract.TABLE_NAME;
        Cursor c = db.rawQuery(select, null);
        List<Player> playerContractList = new ArrayList<>();
        while (c.moveToNext()) {
            playerContractList.add(populateModel(c));
        }
        return playerContractList;
    }

    public int deletePlayer(long id) {
        return getWritableDatabase().delete(PlayerContract.TABLE_NAME, PlayerContract._ID + " = ?", new String[]{String.valueOf(id)});
    }
}
