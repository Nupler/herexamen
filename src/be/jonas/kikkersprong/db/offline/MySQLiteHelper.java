package be.jonas.kikkersprong.db.offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_AANWEZIGHEDEN = "aanwezigheden";
	public static final String COLUMN_ID_KIND = "id_kind";
	public static final String COLUMN_AANKOMST = "uur_aankomst";
	public static final String COLUMN_VERTREK = "uur_vertrek";

	private static final String DATABASE_NAME = "aanwezigheden.db";
	private static final int DATABASE_VERSION = 1;

	private static final String FILTER_CREATE = "create table "
			+ TABLE_AANWEZIGHEDEN + "(" + COLUMN_ID_KIND + " int not null, "
			+ COLUMN_AANKOMST + " int, " + COLUMN_VERTREK
			+ " int " + ");";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(FILTER_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AANWEZIGHEDEN);
		onCreate(db);
	}

}
