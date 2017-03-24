package elephant.jp.net.database.sqlite.bean;

import java.util.ArrayList;

/**
 * @author USER
 *
 */
public class TableBean {

    /** テーブル名 */
    public String tableName;
    
    /**
     * バージョンアップ時に古いテーブルデータの保持: true=保持する(新しいテーブルにコピー), false=保持しない(削除して新規作成)
     */
    public boolean copy;
    
    /** カラム */
    public ArrayList<ColumnBean> columnList = new ArrayList<ColumnBean>();

    /** CREATE文 */
    public String sqlCreate;
    
    /** CREATE文(コピー用) */
//    public String sqlCreateCopy;
    
    /** DROP文 */
    public String sqlDrop;
    
}