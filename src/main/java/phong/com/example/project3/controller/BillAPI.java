package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import phong.com.example.project3.dto.ResponseDTO;
import phong.com.example.project3.entity.Bill;
import phong.com.example.project3.repository.BillRepository;
import phong.com.example.project3.repository.UserRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/bill")
public class BillAPI {
    @Autowired
    BillRepository billRepository;


    @PostMapping("/new")
    public Bill Add(@ModelAttribute Bill bill){
        billRepository.save(bill);
        return bill;
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") int id){
        billRepository.deleteById(id);

    }

    @PostMapping("/edit")
    public Bill edit(@ModelAttribute Bill bill) throws SQLException {

        Bill current = billRepository.findById(bill.getId()).orElse(null);

        if (current != null) {
            // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
//            current.setBuyDate(bill.getBuyDate());
            current.setUser(bill.getUser());
            // ... set them thuoc tinh khac
            billRepository.save(current);
        }

        return current;
    }
    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(name= "id" , required = false) Integer id ,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "start", required = false ) @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date end,
                              @RequestParam(name= "userId" , required = false) Integer userId ,
                              @RequestParam(name = "userName", required = false) String userName,
                              @RequestParam(name = "email", required = false) String email,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "page", required = false) Integer page,
                              Model model){

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);

        page = page == null ? 0 : page;
        size = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(page,size);

        if ( id != null){
            List<Bill> billList = billRepository.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) billList.size());
            responseDTO.setData(billList);

        } else{
            Page<Bill> billPage = null ;
            if (StringUtils.hasText(name)){
                billPage = billRepository.searchByuName("%" + name + "%",pageable);
            } else if (StringUtils.hasText(userName)){
                billPage = billRepository.searchByUserName("%" + userName + "%",pageable);
            } else if (StringUtils.hasText(email)){
                billPage = billRepository.searchByuserEmail("%" + email + "%",pageable);
            } else if (userId != null){
                billPage = billRepository.searchByuserId(userId,pageable);
            } else if (start != null){
                billPage = billRepository.searchByStartbuyDate(start,pageable);
            } else if (end != null){
                billPage = billRepository.searchByEndbuyDate(end,pageable);
            } else {
                billPage = billRepository.findAll(pageable);
            }
            responseDTO.setTotalPage(billPage.getTotalPages());
            responseDTO.setCount(billPage.getTotalElements());
            responseDTO.setData(billPage.getContent());
        }


        return responseDTO;

    }
}
