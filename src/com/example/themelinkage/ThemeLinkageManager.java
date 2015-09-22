package com.example.themelinkage;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.themelinkage.ThemeLinkageSettingsContract.AppSettingContract;
import com.example.themelinkage.ThemeLinkageSettingsContract.WelcomeSheetSettingContract;
import com.example.themelinkage.ThemeLinkageSettingsModel.AppSetting;
import com.example.themelinkage.ThemeLinkageSettingsModel.Setting;
import com.example.themelinkage.ThemeLinkageSettingsModel.WelcomeSheetSetting;
import com.example.themelinkage.ThemeLinkageSettingsMonitor.ThemeLinkageSettingsMonitorCallback;

/**
 * Managing theme linkage settings.
 *
 */
public class ThemeLinkageManager {

	private static final String TAG = ThemeLinkageManager.class.getSimpleName();
	private static final boolean DEBUG = true;

	private static ThemeLinkageManager sInstance = null;
	public static ThemeLinkageManager getInstance() {
		if (sInstance == null) {
			sInstance = new ThemeLinkageManager();
		}
		return sInstance;
	}

	private ThemeLinkageManager() {
		mSettingHolder = new SettingHolder();
		mMonitor = new ThemeLinkageSettingsMonitor(mMonitorCallback);
		mThemeLinkageSettingsChangedCallback = new ArrayList<>();
	}

	private SettingHolder mSettingHolder;
	private static class SettingHolder {
		AppSetting mAppSetting;
		WelcomeSheetSetting mWelcomeSheetSetting;

		private SettingHolder() {
			mAppSetting = new AppSetting();
			mWelcomeSheetSetting = new WelcomeSheetSetting();
		}
	}

	public AppSetting getAppSetting() {
		if (DEBUG) Log.d(TAG, "getAppSetting appSetting:" + mSettingHolder.mAppSetting);
		return mSettingHolder.mAppSetting;
	}

	public WelcomeSheetSetting getWelcomeSheetSetting() {
		if (DEBUG) Log.d(TAG, "getAppSetting WelcomeSheetSetting:" + mSettingHolder.mWelcomeSheetSetting);
		return mSettingHolder.mWelcomeSheetSetting;
	}

	private ThemeLinkageSettingsMonitor mMonitor;
	private ThemeLinkageSettingsMonitorCallback mMonitorCallback = new ThemeLinkageSettingsMonitorCallback() {
		@Override
		public void onReceivedThemeLinkageSettingsIntent(Intent intent) {
			String action = intent.getAction();
			if (AppSettingContract.INTENT_ACTION_NAME.equals(action)) {
				mSettingHolder.mAppSetting.load(intent);
				notifyAppSettingChanged(mSettingHolder.mAppSetting);
			} else if (WelcomeSheetSettingContract.INTENT_ACTION_NAME.equals(action)) {
				mSettingHolder.mWelcomeSheetSetting.load(intent);
				notifyWelcomeSheetSettingChanged(mSettingHolder.mWelcomeSheetSetting);
			}
		}
	};

	private void notifyAppSettingChanged(AppSetting appSetting) {
		for (ThemeLinkageSettingsChangedCallback callback : mThemeLinkageSettingsChangedCallback) {
			callback.onThemeLinkageAppSettingsChanged(appSetting);
		}
	}

	private void notifyWelcomeSheetSettingChanged(WelcomeSheetSetting welcomeSheetSetting) {
		for (ThemeLinkageSettingsChangedCallback callback : mThemeLinkageSettingsChangedCallback) {
			callback.onThemeLinkageWelcomeSheetSettingsChanged(welcomeSheetSetting);
		}
	}

	public void startMonitor(Context context) {
		mMonitor.start(context);
	}

	public void finishMonitor(Context context) {
		mMonitor.finish(context);
	}

	private ArrayList<ThemeLinkageSettingsChangedCallback> mThemeLinkageSettingsChangedCallback;
	public interface ThemeLinkageSettingsChangedCallback {
		public void onThemeLinkageAppSettingsChanged(AppSetting appSetting);
		public void onThemeLinkageWelcomeSheetSettingsChanged(WelcomeSheetSetting welcomeSheetSetting);
	}
	public void addCallback(ThemeLinkageSettingsChangedCallback callback) {
		mThemeLinkageSettingsChangedCallback.add(callback);
	}
	public void removeCallback(ThemeLinkageSettingsChangedCallback callback) {
		mThemeLinkageSettingsChangedCallback.remove(callback);
	}
	
	public void loadSettings(Context context) {
		// Load AppSetting.
		loadThemeLinkageSetting(context, AppSettingContract.CONTENT_URI, mSettingHolder.mAppSetting, true);

		// Load WelcomeSheetSetting.
		loadThemeLinkageSetting(context, WelcomeSheetSettingContract.CONTENT_URI, mSettingHolder.mWelcomeSheetSetting, true);
	}

	private boolean loadThemeLinkageSetting(Context context, Uri uri, Setting setting,
			boolean loadDefault) {
		Cursor cursor = null;
		boolean loadResult = true;

		try {
			cursor = context.getContentResolver().query(uri, null, null, null, null);
			loadResult = setting.load(cursor);
		} catch (Exception e) {
			loadResult = false;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		if (!loadResult && loadDefault) {
			setting.loadDefault();
		}

		if (DEBUG) Log.d(TAG, "loadThemeLinkageSetting loadResult:" + loadResult);
		return loadResult;
	}
}
