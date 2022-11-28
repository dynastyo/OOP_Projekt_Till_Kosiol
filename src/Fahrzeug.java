import java.util.concurrent.atomic.AtomicInteger;

public class Fahrzeug {

    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Integer id;
    private String marke, modell, farbe;
    private int baujahr;
    private double preis;

    public Fahrzeug(String marke, String modell, String farbe, int baujahr, double preis) {
        this.marke = marke;
        this.modell = modell;
        this.farbe = farbe;
        this.baujahr = baujahr;
        this.preis = preis;
        id = idGenerator.getAndIncrement();
    }
    public int getId(){
        return id;
    }
    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getFarbe() {
        return farbe;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }
}

