package nem.com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exchanges {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "video", nullable = true, length = 255)
    private String video;

    @Basic
    @Column(name = "reason", nullable = true, length = 255)
    private String reason;

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    private String note;

    @Basic
    @Column(name = "total", nullable = true)
    private Integer total;

    @Basic
    @Column(name = "create_date", nullable = true)
    private Timestamp createDate;

    @Basic
    @Column(name = "status", nullable = true)
    private Short status;

    @JsonIgnore
    @OneToMany(mappedBy = "exchange")
    private List<ExchangeImages> listExchangesImages;

    @JsonIgnore
    @OneToMany(mappedBy = "exchanges")
    private List<OrderDetails> listOrderDetails;
}
