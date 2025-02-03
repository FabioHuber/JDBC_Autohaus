public class Autohaus {
    String name;
    int id;

    public Autohaus(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Autohaus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
