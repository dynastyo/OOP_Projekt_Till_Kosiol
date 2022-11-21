import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//baujahr
public class MainBoerse {

    static ArrayList<Fahrzeug> datenbank = new ArrayList<Fahrzeug>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        dummyDaten(30);

        for (int i = 1; i <= datenbank.size(); i++) {
            ausgabeFzg(i);
        }

        hauptMenue();
    }

    public static void hauptMenue(){

        System.out.println("""
                ---------------------------------------------------
                Fahrzeugbörse       Hauptmenü     von: Till Kosiol
                1) Fahrzeug anlegen
                2) Fahrzeug bearbeiten
                3) Fahrzeug suchen
                4) Fahrzeug löschen
                0) Börse beenden
                ---------------------------------------------------
                Bitte wählen:""");
        String choice = sc.nextLine();
        switch (choice) {
            case "1" -> fzgAnlegen();
            case "2" -> fzgBearbeiten();
            case "3" -> fzgSuchen();
            case "4" -> fzgLoeschen();
            case "0" -> beenden();
            default -> {
                System.out.println("Falsche eingabe! Probiers nochmal!");
                hauptMenue();
            }
        }
    }

    public static void fzgSuchen() {
        System.out.println("""
                ---------------------------------------------------
                Nach welchem Attribut möchtest du suchen?
                1) ID-Nr.
                2) Fahrzeugtyp
                3) Marke
                4) Modell
                5) Farbe
                6) Baujahr
                7) Preis
                ---------------------------------------------------
                Bitte wählen:""");
        String choice = sc.nextLine();
        switch (choice) {
            case "1" -> sucheID();
            case "2" -> sucheTyp();
//            case "3" -> sucheMarke();
//            case "4" -> sucheModell();
//            case "5" -> SucheFarbe();
//            case "6" -> sucheBaujahr();
//            case "7" -> suchePreis();
            default -> {
                System.out.println("Falsche eingabe! Probiers nochmal!");
                fzgSuchen();
            }
        }
        hauptMenue();
    }
    public static void sucheID() {
        int id = Integer.parseInt(addAttribute("Id angeben:", Integer.class));
        ausgabeFzg(id);
        hauptMenue();
    }
    public static void sucheTyp() {
        Class<?> fzgTyp = null;
        int gefundeneMenge = 0;
        boolean eingabeFalsch;
        do {
            eingabeFalsch = false;
            System.out.println("""
                    Fahrzeugtyp Wählen:
                    1) Pkw
                    2) Lkw
                    3) Boot
                    4) Motorboot
                    ---------------------------------------------------
                    Bitte wählen:""");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> fzgTyp = Pkw.class;
                case "2" -> fzgTyp = Lkw.class;
                case "3" -> fzgTyp = Boot.class;
                case "4" -> fzgTyp = Motorrad.class;
                default -> {
                    System.out.println("Falsche eingabe! Probiers nochmal!");
                    eingabeFalsch = true;
                }
            }
        } while (eingabeFalsch);
        for (Fahrzeug fahrzeug : datenbank) {
            if(fahrzeug.getClass() == fzgTyp){
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach Farzeugtyp " + fzgTyp.getSimpleName() + " ergab " + gefundeneMenge + " Treffer.");
        hauptMenue();
    }

    public static void fzgBearbeiten(){
        int id = Integer.parseInt(addAttribute("ID des zu bearbeitenden Fahrzeugs angeben:", Integer.class));
        int index = findeIndex(id);
        if (index == -1) {
            System.out.println("Id nicht gefunden.");
        } else {
            boolean eingabeFalsch;
            do {
                eingabeFalsch = false;
                System.out.println("""
                    Was möchtest du bearbeiten?
                    1) Marke
                    2) Modell
                    3) Farbe
                    4) Baujahr
                    5) Preis
                    ---------------------------------------------------
                    Bitte wählen:""");
                String choice = sc.nextLine();
                if ("1".equals(choice)) {
                    datenbank.get(index).setMarke(addAttribute("Bitte Marke angeben:", String.class));
                } else if ("2".equals(choice)) {
                    datenbank.get(index).setModell(addAttribute("Bitte Modell angeben:", String.class));
                } else if ("3".equals(choice)) {
                    datenbank.get(index).setFarbe(addAttribute("Bitte Farbe angeben:", String.class));
                } else if ("4".equals(choice)) {
                    int baujahr = Integer.parseInt(addAttribute("Bitte Baujahr angeben:", Integer.class));
                    while (4 != (int)(Math.log10(baujahr)+1) ){
                        System.out.println("Das Baujahr muss 4 Stellen lang sein und größer als 0");
                        baujahr = Integer.parseInt(addAttribute("Bitte Baujahr angeben:", Integer.class));
                    }
                    datenbank.get(index).setBaujahr(baujahr);
                } else if ("5".equals(choice)) {
                    datenbank.get(index).setPreis(Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class)));
                } else {
                    System.out.println("Falsche eingabe! Probiers nochmal!");
                    eingabeFalsch = true;
                }
            } while (eingabeFalsch);
            System.out.println("Das Bearbeiten war erfolgreich!");
        }
        hauptMenue();
    }

    public static void fzgLoeschen() {
        int id = Integer.parseInt(addAttribute("ID des zu löschenden Fahrzeugs angeben:", Integer.class));
        int index = findeIndex(id);
        if (index == -1) {
            System.out.println("Id nicht gefunden.");
        } else {
            datenbank.remove(index);
            System.out.println("Fahrzeug mit ID: " + id + " wurde gelöscht.");
        }
        hauptMenue();
    }



    public static void beenden() {
        System.out.println("""
                ---------------------------------------------------
                               Programm wird beendet!
                ---------------------------------------------------""");
        sc.close();
    }

    public static void fzgAnlegen(){
        int fzgTyp = 0;
        boolean eingabeFalsch;
        System.out.println("""
                ---------------------------------------------------
                                    Fahrzeug anlegen
                ---------------------------------------------------""");
        do {
            eingabeFalsch = false;
            System.out.println("""
                    Fahrzeugtyp Wählen:
                    1) Pkw
                    2) Lkw
                    3) Boot
                    4) Motorboot
                    ---------------------------------------------------
                    Bitte wählen:""");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> fzgTyp = 1;
                case "2" -> fzgTyp = 2;
                case "3" -> fzgTyp = 3;
                case "4" -> fzgTyp = 4;
                default -> {
                    System.out.println("Falsche eingabe! Probiers nochmal!");
                    eingabeFalsch = true;
                }
            }
        } while (eingabeFalsch);

        String marke = addAttribute("Bitte Marke angeben:", String.class);
        String modell = addAttribute("Bitte Modell angeben:", String.class);
        String farbe = addAttribute("Bitte Farbe angeben:", String.class);
        int baujahr = Integer.parseInt(addAttribute("Bitte Baujahr angeben:", Integer.class));
        while (4 != (int)(Math.log10(baujahr)+1) ){
            System.out.println("Das Baujahr muss 4 Stellen lang sein und größer als 0");
            baujahr = Integer.parseInt(addAttribute("Bitte Baujahr angeben:", Integer.class));
        }
        double preis = Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class));
        anlegenFzg(fzgTyp, marke, modell, farbe, baujahr, preis);
        hauptMenue();
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
            int fzgTyp = rand.nextInt(1,5);
            System.out.println("hallo");
            anlegenFzg(fzgTyp, marken[marke], modelle[modell], farben[farbe], baujahr, preis);
        }
    }

    public static void anlegenFzg(int typ, String marke, String modell, String farbe, int baujahr, double preis){
        switch (typ) {
            case 1 -> datenbank.add(new Pkw(marke, modell, farbe, baujahr, preis));
            case 2 -> datenbank.add(new Lkw(marke, modell, farbe, baujahr, preis));
            case 3 -> datenbank.add(new Boot(marke, modell, farbe, baujahr, preis));
            case 4 -> datenbank.add(new Motorrad(marke, modell, farbe, baujahr, preis));
        }
    }


    public static String addAttribute(String soutBegin, Class<?> type) {
        System.out.println(soutBegin);
        String value = sc.nextLine();
        while (!checkDatatype(type, value)) {
            System.out.println("Eingabe muss ein " + type.getSimpleName() + " sein. Versuche es noch einmal!");
            value = sc.nextLine();
        }
        return value;
    }

    public static boolean checkDatatype(Class<?> type, String value) {
        Scanner checkscan = new Scanner(value);
        boolean result = false;
        if (type.equals(Integer.class)) {
            result = checkscan.hasNextInt();
        } else if (type.equals(Boolean.class)) {
            result = checkscan.hasNextBoolean();
        } else if (type.equals(Double.class)) {
            result = checkscan.hasNextDouble();
        } else if (type.equals(String.class)) {
            result = !value.isEmpty();
        }
        checkscan.close();
        return result;
    }

    public static void ausgabeFzg(int id) {
        int index = findeIndex(id);
        if (index == -1) {
            System.out.println("Id nicht gefunden");
        } else {
            System.out.println("---------------------------------------------------");
            System.out.println("Id Nummer:\t\t" + datenbank.get(index).getId());
            System.out.println("----------------");
            System.out.println("Fahrzeugtyp:\t" + datenbank.get(index).getClass().getName());
            System.out.println("Marke:\t\t\t" + datenbank.get(index).getMarke());
            System.out.println("Modell:\t\t\t" + datenbank.get(index).getModell());
            System.out.println("Farbe:\t\t\t" + datenbank.get(index).getFarbe());
            System.out.println("Baujahr:\t\t" + datenbank.get(index).getBaujahr());
            System.out.println("Preis:\t\t\t" + datenbank.get(index).getPreis());
        }
    }

    public static int findeIndex(int id){
        int index = 0;
        for (Fahrzeug fahrzeug : datenbank) {
            if(fahrzeug.getId() == id){
                return index;
            }
            index++;
        }
        return -1;
    }
}