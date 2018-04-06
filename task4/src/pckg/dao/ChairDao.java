package pckg.dao;

import pckg.exception.StorageException;
import pckg.entity.Chair;
import pckg.entity.Faculty;
import pckg.factory.DaoFactory;

import java.sql.*;

/**
 * <h1>ChairDao</h1>
 * Класс для взаимодействия с таблицей chair.
 *
 * Подробнее {@link RecordDao}
 */
public class ChairDao extends RecordDao<Chair> {
    public ChairDao(DaoFactory factory, Connection connection) {
        this.factory = factory;
        this.connection = connection;
    }

    @Override
    String getTableName() {
        return "chair";
    }

    @Override
    Chair getRecord(ResultSet rs) throws StorageException {
        Chair chair;
        String faculty;
        try {
            chair = new Chair(rs.getInt("id"));
            chair.setName(rs.getString("name"));
            faculty = rs.getString("faculty");
            if (faculty == null) {
                chair.setFaculty(null);
            } else {
                chair.setFaculty(factory.getFacultyDao().read(Integer.parseInt(faculty)));
            }
        } catch (SQLException e) {
            throw new StorageException(e.getMessage());
        }
        return chair;
    }

    @Override
    String getUpdateQuery() {
        return "UPDATE chair SET name = ?, faculty = ? WHERE id = ?";
    }

    @Override
    String getCreateQuery() {
        return "INSERT INTO chair (name) VALUES ('defaultName')";
    }

    @Override
    void prepareStatementForUpdate(PreparedStatement ps, Chair object) throws SQLException {
        Faculty faculty = object.getFaculty();
        ps.setString(1, object.getName());
        if (faculty != null) {
            ps.setInt(2, faculty.getId());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        ps.setInt(3, object.getId());
    }
}
