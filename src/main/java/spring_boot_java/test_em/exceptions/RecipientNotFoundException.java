package spring_boot_java.test_em.exceptions;

public class RecipientNotFoundException extends RuntimeException {
    public RecipientNotFoundException(String recipientNotFound) {
    }
}
