package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phong.com.example.project3.dto.ResponseDTO;
import phong.com.example.project3.entity.User;
import phong.com.example.project3.repository.UserRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserAPI {
    @Autowired
    UserRepository userRepository ;

    @PostMapping("/new")
    public User add(@ModelAttribute User user,@RequestParam("flie") MultipartFile file  )throws IOException {
        if (user.getFile() != null) {
            final String UPLOAD_FOLDER = "D:/project3/";

            String filename = user.getFile().getOriginalFilename();
            File newFile = new File(UPLOAD_FOLDER + filename);

            file.transferTo(newFile);

            user.setAvatar(filename);// save to db
        }
        user.setPassword(new BCryptPasswordEncoder()
                .encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @DeleteMapping("/delete")
//    @Secured
    public String delete(@RequestParam("id") int id , Principal principal){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth != null
                && !(auth instanceof AnonymousAuthenticationToken)) {
            /// get thong tin ra
            //principal: chinh la user tu LoginService tra ve

        }
        userRepository.deleteById(id);
        return "redirect:/admin/user/search";
    }

    @PostMapping("/edit")
    public User edit(@ModelAttribute("user") @Valid User editUser) throws IOException {


        User current = userRepository.findById(editUser.getId()).orElse(null);

        if (current != null) {
            if (editUser.getFile() != null) {
                final String UPLOAD_FOLDER = "D:/project3/";

                String filename = editUser.getFile().getOriginalFilename();
                File newFile = new File(UPLOAD_FOLDER + filename);

                editUser.getFile().transferTo(newFile);

                current.setAvatar(filename);// save to db
            }

            // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
            current.setName(editUser.getName());
            current.setUsername(editUser.getUsername());
            current.setPassword(new BCryptPasswordEncoder()
                    .encode(editUser.getPassword()));;
            current.setEmail(editUser.getEmail());
            current.setBirthdate(editUser.getBirthdate());
            userRepository.save(current);
        }
        return current;
    }
    @GetMapping("/admin/user/search")
    public ResponseDTO search(@RequestParam(name= "id" , required = false) Integer id ,
                              @RequestParam(name = "name", required = false) String name,
                              @RequestParam(name = "userName", required = false) String userName,
                              @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date start,
                              @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date end,
                              @RequestParam(name = "birthdate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date birthdate,
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
            List<User> userList = userRepository.findAllById(Arrays.asList(id));

            responseDTO.setTotalPage(1);
            responseDTO.setCount((long) userList.size());
            responseDTO.setData(userList);

        } else{
            Page<User> userPage = null ;
            if (StringUtils.hasText(name)){
                userPage = userRepository.searchByName("%" + name + "%",pageable);
            } else if (StringUtils.hasText(userName)){
                userPage = userRepository.searchByUserName("%" + userName + "%",pageable);
            } else if (StringUtils.hasText(email)){
                userPage = userRepository.searchByEmail("%" + email + "%",pageable);
            } else if (StringUtils.hasText(name) && start != null && end != null){
                userPage = userRepository.searchByNameAndDate("%" + name + "%",start,end,pageable);
            } else if (StringUtils.hasText(name) && start != null){
                userPage = userRepository.searchByNameAndStartDate("%" + name + "%",start,pageable);
            } else if (StringUtils.hasText(name) && end != null){
                userPage = userRepository.searchByNameAndEndDate("%" + name + "%",end,pageable);
            } else if (start != null){
                userPage = userRepository.searchByStartDate(start,pageable);
            } else if ( end != null){
                userPage = userRepository.searchByEndDate(end,pageable);
            } else {
                userPage = userRepository.findAll(pageable);
            }

            responseDTO.setTotalPage(userPage.getTotalPages());
            responseDTO.setCount(userPage.getTotalElements());
            responseDTO.setData(userPage.getContent());
        }

        return responseDTO;

    }
}
