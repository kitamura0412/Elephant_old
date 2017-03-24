package elephant.jp.net.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferencesを管理するクラス
 * 
 * @author k.kitamura
 * @date 2011.05.11 新規作成
 * 
 */
public class ElephantSharedPreferences {

	/**
	 * 
	 * 
	 * @param context
	 * @param mode
     * MODE_PRIVATE ファイル作成モード デフォルトのモード (getSharedPreferences を呼んだ(= ファイルを作成した) アプリケーションからのみアクセスできる（ただし、同じ user ID を共有しているアプリケーションもアクセスできる）)/
     * MODE_WORLD_READABLE ファイル作成モード(他の全てのアプリケーションが読み込みアクセス権限をもつ)/
     * MODE_WORLD_WRITEABLE ファイル作成モード(他の全てのアプリケーションが書き込みアクセス権限をもつ)/
     * MODE_MULTI_PROCESS SharedPreference の読み込みフラグ
     *
	 * @param key
	 * @param value
	 */
	public static void setSharedPreferences(Context context, int mode, String fName, String key, String value) {

		SharedPreferences pref = context.getSharedPreferences(fName, mode);

		// Editor の設定
		SharedPreferences.Editor editor = pref.edit();
		// Editor に値を代入
		editor.putString(key, value);
		// データの保存
		editor.commit();
	}

	public static void setSharedPreferences(Context context, int mode, String fName, String key, int value) {

		SharedPreferences pref = context.getSharedPreferences(fName, mode);

		// Editor の設定
		SharedPreferences.Editor editor = pref.edit();
		// Editor に値を代入
		editor.putInt(key, value);
		// データの保存
		editor.commit();
	}

	public static String getSharedPreferences(Context context, int mode, String fName, String key, String defValue) {

		SharedPreferences pref = context.getSharedPreferences(fName, mode);
		return pref.getString(key, defValue);
	}

	public static int getSharedPreferences(Context context, int mode, String fName, String key, int defValue) {

		SharedPreferences pref = context.getSharedPreferences(fName, mode);
		return pref.getInt(key, defValue);
	}
}
