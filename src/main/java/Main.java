import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ContactDao contactDao = new ContactDao();
        Contact contact = new Contact(
                "Denis",

                "Hormozi",
                "9991283418",
                LocalDate.of(2000, 10, 14)
        );
        // contactDao.save(contact);

        contactDao.closeConnection();
    }
}
