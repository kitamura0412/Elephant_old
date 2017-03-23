package elephant.jp.net.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベースを作成.ヴァージョンの変更を行う
 * 
 * @author k.kitamura
 * @date 2011.05.11 新規作成
 * 
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    /**
     * コンストラクタ
     * 
     * @param context
     */
    public DatabaseOpenHelper(Context context, String dbName, int dbVersion) {

	// 指定したデータベース名が存在しない場合は、
	// 新たに作成されonCreate()が呼ばれる
	// バージョンを変更するとonUpgrade()が呼ばれる
	// 第二引数をnullにすると端末内にDBが作成される
	super(context, dbName, null, dbVersion);
//
//	// Contextのset
//	this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
    }



}
