package elephant.jp.net.database.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import elephant.jp.net.common.cons.Const;
import elephant.jp.net.common.util.AssetsManagement;
import elephant.jp.net.common.util.StringUtil;
import elephant.jp.net.common.util.log.OutputLog;
import elephant.jp.net.database.sqlite.bean.ColumnBean;
import elephant.jp.net.database.sqlite.bean.DataBasesBean;
import elephant.jp.net.database.sqlite.bean.TableBean;
import elephant.jp.net.exception.ElephantException;

/**
 * データベースを作成.ヴァージョンの変更を行う
 *
 * @author k.kitamura
 * @date 2011.05.11 新規作成
 */
public class SQLiteOpenHelperElePhant extends SQLiteOpenHelper {

    /**
     * Context
     */
    private Context context = null;

    /**
     * DataBasesBean
     */
    private DataBasesBean dataBasesBean;

    /**
     * ログ出力クラス
     */
    private OutputLog log = new OutputLog("SQLiteOpenHelperElePhant");

    /**
     * コンストラクタ
     *
     * @param context
     */
    public SQLiteOpenHelperElePhant(Context context, DataBasesBean dataBasesBean) {
        // 指定したデータベース名が存在しない場合は、
        // 新たに作成されonCreate()が呼ばれる
        // バージョンを変更するとonUpgrade()が呼ばれる
        // 第2引数をnullにすると端末内にDBが作成される
        // ファイル名を指定した場合は 「/data/data/パッケージ名/database/ファイル名」に、データベースファイルが作成される
        // super(context, dataBasesBean.databasesName, null,
        // dataBasesBean.version);
        super(context, dataBasesBean.databasesName, null, dataBasesBean.version);
        Log.d("ElephantLog", "#######################################################################");
        Log.d("ElephantLog", "Elephant Ver : [ " + Const.Elephant.VERSION + " ]");
        Log.d("ElephantLog", "#######################################################################");
        Log.d("ElephantLog", "databasesName : " + dataBasesBean.databasesName);
        Log.d("ElephantLog", "version       : " + dataBasesBean.version);

        // Contextのset
        this.context = context;

        this.dataBasesBean = dataBasesBean;
    }

    /**
     * 新たにDBが作成されたタイミングで呼び出しを受ける 一般的にはここで利用するテーブルの作成を行う
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ElephantLog", "onCreate");
        try {
            // テーブルの作成
            createTable(db);

            // 初期データ投入
            firstInput(db);

        } catch (SQLException e) {
            // 初期データの作成に失敗
            log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0007, e);
        }

    }

    /**
     * ヴァージョンを変更したタイミングで呼び出しを受ける
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("ElephantLog", "onUpgrade");

        boolean copyFlg = false;

        copyFlg = copyTableCheck(dataBasesBean);

        // 古いテーブル一覧
        ArrayList<HashMap<String, String>> tableList = null;

        // 既存テーブル削除処理
        if (copyFlg) {
            // 既存テーブルからコピーの場合
            // 古いテーブル一覧
            try {
                tableList = getTableList(db);
            } catch (Exception e) {
                // テーブル一覧の取得に失敗
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0002,
                        e);
            }
            // テーブルのリネーム

            try {
                reNameTable(db, tableList);
            } catch (SQLException e) {
                // テーブルのリネームに失敗
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0003,
                        e);
            }

        } else {
            // 上記以外の場合
            try {
                // 既存のテーブル削除
                dropTable(db);
            } catch (SQLException e) {
                // 既存のテーブル削除に失敗
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0004,
                        e);
            }
        }

        try {
            // テーブルの新規作成
            createTable(db);
        } catch (SQLException e) {
            // テーブルの新規作成に失敗
            log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0005, e);
        }

        // データ移行
        if (copyFlg) {
            // 既存テーブルからコピーの場合

            try {
                // テーブルデータのコピー
                copyTable(db);
            } catch (SQLException e) {
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0006,
                        e);
            } catch (ElephantException e) {
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0006,
                        e);
            }
        }
        // 初期データ投入
        try {
            firstInput(db);
        } catch (SQLException e) {

        }

        // コピーデータの削除
        if (copyFlg) {
            try {
                dropCopyTable(db, tableList);
            } catch (SQLException e) {
                log.logE(Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0004,
                        e);
            }
        }

    }

    /**
     * 初期データ投入
     *
     * @param db テーブルを作成するDB
     * @throws android.database.SQLException テーブルの作成に失敗した場合
     */
    private void firstInput(SQLiteDatabase db) throws SQLException {
        Log.d("ElephantLog", "初期データ投入");
        AssetsManagement am = new AssetsManagement(this.context,
                Const.PropertiesFile.SQL_PROPERTIES_FIRST_INPUT);

        int i = 0;
        String sql = null;

        do {
            sql = am.getProperty(Const.SQLiteKey.FIRST_INPUT
                    + Integer.toString(i++));
            if (StringUtil.isNotEmpty(sql)) {

                try {
                    Log.d("ElephantLog", "初期データ投入 " + "[" + i + "] : " + sql);
                    db.execSQL(sql);

                } catch (SQLiteConstraintException e) {
                    // 初期データの作成に失敗
                    log.logE("制約違反 : " + sql);
                    log.logE(
                            Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0007,
                            e);
                }

            } else {
                break;
            }
        } while (StringUtil.isNotEmpty(sql));

        // ファイルストリームを解放する
        am.closeAssets();

    }

