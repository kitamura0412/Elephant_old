package elephant.jp.net.database.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;

import elephant.jp.net.common.cons.Const;
import elephant.jp.net.common.util.AssetsManagement;
import elephant.jp.net.common.util.StringUtil;
import elephant.jp.net.common.util.log.OutputLog;
import elephant.jp.net.preferences.ElephantSharedPreferences;

/**
 * SQLを発行するクラスDatabaseOpenHelperを呼び出す SQLを発行する際は1.コンストラクタを利用し
 * 、インスタンス化、2.insertUpdateDeleteSQL 又は selectSQL 、3.処理終了closeの手順で行う
 *
 * @author k.kitamura
 * @date 2011.05.11 新規作成
 */
public class IssueSQL {

    // クラス変数定義
    /**
     * ログ出力クラス
     */
    private OutputLog log = new OutputLog("IssueSQL");
    /**
     * DatabaseOpenHelper
     */
    private DatabaseOpenHelper helper = null;
    /**
     * Context
     */
    private Context context;
    /**
     * プロパティーファイルを取得するクラス
     */
    AssetsManagement am = null;

    /**
     * @param context
     */
    public IssueSQL(Context context) {

        this.context = context;

        // インスタンス化
        if (this.helper == null) {

            // バージョン番号取得
            int version = ElephantSharedPreferences.getSharedPreferences(context, Activity.MODE_PRIVATE,
                    Const.PropertiesFile.PREFERENCES_DB, "VERSION", 0);
            // DB名取得
            String name = ElephantSharedPreferences.getSharedPreferences(context, Activity.MODE_PRIVATE,
                    Const.PropertiesFile.PREFERENCES_DB, "NAME", null);

            if (version != 0 && StringUtil.isNotEmpty(name)) {
                // DatabaseOpenHelperのインスタンス化
                this.helper = new DatabaseOpenHelper(context, name, version);
            }
        }
    }

    /**
     * SQLを発行する 1.レコード追加 2.レコード削除 3.レコード更新
     *
     * @param fName  SQLが格納されたファイル名
     * @param sqlKey 発行するSQLkey
     * @param params セットするパラメータ
     * @return 更新結果（1以上なら成功）
     */
    public boolean insertUpdateDeleteSQLSetFName(String fName, String sqlKey, String... params) {

        // SQLをpropertyファイルから取得する
        String sql = getSQL(this.context, fName, sqlKey);
        // 更新結果をリターン
        return insertUpdateDelete(sql, params);
    }

    /**
     * SQLを発行する 1.レコード追加 2.レコード削除 3.レコード更新
     *
     * @param sqlKey 発行するSQLkey
     * @param params セットするパラメータ
     * @return 更新結果（1以上なら成功）
     */
    public boolean insertUpdateDeleteSQL(String sqlKey, String... params) {

        // SQLをpropertyファイルから取得する
        // 更新結果をリターン
        return insertUpdateDelete(getSQL(this.context, null, sqlKey), params);
    }

    /**
     * @param sql    SQL
     * @param params パラメータ
     * @return 更新結果（1以上なら成功）
     */
    public boolean insertUpdateDelete(String sql, String... params) {

        // データベース取得 & テーブルの作成
        // SQLiteDatabaseの取得
        SQLiteDatabase db = this.helper.getWritableDatabase();

        // トランザクション開始
        db.beginTransaction();

        for (String param : params) {
            log.logD("param : " + param);
        }
        log.logD("sql : " + sql);
        // SQLをステートメントにsetする
        SQLiteStatement stmt = null;
        boolean result = false;
        try {
            stmt = db.compileStatement(sql);

            for (int i = 0, paramSize = params.length; i < paramSize; i++) {
                // ステートメントにパラメータをsetする
                stmt.bindString(i + 1, params[i]);
            }

            // SQLを発行する
            stmt.execute();
            // コミット (ここを通過しないでfinallyに行くと失敗)
            db.setTransactionSuccessful();
            // 成功
            result = true;

        } catch (Exception e) {
            // 失敗
            log.logE("SQL発行失敗 : ", e);
            result = false;

        } finally {
            db.endTransaction();

            // DBの終了（クローズ）
            db.close();

        }
        log.logD("result : " + result);
        return result;
    }


    /**
     * レコードを抽出する
     * <p/>
     * WHERE LIKE 句 とバインド変数を合わせて利用する場合、 android特有のエラーが出るので、こちらのメソッドを利用する
     * <p/>
     * 今回は結局利用しない
     *
     * @param table         取得するテーブル
     * @param columns       取得するカラム
     * @param selection     レコード条件
     * @param selectionArgs レコード条件
     * @param groupBy       group by句を指定
     * @param having        Having句を指定
     * @param orderBy       order by句を指定
     * @return 取得したデータList<Map>
     */
    public ArrayList<HashMap<String, String>> selectQuerySQL(String table, String[] columns, String selection,
                                                             String[] selectionArgs, String groupBy, String having, String orderBy) {

        // データベース取得 & テーブルの作成
        // SQLiteDatabaseの取得
        SQLiteDatabase db = this.helper.getWritableDatabase();

        // 検索結果を収めるCursorクラス
        Cursor resultC = null;

        // レコードを抽出する
        resultC = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);

        // 結果をListで取得する
        ArrayList<HashMap<String, String>> returnList = getList(resultC);

        // DBの終了（クローズ）
        db.close();

