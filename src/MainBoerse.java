import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//baujahr
public class MainBoerse {

    static ArrayList<Fahrzeug> datenbank = new ArrayList<Fahrzeug>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        dummyDaten(150);

        for (Fahrzeug fahrzeug: datenbank
             ) {
            System.out.println(fahrzeug.getClass().getName());
        }

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
        System.out.println("""
                ------------------------------------------------------
                Fahrzeugbörse        Hauptmenü        von: Till Kosiol
                
                """);
        String choice = sc.nextLine();
        switch (choice) {
//            case "1" -> anlegen();
//            case "2" -> bearbeiten();
//            case "3" -> suchen();
//            case "4" -> löschen();
//            case "0" -> beenden();
            default -> {
                System.out.println("Falsche eingabe! Probiers nochmal!");
                hauptMenue();
            }
        }
    }
    
    public static void anlegen(){
        
    }


    public static void dummyDaten(int amountOfCars) {
        String[] marken = new String[]{"BMW", "Audi", "VW", "Opel", "Dacia", "Subaru", "Suzuki"};
        String[] modelle = new String[]{"500", "A7", "Corsa", "3", "Tiguan", "F240", "Diabolo"};
        String[] farben = new String[]{"Rot", "Gruen", "Blau", "Silber", "Regenbogen", "Glitzer", "Fantasie"};
       
        Random rand = new Random();
        for (int i = 0; i < amountOfCars; i++) {
            int marke = rand.nextInt(marken.length);
            int modell = rand.nextInt(modelle.length);
            int farbe = rand.nextInt(farben.length);
            double preis = Math.round(rand.nextDouble(500, 100000) * 100) / 100.00;
            int baujahr = rand.nextInt(1900, 2022);
            
            int randKlasse = rand.nextInt(4);
            switch (randKlasse) {
                case 0 -> datenbank.add(new Pkw(marken[marke], modelle[modell], farben[farbe], baujahr, preis));
                case 1 -> datenbank.add(new Lkw(marken[marke], modelle[modell], farben[farbe], baujahr, preis));
                case 2 -> datenbank.add(new Boot(marken[marke], modelle[modell], farben[farbe], baujahr, preis));
                case 3 -> datenbank.add(new Motorrad(marken[marke], modelle[modell], farben[farbe], baujahr, preis));
            }
        }
    }
}

