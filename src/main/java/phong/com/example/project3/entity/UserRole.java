package phong.com.example.project3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name="user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    private User user ;

    @NotBlank(message = "{not.empty}")
    private String role;

    public UserRole() {

    }

    public UserRole(String role) {
        this.role = role;
    }
}
