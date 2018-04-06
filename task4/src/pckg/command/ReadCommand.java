package pckg.command;

import pckg.dao.RecordDao;
import pckg.entity.Record;
import pckg.exception.StorageException;

/**
 * <h1>ReadCommand<T extends Record></h1>
 * Command для взаимодействия с {@link RecordDao}.
 * <p>
 * Вызывает метод read()
 */
public class ReadCommand<T extends Record> implements Command {

    // Dao для взаимодействия
    private RecordDao<T> dao;

    /**
     * Конструирует новый <code>ReadCommand</code>
     *
     * @param  dao RecordDao для взаимодействия
     */
    public ReadCommand(RecordDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Возвращает объект Record, полученный методом read()
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return Record, полученный методом read()
     */
    public T execute(int id) throws StorageException {
        return dao.read(id);
    }
}
