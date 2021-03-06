package com.MRS.main;

import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.MRS.MyRecipeStory.R;
import com.MRS.common.BackPressCloseHandler;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;

import android.content.Context;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class MarketActivity extends NMapActivity implements OnMapStateChangeListener, OnMapViewTouchEventListener, OnCalloutOverlayListener {

	// '뒤로' 버튼 두번 입력시 어플리케이션 종료
	private              BackPressCloseHandler backPressCloseHandler;
	// API-KEY
	private static final String          API_KEY        = "cd710fbb302d1ee5162dcd491598c406";
	// 맵뷰
	                     NMapView        mMapView       = null;
	// 맵컨트롤러
	                     NMapController  mMapController = null;
	// 레이아웃
	                     LinearLayout    MapContainer;
	                     LocationManager mLocMgr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MapContainer          = (LinearLayout) findViewById(R.id.map);
		mMapView              = new NMapView(this);
		mMapView.setApiKey(API_KEY);
		setContentView(mMapView);
		mMapView.setClickable(true);
		mMapView.setOnMapStateChangeListener(this);
		mMapView.setOnMapViewTouchEventListener(this);
		mMapView.setBuiltInZoomControls(true, null);
		mMapController        = mMapView.getMapController();
		mLocMgr               = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		backPressCloseHandler = new BackPressCloseHandler(this);
	}

	@Override
	public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
		if ( errorInfo == null ) {
			// startMyLocation();
			mMapController.setMapCenter(new NGeoPoint(37.566535, 126.977969), 11);
		} else {
			android.util.Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
		}
	}

	@Override
	public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {

	}

	@Override
	public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {

	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {

	}

	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {

	}

	@Override
	public void onLongPress(NMapView arg0, MotionEvent arg1) {

	}

	@Override
	public void onLongPressCanceled(NMapView arg0) {

	}

	@Override
	public void onScroll(NMapView arg0, MotionEvent arg1, MotionEvent arg2) {

	}

	@Override
	public void onSingleTapUp(NMapView arg0, MotionEvent arg1) {

	}

	@Override
	public void onTouchDown(NMapView arg0, MotionEvent arg1) {

	}

	@Override
	public void onTouchUp(NMapView arg0, MotionEvent arg1) {

	}

	@Override
	public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0, NMapOverlayItem arg1, Rect arg2) {
		return null;
	}

	LocationListener mLocListener = new LocationListener() {
		public void onLocationChanged(Location location) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
	};

	public static Criteria getCriteria() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(true);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		return criteria;
	}

	public void onResume() {
		super.onResume();
		String locProv = mLocMgr.getBestProvider(new Criteria(), true);
		mLocMgr.requestLocationUpdates(locProv, 3000, 3, mLocListener);
	}

	public void onPause() {
		super.onPause();
		mLocMgr.removeUpdates(mLocListener);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		backPressCloseHandler.onBackPressed();
	}
}