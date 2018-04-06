package pckg.entity;

/**
 * <h1>Teacher</h1>
 * Преподаватель из таблицы teacher.
 */
public class Teacher extends Record {

    //имя
    private String name;

    /**
     * ссылка на кафедру
     * может быть NULL
     * SET NULL при изменении или удалении
     */
    private Chair chair;

    /**
     * Конструирует новый <code>Teacher</code>
     *
     * @param id неизменяемый идентификатор
     */
    public Teacher(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Chair getChair() {
        return chair;
    }

    public void setChair(Chair chair) {
        this.chair = chair;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", chair=" + chair +
                '}';
    }
}
