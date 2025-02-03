public class Auto {
    private String marke;
    private double preis;
    private int id;
    private int autohaus_id;

    public Auto(String marke, double preis, int id) {
        this.marke = marke;
        this.preis = preis;
        this.id = id;
        this.autohaus_id = autohaus_id;
    }

    public Auto(String marke, double preis) {
        this.marke = marke;
        this.preis = preis;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAutohaus_id() {
        return autohaus_id;
    }

    public void setAutohaus_id(int autohaus_id) {
        this.autohaus_id = autohaus_id;
    }
}
