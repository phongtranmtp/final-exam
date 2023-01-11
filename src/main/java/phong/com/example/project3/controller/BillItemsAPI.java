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
import phong.com.example.project3.entity.BillItems;
import phong.com.example.project3.repository.BillItemsRepository;
import phong.com.example.project3.repository.BillRepository;
import phong.com.example.project3.repository.ProductRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/billItems")
public class BillItemsAPI {
    @Autowired
    BillItemsRepository billItemsRepository;


   @PostMapping("/new")
   public BillItems add(@ModelAttribute BillItems billItems){
       billItemsRepository.save(billItems);
       return billItems;
   }

   @DeleteMapping("/delete")
   public void delete(@RequestParam("id") int id){
       billItemsRepository.deleteById(id);
   }

   @PostMapping("/edit")
   public BillItems edit(@ModelAttribute BillItems billItems){
       BillItems current = billItemsRepository.findById(billItems.getId()).orElse(null);

       if (current != null){
           current.setQuantity(billItems.getQuantity());
           current.setBuyPrice(billItems.getBuyPrice());
           billItemsRepository.save(current);
       }
       return current;
   }

    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(name = "id", required = false) Integer id,
                              @RequestParam(name = "quantity", required = false) Integer quantity,
                              @RequestParam(name = "buyPrice", required = false) Double buyPrice,
                              @RequestParam(name = "start", required = false ) @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date end,
                              @RequestParam(name = "billId", required = false) Integer billId,
                              @RequestParam(name = "productId", required = false) Integer productId,
                              @RequestParam(name = "name", required = false) String  name,
                              @RequestParam(name = "price", required = false) Double price,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "page", required = false) Integer page, Model model) {

       ResponseDTO responseDTO = new ResponseDTO();
       responseDTO.setCode(200);

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        if (id != null) {
            List<BillItems> billItemsList = billItemsRepository.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) billItemsList.size());
            responseDTO.setData(billItemsList);

        } else {
            Page<BillItems> billItemsPage = null;

            if (quantity!= null) {
                billItemsPage = billItemsRepository.searchQuantity(quantity, pageable);
            } else if  (buyPrice != null) {
                billItemsPage = billItemsRepository.searchbuyPrice(buyPrice, pageable);
            } else if (StringUtils.hasText(name)) {
                billItemsPage = billItemsRepository.searchproductName("%" + name +"%", pageable);
            } else if  (billId != null) {
                billItemsPage = billItemsRepository.searchbillId(billId, pageable);
            } else if  (productId != null) {
                billItemsPage = billItemsRepository.searchproductId(productId, pageable);
            } else if  (price != null) {
                billItemsPage = billItemsRepository.searchproductPrice(price, pageable);
            } else if (start != null){
                billItemsPage = billItemsRepository.searchByStartDate(start,pageable);
            } else if ( end != null){
                billItemsPage = billItemsRepository.searchByEndDate(end,pageable);
            } else {
                billItemsPage = billItemsRepository.findAll(pageable);
            }
            responseDTO.setTotalPage(billItemsPage.getTotalPages());
            responseDTO.setCount(billItemsPage.getTotalElements());
            responseDTO.setData(billItemsPage.getContent());
        }

        return responseDTO;
    }
}
