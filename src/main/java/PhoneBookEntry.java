import java.time.LocalDate;

public class PhoneBookEntry {
    public String phoneNumber;
    public String firstName;
    public String lastName;
    public LocalDate birthDate;
    public LocalDate dateAdded;

    public PhoneBookEntry(
        String phoneNumber,
        String firstName,
        String lastName,
        LocalDate birthDate
    ) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.dateAdded = LocalDate.now();
    }
}
