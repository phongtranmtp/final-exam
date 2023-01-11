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
import phong.com.example.project3.dto.ResponseDTO;
import phong.com.example.project3.entity.Category;
import phong.com.example.project3.repository.CategoryRepository;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryAPI {
    @Autowired
    CategoryRepository categoryRepository ;

    @PostMapping("/new")
    public Category add(@ModelAttribute Category category){
        categoryRepository.save(category);
        return category;
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") int id){
        categoryRepository.deleteById(id);
    }

    @PostMapping("/edit")
    public Category edit(@ModelAttribute Category category){
        Category current = categoryRepository.findById(category.getId()).orElse(null);
        if (current != null){
            current.setName(category.getName());
            categoryRepository.save(current);
        }
        return current;
    }

//    @PostMapping("/edit")
//    public String edit(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult){
//        if (bindingResult.hasErrors()){
//            return "category/edit";
//        }
//        Category current = categoryRepository.findById(category.getId()).orElse(null);
//        if (current != null){
//            current.setName(category.getName());
//            categoryRepository.save(current);
//        }
//        return "redirect:/category/search";
//    }
    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(name= "id" , required = false) Integer id ,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date end,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "page", required = false) Integer page,
                              Model model){

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);

        page = page == null ? 0 : page;
        size = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(page,size);

        if ( id != null){
            List<Category> categoryList = categoryRepository.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) categoryList.size());
            responseDTO.setData(categoryList);

        } else{
            Page<Category> categoryPage = null ;
            if (StringUtils.hasText(name) && start != null && end != null){
                categoryPage = categoryRepository.searchByNameAndDate("%" + name + "%",start,end,pageable);
            } else if (StringUtils.hasText(name) && start != null){
                categoryPage = categoryRepository.searchByNameAndStartDate("%" + name + "%",start,pageable);
            } else if (StringUtils.hasText(name) && end != null){
                categoryPage = categoryRepository.searchByNameAndEndDate("%" + name + "%",end,pageable);
            } else if (StringUtils.hasText(name)){
                categoryPage = categoryRepository.searchByName("%" + name + "%",pageable);
            } else if (start != null){
                categoryPage = categoryRepository.searchByStartDate(start,pageable);
            } else if ( end != null){
                categoryPage = categoryRepository.searchByEndDate(end,pageable);
            } else {
                categoryPage = categoryRepository.findAll(pageable);
            }
            responseDTO.setTotalPage(categoryPage.getTotalPages());
            responseDTO.setCount(categoryPage.getTotalElements());
            responseDTO.setData(categoryPage.getContent());
        }
        return responseDTO;
    }

}
