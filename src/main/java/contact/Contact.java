package contact;

import java.time.LocalDate;

public class Contact {
    private int  id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final LocalDate birthdate;
    private LocalDate dateAdded;

    public Contact(
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthdate
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }
}
