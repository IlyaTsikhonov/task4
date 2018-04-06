package pckg.controller;

import pckg.command.*;
import pckg.entity.Record;
import pckg.exception.InputException;
import pckg.exception.StorageException;
import pckg.factory.DaoFactory;
import pckg.option.Option;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * <h1>Controller<T extends Record></h1>
 * Класс для взаимодействия с {@link pckg.dao.RecordDao} через {@link Command}.
 */
public abstract class Controller<T extends Record> implements Option {

    //список команд для ввода
    private static final String CREATE = "create";
    private static final String READ = "read";
    private static final String READ_ALL = "readall";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    public static final String QUIT = "quit";
    public static final String HELP = "/?";

    //Фабрика dao
    private DaoFactory daoFactory;

    //Поток для вывода информации
    private OutputStream out;

    //список команд
    private CreateCommand<T> createCommand;
    private ReadCommand<T> readCommand;
    private ReadAllCommand<T> readAllCommand;
    private UpdateCommand<T> updateCommand;
    private DeleteCommand<T> deleteCommand;

    /**
     * Конструирует новый <code>Controller</code>
     *
     * @param  out OutputStream для вывода информации
     */
    public Controller(OutputStream out) {
        this.out = out;
    }

    /**
     * Взаимодействует с dao,
     * взаимодействие определяется args.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  args аргументы
     * @param  stream поток для вывода информации
     */
    public void executeCommand(String[] args, OutputStream stream) throws InputException, StorageException {
        try {
            switch (args[0]) {
                case Controller.CREATE:
                    createOption(stream);
                    break;
                case Controller.READ:
                    if (args.length >= 3) {
                        readOption(Integer.parseInt(args[2]), stream);
                    } else {
                        throw new InputException("Wrong parameters count = " + args.length + " (required 3 or 4).");
                    }
                    break;
                case Controller.READ_ALL:
                    readAllOption(stream);
                    break;
                case Controller.UPDATE:
                    if (args.length >= 3) {
                        updateOption(args, stream);
                    } else {
                        throw new InputException("Wrong parameters count = " + args.length + " (required 3 or 4).");
                    }
                    break;
                case Controller.DELETE:
                    if (args.length >= 3) {
                        deleteOption(Integer.parseInt(args[2]), stream);
                    } else {
                        throw new InputException("Wrong parameters count = " + args.length + " (required 3 or 4).");
                    }
                    break;
                default:
                    stream.write(("Wrong first parameter (required " +
                            Controller.CREATE + ", " +
                            Controller.READ + ", " +
                            Controller.READ_ALL + ", " +
                            Controller.UPDATE + " or " +
                            Controller.DELETE + ").").getBytes());
            }
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    /**
     * Взаимодействует с dao,
     * возвращает объект, полученный методом create() через Command.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return Record, полученный методом create() через Command
     */
    T create() throws StorageException {
        return createCommand.execute();
    }

    /**
     * Взаимодействует с dao,
     * возвращает объект, полученный методом read() через Command.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return Record, полученный методом read() через Command
     */
    T read(int id) throws StorageException {
        return readCommand.execute(id);
    }

    /**
     * Взаимодействует с dao,
     * возвращает список объектов, полученный методом readAll() через Command.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return List<RecordЮ, полученный методом readAll() через Command
     */
    List<T> readAll() throws StorageException {
        return readAllCommand.execute();
    }

    /**
     * Взаимодействует с dao,
     * обновляет объект, передавая его в метод update() через Command.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     */
    void update(T object) throws StorageException {
        updateCommand.execute(object);
    }

    /**
     * Взаимодействует с dao,
     * удаляет объект, передавая его в метод delete() через Command.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     */
    void delete(T object) throws StorageException {
        deleteCommand.execute(object);
    }

    DaoFactory getDaoFactory() {
        return daoFactory;
    }

    void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    void setCreateCommand(CreateCommand<T> createCommand) {
        this.createCommand = createCommand;
    }

    void setReadCommand(ReadCommand<T> readCommand) {
        this.readCommand = readCommand;
    }

    void setReadAllCommand(ReadAllCommand<T> readAllCommand) {
        this.readAllCommand = readAllCommand;
    }

    void setUpdateCommand(UpdateCommand<T> updateCommand) {
        this.updateCommand = updateCommand;
    }

    void setDeleteCommand(DeleteCommand<T> deleteCommand) {
        this.deleteCommand = deleteCommand;
    }
}
