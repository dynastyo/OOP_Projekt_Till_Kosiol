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
        Fahrzeug fzgTyp;
        System.out.println("""
                ---------------------------------------------------
                                    Fahrzeug anlegen
                ---------------------------------------------------""");
        do {
            fzgTyp = createFzgTyp();
            if (fzgTyp == null) {
                System.out.println("Falsche eingabe! Probiers nochmal!");
            }
        } while (fzgTyp == null);
        marke = addAttribute("Bitte Marke angeben:", String.class);
        modell = addAttribute("Bitte Modell angeben:", String.class);
        farbe = addAttribute("Bitte Farbe angeben:", String.class);
        baujahr = baujahrEingeben("Bitte Baujahr angeben:");
        preis = Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class));
        if (marke.equals("") || modell.equals("") || farbe.equals("")) {
            System.out.println("Eingaben unvollständig!!! DAS FAHRZEUG KONNTE NICHT GESPEICHERT WERDEN!!!");
            hauptMenue();
        }
        anlegenFzg(fzgTyp, marke, modell, farbe, baujahr, preis);
        System.out.println("""
                Fahrzeugtyp angelegt.
                Noch ein Fahrzeug anlegen? (Y/N)""");
        choice = sc.nextLine().toLowerCase();
        if (choice.equals("y")) fzgAnlegen();
        else hauptMenue();
    }

    public static void fzgBearbeiten() {
        for (int i = 1; i <= datenbank.size(); i++) {
            ausgabeFzg(i);
        }
        System.out.println("---------------------------------------------------");
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
                switch (choice) {
                    case "1" -> datenbank.get(index).setMarke(addAttribute("Bitte Marke angeben:", String.class));
                    case "2" -> datenbank.get(index).setModell(addAttribute("Bitte Modell angeben:", String.class));
                    case "3" -> datenbank.get(index).setFarbe(addAttribute("Bitte Farbe angeben:", String.class));
                    case "4" -> datenbank.get(index).setBaujahr(baujahrEingeben("Bitte Baujahr angeben:"));
                    case "5" ->
                            datenbank.get(index).setPreis(Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class)));
                    case "6" -> {
                        datenbank.get(index).setMarke(addAttribute("Bitte Marke angeben:", String.class));
                        datenbank.get(index).setModell(addAttribute("Bitte Modell angeben:", String.class));
                        datenbank.get(index).setFarbe(addAttribute("Bitte Farbe angeben:", String.class));
                        datenbank.get(index).setBaujahr(baujahrEingeben("Bitte Baujahr angeben:"));
                        datenbank.get(index).setPreis(Double.parseDouble(addAttribute("Bitte Preis angeben", Double.class)));
                    }
                    default -> {
                        System.out.println("""
                                Falsche eingabe!
                                1) Nochmal Probieren!
                                2) Zurück zum Hauptmenü""");
                        String choice2 = sc.nextLine();
                        if (choice2.equals("2")) {
                            hauptMenue();
                        }
                        eingabeFalsch = true;
                    }
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
        System.out.println("Noch einmal suchen? j/n");
        String choice2 = sc.nextLine().toLowerCase();
        if (choice2.equals("j")) {
            fzgSuchen();
        }
        hauptMenue();
    }

    public static void fzgSuchenBaujahr() {
        int baujahrMin, baujahrMax, gefundeneMenge = 0;
        baujahrMin = baujahrEingeben("Wie alt darf das Fahrzeug sein?:");
        baujahrMax = baujahrEingeben("Wie neu darf das Fahrzeug sein?");
        for (Fahrzeug fahrzeug : datenbank) {
            if (baujahrMin <= fahrzeug.getBaujahr() && baujahrMax > fahrzeug.getBaujahr()) {
                ausgabeFzg(fahrzeug.getId());
                gefundeneMenge++;
            }
        }
        System.out.println("----------------");
        System.out.println("Die Suche nach Baujahr " + baujahrMin + " bis " + baujahrMax + " ergab " + gefundeneMenge + " Treffer.");
    }

    public static void fzgSuchenPreis() {
        double preisMin, preisMax;
        int gefundeneMenge = 0;
        preisMin = Double.parseDouble(addAttribute("Bitte minimalen Preis angeben", Double.class));
        preisMax = Double.parseDouble(addAttribute("Bitte maximalen Preis angeben", Double.class));
        for (Fahrzeug fahrzeug : datenbank) {
            if (preisMin <= fahrzeug.getPreis() && preisMax > fahrzeug.getPreis()) {
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
        int index = findeIndex(id);
        if (index == -1) {
            System.out.println("Id nicht gefunden.");
        } else {
            ausgabeFzg(id);
        }
    }

    public static void sucheTyp() {
        Class<?> fzgTyp = sucheFzgTyp();
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
        //alle fahrzeuge anzeigen
        for (int i = 1; i <= datenbank.size(); i++) {
            ausgabeFzg(i);
        }
        System.out.println("---------------------------------------------------");
        int id = Integer.parseInt(addAttribute("ID des zu löschenden Fahrzeugs angeben:", Integer.class));
        int index = findeIndex(id);
        if (index == -1) {
            System.out.println("Id nicht gefunden. Gehe zurück zum Hauptmenu");
        } else {
            System.out.println("Soll das Fahrzeug wirklich gelöscht werden? j/n");
            String choice = sc.nextLine().toLowerCase();
            if (choice.equals("j")) {
                datenbank.remove(index);
                System.out.println("Fahrzeug mit ID: " + id + " wurde gelöscht.");
            }
        }
        hauptMenue();
    }

    public static void beenden() {
        System.out.println("Programm wirklich beenden? j/n");
        String choice = sc.nextLine().toLowerCase();
        if (choice.equals("j")) {
            System.out.println("""
                    ---------------------------------------------------
                                   Programm wird beendet!
                    ---------------------------------------------------""");
            sc.close();
        } else {
            hauptMenue();
        }
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
            int fzgTyp = rand.nextInt(4);
            Fahrzeug fahrzeug = switch (fzgTyp) {
                case 0 -> new Pkw();
                case 1 -> new Lkw();
                case 2 -> new Boot();
                case 3 -> new Motorrad();
                default -> throw new IllegalStateException("Unexpected value: " + fzgTyp);
            };
            anlegenFzg(fahrzeug, marken[marke], modelle[modell], farben[farbe], baujahr, preis);
        }
    }

    public static Class<?> sucheFzgTyp() {
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
                sucheFzgTyp();
                break;
        }
        return Fahrzeug.class;
    }

    public static Fahrzeug createFzgTyp() {
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
        return switch (choice) {
            case "1" -> new Pkw();
            case "2" -> new Lkw();
            case "3" -> new Boot();
            case "4" -> new Motorrad();
            default -> null;
        };
    }


    public static void anlegenFzg(Fahrzeug typ, String marke, String modell, String farbe, int baujahr, double preis) {
        typ.setMarke(marke);
        typ.setModell(modell);
        typ.setFarbe(farbe);
        typ.setBaujahr(baujahr);
        typ.setPreis(preis);
        datenbank.add(typ);
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
            result = true;
        }
        checkscan.close();
        return result;
    }

    public static void ausgabeFzg(int id) {
        int index = findeIndex(id);
        if (index != -1) {
            System.out.printf("ID: %-3d Typ: %-10s Marke: %-10s Modell: %-10s Farbe: %-10s Baujahr: %-6d Preis: %10.2f%n", datenbank.get(index).getId(), datenbank.get(index).getClass().getName(), datenbank.get(index).getMarke(), datenbank.get(index).getModell(), datenbank.get(index).getFarbe(), datenbank.get(index).getBaujahr(), datenbank.get(index).getPreis());
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