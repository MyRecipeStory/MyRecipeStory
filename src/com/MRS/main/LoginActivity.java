package com.MRS.main;

import com.MRS.MyRecipeStory.R;
import com.MRS.common.BackPressCloseHandler;
import com.MRS.common.FoodnoteDBManager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	FoodnoteDBManager mDbManager = null;
	EditText et_id, et_pw;
	private BackPressCloseHandler backHandler;

	// EditText db_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mDbManager = FoodnoteDBManager.getInstance(this);
		
		startActivity(new Intent(this, SplashActivity.class));

		et_id = (EditText) findViewById(R.id.editID);
		et_pw = (EditText) findViewById(R.id.editPW);
		
		backHandler = new BackPressCloseHandler(this);

		/* 아이디 유효성 검사 */
		// et_id.setFilters(new InputFilter[]{editFilter});

		// db_info = (EditText) findViewById(R.id.db_info);

		// 회원가입완료시 아이디칸에 입력값을 받아 자동 입력.
		Intent intent = getIntent();
		String mLoginID = intent.getStringExtra("LoginID");
			et_id.setText("qpqpqpqp");
			//et_id.setText(mLoginID);
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backHandler.onBackPressed();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.loginBtn: {
			String db_IdChk = "";
			String db_NNChk = "";
			String db_Pwd = "";
			String idChk = et_id.getText().toString();
			String pwdChk = et_pw.getText().toString();
			String[] columns = new String[] { "mID", "mPassword", "mPassword2", "mNickName, mIntro" };
			Cursor c = mDbManager.query(columns, "mID='" + idChk + "'", null, null, null, null);

			while (c.moveToNext()) {
				db_IdChk = c.getString(0);
				db_Pwd = c.getString(1);
				db_NNChk = c.getString(3);
			}
			/* 아이디 미입력시 */
			if (idChk.isEmpty()) {
				Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
				break;
			} else if (idChk.equals(db_IdChk)) {
				if (pwdChk.equals(db_Pwd)) {
					Intent intent = new Intent(this, MainActivity.class);
					intent.putExtra("NickName", db_NNChk);
					startActivity(intent);
					break;
				} else if (pwdChk.isEmpty()) {
					Toast.makeText(this, "패스워드를 입력하세요.", Toast.LENGTH_LONG).show();
					break;
				} else {
					Toast.makeText(this, "패스워드가 틀렸습니다.", Toast.LENGTH_LONG).show();
					break;
				}
			} else {
				Toast.makeText(this, "아이디가 없습니다.", Toast.LENGTH_LONG).show();
				break;
			}
			/* 회원가입 */
		}
		case R.id.joinBtn: {
			Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
			startActivity(intent);
			break;
		}
		}
	}
	
}