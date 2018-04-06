package pckg.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <h1>MySqlDaoFactory</h1>
 * Реализация DaoFactory для MySql
 *
 * Подробнее {@link DaoFactory}
 */
public class MySqlDaoFactory extends DaoFactory {

    //данные для подключения к бд
    private String user = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost:3306/university";
    private String driver = "com.mysql.jdbc.Driver";

    /**
     * Конструирует новый <code>MySqlDaoFactory</code>
     * Регистрирует драйвер для возможности получения соединений к бд.
     */
    public MySqlDaoFactory() {
        try {
            Class.forName(driver);//регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
