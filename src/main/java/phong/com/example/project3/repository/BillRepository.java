package phong.com.example.project3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phong.com.example.project3.dto.BillStatistic;
import phong.com.example.project3.entity.Bill;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.email LIKE :email ")
    public List<Bill> findByUser(@Param("email") String email);

    @Query("SELECT b FROM Bill b WHERE b.buyDate >= :start ")
    Page<Bill> searchByStartbuyDate(@Param("start") Date start, Pageable pageable);

    @Query("SELECT b FROM Bill b WHERE b.buyDate <= :end ")
    Page<Bill> searchByEndbuyDate(@Param("end") Date end, Pageable pageable);

    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.id = :x ")
    Page<Bill> searchByuserId(@Param("x") int id, Pageable pageable);

    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.name LIKE :x ")
    Page<Bill> searchByuName(@Param("x") String name, Pageable pageable);

    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.username LIKE :x ")
    Page<Bill> searchByUserName(@Param("x") String userName, Pageable pageable);

    @Query("SELECT b FROM Bill b JOIN b.user u WHERE u.email LIKE :x ")
    Page<Bill> searchByuserEmail(@Param("x") String email, Pageable pageable);

    @Query("SELECT b FROM Bill b WHERE b.buyDate >= :x ")
    List<Bill> searchByDate(@Param("x") Date s);

    ///Đếm số lượng đơn group by MONTH(buyDate)
    //- dùng custom object để build
    // SELECT id, MONTH(buyDate) from bill;
    // select count(*), MONTH(buyDate) from bill
    // group by MONTH(buyDate)
    @Query("SELECT count(b.id), month(b.buyDate)"
            + " FROM Bill b GROUP BY month(b.buyDate) ")
    List<Object[]> thongKeBill();

    @Query("SELECT new phong.com.example.project3.dto.BillStatistic(count(b.id), month(b.buyDate)) "
            + " FROM Bill b GROUP BY month(b.buyDate) ")
    List<BillStatistic> thongKeBill2();
}