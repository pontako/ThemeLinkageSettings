package com.example.themelinkage;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.themelinkage.ThemeLinkageSettingsContract.AppSettingContract;
import com.example.themelinkage.ThemeLinkageSettingsContract.WelcomeSheetSettingContract;

/**
 * Model for ThemeLinkageSettings.
 *
 */
public class ThemeLinkageSettingsModel {

	/**
	 * Abstract class for ThemeLinkageSettings model.
	 *
	 */
	abstract static class Setting {
		/**
		 * This package private method is used to load default setting.
		 */
		abstract void loadDefault();

		/**
		 * This package private method is used to load setting with Intent object.
		 */
		abstract boolean load(Intent intent);

		/**
		 * This package private method is used to load setting with Cursor object.
		 */
		abstract boolean load(Cursor cursor);
	}
	
	/**
	 * Model for AppSetting.
	 *
	 */
	public static class AppSetting extends Setting {
		private static final String TAG = AppSetting.class.getSimpleName();
		private static final boolean DEBUG = true;

		private int mThemeType;
		private int mColor;

		public AppSetting() {
			mThemeType = AppSettingContract.DEFAULT_THEME_TYPE;
			mColor = AppSettingContract.DEFAULT_COLOR;
		}

		public AppSetting(int themeType, int color) {
			mThemeType = themeType;
			mColor = color;
		}

		public int getThemeType() {
			return mThemeType;
		}

		public int getColor() {
			return mColor;
		}

		@Override
		void loadDefault() {
			mThemeType = AppSettingContract.DEFAULT_THEME_TYPE;
			mColor = AppSettingContract.DEFAULT_COLOR;
			if (DEBUG) Log.d(TAG, "loadDefault AppSetting:" + this);
		}

		@Override
		boolean load(Intent intent) {
			boolean loadResult = true;

			try {
				mThemeType = intent.getIntExtra(AppSettingContract.KEY_THEME_TYPE,
						AppSettingContract.DEFAULT_THEME_TYPE);
				mColor = intent.getIntExtra(AppSettingContract.KEY_COLOR,
						AppSettingContract.DEFAULT_COLOR);
			} catch (Exception e) {
				loadResult = false;
			}

			if (DEBUG) Log.d(TAG, "load intent:" + intent +
					" loadResult:" + loadResult +
					" AppSetting:" + this);
			return loadResult;
		}

		@Override
		boolean load(Cursor cursor) {
			boolean loadResult = true;

			try {
				cursor.moveToFirst();
				mThemeType = cursor.getInt(cursor.getColumnIndex(AppSettingContract.COLUMN_THEME_TYPE));
				mColor = cursor.getInt(cursor.getColumnIndex(AppSettingContract.COLUMN_COLOR));
			} catch (Exception e) {
				loadResult = false;
			}

			if (DEBUG) Log.d(TAG, "load cursor:" + cursor +
					" loadResult:" + loadResult +
					" AppSetting:" + this);
			return loadResult;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder().
					append("{").
					append(" mThemeType:" + mThemeType).
					append(" mColor:" + mColor).
					append(" }");
			return builder.toString();
		}
	}

	/**
	 * Model for WelcomeSheetSetting.
	 *
	 */
	public static class WelcomeSheetSetting extends Setting {
		private static final String TAG = WelcomeSheetSetting.class.getSimpleName();
		private static final boolean DEBUG = true;

		private int mMickeySetting;

		public WelcomeSheetSetting() {
			mMickeySetting = WelcomeSheetSettingContract.DEFAULT_MICKEY_SETTING;
		}

		public WelcomeSheetSetting(int mickeySetting) {
			mMickeySetting = mickeySetting;
		}

		public int getMickeySetting() {
			return mMickeySetting;
		}

		@Override
		void loadDefault() {
			mMickeySetting = WelcomeSheetSettingContract.DEFAULT_MICKEY_SETTING;
			if (DEBUG) Log.d(TAG, "loadDefault WelcomeSheetSetting:" + this);
		}

		@Override
		boolean load(Intent intent) {
			boolean loadResult = true;

			try {
				mMickeySetting = intent.getIntExtra(WelcomeSheetSettingContract.KEY_MICKEY_SETTING,
						WelcomeSheetSettingContract.DEFAULT_MICKEY_SETTING);
			} catch (Exception e) {
				loadResult = false;
			}

			if (DEBUG) Log.d(TAG, "load intent:" + intent +
					" loadResult:" + loadResult +
					" WelcomeSheetSetting:" + this);
			return loadResult;
		}

		@Override
		boolean load(Cursor cursor) {
			boolean loadResult = true;

			try {
				cursor.moveToFirst();
				mMickeySetting = cursor.getInt(cursor.getColumnIndex(WelcomeSheetSettingContract.COLUMN_MICKEY_SETTING));
			} catch (Exception e) {
				loadResult = false;
			}

			if (DEBUG) Log.d(TAG, "load cursor:" + cursor +
					" loadResult:" + loadResult +
					" WelcomeSheetSetting:" + this);
			return loadResult;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder().
					append("{").
					append(" mMickeySetting:" + mMickeySetting).
					append(" }");
			return builder.toString();
		}
	}	
}