    /**
     * コピーテーブルを削除
     *
     * @param db テーブルを作成するDB
     * @throws android.database.SQLException テーブルの作成に失敗した場合
     */
    private void dropCopyTable(SQLiteDatabase db,
                               ArrayList<HashMap<String, String>> tableList) throws SQLException {
        for (HashMap<String, String> map : tableList) {
            String tableName = map.get("name");
            if (!"android_metadata".equals(tableName)) {
                // 現在のテーブルのリネーム
                Log.d("ElephantLog", "現在のテーブルのリネーム");
                String sql = "DROP TABLE " + "COPY_TEMP_" + tableName;
                Log.d("ElephantLog", sql);
                db.execSQL(sql);
            }

        }

    }

    /**
     * テーブルを作成
     *
     * @param db テーブルを作成するDB
     * @throws android.database.SQLException テーブルの作成に失敗した場合
     */
    private void createTable(SQLiteDatabase db) throws SQLException {
        Log.d("ElephantLog", "createTable");

        // テーブルの作成
        Log.d("ElephantLog", "テーブルの作成");
        for (TableBean tableBean : this.dataBasesBean.tableList) {
            Log.d("ElephantLog", tableBean.sqlCreate);
            db.execSQL(tableBean.sqlCreate);
        }
    }

    /**
     * テーブル一覧の取得
     *
     * @param db SQLiteDatabase
     * @return テーブル一覧
     * @throws Exception 例外
     */
    private ArrayList<HashMap<String, String>> getTableList(SQLiteDatabase db)
            throws Exception {
        // テーブル一覧取得
        ArrayList<HashMap<String, String>> tableList = null;
        Log.d("ElephantLog", "テーブル一覧取得");
        String sql = "SELECT * FROM SQLITE_MASTER WHERE type='table'";
        Cursor resultC = db.rawQuery(sql, null);
        tableList = getList(resultC);

        return tableList;
    }

    /**
     * テーブルのリネーム
     *
     * @param db        SQLiteDatabase
     * @param tableList 古いテーブル一覧
     * @throws android.database.SQLException 例外
     * @throws android.database.SQLException
     */
    private void reNameTable(SQLiteDatabase db,
                             ArrayList<HashMap<String, String>> tableList) throws SQLException {

        // テーブルのリネーム
        Log.d("ElephantLog", "テーブルのリネーム");
        for (HashMap<String, String> map : tableList) {
            String tableName = map.get("tbl_name");
            if (!"android_metadata".equals(tableName)) {
                // 現在のテーブルのリネーム
                String sql = "ALTER TABLE " + tableName + " RENAME TO "
                        + "COPY_TEMP_" + tableName;
                Log.d("ElephantLog", sql);
                db.execSQL(sql);
            }
        }
    }

    /**
     * テーブルを削除
     *
     * @param db
     * @throws android.database.SQLException
     */
    private void dropTable(SQLiteDatabase db) throws SQLException {
        Log.d("ElephantLog", "テーブルを削除");
        for (TableBean tableBean : dataBasesBean.tableList) {
            String sql = tableBean.sqlDrop;
            Log.d("ElephantLog", sql);
            db.execSQL(sql);
        }

    }

