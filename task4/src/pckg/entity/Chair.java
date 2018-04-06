package pckg.entity;

/**
 * <h1>Chair</h1>
 * Кафедра из таблицы chair.
 */
public class Chair extends Record {

    //название
    private String name;

    /**
     * ссылка на факультет
     * может быть NULL
     * SET NULL при изменении или удалении
     */
    private Faculty faculty;

    /**
     * Конструирует новый <code>Chair</code>
     *
     * @param id неизменяемый идентификатор
     */
    public Chair(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Chair{" +
                "name='" + name + '\'' +
                ", faculty=" + faculty +
                '}';
    }
}
