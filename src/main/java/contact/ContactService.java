package contact;

public class ContactService {
    private ContactDAO contactDAO;
    private ContactDTOMapper contactDTOMapper;
    public ContactService(ContactDAO contactDAO, ContactDTOMapper contactDTOMapper) {
        this.contactDAO = contactDAO;
        this.contactDTOMapper = contactDTOMapper;
    }

    public void create(ContactDTO contactDTO) {
        Contact contact = contactDTOMapper.toContact(contactDTO);
        contactDAO.save(contact);
    }
}
