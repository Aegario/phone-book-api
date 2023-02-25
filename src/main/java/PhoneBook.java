import java.sql.Connection;
import java.time.LocalDate;
import java.sql.*;

public class PhoneBook {
    private static String DB_URL = "jdbc:postgresql://localhost:5432/dbname";
    private static Connection conn;
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        PhoneBookEntry entry = new PhoneBookEntry(
            "79992994242",
            "Alex",
            "Gruve",
            LocalDate.now()
        );

       phoneBook.addEntry(entry);
    }

    public PhoneBook() {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEntry(PhoneBookEntry entry) {

    }

    public void deleteEntry(String name) {
        System.out.println("String parameter");
    }

    public void deleteEntry(boolean name) {
        System.out.println("Boolean parameter");
    }

    public void updateEntry(int id) {

    }

    // public PhoneBookEntry getEntry(int id) {
    // }
}
