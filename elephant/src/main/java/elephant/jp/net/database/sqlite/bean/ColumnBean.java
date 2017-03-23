package elephant.jp.net.database.sqlite.bean;

/**
 * @author USER
 *
 */
public class ColumnBean {

    /** カラム名 */
    public String columnName;
    /** データ型 */
    public String dataType;
    /** データサイズ */
    public int dataSize;
    /** 主キー */
    public boolean primaryKey;
    /** NOTNULL */
    public boolean notNull;
    /** 初期値 */
    public String defaultValue;

}