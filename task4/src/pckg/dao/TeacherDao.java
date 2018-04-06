package pckg.dao;

import pckg.exception.StorageException;
import pckg.entity.Chair;
import pckg.entity.Teacher;
import pckg.factory.DaoFactory;

import java.sql.*;

/**
 * <h1>TeacherDao</h1>
 * Класс для взаимодействия с таблицей teacher.
 *
 * Подробнее {@link RecordDao}
 */
public class TeacherDao extends RecordDao<Teacher> {
    public TeacherDao(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    @Override
    String getTableName() {
        return "teacher";
    }

    @Override
    Teacher getRecord(ResultSet rs) throws StorageException {
        Teacher teacher;
        String chair;
        try {
            teacher = new Teacher(rs.getInt("id"));
            teacher.setName(rs.getString("name"));
            chair = rs.getString("chair");
            if (chair == null) {
                teacher.setChair(null);
            } else {
                teacher.setChair(factory.getChairDao().read(Integer.parseInt(chair)));
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }
        return teacher;
    }

    @Override
    String getUpdateQuery() {
        return "UPDATE teacher SET name = ?, chair = ? WHERE id = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO teacher (name) VALUES ('defaultName')";
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement ps, Teacher object) throws SQLException {
        Chair chair = object.getChair();
        ps.setString(1, object.getName());
        if (chair != null) {
            ps.setInt(2, chair.getId());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        ps.setInt(3, object.getId());
    }
}
