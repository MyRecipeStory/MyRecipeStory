package com.MRS.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class WriteDBManager extends SQLiteOpenHelper {

	// DB 생성, 테이블 생성, 버젼.
	static final String DB_NAME = "write.db";
	static final String WRITE_TABLE_NAME = "writeTBL";
	static final int DB_VERSION = 3;	//20150824

	Context mContext = null;

	public static WriteDBManager wDbManager = null;

	public static WriteDBManager getInstance(Context context) {
		if (wDbManager == null) {
			wDbManager = new WriteDBManager(context, DB_NAME, null, DB_VERSION);
		}
		return wDbManager;
	}

	// DB Singleton 으로 연결.
	private WriteDBManager(Context context, String db_name, CursorFactory factory, int version) {
		super(context, db_name, factory, version);

		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + WRITE_TABLE_NAME + "("+
				"_id			  INTEGER PRIMARY KEY AUTOINCREMENT,"+	
				"wNickName        TEXT NOT NULL," +
				"wTitle            TEXT NOT NULL, " +
				"wSubject          TEXT NOT NULL, " +
				"wDate          TEXT NOT NULL, " +
				"wWeather          TEXT NOT NULL," +
				"wPicture		TEXT NOT NULL);"); 
				//"FOREIGN KEY(wNickName) REFERENCES memberTBL(mNickName); ");
		
		/*db.execSQL("CREATE TABLE IF NOT EXISTS " + WRITE_TABLE_NAME + "(" +
				"mID            TEXT UNIQUE, " +
				"mPassword      TEXT NOT NULL, " +
				"mPassword2     TEXT NOT NULL, " +
				"mNickName      TEXT," +
				"mIntro         TEXT NULL," +
				"PRIMARY KEY(mNickName));");*/
		}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		Log.d("확인 ? ", "열림");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WRITE_TABLE_NAME);
			onCreate(db);
			Log.d("확인 ? ", "수정됨");
		}
	}

	public long insert(ContentValues addRowValue) {
		Log.d("확인 ? ", "추가");
		return getWritableDatabase().insert(WRITE_TABLE_NAME, null, addRowValue);
	}

	public int delete(String whereClause, String[] whereArgs) {
		Log.d("확인 ? ", "삭제");
		return getWritableDatabase().delete(WRITE_TABLE_NAME, whereClause, whereArgs);
	}

	public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		Log.d("확인 ? ", "쿼리");
		return getReadableDatabase().query(WRITE_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

}