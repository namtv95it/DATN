package nem.com.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ColorDTO {
    @Id
    private Integer id ;
    private String code ;
    private String name ;
    private Long quantity ;
}
