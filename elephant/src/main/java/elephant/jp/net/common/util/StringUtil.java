package elephant.jp.net.common.util;

import android.text.Editable;
import android.widget.EditText;

/**
 * Stringの共通クラス
 *
 * @author k.kitamura
 * @date 2014.08.29 新規作成
 */
public class StringUtil {

    /**
     * null,空文字の場合true
     *
     * @param value チェックする文字列
     * @return 結果:true=null 又は空文字/false=null 又は空文字以外
     */
    public static boolean isEmpty(String value) {
        return (value == null || value.length() == 0);
    }

    /**
     * 文字列の場合true
     *
     * @param value チェックする文字列
     * @return 結果:true=null 又は空文字以外 又は空文字/false=null
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }


    /**
     * EditTextからStringを取り出す。
     */
    public static String toString(EditText editText) {
        if (editText == null) {
            return null;
        }
        Editable editable = editText.getText();
        if (editable == null) {
            return null;
        }
        return editable.toString();
    }

}
