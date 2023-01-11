package phong.com.example.project3.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotBlank(message = "{not.empty}")
    private String name ;

    private Double price;

    private String description;

    private String image;

    @JsonIgnore
    @Transient // field is not persistent.
    private MultipartFile file;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private Date createdAt ;

    @ManyToOne
    private Category category ;
}
