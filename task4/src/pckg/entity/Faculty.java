package pckg.entity;

/**
 * <h1>Faculty</h1>
 * Факультет из таблицы faculty.
 */
public class Faculty extends Record {

    //название
    private String name;

    /**
     * Конструирует новый <code>Faculty</code>
     *
     * @param id неизменяемый идентификатор
     */
    public Faculty(int id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                '}';
    }
}
