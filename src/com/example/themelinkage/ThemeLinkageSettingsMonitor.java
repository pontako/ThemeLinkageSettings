package com.example.themelinkage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.themelinkage.ThemeLinkageSettingsContract.AppSettingContract;
import com.example.themelinkage.ThemeLinkageSettingsContract.WelcomeSheetSettingContract;

/**
 * Used by {@link ThemeLinkageManager} to monitor that the theme linkage setting has been changed.
 * 
 */
/* package private */ class ThemeLinkageSettingsMonitor {
	private static final String TAG = ThemeLinkageSettingsMonitor.class.getSimpleName();
	private static final boolean DEBUG = true;

	interface ThemeLinkageSettingsMonitorCallback {
		public void onReceivedThemeLinkageSettingsIntent(Intent intent);
	}

	private ThemeLinkageSettingsMonitorCallback mCallback;
	private ThemeLinkageSettingReceiver mReceiver = null;
	private Handler mHandler;

	ThemeLinkageSettingsMonitor(ThemeLinkageSettingsMonitorCallback callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Callback may not to be null!!");
		}
		mCallback = callback;

		HandlerThread ht = new HandlerThread("MonitorHandlerThread");
		ht.start();
		mHandler = new Handler(ht.getLooper());
	}

	private void notifyThemeLinkageSettingsIntentReceived(Intent intent) {
		mCallback.onReceivedThemeLinkageSettingsIntent(intent);
	}

	private void handleThemeLinkageSettingsIntentReceived(final Intent intent) {
		Log.d(TAG, "handleThemeLinkageSettingsIntentReceived thread:" + Thread.currentThread());
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "handleThemeLinkageSettingsIntentReceived Runnable thread:" + Thread.currentThread());
				notifyThemeLinkageSettingsIntentReceived(intent);
			}
		});
	}

	private final class ThemeLinkageSettingReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (DEBUG) Log.d(TAG, "onReceive intent:" + intent);
			String action = intent.getAction();
			if (AppSettingContract.INTENT_ACTION_NAME.equals(action) ||
					WelcomeSheetSettingContract.INTENT_ACTION_NAME.equals(action)) {
				handleThemeLinkageSettingsIntentReceived(intent);
			}
		}
	}

	void start(Context context) {
		if (mReceiver == null) {
			mReceiver = new ThemeLinkageSettingReceiver();

			IntentFilter filter = new IntentFilter();
			filter.addAction(AppSettingContract.INTENT_ACTION_NAME);
			filter.addAction(WelcomeSheetSettingContract.INTENT_ACTION_NAME);

			context.registerReceiver(mReceiver, filter);
			if (DEBUG) Log.d(TAG, "mReceiver is registered.");
		}
	}

	void finish(Context context) {
		if (mReceiver != null) {
			context.unregisterReceiver(mReceiver);
			mReceiver = null;
			if (DEBUG) Log.d(TAG, "mReceiver is unregistered.");
		}
	}

}
