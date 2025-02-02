# Lager- und Bestellsystem

## Beschreibung
Dieses Programm dient zur Verwaltung von Produkten und Bestellungen in einem einfachen Lagerverwaltungssystem. Es ermöglicht das Anlegen, Bearbeiten und Löschen von Produkten sowie das Aufgeben von Bestellungen.

## Funktionen
### Produktverwaltung
- Produkte anlegen
- Produkte bearbeiten
- Produkte löschen
- Alle Produkte anzeigen

### Bestellverwaltung
- Bestellung aufgeben (Bestand wird automatisch reduziert)
- Achtung: 2 Klassen zur verwaltung der DB-Tabellen sind erforderlich, daher auch das `Connection` Objekt in der Main!

## Benutzerführung
Das Programm bietet ein einfaches Menü, in dem der Benutzer die gewünschten Funktionen durch Eingabe der entsprechenden Zahlen auswählen kann.

---

## Datenbanktabellen (Achtung - 2 Tabellen)

### `products`
- `product_id` (int, Primary Key)
- `name` (varchar)
- `price` (float)
- `stock` (int)

### `orders`
- `order_id` (int, Primary Key)
- `product_id` (int, Foreign Key zu products)
- `quantity` (int)

---

## Beispielablauf

### Menü-Ausgabe
```
=== Lager- und Bestellsystem ===  
1. Produkt anlegen  
2. Produkt bearbeiten  
3. Produkt löschen  
4. Alle Produkte anzeigen  
5. Bestellung aufgeben  
6. Beenden  
Bitte wählen Sie eine Option:
```

### Beispiel für Benutzerinteraktionen
- **Produkt anlegen:**
  ```
  Produktname: Schrauben
  Preis: 1.50
  Lagerbestand: 100
  ```

- **Bestellung aufgeben:**
  ```
  Produkt-ID: 1
  Anzahl: 5
  Bestellung erfolgreich. Neuer Bestand: 95
  ```

---

## Anforderungen
- **Benutzereingabe über `Scanner`** für Menü und Daten.
- **Datenbankoperationen mit JDBC:**
    - Produktdaten in die Tabelle `products` einfügen, aktualisieren, löschen und auslesen.
    - Bestellungen in die Tabelle `orders` speichern und den Bestand aktualisieren.
- **Transaktionen:**
    - Bestellungen müssen als Transaktion behandelt werden (Speichern der Bestellung + Bestandsänderung).
- **Fehlerbehandlung:**
    - Fehlermeldungen bei ungültigen Eingaben oder Datenbankfehlern anzeigen.

---

## Beispielcode für das Menü

```java
import java.sql.Connection;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = DatabaseConnection.getConnection(); // Verbindung zur DB herstellen
        ProductManager productManager = new ProductManager(conn);
        OrderManager orderManager = new OrderManager(conn);

        while (true) {
            System.out.println("\n=== Lager- und Bestellsystem ===");
            System.out.println("1. Produkt anlegen");
            System.out.println("2. Produkt bearbeiten");
            System.out.println("3. Produkt löschen");
            System.out.println("4. Alle Produkte anzeigen");
            System.out.println("5. Bestellung aufgeben");
            System.out.println("6. Beenden");
            System.out.print("Bitte wählen Sie eine Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Eingabepuffer leeren

            switch (choice) {
                case 1 -> productManager.createProduct(scanner);
                case 2 -> productManager.updateProduct(scanner);
                case 3 -> productManager.deleteProduct(scanner);
                case 4 -> productManager.listProducts();
                case 5 -> orderManager.placeOrder(scanner);
                case 6 -> {
                    System.out.println("Programm wird beendet.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }
        }
    }
}
