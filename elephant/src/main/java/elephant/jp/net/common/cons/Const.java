package elephant.jp.net.common.cons;

/**
 * 定数定義クラス
 *
 * @author k.kitamura
 * @date 2014/05/05
 */
public class Const {

    public class Elephant {
        /**
         * Elephantのバージョン
         */
        public static final String VERSION = "0.1.3";
    }

    /**
     * 例外(SQLExceptionMessage)発生時にログ出力するメッセージ
     *
     * @author k.kitamura
     * @date 2014/09/06 新規作成
     */
    public class SQLExceptionMessage {
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0001 = "テーブルの作成に失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0002 = "テーブル一覧の取得に失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0003 = "テーブルのリネームに失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0004 = "テーブルの削除に失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0005 = "テーブル新規作成に失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0006 = "テーブルのコピーに失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0007 = "初期データの作成に失敗";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0008 = "SQLが取得できませんでした。";
        /**
         * メッセージ
         */
        public static final String SQL_EXCEPTION_MESSAGE_0009 = "PRIMARYKEYを変更した場合は、COPY=yesを設定できません。";

    }

    /**
     * Xmlファイル定義
     *
     * @author k.kitamura
     * @date 2014/09/06 新規作成
     */
    public class XMLFile {
        /**
         * 場所
         */
        public static final String SQLITEDATABASES = "sqlite/sqliteDatabases.xml";
        /**
         * タグ
         */
        public static final String ENCODING = "UTF-8";
        /**
         * タグ
         */
        public static final String VERSION = "VERSION";
        /**
         * タグ
         */
        public static final String DB_NAME = "NAME";
        /**
         * タグ
         */
        public static final String TABLE = "TABLE";
        /**
         * タグ
         */
        public static final String TABLE_NAME = "NAME";
        /**
         * タグ
         */
        public static final String COPY = "COPY";
        /**
         * タグ
         */
        public static final String COLUMN = "COLUMN";
        /**
         * タグ
         */
        public static final String DATATYPE = "DATATYPE";
        /**
         * タグ
         */
        public static final String DATASIZE = "DATASIZE";
        /**
         * タグ
         */
        public static final String PRIMARYKEY = "PRIMARYKEY";
        /**
         * タグ
         */
        public static final String NOTNULL = "NOTNULL";
        /**
         * タグ
         */
        public static final String DEFAULT = "DEFAULT";
        /**
         * タグ
         */
        public static final String YES = "yes";
    }

    /**
     * ファイル定義
     *
     * @author k.kitamura
     * @date 2014/09/06 新規作成
     */
    public class PropertiesFile {
        /**
         * SQLファイル（指定無し）
         */
        public static final String SQL_PROPERTIES = "sqlite/sql.properties";
        /**
         * SQLファイル（初期データ）
         */
        public static final String SQL_PROPERTIES_FIRST_INPUT = "sqlite/sql_first_input.properties";
        /**
         * DB情報
         */
        public static final String PREFERENCES_DB = "PREFERENCES_DB";
    }

    /**
     * SQLプロパティーフィルのKey
     *
     * @author k.kitamura
     * @date 2014/09/06 新規作成
     */
    public class SQLiteKey {
        /**
         * 初期データ投入
         */
        public static final String FIRST_INPUT = "FIRST_INPUT_";
    }

}
