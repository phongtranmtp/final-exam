package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phong.com.example.project3.dto.ResponseDTO;
import phong.com.example.project3.entity.Product;
import phong.com.example.project3.repository.CategoryRepository;
import phong.com.example.project3.repository.ProductRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductAPI {
    @Autowired
    ProductRepository productRepository ;

    @Autowired
    CategoryRepository categoryRepository;


    @PostMapping("/new")
    public Product add(@ModelAttribute Product product)
            throws  IOException {

        if (product.getFile() != null) {
            final String UPLOAD_FOLDER = "D:/project3/";

            String filename = product.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            product.getFile().transferTo(newFile);

            product.setImage(filename);// save to db
        }

        productRepository.save(product);
        return product;
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") int id){
        productRepository.deleteById(id);
    }


    @PostMapping("/edit")
    public Product edit(@ModelAttribute("product") @Valid Product product) throws SQLException, IOException {


        Product current = productRepository.findById(product.getId()).orElse(null);

        if (current != null) {
            if (product.getFile() != null) {
                final String UPLOAD_FOLDER = "D:/project3/";

                String filename = product.getFile().getOriginalFilename();
                File newFile = new File(UPLOAD_FOLDER + filename);

                product.getFile().transferTo(newFile);

                current.setImage(filename);// save to db
            }

            // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
            current.setName(product.getName());
            current.setPrice(product.getPrice());
            current.setDescription(product.getDescription());
            productRepository.save(current);
        }
        return current;
    }
    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(name = "id", required = false) Integer id,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "price", required = false) Double price,
                              @RequestParam(name = "categoryId", required = false) Integer categoryId,
                              @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date end,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "page", required = false) Integer page, Model model) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        if (id != null) {
            List<Product> productList = productRepository.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) productList.size());
            responseDTO.setData(productList);

        } else {
            Page<Product> productPage = null;

            if(StringUtils.hasText(name) ) {
                productPage = productRepository.searchByName("%" + name + "%", pageable);
            } else if (price != null){
                productPage = productRepository.searchByPrice(price, pageable);
            } else if (categoryId != null){
                productPage = productRepository.searchByCategoryId(categoryId, pageable);
            } else if (StringUtils.hasText(name) ){
                productPage = productRepository.searchByCategoryName("%" + name + "%", pageable);
            } else if(StringUtils.hasText(name) && price != null ) {
                productPage = productRepository.searchByNameAndPrice("%" + name + "%",price, pageable);
            } else if (start != null){
                productPage = productRepository.searchByStartDate(start,pageable);
            } else if ( end != null){
                productPage = productRepository.searchByEndDate(end,pageable);
            } else if (StringUtils.hasText(name) && start != null && end != null){
                productPage = productRepository.searchByNameAndDate("%" + name + "%",start,end,pageable);
            } else{
                productPage = productRepository.findAll(pageable);
            }
            responseDTO.setTotalPage(productPage.getTotalPages());
            responseDTO.setCode(productPage.getNumberOfElements());
            responseDTO.setData(productPage.getContent());
        }

        return responseDTO ;
    }

}
