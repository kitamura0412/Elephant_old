package elephant.jp.net.exception;

/**
 * 例外クラス
 *
 * @author k.kitamura
 * @date 2014/09/02 新規作成
 */
public class ElephantException extends Exception {

    /**
     * シリアルバージョンID
     */
    private static final long serialVersionUID = 6429383599625059858L;


    /**
     * メッセージを出力する
     *
     * @param message エラーメッセージ
     */
    public ElephantException(String message) {
        super(message);
    }

    /**
     * メッセージIDとメッセージを出力する
     *
     * @param messageId メッセージID
     * @param message   エラーメッセージ
     */
    public ElephantException(String messageId, String message) {
        super(messageId + " : " + message);
    }

}