package kh.nobita.hang.database;

import android.provider.BaseColumns;

public final class PlayersContract {

	public static abstract class PlayerContract implements BaseColumns {
		public static final String TABLE_NAME = "players";
		public static final String COLUMN_NAME_PLAYER_NAME = "name";
		public static final String COLUMN_NAME_PLAYER_PATH_PROFILE = "path_profile";
	}

}
