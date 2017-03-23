package elephant.jp.net.common.util.log;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * ログを出力するクラス
 *
 * @author k.kitamura
 * @date 2014/05/05
 */
public class OutputLog {

    // クラス定数宣言
    /**
     * プロジェクト名
     */
    private String tag = "Elephant";

    public OutputLog() {
    }

    /**
     * @param tag tag
     */
    public OutputLog(String tag) {
        this.tag = tag;
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logD(String message) {
        Log.d(this.tag, message + getHed((new Throwable()).getStackTrace()));
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logD(String tag, String message) {
        Log.d(tag, message + getHed((new Throwable()).getStackTrace()));
    }

    /**
     * @param context 呼び出し元のContext
     * @param message 出力するメッセージ
     */
    public void logT(Context context, String message) {
        logD(message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * @param context 呼び出し元のContext
     * @param message 出力するメッセージ
     */
    public void logT(Context context, String tag, String message) {
        logD(tag, message);
        Toast.makeText(context, tag + " : " + message, Toast.LENGTH_LONG)
                .show();
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logE(String message) {
        Log.e(this.tag, message + getHed((new Throwable()).getStackTrace()));
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logE(String tag, String message) {
        Log.e(tag, message + getHed((new Throwable()).getStackTrace()));
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logE(String message, Exception e) {
        Log.e(this.tag, message + getHed((new Throwable()).getStackTrace()), e);
    }

    /**
     * @param message 出力するメッセージ
     */
    public void logE(String tag, String message, Exception e) {
        Log.e(tag, message + getHed((new Throwable()).getStackTrace()), e);
    }

    /**
     * 呼び出し元のメソッド名/クラス名/行数を作成する
     *
     * @param ste StackTraceElement
     * @return メソッド名/クラス名/行数
     */
    private String getHed(StackTraceElement[] ste) {
        return ": " + ste[1].getMethodName() + "(" + ste[1].getFileName() + ":"
                + ste[1].getLineNumber() + ") ";
    }

}
