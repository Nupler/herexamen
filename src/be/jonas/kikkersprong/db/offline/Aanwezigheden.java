package be.jonas.kikkersprong.db.offline;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import be.jonas.kikkersprong.db.online.OnlineDBTask;
import be.jonas.kikkersprong.db.online.SyncOnline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class Aanwezigheden {

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private Context context;

	private String[] allColumns = { MySQLiteHelper.COLUMN_ID_KIND,
			MySQLiteHelper.COLUMN_AANKOMST, MySQLiteHelper.COLUMN_VERTREK };

	public Aanwezigheden(Context context) {
		this.context = context;
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void childArrives(int childId) {
		open();
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_AANKOMST, System.currentTimeMillis());
		values.put(MySQLiteHelper.COLUMN_ID_KIND, childId);

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());

		Log.i("Aanwezigheden", c.get(Calendar.DAY_OF_MONTH) + "");
		database.insert(MySQLiteHelper.TABLE_AANWEZIGHEDEN, null, values);
		logContent();
		close();
	}

	public boolean childIsPresent(int childId) {
		open();
		boolean present = false;
		String[] clmns = { MySQLiteHelper.COLUMN_VERTREK };
		Cursor cursor = database.query(MySQLiteHelper.TABLE_AANWEZIGHEDEN,
				clmns, MySQLiteHelper.COLUMN_ID_KIND + " = " + childId, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if (cursor.getLong(0) == 0) {
				present = true;
			}
			cursor.moveToNext();
		}
		close();
		return present;
	}

	public void childLeaves(int childId) {
		open();

		String strFilter = MySQLiteHelper.COLUMN_ID_KIND + " = " + childId
				+ " AND " + MySQLiteHelper.COLUMN_VERTREK + " is NULL";

		ContentValues args = new ContentValues();

		args.put(MySQLiteHelper.COLUMN_VERTREK, System.currentTimeMillis());
		database.update(MySQLiteHelper.TABLE_AANWEZIGHEDEN, args, strFilter,
				null);

		close();
		if (isNetworkAvailable()) {
			saveOnline();
		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void logContent() {

		Cursor cursor = database.query(MySQLiteHelper.TABLE_AANWEZIGHEDEN,
				allColumns, null, null, null, null, null);

		int counter = 0;
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {

			Log.i("DATABASE", "ENTRY" + counter);
			counter++;
			for (int i = 0; i < 3; i++) {
				Log.i(cursor.getColumnName(i), cursor.getLong(i) + "");
			}
			cursor.moveToNext();
		}
	}

	public void saveOnline() {
		open();

		SyncOnline db = new SyncOnline();
		String strFilter = MySQLiteHelper.COLUMN_VERTREK + " is not NULL";

		Cursor cursor = database.query(MySQLiteHelper.TABLE_AANWEZIGHEDEN,
				allColumns, strFilter, null, null, null, null);
		cursor.moveToFirst();
		List<Aanwezigheid> aanwezigheden = new ArrayList<Aanwezigheid>();

		while (!cursor.isAfterLast()) {

			String childId = cursor.getInt(0)+"";
			long aankomst = cursor.getLong(1);
			long vertrek = cursor.getLong(2);
			Calendar a = Calendar.getInstance();
			Calendar v = Calendar.getInstance();

			a.setTimeInMillis(aankomst);
			v.setTimeInMillis(vertrek);

			String uren = ((double) ((vertrek - aankomst)) / 1000)+"";
			String datum = a.get(Calendar.YEAR) + "-"
					+ a.get(Calendar.MONTH) + "-"
					+ a.get(Calendar.DAY_OF_MONTH);
			
			
			aanwezigheden.add(new Aanwezigheid(childId,datum,uren));
			cursor.moveToNext();
		}

		db.uploadAanwezigheden(aanwezigheden);

		database.delete(MySQLiteHelper.TABLE_AANWEZIGHEDEN,
				MySQLiteHelper.COLUMN_VERTREK + " is not null", null);
		close();
	}

	public List<Aanwezigheid> getAanwezigheden() {
		List<Aanwezigheid> aanwezigheden = new ArrayList<Aanwezigheid>();
		open();

		String strFilter = MySQLiteHelper.COLUMN_VERTREK + " is not NULL";

		Cursor cursor = database.query(MySQLiteHelper.TABLE_AANWEZIGHEDEN,
				allColumns, strFilter, null, null, null, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {

			int childId = cursor.getInt(0);
			long aankomst = cursor.getLong(1);
			long vertrek = cursor.getLong(2);
			Calendar a = Calendar.getInstance();
			Calendar v = Calendar.getInstance();

			a.setTimeInMillis(aankomst);
			v.setTimeInMillis(vertrek);

			double uren = (double) ((vertrek - aankomst)) / 1000;
			String datum = a.get(Calendar.YEAR) + "-" + a.get(Calendar.MONTH)
					+ "-" + a.get(Calendar.DAY_OF_MONTH);

			aanwezigheden.add(new Aanwezigheid(childId + "", datum, uren + ""));
			cursor.moveToNext();
		}

		close();
		return aanwezigheden;

	}
}
