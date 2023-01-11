package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.entity.Category;
import phong.com.example.project3.entity.User;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.birthdate = :x ")
    List<User> searchByBirthdate(@Param("x") Date birthdate );

    @Query("SELECT u FROM User u WHERE u.name LIKE :x ")
    Page<User> searchByName(@Param("x") String name , Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.username LIKE :x ")
    Page<User> searchByUserName(@Param("x") String userName , Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.email LIKE :x ")
    Page<User> searchByEmail(@Param("x") String email , Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.createdAt >= :start AND u.createdAt <= :end ")
    Page<User> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.createdAt >= :start ")
    Page<User> searchByStartDate(@Param("start")Date start,Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.createdAt <= :end ")
    Page<User> searchByEndDate(@Param("end") Date end,Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start AND u.createdAt <= :end ")
    Page<User> searchByNameAndDate(@Param("x") String name,  @Param("start") Date start,@Param("end") Date end,Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt >= :start ")
    Page<User> searchByNameAndStartDate(@Param("x") String name,@Param("start") Date start,Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.name LIKE :x AND u.createdAt <= :end ")
    Page<User> searchByNameAndEndDate(@Param("x") String name,@Param("end") Date end,Pageable pageable);
}