package pckg.option;

import pckg.exception.InputException;
import pckg.exception.StorageException;

import java.io.OutputStream;

/**
 * <h1>Option</h1>
 * Интерфейс, представляющий собой команды пользователя для взаимодействия с бд.
 */
public interface Option {

    /**
     * Создает объект и выводит информацию о нем в stream.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  stream поток для вывода информации
     */
    void createOption(OutputStream stream) throws InputException, StorageException;

    /**
     * Читает объект и выводит информацию о нем в stream.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  id идентификатор читаемого объекта
     * @param  stream поток для вывода информации
     */
    void readOption(int id, OutputStream stream) throws InputException, StorageException;

    /**
     * Читает все объекты и выводит информацию о них в stream.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  stream поток для вывода информации
     */
    void readAllOption(OutputStream stream) throws InputException, StorageException;

    /**
     * Обновляет объект и выводит информацию о нем в stream.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  args аргументы для создания объекта
     * @param  stream поток для вывода информации
     */
    void updateOption(String[] args, OutputStream stream) throws InputException, StorageException;

    /**
     * Удаляет объект и выводит информацию о нем в stream.
     *
     * @throws InputException если были проблемы с аргументами
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param  id идентификатор удаляемого объекта
     * @param  stream поток для вывода информации
     */
    void deleteOption(int id, OutputStream stream) throws InputException, StorageException;
}
