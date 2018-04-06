package pckg.exception;

/**
 * <h1>StorageException</h1>
 * Исключение при взаимодействии с бд.
 */
public class StorageException extends Exception {

    /**
     * Конструирует новый <code>StorageException</code>
     *
     * @param  message сообщение об ошибке
     */
    public StorageException(String message) {
        super(message);
    }
}