        // 結果を保存したリストをreturn
        return returnList;

    }

    /**
     * レコードを抽出する
     *
     * @param sqlKey 発行するSQLのkey
     * @param params セットするパラメータ
     * @return 検索結果
     */
    public ArrayList<HashMap<String, String>> selectSQL(String sqlKey, String... params) {
        // SQLをpropertyファイルから取得する
        // 結果を保存したリストをreturn
        return selectQuery(getSQL(this.context, null, sqlKey), params);
    }


    /**
     * @param sqlKey 発行するSQLのkey
     * @return 検索結果
     */
    public ArrayList<HashMap<String, String>> selectSQL(String sqlKey) {
        return selectSQL(sqlKey, new String[]{});
    }


    /**
     * レコードを抽出する
     *
     * @param sqlKey 発行するSQLのkey
     * @param params セットするパラメータ
     * @return 検索結果
     */
    public ArrayList<HashMap<String, String>> selectSQLSetFName(String fName, String sqlKey, String... params) {
        // SQLをpropertyファイルから取得する
        // 結果を保存したリストをreturn
        return selectQuery(getSQL(this.context, fName, sqlKey), params);
    }

    /**
     * @param sqlKey 発行するSQLのkey
     * @return 検索結果
     */
    public ArrayList<HashMap<String, String>> selectSQLSetFName(String fName, String sqlKey) {
        return selectSQLSetFName(fName, sqlKey, new String[]{});
    }

    public ArrayList<HashMap<String, String>> selectQuery(String sql, String... params) {
        log.logD("selectQuery");
        // データベース取得 & テーブルの作成
        // SQLiteDatabaseの取得
        SQLiteDatabase db = this.helper.getWritableDatabase();

        // 検索結果を収めるCursorクラス
        Cursor resultC = null;


        for (String param : params) {
            log.logD("param : " + param);
        }
        log.logD("sql : " + sql);


        // TODO like文が入っているかチェックする
//        if (sql.toUpperCase().indexOf("LIKE") != -1) {
//            // like文が入っている場合
//            sql = getLikeSQL(sql, params);
//            // SQLを発行する
//            resultC = db.rawQuery(sql, null);
//
//        } else {

        // SQLを発行する
//        resultC = db.execSQL(sql);
        resultC = db.rawQuery(sql, params);
        if (sql.toUpperCase().indexOf("LIKE") == -1) {
            // like文が入ってない場合
            log.logD("sql : " + getLikeSQL(sql, params));
        }
//        }

        // 結果をListで取得する
        ArrayList<HashMap<String, String>> returnList = getList(resultC);

        // DBの終了（クローズ）
        db.close();
        if (returnList == null) {
            log.logD("結果:" + returnList);
        } else {
            log.logD("結果:" + returnList.size() + " 件");
        }

        // 結果を保存したリストをreturn
        return returnList;
    }

    /**
     * propertyファイルからSQLを取得する
     *
     * @param context
     * @param sqlKey  propertyファイルから取得するSQL
     * @return 取得したSQL
     */
    private String getSQL(Context context, String fName, String sqlKey) {

        // AssetsManagementをクラス変数から取得
        AssetsManagement am = this.am;

        if (am == null) {
            if (fName == null) {
                am = new AssetsManagement(context, Const.PropertiesFile.SQL_PROPERTIES);
            } else {
                am = new AssetsManagement(context, fName);
            }
        }
        log.logD("sqlKey : " + sqlKey);
        // SQLを取得する
        String sql = am.getProperty(sqlKey);
//        log.logD("sql : " + sql);
        // propertyファイルのストリームを解放する
        am.closeAssets();

        // AssetsManagementをクラス変数に代入
        this.am = am;

        if (sql == null || "".equals(sql)) {
            log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0008 + " : " + sqlKey);
        }

        // propertyファイルから取得したSQLをリターンする
        return sql;
    }

    /**
     * CursorからList<Map>にデータを変換する
     *
     * @param resultC 結果が保存されたCursor
     * @return List<Map>
     */
    private ArrayList<HashMap<String, String>> getList(Cursor resultC) {

        // カラム数を取得
        int cSize = resultC.getColumnCount();
        log.logD("resultC.getCount() : " + Integer.toString(resultC.getCount()));
        // カラムをString[]に代入
        String[] col = resultC.getColumnNames();


        // 結果を格納するListを生成
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // カーソルを先頭に移動する
        if (resultC.moveToFirst()) {

            // List<MAP>に変換
            do {

                // 一行を格納するMAPを生成
                HashMap<String, String> map = new HashMap<String, String>();

                for (int i = 0; i < cSize; i++) {

                    // 1行を取得
                    map.put(col[i], resultC.getString(i));
                }

                list.add(map);

                // 最後の行に到達したら読み込みを終了する
            } while (resultC.moveToNext());
        }
        return list;
    }

    /**
     * SQLにWHERE LIKE 文が入っていた場合は独自の手法でパラメータを置き換える
     *
     * @param sql    SQL
     * @param params パラメータ
     * @return SQL
     */
    private String getLikeSQL(String sql, String[] params) {

        String obj = "?";

        // パラメータの数だけ繰り返し
        for (int i = 0, paramSize = params.length; i < paramSize; i++) {

            // 正規表現でチェックし、パラメータに置き換える
            sql = sql.replaceFirst("\\" + "?", params[i]);

            // '?'がこれ以上存在しない場合は置き換えを終了する
            if (sql.toUpperCase().indexOf(obj) == -1) {
                break;
            }
        }

        return sql;
    }
}
