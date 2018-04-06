package pckg.entity;

/**
 * <h1>Record</h1>
 * Класс любого объекта в бд.
 */
public abstract class Record {

    //идентификатор объекта
    private int id;

    public Record(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
