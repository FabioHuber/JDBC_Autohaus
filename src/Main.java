import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        try (Database db = new Database()) {

            while (!exit) {
                System.out.println("\n=== Autohausverwaltung ===");
                System.out.println("0. Datenbank aufsetzen");
                System.out.println("1. Autohaus erstellen");
                System.out.println("2. Autohaus entfernen");
                System.out.println("3. Autohaus updated");
                System.out.println("4. Auto erstellen");
                System.out.println("5. Auto entfernen");
                System.out.println("6. Auto updated");
                System.out.println("7. Auto zu Autohaus transferieren");
                System.out.println("8. Beenden");
                System.out.print("Bitte wählen Sie eine Option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Eingabepuffer leeren

                switch (choice) {
                    case 0:
                        db.setupDatabase();
                        break;

                    case 1:
                        createAutohaus(scanner, db);
                        break;

                    case 2:
                        deleteAutohaus(scanner, db);
                        break;

                    case 3:
                        updateAutohaus(scanner, db);
                        break;

                    case 4:
                        createAuto(scanner, db);
                        break;

                    case 5:
                        deleteAuto(scanner, db);
                        break;

                    case 6:
                        updateAuto(scanner, db);
                        break;

                    case 7:
                        transferAuto(scanner, db);
                        break;

                    case 8:
                        System.out.println("Programm wird beendet.");
                        scanner.close();
                        exit = true;
                        break;

                    default:
                        System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
                        break;
                }
            }
        } catch (SQLException e) {
            System.out.println("DATENBANKFEHLER!!!!");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void createAutohaus(Scanner sc, Database db) throws SQLException {
        System.out.println("Wie möchten sie ihr neues Autohaus nennen?");
        System.out.print("Name: ");
        String name = sc.nextLine();

        if(db.createAutohaus(new Autohaus(name)) > 0){
            System.out.println("Autohaus erfolgreich hinzugefügt!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }
    }

    private static void deleteAutohaus(Scanner sc, Database db) throws SQLException{
        System.out.println("Welches Autohaus möchten Sie entfernen?");
        System.out.print("ID: ");
        int id = sc.nextInt();

        db.removeAutohaus(id);
        System.out.println("EPPA SUPPA GEIL");
    }

    private static void updateAutohaus(Scanner sc, Database db) throws SQLException{
        System.out.println("Welches Autohaus möchten Sie bearbeiten?");
        System.out.print("ID: ");
        int id = sc.nextInt();
        System.out.print("neuer Name: ");
        String name = sc.nextLine();

        if(db.updateAutohaus(new Autohaus(name, id)) > 0){
            System.out.println("Autohaus erfolgreich aktualisiert!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }

    }

    private static void createAuto(Scanner sc, Database db) throws SQLException {
        System.out.print("Marke des Autos: ");
        String marke = sc.nextLine();
        System.out.print("Preis des Autos: ");
        int price = sc.nextInt();

        if(db.createAuto(new Auto(marke, price)) > 0){
            System.out.println("Auto erfolgreich hinzugefügt!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }
    }

    private static void deleteAuto(Scanner sc, Database db) throws SQLException{
        System.out.println("Welches Auto möchten Sie entfernen?");
        System.out.print("ID: ");
        int id = sc.nextInt();

        if(db.removeAuto(id) > 0){
            System.out.println("Auto erfolgreich entfernt!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }
    }

    private static void updateAuto(Scanner sc, Database db) throws SQLException{
        System.out.println("Welches Auto möchten Sie bearbeiten?");
        System.out.print("ID: ");
        int id = sc.nextInt();
        System.out.print("neue Marke: ");
        String marke = sc.nextLine();
        System.out.print("neuer Preis: ");
        int price = sc.nextInt();

        if(db.updateAuto(new Auto(marke, price, id)) > 0){
            System.out.println("Auto erfolgreich aktualisiert!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }
    }

    private static void transferAuto(Scanner sc, Database db) throws SQLException{
        System.out.println("Welches Auto möchten Sie transferieren?");
        System.out.print("ID: ");
        int id = sc.nextInt();
        System.out.println("In welches Autohaus möchten Sie das Auto transferieren?");
        System.out.print("ID: ");
        int autohausId = sc.nextInt();

        if(db.transferAuto(id, autohausId) > 0){
            System.out.println("Auto erfolgreich transferiert!");
        }else {
            System.out.println("FEHLER ZEFIX");
        }
    }
}