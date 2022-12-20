package nem.com.service;

public interface EmailService {

    boolean sendSimpleMail(String email, String resetPasswordLink);
}
