package com.MRS.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.MRS.MyRecipeStory.R;
import com.MRS.common.WriteDBManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class WriteActivity extends Activity {

	final int REQ_CODE_SELECT_IMAGE =100;

	String name_Str = "";

	WriteDBManager wDbManager = null;

	String[] mWeather = null;
	ArrayAdapter<String> mAdapter = null;
	Spinner sp = null;
	Button btn_reset, btn_capic, btn_submit;
	EditText et_Title, et_Subject;
	String wTitle, wSubject, wWeather;

	Intent mintent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write);

		wDbManager = WriteDBManager.getInstance(this);

		// 버튼
		btn_reset = (Button) findViewById(R.id.reset);
		btn_capic = (Button) findViewById(R.id.capic);
		btn_submit = (Button) findViewById(R.id.submit);

		et_Title = (EditText) findViewById(R.id.food_Title);
		et_Subject = (EditText) findViewById(R.id.food_Subject);

		// 스피너
		sp = (Spinner) findViewById(R.id.weather);

		// spinner 데이터 준비
		mWeather = getResources().getStringArray(R.array.weather);

		// 어댑터에 데이터 장착
		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, mWeather);

		// 스피터에 어뎁터 세팅
		sp.setAdapter(mAdapter);

		// 아이템 선택
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				wWeather = mAdapter.getItem((int) id).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQ_CODE_SELECT_IMAGE) {
			if (resultCode == Activity.RESULT_OK) {
				try {
					// Uri에서 이미지 이름을 얻어온다.
					name_Str = getImageNameToUri(data.getData());

					// 이미지 데이터를 비트맵으로 받아온다.
					Bitmap image_bitmap = Images.Media.getBitmap(
							getContentResolver(), data.getData());
					ImageView image = (ImageView) findViewById(R.id.pic_image);

					// 배치해놓은 ImageView에 set
					image.setImageBitmap(image_bitmap);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getImageNameToUri(Uri data) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(data, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		String imgPath = cursor.getString(column_index);
		String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

		return imgPath;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset: {

			break;
		}
		case R.id.capic: {

			// 2015-08-24작업
			// 버튼 클릭시 처리로직
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);


			break;
		}
		case R.id.submit: {

			// 현재시간
			long time = System.currentTimeMillis();

			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");

			String wDate = dayTime.format(new Date(time));

			wTitle = et_Title.getText().toString();
			wSubject = et_Subject.getText().toString();
			
			if(wTitle.isEmpty()){
				Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
				break;
			}else if(wSubject.isEmpty()){
				Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
				break;
			}else if(wWeather.equals("날씨")){
				Toast.makeText(this, "날씨를 선택해주세요", Toast.LENGTH_SHORT).show();
				break;
			}else if(name_Str.isEmpty()){
				Toast.makeText(this, "사진을 추가해주세요", Toast.LENGTH_SHORT).show();
				break;
			}
			ContentValues addRowValue = new ContentValues();
			mintent = getIntent();
			String wNickName = mintent.getStringExtra("wNickName");

			addRowValue.put("wNickName", wNickName);
			addRowValue.put("wTitle", wTitle);	
			addRowValue.put("wSubject", wSubject);
			addRowValue.put("wDate", wDate);
			addRowValue.put("wWeather", wWeather);
			addRowValue.put("wPicture", name_Str);

			wDbManager.insert(addRowValue); 
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("NickName", wNickName);
			startActivity(intent);

			break;
		}
		}
	}

}