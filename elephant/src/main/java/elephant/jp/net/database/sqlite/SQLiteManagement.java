package elephant.jp.net.database.sqlite;

import android.app.Activity;
import android.content.Context;
import elephant.jp.net.common.cons.Const;
import elephant.jp.net.common.util.XMLManagement;
import elephant.jp.net.preferences.ElephantSharedPreferences;

/**
 * SQLiteを管理するクラス。
 * 
 * アプリケーション起動時にテーブルの作成、削除、コピー、初期データ投入に必要なSQLを作成する。
 * 
 * @author k.kitamura
 * @date 2014.08.28 新規作成
 * 
 */
public class SQLiteManagement {

    /**
     * コンストラクタ
     * 
     * @param context
     */
    public SQLiteManagement(Context context) {

	// XMLManagementのインスタンス化
	XMLManagement xMLManagement = new XMLManagement(context);

	// DBを取得

	// バージョンチェック
	if (xMLManagement.dataBasesBean.versionUp) {
	    // バージョンが上がっていた場合

	    xMLManagement.dataBasesBean.makeCreate();
	    xMLManagement.dataBasesBean.makeDrop();

	    // DB作成
	    SQLiteOpenHelperElePhant elephantSQLiteOpenHelper = new SQLiteOpenHelperElePhant(
		    context, xMLManagement.dataBasesBean);
	    elephantSQLiteOpenHelper.getReadableDatabase();

	    // バージョン番号の更新
	    ElephantSharedPreferences.setSharedPreferences(context,
		    Activity.MODE_PRIVATE, Const.PropertiesFile.PREFERENCES_DB,
		    Const.XMLFile.VERSION, xMLManagement.dataBasesBean.version);

	}

    }

}