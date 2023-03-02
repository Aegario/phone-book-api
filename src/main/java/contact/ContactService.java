package contact;

public class ContactService {
    private ContactDAO contactDAO;
    private ContactDTOMapper contactDTOMapper;
    public ContactService(ContactDAO contactDAO, ContactDTOMapper contactDTOMapper) {
        this.contactDAO = contactDAO;
        this.contactDTOMapper = contactDTOMapper;
    }

    public void createContact(ContactDTO contactDTO) {
        Contact contact = contactDTOMapper.toContact(contactDTO);
        contactDAO.save(contact);
    }

    public ContactDTO getContact(int id) {
        Contact contact = contactDAO.get(id);
        return contactDTOMapper.toDto(contact);
    }

    public int deleteContacts(String identifier) {
        return contactDAO.delete(identifier);
    }
}
