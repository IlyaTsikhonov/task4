package pckg.factory;

import pckg.dao.ChairDao;
import pckg.dao.FacultyDao;
import pckg.dao.TeacherDao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <h1>DaoFactory</h1>
 * Фабрика {@link pckg.dao.RecordDao}.
 */
public abstract class DaoFactory {

    //Dao для взаимодействия с факультетами
    private FacultyDao facultyDao;

    //Dao для взаимодействия с кафедрами
    private ChairDao chairDao;

    //Dao для взаимодействия с преподавателями
    private TeacherDao teacherDao;

    /**
     * Получает соединение к используемой бд или ее аналогу.
     *
     * @throws SQLException если были проблемы со взаимодействием с бд
     *
     * @return {@link Connection} соединение к бд
     */
    abstract Connection getConnection() throws SQLException;

    /**
     * Возвращает {@link FacultyDao} используя ленивую инициализацию.
     *
     * @throws SQLException если были проблемы со взаимодействием с бд
     *
     * @return dao для взаимодействия с факультетами
     */
    public FacultyDao getFacultyDao() throws SQLException {
        if (facultyDao == null) {
            facultyDao = new FacultyDao(this, getConnection());
        }
        return facultyDao;
    }

    /**
     * Возвращает {@link ChairDao} используя ленивую инициализацию.
     *
     * @throws SQLException если были проблемы со взаимодействием с бд
     *
     * @return dao для взаимодействия с кафедрами
     */
    public ChairDao getChairDao() throws SQLException {
        if (chairDao == null) {
            chairDao = new ChairDao(this, getConnection());
        }
        return chairDao;
    }

    /**
     * Возвращает {@link TeacherDao} используя ленивую инициализацию.
     *
     * @throws SQLException если были проблемы со взаимодействием с бд
     *
     * @return dao для взаимодействия с преподавателями
     */
    public TeacherDao getTeacherDao() throws SQLException {
        if (teacherDao == null) {
            teacherDao = new TeacherDao(this, getConnection());
        }
        return teacherDao;
    }
}
