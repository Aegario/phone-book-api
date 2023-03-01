package contact;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.*;

public class ContactDAO {
    private static String DB_URL = "jdbc:postgresql://localhost:5432/phone_book";
    private static Connection conn;

    public ContactDAO() {
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
     * Adds a new contact
     * @param contact
     */
    public void save(Contact contact) {
        try {
            PreparedStatement insertStatement = conn.prepareStatement(
                "insert into contact values (default, ?, ?, default, ?, ?)"
            );

            insertStatement.setString(1, contact.firstName);
            insertStatement.setString(2, contact.lastName);
            insertStatement.setDate(3, Date.valueOf(contact.birthdate));
            insertStatement.setString(4, contact.phoneNumber);

            int rowsInserted = insertStatement.executeUpdate();
            System.out.println("Number of inserted rows: " + rowsInserted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes contacts by identifier value
     * @param value accepts first name, last name, full name and phone number.
     */
    public void delete(String value) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement(
                "delete from contact " +
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
     * Deletes contact by id
     * @param id contact's id.
     */
    public void delete(int id) {
        try {
            PreparedStatement deleteStatement = conn.prepareStatement(
                "delete from contact where id = ?"
            );

            deleteStatement.setInt(1, id);

            int rowsDeleted = deleteStatement.executeUpdate();
            System.out.println("Number of deleted rows: " + rowsDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates phoneNumber of the contact with a given id
     * @param id contact's id
     * @param phoneNumber new phoneNumber name of the contact
     */
    public void updatePhoneNumber(int id, String phoneNumber) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update contact set phone_number = ? where id = ?"
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
     * Updates first name of the contact with a given id
     * @param id contact's id
     * @param firstName new first name of the contact
     */
    public void updateFirstName(int id, String firstName) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update contact set first_name = ? where id = ?"
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
     * Updates last name of the contact with a given id
     * @param id contact's id
     * @param lastName new last name of the contact
     */
    public void updateLastName(int id, String lastName) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update contact set last_name = ? where id = ?"
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
     * Updates birthdate of the contact with a given id
     * @param id contact's id
     * @param birthdate new birthdate name of the contact
     */
    public void updateBirthdate(int id, LocalDate birthdate) {
        try {
            PreparedStatement updateStatement = conn.prepareStatement(
                "update contact set birthdate = ? where id = ?"
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
     * Fetches contact by id
     * @param id contact's id
     */
    public Contact get(int id) {
        try {
            PreparedStatement selectStatement = conn.prepareStatement(
                "select * from contact where id = ?"
            );

            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();

            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String phoneNumber = resultSet.getString("phone_number");
            LocalDate birthdate = resultSet.getDate("birthdate").toLocalDate();
            LocalDate dateAdded = resultSet.getDate("date_added").toLocalDate();

            Contact contact = new Contact(
                    firstName,
                    lastName,
                    phoneNumber ,
                    birthdate
            );
            contact.id = id;
            contact.dateAdded = dateAdded;

            return contact;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
