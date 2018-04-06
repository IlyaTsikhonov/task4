package pckg;

import pckg.controller.ChairController;
import pckg.controller.Controller;
import pckg.controller.FacultyController;
import pckg.controller.TeacherController;
import pckg.exception.InputException;
import pckg.exception.StorageException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Terminal</h1>
 * Класс, предоставляющий польковательский интерфейс(консоль) для работы с бд.
 */
public class Terminal {

    //поток для ввода данных
    private InputStream in;

    //поток для вывода данных
    private OutputStream out;

    //контроллер для работы с факультетами
    private FacultyController facultyController;

    //контроллер для работы с кафедрами
    private ChairController chairController;

    //контроллер для работы с преподавателями
    private TeacherController teacherController;

    /**
     * Конструирует новый <code>Terminal</code>
     *
     * @param  in InputStream для вввода информации
     * @param  out OutputStream для вывода информации
     */
    public Terminal(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        facultyController = new FacultyController(out);
        chairController = new ChairController(out);
        teacherController = new TeacherController(out);
    }

    /**
     * Начинает работу
     */
    public void start() {
        try {
            Scanner scanner = new Scanner(in);
            String[] args;
            String line;
            out.write(("Help " + Controller.HELP + "\n").getBytes());
            while (true) {
                line = scanner.nextLine();
                if (line.trim().equals(Controller.QUIT)) {
                    return;
                }
                if (line.trim().equals(Controller.HELP)) {
                    out.write((getHelpMessage() + "\n").getBytes());
                    continue;
                }

                args = getArgs(line);
                if (args.length >= 2) {
                    switch (args[1]) {
                        case "faculty":
                            parseCommand(facultyController, args);
                            break;
                        case "chair":
                            parseCommand(chairController, args);
                            break;
                        case "teacher":
                            parseCommand(teacherController, args);
                            break;
                        default:
                            out.write(("Wrong second parameter = '" + args[1] + "' (required faculty, chair or teacher).\n").getBytes());
                    }
                } else {
                    out.write(("Wrong parameters count = " + args.length + " (required 2 or more).\n").getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получает сообщение о помощи.
     *
     * @return сообщение о помощи
     */
    private String getHelpMessage() {
        return "To create: create (tableName)\n" +
                "To read: read (tableName) [id]\n" +
                "To read all: readall (tableName)\n" +
                "To update: update (tableName) [id] [requiredParam1] [requiredParam2] ...\n" +
                "To delete: delete (tableName) [id]\n" +
                "To quit: quit";
    }

    /**
     * Получает унифицированный список аргументов.
     *
     * @param line введенная пользователем строка
     *
     * @return список аргументов
     */
    private String[] getArgs(String line) {
        ArrayList<String> args = new ArrayList<>();
        String[] argsArray = line.trim().split(" ");
        for (String arg : argsArray) {
            if (arg.length() > 0) {
                args.add(arg.toLowerCase());
            }
        }
        return args.toArray(new String[0]);
    }

    /**
     * Передает команду контроллеру.
     * Выводит результат в поток вывода.
     *
     * @param controller обработчик команды
     * @param args аргументы команды
     */
    private void parseCommand(Controller controller, String[] args) throws IOException {
        try {
            controller.executeCommand(args, out);
            out.write("\n".getBytes());
        } catch (StorageException | InputException e) {
            try {
                out.write((e.getMessage() + "\n").getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
