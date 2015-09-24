package com.MRS.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class FoodnoteDBManager extends SQLiteOpenHelper {

	// DB 생성, 테이블 생성, 버젼.
	static final     String            DB_NAME           = "foodnote.db";
	static final     String            MEMBER_TABLE_NAME = "memberTBL";
	static final int                   DB_VERSION        = 5;

	                 Context           mContext          = null;

	public static    FoodnoteDBManager mDbManager        = null;

	public static    FoodnoteDBManager getInstance(Context context) {
		if ( mDbManager == null ) {
			mDbManager = new FoodnoteDBManager(context, DB_NAME, null, DB_VERSION);
		}
		return mDbManager;
	}

	// DB 클래스 (singleton)으로 연결.
	private FoodnoteDBManager(Context context, String db_name, CursorFactory factory, int version) {
		super(context, db_name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " + MEMBER_TABLE_NAME + "(" +
				   "mID            TEXT UNIQUE, " +
				   "mPassword      TEXT NOT NULL, " +
				   "mPassword2     TEXT NOT NULL, " +
				   "mNickName      TEXT," +
				   "mIntro         TEXT NULL," +
				   "mPicture       TEXT NULL," +
				   "PRIMARY KEY(mNickName));");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE_NAME);
			onCreate(db);
		}
	}

	public long insert(ContentValues addRowValue) {
		return getWritableDatabase().insert(MEMBER_TABLE_NAME, null, addRowValue);
	}

	public int delete(String whereClause, String[] whereArgs) {
		return getWritableDatabase().delete(MEMBER_TABLE_NAME, whereClause, whereArgs);
	}

	public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return getReadableDatabase().query(MEMBER_TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
}