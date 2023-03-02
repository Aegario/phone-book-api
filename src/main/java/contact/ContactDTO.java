package contact;

public record ContactDTO(
        Integer id,
        String firstName,
        String lastName,
        String phoneNumber,
        String birthdate
) {}
