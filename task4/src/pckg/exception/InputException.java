package pckg.exception;

/**
 * <h1>InputException</h1>
 * Исключение при обработке аргументов.
 */
public class InputException extends Exception {

    /**
     * Конструирует новый <code>InputException</code>
     *
     * @param  message сообщение об ошибке
     */
    public InputException(String message) {
        super(message);
    }
}
