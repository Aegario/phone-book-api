import java.time.LocalDate;

public class PhoneBookEntry {
    public String phoneNumber;
    public String firstName;
    public String lastName;
    public LocalDate birthdate;
    public LocalDate dateAdded;

    public PhoneBookEntry(
        String phoneNumber,
        String firstName,
        String lastName,
        LocalDate birthdate,
        LocalDate dateAdded
    ) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.dateAdded = dateAdded;
    }
}
