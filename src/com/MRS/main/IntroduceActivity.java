package com.MRS.main;

import com.MRS.MyRecipeStory.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.view.WindowManager;

public class IntroduceActivity extends Activity {

	// Dialog 생성.
	private Dialog mDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduce);

		createDialog();
	}

	// 클릭 시 이벤트처리.
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.introCngBtn:
			dismissDialog();
			break;
		default:
			break;
		}
	}

	private void createDialog() {
		final View innerView = getLayoutInflater().inflate(R.layout.introduce, null);

		mDialog = new Dialog(this);
		mDialog.setTitle("Title");
		mDialog.setContentView(innerView);

		// Back키 눌렀을 경우 Dialog Cancel 여부 설정.
		mDialog.setCancelable(true);

		// Dialog 상단 좌측으로 이동.
		mDialog.getWindow().setGravity(Gravity.BOTTOM);

		// Dialog 호출 시 배경화면이 검정색으로 바뀌는 것 막기.
		mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		// Dialog 밖을 터치 했을 경우 Dialog 사라지게 하기.
		mDialog.setCanceledOnTouchOutside(true);

		// Dialog 밖의 View를 터치할 수 있게 하기 (다른 View를 터치시 Dialog dismiss).
		mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

		// Dialog 자체 배경을 투명하게 하기.
		// mDialog.getWindow().setBackgroundDrawable
		// (new ColorDrawable(android.graphics.Color.TRANSPARENT));

		// Dialog Cancle시 Event 받기.
		mDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				Toast.makeText(IntroduceActivity.this, "cancle listener", Toast.LENGTH_SHORT).show();
			}
		});

		// Dialog Show시 Event 받기.
		mDialog.setOnShowListener(new OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				Toast.makeText(IntroduceActivity.this, "show listener", Toast.LENGTH_SHORT).show();
			}
		});

		// Dialog Dismiss시 Event 받기.
		mDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				Toast.makeText(IntroduceActivity.this, "dismiss listener", Toast.LENGTH_SHORT).show();
			}
		});

		// Dialog 상단에 붙임.
		LayoutParams params = mDialog.getWindow().getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
		mDialog.show();
	}

	private void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

}