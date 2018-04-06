package pckg.controller;

import pckg.command.*;
import pckg.dao.TeacherDao;
import pckg.entity.Teacher;
import pckg.exception.InputException;
import pckg.exception.StorageException;
import pckg.factory.MySqlDaoFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * <h1>TeacherController</h1>
 * Класс для взаимодействия с TeacherDao.
 *
 * Подробнее {@link Controller}
 */
public class TeacherController extends Controller<Teacher> {
    public TeacherController(OutputStream out) {
        super(out);
        TeacherDao dao;
        try {
            setDaoFactory(new MySqlDaoFactory());
            dao = getDaoFactory().getTeacherDao();
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
        Teacher object = create();
        String print = "Record: " + object + " has been created";
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public void readOption(int id, OutputStream stream) throws InputException, StorageException {
        Teacher object = read(id);
        String print = "Record: " + object;
        try {
            stream.write(print.getBytes());
        } catch (IOException e) {
            throw new InputException(e.getMessage());
        }
    }

    @Override
    public void readAllOption(OutputStream stream) throws InputException, StorageException {
        List<Teacher> list = readAll();
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
            Teacher object = read(Integer.parseInt(args[2]));
            String print;
            object.setName(args[3]);
            if (args.length >= 5) {
                try {
                    object.setChair(getDaoFactory().getChairDao().read(Integer.parseInt(args[4])));
                } catch (NumberFormatException e) {
                    throw new InputException(e.getMessage());
                } catch (SQLException e) {
                    throw new StorageException(e.getMessage());
                }
            }
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
        Teacher object = read(id);
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
