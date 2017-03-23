package elephant.jp.net.common.util;

import android.app.Activity;
import android.content.Context;
import android.util.Xml;

import elephant.jp.net.common.cons.Const;
import elephant.jp.net.database.sqlite.bean.ColumnBean;
import elephant.jp.net.database.sqlite.bean.DataBasesBean;
import elephant.jp.net.database.sqlite.bean.TableBean;
import elephant.jp.net.preferences.ElephantSharedPreferences;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * DBを定義したXMLを管理するクラス
 * 
 * @author k.kitamura
 * @date 2014.08.26 新規作成
 * 
 */
public class XMLManagement {

	// クラス定数定義
	/** データサイズ未定義の場合 */
	public static final int DATASIZE_DEFAULT = -1;

	/** バージョン未定義の場合 */
	public final int VERSION_DEFAULT = 1;

	// インスタンス変数定義
	/** データベース定義 */
	public DataBasesBean dataBasesBean = new DataBasesBean();

	/**
	 * 
	 * DBを定義したXMLファイルを読み込む
	 * 
	 * @param context
	 *            呼び出し元のContext
	 */
	public XMLManagement(Context context) {
		// 対象のファイルをオープンして、ファイルストリームを生成する
		InputStream is = null;
		try {
			is = context.getAssets().open(Const.XMLFile.SQLITEDATABASES);

		} catch (IOException e) {
			// 対象ファイルなし
			e.printStackTrace();
		}

		// XmlPullParserのインスタンス化
		XmlPullParser xmlPullParser = Xml.newPullParser();

		// XMLファイルの設定
		try {
			xmlPullParser.setInput(is, Const.XMLFile.ENCODING);

		} catch (XmlPullParserException e) {

			e.printStackTrace();
		}

		// テーブル一覧を作成
		readXML(context, xmlPullParser, dataBasesBean);

	}

	/**
	 * @param context
	 *            呼び出し元のContext
	 * @param xmlPullParser
	 * @param dataBasesBean
	 */
	private void readXML(Context context, XmlPullParser xmlPullParser,
			DataBasesBean dataBasesBean) {

		try {

			for (int e = xmlPullParser.getEventType(); e != XmlPullParser.END_DOCUMENT; e = xmlPullParser
					.next()) {
				// xmlを読み込み

				String value = xmlPullParser.getName();

				if (e == XmlPullParser.START_TAG
						&& Const.XMLFile.VERSION.equals(value)) {
					// VERSIONの開始タグである場合

					String version = xmlPullParser.nextText();
					if (StringUtil.isNotEmpty(version)) {
						dataBasesBean.version = Integer.parseInt(version);

						// バージョン番号取得
						dataBasesBean.oldVersion = ElephantSharedPreferences
								.getSharedPreferences(context,
										Activity.MODE_PRIVATE,
										Const.PropertiesFile.PREFERENCES_DB,
										Const.XMLFile.VERSION, 0);

						if (dataBasesBean.oldVersion < dataBasesBean.version) {
							// バージョンが上がっていた場合
							dataBasesBean.versionUp = true;
						} else {
							// バージョンが変わっていなかった場合
							dataBasesBean.versionUp = false;

						}

						if (!dataBasesBean.versionUp) {
							// バージョンが変わっていなかった場合
							// 読み込みを終了する。
							break;
						}

					} else {
						// バージョン未定義の場合
						dataBasesBean.version = VERSION_DEFAULT;
						dataBasesBean.versionUp = false;
					}

				} else if (e == XmlPullParser.START_TAG
						&& Const.XMLFile.DB_NAME.equals(value)) {
					// NAMEの開始タグである場合

					dataBasesBean.databasesName = xmlPullParser.nextText();
					ElephantSharedPreferences.setSharedPreferences(context,
							Activity.MODE_PRIVATE,
							Const.PropertiesFile.PREFERENCES_DB, "NAME",
							dataBasesBean.databasesName);

				} else if (e == XmlPullParser.START_TAG
						&& Const.XMLFile.TABLE.equals(value)) {
					// TABLEの開始タグである場合

					TableBean tableBean = new TableBean();
					String valueTable = null;
					for (;; e = xmlPullParser.next()) {

						// 1つのTABLEを読み込み

						valueTable = xmlPullParser.getName();

						if (e == XmlPullParser.END_TAG
								&& Const.XMLFile.TABLE.equals(valueTable)) {
							break;
						}

						// NAME取得
						if (e == XmlPullParser.START_TAG
								&& Const.XMLFile.TABLE_NAME.equals(valueTable)) {
							// NAMEの開始タグである場合
							tableBean.copy = Const.XMLFile.YES
									.equals(xmlPullParser.getAttributeValue(
											null, Const.XMLFile.COPY));
							tableBean.tableName = xmlPullParser.nextText();
							// xmlPullParser.next();
						} else if (e == XmlPullParser.START_TAG
								&& Const.XMLFile.COLUMN.equals(valueTable)) {
							// COLUMNの開始タグである場合
							ColumnBean columnBean = new ColumnBean();

							columnBean.dataType = xmlPullParser
									.getAttributeValue(null,
											Const.XMLFile.DATATYPE);

							// サイズ
							String dataSize = xmlPullParser.getAttributeValue(
									null, Const.XMLFile.DATASIZE);
							if (StringUtil.isNotEmpty(dataSize)) {
								columnBean.dataSize = Integer
										.parseInt(dataSize);
							} else {
								// データサイズ未定義の場合
								columnBean.dataSize = DATASIZE_DEFAULT;
							}

							columnBean.primaryKey = Const.XMLFile.YES
									.equals(xmlPullParser.getAttributeValue(
											null, Const.XMLFile.PRIMARYKEY));
							columnBean.notNull = Const.XMLFile.YES
									.equals(xmlPullParser.getAttributeValue(
											null, Const.XMLFile.NOTNULL));

							columnBean.defaultValue = xmlPullParser
									.getAttributeValue(null,
											Const.XMLFile.DEFAULT);

							columnBean.columnName = xmlPullParser.nextText();

							tableBean.columnList.add(columnBean);
						}

					}
					dataBasesBean.tableList.add(tableBean);

				}

			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
