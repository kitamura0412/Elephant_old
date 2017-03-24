package elephant.jp.net.common.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import elephant.jp.net.common.util.log.OutputLog;

/**
 * assetsフォルダを管理するクラス
 *
 * @author k.kitamura
 * @date 2011.05.11 新規作成
 */
public class AssetsManagement {

    /**
     * ログ出力クラス
     */
    private static OutputLog log = new OutputLog();
    /**
     * ファイルストリーム
     */
    private static InputStream stream;
    /**
     * プロパティーファイル
     */
    private static Properties properties;

    /**
     * コンストラクタ
     * <p/>
     * 対象のファイルを読み込む
     *
     * @param context Context
     * @param fName   管理するファイル ファイルストリームの取得に失敗／プロパティーファイルのロードに失敗
     */
    public AssetsManagement(Context context, String fName) {

        openFile(context, fName);

    }

    /**
     * プロパティーファイルからkeyに紐付くvakueを取得する
     */
    public static void openFile(Context context, String fName) {
        // プロパティーの生成
        properties = new Properties();

        // 対象のファイルをオープンして、ファイルストリームを生成する
        try {
            stream = context.getAssets().open(fName);

        } catch (IOException e) {
            log.logE("対象ファイルなし", e);
        }

        // プロパティーファイルをロードする
        try {
            properties.load(new InputStreamReader(stream, "UTF-8"));
        } catch (IOException e) {
            log.logE("プロパティーファイルロード失敗", e);
        }
    }


    /**
     * プロパティーファイルからkeyに紐付くvakueを取得する
     *
     * @param key プロパティーファイルから取得するvakueのkey
     * @return プロパティーファイルから取得したvalue。keyが存在しない場合はnullが返る
     */
    public static String getProperty(String key) {

        // プロパティーファイルからkeyに紐付くvakueを取得する
        return properties.getProperty(key);
    }

    /**
     * クローズ処理
     */
    public static void closeAssets() {

        // ファイルストリームを解放する
        try {
            stream.close();
        } catch (IOException e) {
            log.logE("クローズ失敗", e);
        }
    }

    /**
     * プロパティーファイルからkeyに紐づくvalueを取得
     */
    public static String getPropertyValue(Context context, String fName, String key) {
        // プロパティーの生成
        Properties properties = new Properties();

        // 対象のファイルをオープンして、ファイルストリームを生成する
        InputStream stream = null;
        try {
            stream = context.getAssets().open(fName);

        } catch (IOException e) {
            log.logE("対象ファイルなし", e);
        }

        // プロパティーファイルをロードする
        try {
            properties.load(new InputStreamReader(stream, "UTF-8"));
        } catch (IOException e) {
            log.logE("プロパティーファイルロード失敗", e);
        }
        String value = getProperty(key);
        closeAssets();
        return value;
    }
}
