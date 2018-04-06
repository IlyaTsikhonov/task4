package pckg.command;

import pckg.dao.RecordDao;
import pckg.entity.Record;
import pckg.exception.StorageException;

/**
 * <h1>UpdateCommand<T extends Record></h1>
 * Command для взаимодействия с {@link RecordDao}.
 * <p>
 * Вызывает метод update()
 */
public class UpdateCommand<T extends Record> implements Command {

    // Dao для взаимодействия
    private RecordDao<T> dao;

    /**
     * Конструирует новый <code>UpdateCommand</code>
     *
     * @param  dao RecordDao для взаимодействия
     */
    public UpdateCommand(RecordDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Вызывает метод update()
     * @throws StorageException если были проблемы со взаимодействием с бд
     */
    public void execute(T object) throws StorageException {
        dao.update(object);
    }
}
