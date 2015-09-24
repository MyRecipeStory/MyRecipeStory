package com.MRS.main;

import java.util.regex.Pattern;

import com.MRS.MyRecipeStory.R;
import com.MRS.common.FoodnoteDBManager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinActivity extends Activity {

	FoodnoteDBManager mDbManager = null;

	EditText          id, password, password2, nickName;
	Button            join, cancel, market;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);

		mDbManager = FoodnoteDBManager.getInstance(this);

		id         = (EditText) findViewById(R.id.id);
		password   = (EditText) findViewById(R.id.password);
		password2  = (EditText) findViewById(R.id.password2);
		nickName   = (EditText) findViewById(R.id.nickName);
		join       = (Button) findViewById(R.id.joinBtn2);
		cancel     = (Button) findViewById(R.id.cancelBtn);
		// market = (Button) findViewById(R.id.marketBtn);

		id.setFilters(new InputFilter[] { editFilter });
		join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// DB 중복 체크.
				String   db_IdChk = "";
				String   db_NNChk = "";
				String   idChk    = id.getText().toString();
				String   nnChk    = nickName.getText().toString();
				String[] columns  = new String[] { "mID", "mPassword", "mPassword2", "mNickName, mIntro" };
				Cursor   c        = mDbManager.query(columns, null, null, null, null, null);

				while (c.moveToNext()) {
					db_IdChk = c.getString(0);
					db_NNChk = c.getString(3);
				}

				try {
					// 강제 Exception 발생
					throw new SQLException();
				} catch (SQLException e) {
					if ( id.getText().toString().equals("") ) {
						id.requestFocus();
						Toast.makeText(getApplicationContext(), "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( id.getText().toString().getBytes().length > 12 ) {
						id.requestFocus();
						Toast.makeText(getApplicationContext(), "아이디가 너무 깁니다!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( id.getText().toString().getBytes().length < 8 ) {
						id.requestFocus();
						Toast.makeText(getApplicationContext(), "아이디가 너무 짧습니다!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( password.getText().toString().equals("") ) {
						password.requestFocus();
						Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( password.getText().toString().getBytes().length < 4 ) {
						Toast.makeText(getApplicationContext(), "비밀번호가 너무 짧습니다!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( password.getText().toString().getBytes().length > 12 ) {
						Toast.makeText(getApplicationContext(), "비밀번호가 너무 깁니다!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( password2.getText().toString().equals("") ) {
						password2.requestFocus();
						Toast.makeText(getApplicationContext(), "비밀번호확인을 입력하세요!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( nickName.getText().toString().equals("") ) {
						nickName.requestFocus();
						Toast.makeText(getApplicationContext(), "닉네임을 입력하세요!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( !password.getText().toString().equals(password2.getText().toString()) ) {
						password2.requestFocus();
						Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
						return;
					} else if ( nickName.getText().toString().getBytes().length > 32 ) {
						Toast.makeText(getApplicationContext(), "닉네임이 너무 깁니다!", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				// 아이디, 닉네임 유효성 체크.
				if ( idChk.equals(db_IdChk) ) {
					id.requestFocus();
					Toast.makeText(getApplicationContext(), "아이디가 이미 있습니다!", Toast.LENGTH_SHORT).show();
					return;
				} else if ( nnChk.equals(db_NNChk) ) {
					nickName.requestFocus();
					Toast.makeText(getApplicationContext(), "닉네임이 이미 있습니다!", Toast.LENGTH_SHORT).show();
					return;
				}
				// DB 데이터 입력.
				ContentValues addRowValue = new ContentValues();
				addRowValue.put("mID", id.getText().toString());
				addRowValue.put("mPassword", password.getText().toString());
				addRowValue.put("mPassword2", password2.getText().toString());
				addRowValue.put("mNickName", nickName.getText().toString());
				mDbManager.insert(addRowValue);

				Toast.makeText(getApplicationContext(), "가입완료!", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(JoinActivity.this, MainActivity.class);
				intent.putExtra("NickName", nickName.getText().toString());
				startActivity(intent);
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});

		/*
		 * market.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(JoinActivity.this, MarketActivity.class);
		 * startActivity(intent); } });
		 */
	}

	protected InputFilter editFilter = new InputFilter() {

	public    CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

			// Pattern pattern = Pattern.compile("^[A-Z]+$"); // 영어 대문자
			// Pattern pattern = Pattern.compile("^[a-zA-Z]+$"); // 영어
			Pattern pattern = Pattern.compile("^[\\sㄱ-ㅎㅏ-ㅣ가-힣]+$"); // 한글
			// Pattern pattern = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣R]+$"); // 영어 숫자 한글

			if ( pattern.matcher(source).matches() ) {
				Toast.makeText(getApplicationContext(), "한글입력은 불가능합니다!", Toast.LENGTH_SHORT).show();
				return "";
			}
			return null;
		}
	};
}