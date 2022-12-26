package nem.com.domain.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RegisterFormUser {
    private String fullname;
    private String email;
    private String phone;
    private String password;
    private  Timestamp birthDate;
    private String photo;
}
