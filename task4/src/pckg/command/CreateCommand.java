package pckg.command;

import pckg.dao.RecordDao;
import pckg.entity.Record;
import pckg.exception.StorageException;

/**
 * <h1>CreateCommand<T extends Record></h1>
 * Command для взаимодействия с {@link RecordDao}.
 * <p>
 * Возвращает результат метода create()
 */
public class CreateCommand<T extends Record> implements Command {

    // Dao для взаимодействия
    private RecordDao<T> dao;

    /**
     * Конструирует новый <code>CreateCommand</code>
     *
     * @param  dao RecordDao для взаимодействия
     */
    public CreateCommand(RecordDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Возвращает объект Record, полученный методом create()
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return Record, полученный методом create()
     */
    public T execute() throws StorageException {
        return dao.create();
    }
}
