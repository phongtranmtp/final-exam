package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.entity.Category;

import javax.xml.crypto.Data;
import java.util.Date;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.name LIKE :x " )
    Page<Category> searchByName(@Param("x") String name , Pageable pageable) ;

    @Query("SELECT c FROm Category c WHERE c.createdAt >= :start AND c.createdAt <= :end ")
    Page<Category> searchByDate(@Param("start")Data start,@Param("end") Date end,Pageable pageable);

    @Query("SELECT c FROm Category c WHERE c.createdAt >= :start ")
    Page<Category> searchByStartDate(@Param("start") Date start,Pageable pageable);

    @Query("SELECT c FROm Category c WHERE c.createdAt <= :end ")
    Page<Category> searchByEndDate(@Param("end") Date end,Pageable pageable);

    @Query("SELECT c FROm Category c WHERE c.name LIKE :x AND c.createdAt >= :start AND c.createdAt <= :end ")
    Page<Category> searchByNameAndDate(@Param("x") String name,  @Param("start") Date start,@Param("end") Date end,Pageable pageable);

    @Query("SELECT c FROm Category c WHERE c.name LIKE :x AND c.createdAt >= :start ")
    Page<Category> searchByNameAndStartDate(@Param("x") String name,@Param("start") Date start,Pageable pageable);

    @Query("SELECT c FROm Category c WHERE c.name LIKE :x AND c.createdAt <= :end ")
    Page<Category> searchByNameAndEndDate(@Param("x") String name,@Param("end") Date end,Pageable pageable);
}