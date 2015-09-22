package com.example.themelinkage;

import android.net.Uri;

/**
 * The contract between the ThemeLinkageSettings application and other applications.
 *
 */
public final class ThemeLinkageSettingsContract {

	/** Contract for AppSetting */
	public static class AppSettingContract {
		/* Intent action name definition */
		public static final String INTENT_ACTION_NAME = "intent_action_app_setting";

		/* Intent key name definition */
		public static final String KEY_THEME_TYPE = "theme_type";
		public static final String KEY_COLOR = "color";

		/* URI definition */
		public static final Uri CONTENT_URI = Uri.parse("uri_app_setting");

		/* Column name definition */
		public static final String COLUMN_THEME_TYPE = "theme_type";
		public static final String COLUMN_COLOR = "color";

		/* Values definition */
		public static final int THEME_TYPE_CASTLE = 1;
		public static final int THEME_TYPE_TOMORROW = 2;
		public static final int THEME_TYPE_UNKNOWN = -1;


		/* Default value definition */
		public static final int DEFAULT_THEME_TYPE = THEME_TYPE_CASTLE;
		public static final int DEFAULT_COLOR = 0x0000;

	}

	/** Contract for WelcomeSheetSetting */
	public static class WelcomeSheetSettingContract {
		/* Intent action name definition */
		public static final String INTENT_ACTION_NAME = "intent_action_welcome_sheet_setting";

		/* Intent key name definition */
		public static final String KEY_MICKEY_SETTING = "mickey_setting";

		/* URI definition */
		public static final Uri CONTENT_URI = Uri.parse("uri_welcome_sheet_setting");

		/* Column name definition */
		public static final String COLUMN_MICKEY_SETTING = "mickey_setting";

		/* Values definition */
		public static final int MICKEY_SETTING_VISIBLE = 0;
		public static final int MICKEY_SETTING_INVISIBLE = 1;

		/* Default value definition */
		public static final int DEFAULT_MICKEY_SETTING = MICKEY_SETTING_VISIBLE;

	}
}
