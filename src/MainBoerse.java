import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//baujahr
public class MainBoerse {

    static ArrayList<Fahrzeug> datenbank = new ArrayList<Fahrzeug>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        hauptMenue();
    }

    public static void hauptMenue(){
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("Fahrzeugbörse             Hauptmenü                     von: [Ihr Vor – und Nachname]");
        System.out.println("1) Fahrzeug anlegen");
        System.out.println("2) Fahrzeug bearbeiten");
        System.out.println("3) Fahrzeug suchen");
        System.out.println("4) Fahrzeug löschen");
        System.out.println("0) Börse beenden");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Bitte wählen:");
        String choice = sc.nextLine();
        switch (choice) {
//            case "1" -> menuAddCar();
//            case "2" -> menuPrintCarpark();
//            case "3" -> menuSearchCar();
//            case "4" -> menuDeleteCar();
//            case "5" -> menuSortCarpark();
            default -> {
                System.out.println("Falsche eingabe! Probiers nochmal!");
                hauptMenue();
            }
        }

//        datenbank.add(new Boot("1", "asd", "sdasd",23132131231232L , 3 ));
        System.out.println(datenbank.get(1).getBaujahr());
    }


    public void dummyDaten(int amountOfCars) {
        String[] marken = new String[]{"BMW", "Audi", "VW", "Opel", "Dacia", "Subaru", "Suzuki"};
        String[] modelle = new String[]{"500", "A7", "Corsa", "3", "Tiguan", "F240", "Diabolo"};
        String[] farben = new String[]{"Rot", "Gruen", "Blau", "Silber", "Regenbogen", "Glitzer", "Fantasie"};
        String[] klassen = new String[]{"Pkw", "Lkw"};
//        Class[] typen = new Class[]{Pkw, Lkw, Boot.class, Motorrad.class};

        Random rand = new Random();
        for (int i = 0; i < amountOfCars; i++) {
            int marke = rand.nextInt(marken.length);
            int modell = rand.nextInt(modelle.length);
            int farbe = rand.nextInt(farben.length);
            int klasse = rand.nextInt(klassen.length);
            int typ = rand.nextInt(typen.length);
            double preis = Math.round(rand.nextDouble(500, 100000) * 100) / 100.00;
            int baujahr = rand.nextInt(80, 270);

            datenbank.add(new typen[typ]("1", "asd", "sdasd",23132131231232L , 3 ));
            Class.forName(klassen[klasse]());
        }
    }

}

