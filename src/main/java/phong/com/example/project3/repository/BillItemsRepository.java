package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.entity.BillItems;
import phong.com.example.project3.entity.Category;

import javax.xml.crypto.Data;
import java.util.Date;

public interface BillItemsRepository extends JpaRepository<BillItems, Integer> {
    @Query("SELECT bt FROM BillItems bt WHERE bt.quantity = :x ")
    Page<BillItems> searchQuantity(@Param("x") int quantity, Pageable pageable);

    @Query("SELECT bt FROM BillItems bt WHERE bt.buyPrice = :x ")
    Page<BillItems> searchbuyPrice(@Param("x") double buyPrice, Pageable pageable);

    @Query("SELECT bt FROM BillItems bt JOIN bt.bill b WHERE b.id = :x ")
    Page<BillItems> searchbillId(@Param("x") int id, Pageable pageable);

    @Query("SELECT bt FROM BillItems bt JOIN bt.product p WHERE p.id = :x ")
    Page<BillItems> searchproductId(@Param("x") int id, Pageable pageable);

    @Query("SELECT bt FROM BillItems bt JOIN bt.product p WHERE p.price = :x ")
    Page<BillItems> searchproductPrice(@Param("x") double price, Pageable pageable);

    @Query("SELECT bt FROM BillItems bt JOIN bt.product p WHERE p.name LIKE :x ")
    Page<BillItems> searchproductName(@Param("x") String name, Pageable pageable);

    @Query("SELECT bt FROm BillItems bt WHERE bt.createdAt >= :start ")
    Page<BillItems> searchByStartDate(@Param("start") Date start, Pageable pageable);

    @Query("SELECT bt FROm BillItems bt WHERE bt.createdAt <= :end ")
    Page<BillItems> searchByEndDate(@Param("end") Date end, Pageable pageable);

}