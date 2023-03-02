package contact;

import java.time.LocalDate;

public class ContactDTOMapper {
    public Contact toContact(ContactDTO contactDTO) {
        return new Contact(
            contactDTO.firstName(),
            contactDTO.lastName(),
            contactDTO.phoneNumber(),
            LocalDate.parse(contactDTO.birthdate())
        );
    }

    public ContactDTO toDto(Contact contact) {
        return new ContactDTO(
            contact.getId(),
            contact.getFirstName(),
            contact.getLastName(),
            contact.getPhoneNumber(),
            contact.getBirthdate().toString()
        );
    }
}
