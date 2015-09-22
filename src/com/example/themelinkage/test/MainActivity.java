package com.example.themelinkage.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.themelinkage.ThemeLinkageManager;
import com.example.themelinkage.ThemeLinkageManager.ThemeLinkageSettingsChangedCallback;
import com.example.themelinkage.ThemeLinkageResourceResolver;
import com.example.themelinkage.ThemeLinkageSettingsModel.AppSetting;
import com.example.themelinkage.ThemeLinkageSettingsModel.WelcomeSheetSetting;
import com.example.themelinkagesettings.R;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mThemeLinkageManagerTest = new ThemeLinkageManagerTest();
		mThemeLinkageManagerTest.start(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private static ThemeLinkageManagerTest mThemeLinkageManagerTest;
	private static class ThemeLinkageManagerTest {
		private ThemeLinkageManager mThemeLinkageManager;
		public void start(Context context) {
			// Generate ThemeLinkageSettings and start monitoring when first booting.
			mThemeLinkageManager = ThemeLinkageManager.getInstance();
			mThemeLinkageManager.startMonitor(context);
			
			// Get ThemeLinkageSettings when first showing keyguard.
			mThemeLinkageManager = ThemeLinkageManager.getInstance();
			mThemeLinkageManager.loadSettings(context);
			mThemeLinkageManager.getAppSetting();
			mThemeLinkageManager.getWelcomeSheetSetting();

			// Update lock icon.
			int themeType = mThemeLinkageManager.getAppSetting().getThemeType();
			int lockIconRes = ThemeLinkageResourceResolver.getLockIconRes(themeType);


			// Get ThemeLinkageSettings when settings changed.
			mThemeLinkageManager.addCallback(new ThemeLinkageSettingsChangedCallback() {
				
				@Override
				public void onThemeLinkageWelcomeSheetSettingsChanged(
						WelcomeSheetSetting welcomeSheetSetting) {
					Log.d(TAG, "onThemeLinkageWelcomeSheetSettingsChanged" +
						" welcomeSheetSetting" + welcomeSheetSetting);
				}
				
				@Override
				public void onThemeLinkageAppSettingsChanged(AppSetting appSetting) {
					Log.d(TAG, "onThemeLinkageAppSettingsChanged" +
							" appSetting" + appSetting);
					Log.d(TAG, "onThemeLinkageAppSettingsChanged thread:" + Thread.currentThread());

					Handler h = new Handler(Looper.getMainLooper());
					h.post(new Runnable() {
						
						@Override
						public void run() {
							// Do something on UI thread.
							Log.d(TAG, "onThemeLinkageAppSettingsChanged Runnable thread:" + Thread.currentThread());
						}
					});
				}
			});

			// Get ThemeLinkageSettings when settings changed.
			mThemeLinkageManager.addCallback(new ThemeLinkageSettingsChangedCallback() {
				
				@Override
				public void onThemeLinkageWelcomeSheetSettingsChanged(
						WelcomeSheetSetting welcomeSheetSetting) {
					Log.d(TAG, "onThemeLinkageWelcomeSheetSettingsChanged 2" +
						" welcomeSheetSetting" + welcomeSheetSetting);
				}
				
				@Override
				public void onThemeLinkageAppSettingsChanged(AppSetting appSetting) {
					Log.d(TAG, "onThemeLinkageAppSettingsChanged 2" +
							" appSetting" + appSetting);
					Log.d(TAG, "onThemeLinkageAppSettingsChanged 2 thread:" + Thread.currentThread());
				}
			});
		}
	}
}
