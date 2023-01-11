package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query("SELECT ur FROM UserRole ur WHERE ur.role LIKE :x ")
    Page<UserRole> searchByRole(@Param("x") String role , Pageable pageable);

    @Query("SELECT ur FROM UserRole ur JOIN ur.user u WHERE u.name LIKE :x ")
    Page<UserRole> searchByName(@Param("x") String name ,Pageable pageable);

    @Query("SELECT ur FROM UserRole ur JOIN ur.user u WHERE u.id = :x ")
    Page<UserRole> searchByUserid(@Param("x") int id ,Pageable pageable);

    @Query("SELECT ur FROM UserRole ur JOIN ur.user u WHERE u.username LIKE :x ")
    Page<UserRole> searchByUsername(@Param("x") String username,Pageable pageable);

    @Query("SELECT ur FROM UserRole ur JOIN ur.user u WHERE u.email LIKE :x ")
    Page<UserRole> searchByUseremail(@Param("x") String email ,Pageable pageable);
}