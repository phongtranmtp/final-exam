package phong.com.example.project3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bill_item")
@EntityListeners(AuditingEntityListener.class)
public class BillItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    private Integer quantity;

    private Double buyPrice;

    @ManyToOne
    private Bill bill;

    @ManyToOne
    private Product product;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date createdAt;
}
