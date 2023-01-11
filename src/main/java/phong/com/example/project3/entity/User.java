package phong.com.example.project3.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id ;

        @NotBlank(message = "{not.empty}")
        private String name;

        private String avatar;

        @JsonIgnore
        @Transient // field is not persistent.
        private MultipartFile file;

        @NotBlank(message = "{not.empty}")
        @Column(unique = true)
        private String username;

        @NotBlank(message = "{not.empty}")
        private String password;

        private String email;

        @JsonFormat(pattern = "dd/MM/yyyy")
        @DateTimeFormat(pattern = "dd/MM/yyyy")
        private Date birthdate;

        @JsonFormat(pattern = "dd/MM/yyyy")
        @CreatedDate
        private Date createdAt ;

        @JsonManagedReference
        @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
        private List<UserRole> userRoleList;





}
