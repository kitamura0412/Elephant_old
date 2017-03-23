package elephant.jp.net.database.sqlite.bean;

import elephant.jp.net.common.util.StringUtil;
import elephant.jp.net.common.util.XMLManagement;

import java.util.ArrayList;

/**
 * @author USER
 *
 */
public class DataBasesBean {

	// インスタンス定数定義
	/** ブランク(半角スペース1文字) */
	public final String BLANK = " ";

	// インスタンス変数定義
	/** DB名 */
	public String databasesName;
	/** バージョン */
	public int version;
	/** 古いバージョン */
	public int oldVersion;
	/** バージョンUp判定フラグ */
	public boolean versionUp;
	/** テーブル */
	public ArrayList<TableBean> tableList = new ArrayList<TableBean>();

	public void makeCreate() {

		for (TableBean tableBean : tableList) {
			// 1テーブル取得

			StringBuilder buf = new StringBuilder();
			buf.append("CREATE TABLE");
			// ブランク
			buf.append(BLANK);
			// テーブル名
			buf.append(tableBean.tableName);
			buf.append("(");
			boolean farst = true;
			for (ColumnBean columnBean : tableBean.columnList) {
				if (!farst) {
					// 初回以外の場合
					buf.append(",");
				} else {
					farst = false;
				}

				// カラム名
				buf.append(columnBean.columnName);
				// ブランク
				buf.append(BLANK);

				// データ型
				buf.append(columnBean.dataType);
				if (columnBean.dataSize != XMLManagement.DATASIZE_DEFAULT) {
					// データサイズが定義されている場合
					// データサイズ
					buf.append("(" + columnBean.dataSize + ")");
				}
				if (columnBean.primaryKey) {
					// プライマリキーの場合
					buf.append(" PRIMARY KEY ");
				}
				if (columnBean.notNull) {
					// null禁止の場合
					buf.append(" NOT NULL ");
				}
				if (StringUtil.isNotEmpty(columnBean.defaultValue)) {
					// 初期値が定義されている場合
					buf.append(" DEFAULT " + "'" + columnBean.defaultValue
							+ "'");
				}

			}
			buf.append(")");
			tableBean.sqlCreate = buf.toString();
		}
	}

	public void makeDrop() {
		for (TableBean tableBean : tableList) {
			// 1テーブル取得
			StringBuilder buf = new StringBuilder();
			buf.append("DROP TABLE ");
			buf.append(tableBean.tableName + ";");
			tableBean.sqlDrop = buf.toString();
		}
	}

	public void makeInsert() {

	}

}