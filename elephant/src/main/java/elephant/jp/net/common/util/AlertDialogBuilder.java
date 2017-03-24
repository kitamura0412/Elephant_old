package elephant.jp.net.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * 概要 : アラートダイアログを生成するクラス
 *
 * @author k.kitamura
 * @date 2014/09/07
 */
public class AlertDialogBuilder {

    public static ProgressDialog showProgressDialog(Activity activity, String title, String message, boolean cancelable) {
        ProgressDialog progressDialog = new ProgressDialog(activity);

        if (title != null) {
            // タイトルを設定
            progressDialog.setTitle(title);
        }
        if (message != null) {
            // メッセージを設定
            progressDialog.setMessage(message);
        }

        // プログレスダイアログのスタイルを円スタイルに設定します
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // プログレスダイアログのキャンセルが可能かどうかを設定します
        progressDialog.setCancelable(cancelable);
        // プログレスダイアログを表示します
        progressDialog.show();

        return progressDialog;
    }


    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param listener1  リスナー１
     */
    public static void showDialog(Activity activity, View view, String title,
                                  String message, boolean cancelable, String button1,
                                  DialogInterface.OnClickListener listener1) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        alertDialogBuilder.setView(view);
        // ボタン1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, listener1);

    }

    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param button2    ボタン２
     * @param listener1  リスナー１
     * @param listener2  リスナー２
     */
    public static void showDialog(Activity activity, View view, String title,
                                  String message, boolean cancelable, String button1, String button2,
                                  DialogInterface.OnClickListener listener1,
                                  DialogInterface.OnClickListener listener2) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        alertDialogBuilder.setView(view);
        // ボタン2,1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, button2, listener1, listener2);

    }

    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param button2    ボタン２
     * @param button3    ボタン３
     * @param listener1  リスナー１
     * @param listener2  リスナー２
     * @param listener3  リスナー３
     */
    public static void showDialog(Activity activity, View view, String title,
                                  String message, boolean cancelable, String button1, String button2,
                                  String button3, DialogInterface.OnClickListener listener1,
                                  DialogInterface.OnClickListener listener2,
                                  DialogInterface.OnClickListener listener3) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);
        alertDialogBuilder.setView(view);
        // ボタン3の設定
        alertDialogBuilder.setPositiveButton(button3, listener3);
        // ボタン2,1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, button2, listener1, listener2);

    }

    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param listener1  リスナー１
     */
    public static void showDialog(Activity activity, String title,
                                  String message, boolean cancelable, String button1,
                                  DialogInterface.OnClickListener listener1) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // ボタン1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, listener1);

    }

    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param button2    ボタン２
     * @param listener1  リスナー１
     * @param listener2  リスナー２
     */
    public static void showDialog(Activity activity, String title,
                                  String message, boolean cancelable, String button1, String button2,
                                  DialogInterface.OnClickListener listener1,
                                  DialogInterface.OnClickListener listener2) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // ボタン2,1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, button2, listener1, listener2);

    }

    /**
     * ダイアログ
     *
     * @param activity   呼び出し元のActivity
     * @param title      タイトル
     * @param message    メッセージ
     * @param cancelable キャンセル有効/無効
     * @param button1    ボタン１
     * @param button2    ボタン２
     * @param button3    ボタン３
     * @param listener1  リスナー１
     * @param listener2  リスナー２
     * @param listener3  リスナー３
     */
    public static void showDialog(Activity activity, String title,
                                  String message, boolean cancelable, String button1, String button2,
                                  String button3, DialogInterface.OnClickListener listener1,
                                  DialogInterface.OnClickListener listener2,
                                  DialogInterface.OnClickListener listener3) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // ボタン3の設定
        alertDialogBuilder.setPositiveButton(button3, listener3);
        // ボタン2,1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, button2, listener1, listener2);

    }

    private static void setDialog(AlertDialog.Builder alertDialogBuilder,
                                  Activity activity, String title, String message,
                                  boolean cancelable, String button1,
                                  DialogInterface.OnClickListener listener1) {
        // ボタン1の設定
        alertDialogBuilder.setNegativeButton(button1, listener1);

        // タイトル
        alertDialogBuilder.setTitle(title);
        // メッセージ
        alertDialogBuilder.setMessage(message);
        // キャンセル
        alertDialogBuilder.setCancelable(cancelable);
        // ダイアログ生成
        alertDialogBuilder.create();
        // ダイアログ表示
        alertDialogBuilder.show();

    }

    private static void setDialog(AlertDialog.Builder alertDialogBuilder,
                                  Activity activity, String title, String message,
                                  boolean cancelable, String button1, String button2,
                                  DialogInterface.OnClickListener listener1,
                                  DialogInterface.OnClickListener listener2) {

        // ボタン2の設定
        alertDialogBuilder.setNeutralButton(button2, listener2);
        // ボタン1の設定
        setDialog(alertDialogBuilder, activity, title, message, cancelable,
                button1, listener1);

    }
}
