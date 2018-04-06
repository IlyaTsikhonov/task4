package pckg.dao;

import pckg.exception.StorageException;
import pckg.entity.Record;
import pckg.factory.DaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RecordDao<T extends Record></h1>
 * Класс для прямого взаимодействия с бд.
 */
public abstract class RecordDao<T extends Record> {

    //фабрика dao для доступа к другим таблицам
    DaoFactory factory;

    //соединение c бд
    Connection connection;

    /**
     * Взаимодействует с бд,
     * создает объект.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return {@link Record}, созданный с помощью запроса
     */
    public T create() throws StorageException {
        String sql = getCreateQuery();
        PreparedStatement ps;
        ResultSet rs = null;
        try {
            connection.prepareStatement(sql).executeUpdate();
            sql = "SELECT * FROM " + getTableName() + " WHERE id = last_insert_id()";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getRecord(rs);
    }

    /**
     * Взаимодействует с бд,
     * читает объект.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param id идентификатор читаемого объекта
     *
     * @return {@link Record}, прочитанный с помощью запроса
     */
    public T read(int id) throws StorageException {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = " + id;
        PreparedStatement ps;
        ResultSet rs = null;
        T object;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            object = getRecord(rs);
        } catch (StorageException e) {
            throw new StorageException("Record with id = " + id +
                    " in " + getTableName() + " is not found.");
        }
        return object;
    }

    /**
     * Взаимодействует с бд,
     * читает список объектов.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @return {@link List<Record>}, созданный с помощью запроса
     */
    public List<T> readAll() throws StorageException {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getRecord(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closePreparedStatement(ps);
                closeResultSet(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Взаимодействует с бд,
     * обновляет объект.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param object обновляемый объект
     */
    public void update(T object) throws StorageException {
        String sql = getUpdateQuery();
        PreparedStatement ps = null;
        int check;
        try {
            ps = connection.prepareStatement(sql);
            prepareStatementForUpdate(ps, object);
            check = ps.executeUpdate();
            if (check != 1){
                throw new StorageException("Record with id = " + object.getId() +
                        " in " + getTableName() + " is not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closePreparedStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Взаимодействует с бд,
     * удаляет объект.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param object удаляемый объект
     */
    public void delete(T object) throws StorageException {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = " + object.getId();
        PreparedStatement ps = null;
        int check;
        try {
            ps = connection.prepareStatement(sql);
            check = ps.executeUpdate();
            if (check != 1){
                throw new StorageException("Record with id = " + object.getId() +
                        " in " + getTableName() + " is not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closePreparedStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Закрывает {@link PreparedStatement}
     *
     * @throws SQLException если были проблемы при закрытии
     *
     * @param ps закрываемый PreparedStatement
     */
    private void closePreparedStatement(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
        }
    }

    /**
     * Закрывает {@link ResultSet}
     *
     * @throws SQLException если были проблемы при закрытии
     *
     * @param rs закрываемый ResultSet
     */
    private void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    /**
     * Возвращает название таблицы в бд.
     *
     * @return название таблицы в бд
     */
    abstract String getTableName();

    /**
     * Возвращает {@link Record}, полученный из бд.
     *
     * @throws StorageException если были проблемы со взаимодействием с бд
     *
     * @param rs результат запроса к бд
     *
     * @return Record, полученный из бд
     */
    abstract T getRecord(ResultSet rs) throws StorageException;

    /**
     * Возвращает запрос для обновления объекта в бд.
     *
     * @return запрос для обновления объекта в бд
     */
    abstract String getUpdateQuery();

    /**
     * Возвращает запрос для создания объекта в бд.
     *
     * @return запрос для создания объекта в бд
     */
    abstract String getCreateQuery();

    /**
     * Составляет {@link PreparedStatement} для обновления объекта object.
     *
     * @throws SQLException если были проблемы при составлении
     *
     * @param ps закрываемый составляемый PreparedStatement
     * @param object объект для обновления
     */
    abstract void prepareStatementForUpdate(PreparedStatement ps, T object) throws SQLException;
}
