package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import phong.com.example.project3.dto.ResponseDTO;
import phong.com.example.project3.entity.UserRole;
import phong.com.example.project3.repository.UserRepository;
import phong.com.example.project3.repository.UserRoleRepository;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/userrole")
public class UserRoleAPI {
    @Autowired
    UserRoleRepository userRoleRepo;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/new")
    public UserRole add(@ModelAttribute("userrole") @Valid UserRole userRole) throws SQLException {

        userRoleRepo.save(userRole);
        return userRole;
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") int id) throws SQLException {
        userRoleRepo.deleteById(id);
    }

    @PostMapping("/edit")
    public UserRole edit(@ModelAttribute("userrole") @Valid UserRole editUserRole) throws SQLException {

        UserRole current = userRoleRepo.findById(editUserRole.getId()).orElse(null);

        if (current != null) {
            // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
            current.setRole(editUserRole.getRole());
            // ... set them thuoc tinh khac
            userRoleRepo.save(current);
        }

        return current;
    }
    @GetMapping("/search")
    public ResponseDTO search(@RequestParam(name = "id", required = false) Integer id,
                              @RequestParam(name = "role", required = false) String role,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "userId", required = false) Integer userId,
                              @RequestParam(name = "userEmail", required = false) String userEmail,
                              @RequestParam(name = "userName", required = false) String userName,
                              @RequestParam(name = "size", required = false) Integer size,
                              @RequestParam(name = "page", required = false) Integer page, Model model) {

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;

        Pageable pageable = PageRequest.of(page, size);

        if (id != null) {
            List<UserRole> userRoleList = userRoleRepo.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) userRoleList.size());
            responseDTO.setData(userRoleList);

        } else {
            Page<UserRole> pageUserRole = null;

            if(StringUtils.hasText(name) ) {
                pageUserRole = userRoleRepo.searchByName("%" + name + "%", pageable);
            } else if (userId != null){
                pageUserRole = userRoleRepo.searchByUserid(userId, pageable);
            } else if (StringUtils.hasText(role)){ //!Objects.equals("", role)
                pageUserRole = userRoleRepo.searchByRole("%" + role + "%", pageable);
            } else if (StringUtils.hasText(userEmail)){
                pageUserRole = userRoleRepo.searchByUseremail("%" + userEmail + "%", pageable);
            } else if (StringUtils.hasText(userName)){
                pageUserRole = userRoleRepo.searchByUsername("%" + userName + "%", pageable);
            }else {
                pageUserRole = userRoleRepo.findAll(pageable);
            }
            responseDTO.setTotalPage(pageUserRole.getTotalPages());
            responseDTO.setCount(pageUserRole.getTotalElements());
            responseDTO.setData(pageUserRole.getContent());
        }

        return responseDTO;
    }
}
