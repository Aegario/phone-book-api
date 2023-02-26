import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.*;

public class PhoneBook {
    private static String DB_URL = "jdbc:postgresql://localhost:5432/phone_book";
    private static Connection conn;

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        PhoneBookEntry entry = new PhoneBookEntry(
        "95182815182",
            "Alex",
            "Groove",
            LocalDate.of(2000, 12, 18),
            LocalDate.now()
        );

        phoneBook.addEntry(entry);
        // phoneBook.deleteEntry("333");
        // phoneBook.updateLastName(16, "Smug");
        // phoneBook.updateBirthdate(16, LocalDate.of(2020, 9, 1));
        // System.out.println(phoneBook.getEntry(16).firstName);

        phoneBook.closeConnection();
    }

    public PhoneBook() {
        try {
            DataSource dataSource = createDataSource();
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(DB_URL);
        return dataSource;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new entry
     * @param entry
     */
    public void addEntry(PhoneBookEntry entry) {
        try {
            PreparedStatement insertStatement = conn.prepareStatement(
                "insert into phone_book values (default, ?, ?, default, ?, ?)"
            );

            insertStatement.setString(1, entry.firstName);
            insertStatement.setString(2, entry.lastName);
            insertStatement.setDate(3, Date.valueOf(entry.birthdate));
            insertStatement.setString(4, entry.phoneNumber);

            int rowsInserted = insertStatement.executeUpdate();
            System.out.println("Number of inserted rows: " + rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes entries by identifier value
     * @param value accepts first name, last name, full name and phone number.
     */
    public void deleteEntry(String value) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement(
                "delete from phone_book " +
                        "where first_name = ? " +
                        "or last_name = ? " +
                        "or concat(first_name, ' ', last_name) = ? " +
                        "or phone_number = ?"
            );

            deleteStatement.setString(1, value);
            deleteStatement.setString(2, value);
            deleteStatement.setString(3, value);
            deleteStatement.setString(4, value);

            int rowsDeleted = deleteStatement.executeUpdate();
            System.out.println("Number of deleted rows: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes entry by id
     * @param id id of the entry.
     */
    public void deleteEntry(int id) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement(
                "delete from phone_book where id = ?"
            );

            deleteStatement.setInt(1, id);

            int rowsDeleted = deleteStatement.executeUpdate();
            System.out.println("Number of deleted rows: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates phoneNumber of the entry with a given id
     * @param id id of the entry
     * @param phoneNumber new phoneNumber name of the entry
     */
    public void updatePhoneNumber(int id, String phoneNumber) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update phone_book set phone_number = ? where id = ?"
            );

            updateStatement.setString(1, phoneNumber);
            updateStatement.setInt(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println("Number of updated rows: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates first name of the entry with a given id
     * @param id id of the entry
     * @param firstName new first name of the entry
     */
    public void updateFirstName(int id, String firstName) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update phone_book set first_name = ? where id = ?"
            );

            updateStatement.setString(1, firstName);
            updateStatement.setInt(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println("Number of updated rows: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates last name of the entry with a given id
     * @param id id of the entry
     * @param lastName new last name of the entry
     */
    public void updateLastName(int id, String lastName) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update phone_book set last_name = ? where id = ?"
            );

            updateStatement.setString(1, lastName);
            updateStatement.setInt(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println("Number of updated rows: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates birthdate of the entry with a given id
     * @param id id of the entry
     * @param birthdate new first name of the entry
     */
    public void updateBirthdate(int id, LocalDate birthdate) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update phone_book set birthdate = ? where id = ?"
            );

            updateStatement.setDate(1, Date.valueOf(birthdate));
            updateStatement.setInt(2, id);

            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println("Number of updated rows: " + rowsUpdated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Fetches entry by id
     * @param id id of the entry
     */
    public PhoneBookEntry getEntry(int id) {
        try {
            PreparedStatement selectStatement = conn.prepareStatement(
                "select * from phone_book where id = ?"
            );

            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();

            String phoneNumber = resultSet.getString("phone_number");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
            LocalDate dateAdded = resultSet.getDate("date_added").toLocalDate();

            return new PhoneBookEntry(phoneNumber, firstName, lastName, birthdate, dateAdded);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