    /**
     * デーブルデータの移行
     *
     * @param db
     * @throws ElephantException             PRIMARYKEYを変更した場合
     * @throws android.database.SQLException
     */
    private void copyTable(SQLiteDatabase db) throws ElephantException,
            SQLException {
        Log.d("ElephantLog", "デーブルデータの移行");
        for (TableBean tableBean : dataBasesBean.tableList) {
            if (tableBean.copy) {

                // テーブルコピー改修
                // String sql = tableBean.sqlCreate +
                // " SELECT * FROM COPY_TEMP_"
                // + tableBean.tableName;
                // Log.d("ElephantLog", "copyTable : " + sql);
                // db.execSQL(sql);

                Cursor resultC = db.rawQuery("PRAGMA TABLE_INFO(COPY_TEMP_"
                        + tableBean.tableName + ");", null);
                ArrayList<HashMap<String, String>> columnList = getList(resultC);

                // checkPrimarykey
                if (!checkPrimarykey(tableBean, columnList)) {
                    throw new ElephantException(
                            Const.SQLExceptionMessage.SQL_EXCEPTION_MESSAGE_0009);
                }

                StringBuilder buf = new StringBuilder();
                boolean farst = true;
                for (ColumnBean columnBean : tableBean.columnList) {
                    if (!farst) {
                        // 初回以外の場合
                        buf.append(",");
                    } else {
                        buf.append(" ");
                        farst = false;
                    }
                    if (checkColumn(columnBean.columnName, columnList)) {

                        buf.append(columnBean.columnName);
                    } else {
                        buf.append("'" + columnBean.defaultValue + "'");
                    }
                }

                String sql = "INSERT INTO " + tableBean.tableName + " SELECT "
                        + buf.toString() + " FROM COPY_TEMP_"
                        + tableBean.tableName;

                Log.d("ElephantLog", sql);

                db.execSQL(sql);

            }
        }
    }

    private boolean checkPrimarykey(TableBean tableBean,
                                    ArrayList<HashMap<String, String>> columnList) {

        for (HashMap<String, String> map : columnList) {
            if ("1".equals(map.get("pk"))) {
                if (!checkPrimarykeyTableBean(map.get("name"),
                        tableBean.columnList)) {
                    // 古いテーブルのPKが新しいテーブルに存在しない場合
                    return false;
                }
            }
        }

        for (ColumnBean columnBean : tableBean.columnList) {
            if (columnBean.primaryKey) {

                if (!checkPrimarykeyColumnList(columnBean.columnName,
                        columnList)) {
                    // 新しいテーブルのPKが古いテーブルに存在しない場合
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkPrimarykeyColumnList(String columnName,
                                              ArrayList<HashMap<String, String>> columnList) {
        for (HashMap<String, String> map : columnList) {
            if (columnName.equals(map.get("name"))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPrimarykeyTableBean(String columnName,
                                             ArrayList<ColumnBean> columnList) {
        for (ColumnBean columnBean : columnList) {
            if (columnName.equals(columnBean.columnName)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumn(String columnName,
                                ArrayList<HashMap<String, String>> columnList) {
        if (StringUtil.isNotEmpty(columnName)) {
            for (HashMap<String, String> map : columnList) {
                if (columnName.equals(map.get("name"))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param dataBasesBean データベース定義
     * @return 結果 true=コピー必要/false=コピー不要
     */
    private boolean copyTableCheck(DataBasesBean dataBasesBean) {
        for (TableBean tableBean : dataBasesBean.tableList) {
            if (tableBean.copy) {
                return true;
            }
        }
        return false;
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

        // カラムをString[]に代入
        String[] col = resultC.getColumnNames();

        // 結果を格納するListを生成
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // カーソルを先頭に移動する
        if (resultC.moveToFirst()) {

            // List<MAP>に変換
            // for (;; resultC.moveToNext()) {
            do {

                // 一行を格納するMAPを生成
                HashMap<String, String> map = new HashMap<String, String>();

                for (int i = 0; i < cSize; i++) {

                    // 1行を取得
                    map.put(col[i], resultC.getString(i));
                }

                list.add(map);

                // 最後の行に到達したら読み込みを終了する
                // if (resultC.isLast()) {
                // break;
                // }
            } while (resultC.moveToNext());
            // }
        }
        return list;
    }

}
