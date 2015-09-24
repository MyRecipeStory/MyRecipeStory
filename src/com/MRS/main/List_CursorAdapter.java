package com.MRS.main;

import com.MRS.MyRecipeStory.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class List_CursorAdapter extends CursorAdapter {
	
	public List_CursorAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		mContext                   = context;
		mLayoutInflater            = LayoutInflater.from(context);
	}

	Context mContext               = null;
	LayoutInflater mLayoutInflater = null;
	/*public List_CursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		// TODO Auto-generated constructor stub
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
	}*/
	class ViewHolder{
		ImageView list_Image;
		TextView  list_title;
		TextView  list_subject;
		TextView  list_date;
		TextView  list_weather;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		//1. 새로운 아이템뷰를 생성한다.
		View itemLayout         = mLayoutInflater.inflate(R.layout.write_list_view, null);
		//2. 아이템에 뷰홀더를 설정한다.
		ViewHolder viewHolder   = new ViewHolder();
		
		viewHolder.list_Image   = (ImageView) itemLayout.findViewById(R.id.list_image);
		viewHolder.list_title   = (TextView) itemLayout.findViewById(R.id.list_title);
		viewHolder.list_date    = (TextView) itemLayout.findViewById(R.id.list_date);
		viewHolder.list_weather = (TextView) itemLayout.findViewById(R.id.list_weather);
		itemLayout.setTag(viewHolder);
		
		return itemLayout;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		//1.아이템뷰에 저장된 뷰홀더를 얻어온다.
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		//2. 현재 커서 위치에 데이터를 얻어온다.
		String     image_path = cursor.getString(cursor.getColumnIndex("wPicture"));
		String     title      = cursor.getString(cursor.getColumnIndex("wTitle"));
		String     date       = cursor.getString(cursor.getColumnIndex("wDate"));
		String     weather    = cursor.getString(cursor.getColumnIndex("wWeather"));
		//3. 데이터를 뷰에 적용한다.
		Bitmap     myBitmap   = BitmapFactory.decodeFile(image_path);
		//myBitmap.createScaledBitmap(myBitmap, , dstHeight, filter)
		viewHolder.list_Image.setImageBitmap(myBitmap);
		viewHolder.list_title.setText(title);
		viewHolder.list_date.setText(date);
		viewHolder.list_weather.setText(weather);
	}
}