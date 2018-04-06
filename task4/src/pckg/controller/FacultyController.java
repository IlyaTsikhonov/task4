package pckg.controller;

import pckg.command.*;
import pckg.dao.FacultyDao;
import pckg.entity.Faculty;
import pckg.exception.InputException;
import pckg.exception.StorageException;
import pckg.factory.MySqlDaoFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * <h1>FacultyController</h1>
 * Класс для взаимодействия с FacultyDao.
 *
 * Подробнее {@link Controller}
 */
public class FacultyController extends Controller<Faculty> {
    public FacultyController(OutputStream out) {
        super(out);
        FacultyDao dao;
        try {
            setDaoFactory(new MySqlDaoFactory());
            dao = getDaoFactory().getFacultyDao();
            setCreateCommand(new CreateCommand<>(dao));
            setReadCommand(new ReadCommand<>(dao));
            setReadAllCommand(new ReadAllCommand<>(dao));
            setUpdateCommand(new UpdateCommand<>(dao));
            setDeleteCommand(new DeleteCommand<>(dao));
        } catch (SQLException e) {
            try {
                out.write((e.getMessage() + "\n").getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void createOption(OutputStream stream) throws InputException, StorageException {
        Faculty object = create();
        String print = "Record: " + object + " has been created";
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public void readOption(int id, OutputStream stream) throws InputException, StorageException {
        Faculty object = read(id);
        String print = "Record: " + object;
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public void readAllOption(OutputStream stream) throws InputException, StorageException {
        List<Faculty> list = readAll();
        String print = "List: " + list;
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public void updateOption(String[] args, OutputStream stream) throws InputException, StorageException {
        if (args.length >= 4) {
            Faculty object = read(Integer.parseInt(args[2]));
            String print;
            object.setName(args[3]);
            update(object);
            print = "Record: " + object + " has been updated";
            try {
                stream.write(print.getBytes());
            } catch (IOException e) {
                throw new InputException(e.getMessage());
            }
        } else {
            throw new InputException("Wrong parameters count = " + args.length + " (required 4).");
        }
    }

    @Override
    public void deleteOption(int id, OutputStream stream) throws InputException, StorageException {
        Faculty object = read(id);
        String print;
        delete(object);
        print = "Record: " + object + " has been deleted";
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }
}
