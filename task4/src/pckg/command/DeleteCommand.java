package pckg.command;

import pckg.dao.RecordDao;
import pckg.entity.Record;
import pckg.exception.StorageException;

/**
 * <h1>DeleteCommand<T extends Record></h1>
 * Command для взаимодействия с {@link RecordDao}.
 * <p>
 * Вызывает метод delete()
 */
public class DeleteCommand<T extends Record> implements Command {

    // Dao для взаимодействия
    private RecordDao<T> dao;

    /**
     * Конструирует новый <code>DeleteCommand</code>
     *
     * @param  dao RecordDao для взаимодействия
     */
    public DeleteCommand(RecordDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Вызывает метод delete()
     * @throws StorageException если были проблемы со взаимодействием с бд
     */
    public void execute(T object) throws StorageException {
        dao.delete(object);
    }
}
