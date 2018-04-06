package pckg.command;

import pckg.dao.RecordDao;
import pckg.entity.Record;
import pckg.exception.StorageException;

import java.util.List;

/**
 * <h1>ReadAllCommand<T extends Record></h1>
 * Command для взаимодействия с {@link RecordDao}.
 * <p>
 * Вызывает метод readAll()
 */
public class ReadAllCommand<T extends Record> implements Command {

    // Dao для взаимодействия
    private RecordDao<T> dao;

    /**
     * Конструирует новый <code>ReadAllCommand</code>
     *
     * @param  dao RecordDao для взаимодействия
     */
    public ReadAllCommand(RecordDao<T> dao) {
        this.dao = dao;
    }

    /**
     * Возвращает объект List<Record>, полученный методом readAll()
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return List<Record>, полученный методом readAll()
     */
    public List<T> execute() throws StorageException {
        return dao.readAll();
    }
}
