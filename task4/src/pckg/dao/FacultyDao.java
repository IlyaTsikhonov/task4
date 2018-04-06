package pckg.dao;

import pckg.exception.StorageException;
import pckg.entity.Faculty;
import pckg.factory.DaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <h1>FacultyDao</h1>
 * Класс для взаимодействия с таблицей faculty.
 *
 * Подробнее {@link RecordDao}
 */
public class FacultyDao extends RecordDao<Faculty> {
    public FacultyDao(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    @Override
    String getTableName() {
        return "faculty";
    }

    @Override
    Faculty getRecord(ResultSet rs) throws StorageException {
        Faculty faculty;
        try {
            faculty = new Faculty(rs.getInt("id"));
            faculty.setName(rs.getString("name"));
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }
        return faculty;
    }

    @Override
    String getUpdateQuery() {
        return "UPDATE faculty SET name = ? WHERE id = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (name) VALUES ('defaultName')";
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement ps, Faculty object) throws SQLException {
        ps.setString(1, object.getName());
        ps.setInt(2, object.getId());
    }
}
