import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainBoerse {
    static ArrayList<Fahrzeug> datenbank = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        dummyDaten(30);
        hauptMenue();
    }
    public static void hauptMenue() {
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

    public static void fzgAnlegen() {
        int baujahr;
        double preis;
        String choice, marke, modell, farbe;
        Class<?> fzgTyp;
        System.out.println("""
                ---------------------------------------------------
                                    Fahrzeug anlegen
                ---------------------------------------------------""");
        fzgTyp = waehleFzgTyp();
        marke = addAttribute("Bitte Marke angeben:", String.class);
        modell = addAttribute("Bitte Modell angeben:", String.class);
        farbe = addAttribute("Bitte Farbe angeben:", String.class);
        baujahr = baujahrEingeben("Bitte Baujahr angeben:");
        preis = Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class));
        anlegenFzg(fzgTyp, marke, modell, farbe, baujahr, preis);
        System.out.println("""
                Fahrzeugtyp angelegt.
                Noch ein Fahrzeug anlegen? (Y/N)""");
        choice = sc.nextLine().toLowerCase();
        if (choice.equals("y")) fzgAnlegen();
        else hauptMenue();
    }

    public static void fzgBearbeiten() {
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
                        6) Alles aendern
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
                    datenbank.get(index).setBaujahr(baujahrEingeben("Bitte Baujahr angeben:"));
                } else if ("5".equals(choice)) {
                    datenbank.get(index).setPreis(Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class)));
                } else if ("6".equals(choice)) {
                    datenbank.get(index).setMarke(addAttribute("Bitte Marke angeben:", String.class));
                    datenbank.get(index).setModell(addAttribute("Bitte Modell angeben:", String.class));
                    datenbank.get(index).setFarbe(addAttribute("Bitte Farbe angeben:", String.class));
                    datenbank.get(index).setBaujahr(baujahrEingeben("Bitte Baujahr angeben:"));
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
            case "3" -> fzgSuchenString("Marke");
            case "4" -> fzgSuchenString("Modell");
            case "5" -> fzgSuchenString("Farbe");
            case "6" -> fzgSuchenBaujahr();
            case "7" -> fzgSuchenPreis();
            default -> {
                System.out.println("Falsche eingabe! Probiers nochmal!");
                fzgSuchen();
            }
        }
        hauptMenue();
    }
    public static void fzgSuchenBaujahr(){
        int baujahrMin, baujahrMax, gefundeneMenge = 0;
        baujahrMin = baujahrEingeben("Wie alt darf das Fahrzeug sein?:");
        baujahrMax = baujahrEingeben("Wie neu darf das Fahrzeug sein?");
        for (Fahrzeug fahrzeug : datenbank){
            if(baujahrMin <= fahrzeug.getBaujahr() && baujahrMax > fahrzeug.getBaujahr()){
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach Baujahr " + baujahrMin + " bis " + baujahrMax + " ergab " + gefundeneMenge + " Treffer.");
    }
    public static void fzgSuchenPreis(){
        double preisMin, preisMax;
        int gefundeneMenge = 0;
        preisMin = Double.parseDouble(addAttribute("Bitte minimalen Preis angeben", Double.class));
        preisMax = Double.parseDouble(addAttribute("Bitte maximalen Preis angeben", Double.class));
        for (Fahrzeug fahrzeug : datenbank){
            if(preisMin <= fahrzeug.getPreis() && preisMax > fahrzeug.getPreis()){
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach Preis " + preisMin + " bis " + preisMax + " ergab " + gefundeneMenge + " Treffer.");
    }
    public static void fzgSuchenString(String attribut) {
        int gefundeneMenge = 0;
        String wert = addAttribute(attribut + "angeben:", String.class);
        for (Fahrzeug fahrzeug : datenbank) {
            if (attribut.equals("Marke") && fahrzeug.getMarke().toLowerCase().contains(wert.toLowerCase())) {
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
            if (attribut.equals("Modell") && fahrzeug.getModell().toLowerCase().contains(wert.toLowerCase())) {
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
            if (attribut.equals("Farbe") && fahrzeug.getFarbe().toLowerCase().contains(wert.toLowerCase())) {
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach " + attribut + " " + wert + " ergab " + gefundeneMenge + " Treffer.");
    }

    public static void sucheID() {
        int id = Integer.parseInt(addAttribute("Id angeben:", Integer.class));
        ausgabeFzg(id);
    }

    public static void sucheTyp() {
        Class<?> fzgTyp = waehleFzgTyp();
        int gefundeneMenge = 0;
        for (Fahrzeug fahrzeug : datenbank) {
            if (fahrzeug.getClass() == fzgTyp) {
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach Farzeugtyp " + fzgTyp.getSimpleName() + " ergab " + gefundeneMenge + " Treffer.");
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

    public static void dummyDaten(int amountOfCars) {
        String[] marken = new String[]{"BMW", "Audi", "VW", "Opel", "Dacia", "Subaru", "Suzuki"};
        String[] modelle = new String[]{"500", "A7", "Corsa", "3", "Tiguan", "F240", "Diabolo"};
        String[] farben = new String[]{"Rot", "Gruen", "Blau", "Silber", "Regenbogen", "Glitzer", "Fantasie"};
        Class<?>[] fzgTypen = new Class<?>[]{Pkw.class, Lkw.class, Boot.class, Motorrad.class};
        Random rand = new Random();
        for (int i = 0; i < amountOfCars; i++) {
            int marke = rand.nextInt(marken.length);
            int modell = rand.nextInt(modelle.length);
            int farbe = rand.nextInt(farben.length);
            double preis = Math.round(rand.nextDouble(500, 100000) * 100) / 100.00;
            int baujahr = rand.nextInt(1900, 2022);
            int fzgTyp = rand.nextInt(fzgTypen.length);
            anlegenFzg(fzgTypen[fzgTyp], marken[marke], modelle[modell], farben[farbe], baujahr, preis);
        }
    }

    public static Class<?> waehleFzgTyp() {
        System.out.println("""
                ---------------------------------------------------
                Fahrzeugtyp Wählen:
                1) Pkw
                2) Lkw
                3) Boot
                4) Motorboot
                ---------------------------------------------------
                Bitte wählen:""");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                return Pkw.class;
            case "2":
                return Lkw.class;
            case "3":
                return Boot.class;
            case "4":
                return Motorrad.class;
            default:
                System.out.println("Falsche eingabe! Probiers nochmal!");
                waehleFzgTyp();
                break;
        }
        return Fahrzeug.class;
    }


    public static void anlegenFzg(Class<?> typ, String marke, String modell, String farbe, int baujahr, double preis) {
        if (Pkw.class.equals(typ)) {
            datenbank.add(new Pkw(marke, modell, farbe, baujahr, preis));
        } else if (Lkw.class.equals(typ)) {
            datenbank.add(new Lkw(marke, modell, farbe, baujahr, preis));
        } else if (Boot.class.equals(typ)) {
            datenbank.add(new Boot(marke, modell, farbe, baujahr, preis));
        } else if (Motorrad.class.equals(typ)) {
            datenbank.add(new Motorrad(marke, modell, farbe, baujahr, preis));
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

    public static int findeIndex(int id) {
        int index = 0;
        for (Fahrzeug fahrzeug : datenbank) {
            if (fahrzeug.getId() == id) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static int baujahrEingeben(String sout) {
        int baujahr = Integer.parseInt(addAttribute(sout, Integer.class));
        while (4 != (int) (Math.log10(baujahr) + 1)) {
            System.out.println("Das Baujahr muss 4 Stellen lang sein und größer als 0");
            baujahr = Integer.parseInt(addAttribute(sout, Integer.class));
        }
        return baujahr;
    }
}