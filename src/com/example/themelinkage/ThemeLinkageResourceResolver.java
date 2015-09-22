package com.example.themelinkage;

import com.example.themelinkage.ThemeLinkageSettingsContract.AppSettingContract;

/**
 * A utility class for resolving which resource to use.
 *
 */
public class ThemeLinkageResourceResolver {

	public static int getLockIconRes(int themeType) {
		ThemeType tt = ThemeType.valudOf(themeType); 
		return tt.getLockIconRes();
	}

	public static int getUnlockSoundId(int themeType) {
		ThemeType tt = ThemeType.valudOf(themeType); 
		return tt.getUnlockSoundId();
	}

	private enum ThemeType {
		CASTLE(AppSettingContract.THEME_TYPE_CASTLE) {
			@Override
			public int getLockIconRes() {
				return 100;
			}

			@Override
			public int getUnlockSoundId() {
				return 101;
			}
		},
		TOMORROW(AppSettingContract.THEME_TYPE_TOMORROW) {
			@Override
			public int getLockIconRes() {
				return 200;
			}

			@Override
			public int getUnlockSoundId() {
				return 201;
			}
		},
		UNKNOWN(AppSettingContract.THEME_TYPE_UNKNOWN) {
			@Override
			public int getLockIconRes() {
				return 0;
			}

			@Override
			public int getUnlockSoundId() {
				return 0;
			}
		};

		private final int mType;
		private ThemeType(final int type) {
			mType = type;
		}

		public static ThemeType valudOf(int themeType) {
			for (ThemeType type : values()) {
				if (type.mType == themeType) {
					return type;
				}
			}
			return UNKNOWN;
		}

		public abstract int getLockIconRes();
		public abstract int getUnlockSoundId();
	}
}
