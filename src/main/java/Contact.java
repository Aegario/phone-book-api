import java.time.LocalDate;

public class Contact {
    public int  id;
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public LocalDate birthdate;
    public LocalDate dateAdded;

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
}
