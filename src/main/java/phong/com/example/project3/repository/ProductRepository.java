package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.entity.Category;
import phong.com.example.project3.entity.Product;

import javax.xml.crypto.Data;
import java.util.Date;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE :x ")
    Page<Product> searchByName(@Param("x") String name , Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price = :x ")
    Page<Product> searchByPrice(@Param("x") double price , Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :x AND p.price = :y ")
    Page<Product> searchByNameAndPrice(@Param("x") String name ,@Param("y") double price, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.id = :x")
    Page<Product> searchByCategoryId(@Param("x") int id , Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE c.name LIKE :x")
    Page<Product> searchByCategoryName(@Param("x") String name , Pageable pageable);

    @Query("SELECT p FROm Product p WHERE p.createdAt >= :start AND p.createdAt <= :end ")
    Page<Product> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);

    @Query("SELECT p FROm Product p WHERE p.createdAt >= :start ")
    Page<Product> searchByStartDate(@Param("start")Date start,Pageable pageable);

    @Query("SELECT p FROm Product p WHERE p.createdAt <= :end ")
    Page<Product> searchByEndDate(@Param("end") Date end,Pageable pageable);

    @Query("SELECT p FROm Product p WHERE p.name LIKE :x AND p.createdAt >= :start AND p.createdAt <= :end ")
    Page<Product> searchByNameAndDate(@Param("x") String name,  @Param("start")Date start,@Param("end") Date end,Pageable pageable);

    @Query("SELECT p FROm Product p WHERE p.name LIKE :x AND p.createdAt >= :start ")
    Page<Product> searchByNameAndStartDate(@Param("x") String name,@Param("start") Date start,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :x AND p.createdAt <= :end ")
    Page<Product> searchByNameAndEndDate(@Param("x") String name,@Param("end") Date end,Pageable pageable);
}