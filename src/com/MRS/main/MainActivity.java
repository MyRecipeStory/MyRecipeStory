package com.MRS.main;

import com.MRS.MyRecipeStory.R;
import com.MRS.common.BackPressCloseHandler;
import com.MRS.common.WriteDBManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private PopupWindow mPopupWindow, mPopupWindow1;

	TextView nick_tv;
	TextView intro_tv;
	String mNickName;
	ListView list;

	WriteDBManager wDBManager = null;
	List_CursorAdapter mAdapter = null;
	
	private BackPressCloseHandler backHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		backHandler = new BackPressCloseHandler(this);
		Intent intent = getIntent();
		mNickName = intent.getStringExtra("NickName");
		// 2015-08-19 작업
		// 1. 데이터를 얻어옴
		wDBManager = WriteDBManager.getInstance(this);
		String columns[] = { "_id", "wNickName", "wTitle", "wSubject", "wDate",
				"wWeather", "wPicture" };
		Cursor c = wDBManager.query(columns, "wNickName='" + mNickName + "'",
				null, null, null, null);
		/*
		 * while(c.moveToNext()){
		 * 
		 * Log.d("wNickName ",c.getString(1)); Log.d("wTitle ",c.getString(2));
		 * Log.d("wSubject ",c.getString(3)); Log.d("wDate ",c.getString(4));
		 * Log.d("wWeather ",c.getString(5)); int _id = c.getInt(0); String
		 * wNickName = c.getString(1); String wTitle = c.getString(2); String
		 * wSubject = c.getString(3); String wDate = c.getString(4); String
		 * wWeather = c.getString(5); }
		 */

		// c.moveToFirst();

		/*
		 * String col[]=c.getColumnNames(); for(int i=0;i<col.length;i++){
		 * Log.d("Column["+i+"] : ", col[i]+"\n"); } int _id = c.getInt(0);
		 * Log.d("_id의 값",String.valueOf(_id));
		 */
		// 2. 어댑터를 생성하고 데이터 설정
		mAdapter = new List_CursorAdapter(this, c);

		// 리스트 뷰에 어댑터 설정
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(mAdapter);
		// 작업 끝

		nick_tv = (TextView) findViewById(R.id.nick_tv);
		nick_tv.setText(mNickName);
		intro_tv = (TextView) findViewById(R.id.intro_tv);

		ImageButton writebtn = (ImageButton) findViewById(R.id.new_write);

		writebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						WriteActivity.class);
				intent.putExtra("wNickName", mNickName);

				startActivity(intent);
			}
		});

		nick_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.isClickable()) {
					View popupView = getLayoutInflater().inflate(
							R.layout.nickname, null);

					mPopupWindow = new PopupWindow(popupView,
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					mPopupWindow.setAnimationStyle(-1); // (-1 : 미적용 / 0 : 적용)
					mPopupWindow.showAsDropDown(nick_tv, 50, 50);

				}
			}
		});

		intro_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v1) {
				if (v1.isClickable()) {
					View popupView1 = getLayoutInflater().inflate(
							R.layout.introduce, null);

					mPopupWindow1 = new PopupWindow(popupView1,
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);

					mPopupWindow1.setAnimationStyle(-1); // (-1 : 미적용 / 0 : 적용)
					mPopupWindow1.showAsDropDown(intro_tv, 50, 50);
				}
			}
		});

	}
	// onCreate() End
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backHandler.onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}