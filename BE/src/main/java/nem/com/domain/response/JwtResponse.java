package nem.com.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {


    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }
}
