package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RatingProductDTO implements Serializable {

    @Id
    private Long id ;
    private String content ;
    private Short rating ;
    private String fullname;
    private Date createDate ;
}
