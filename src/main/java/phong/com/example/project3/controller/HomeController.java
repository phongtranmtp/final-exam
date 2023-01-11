package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phong.com.example.project3.entity.User;
import phong.com.example.project3.entity.UserRole;
import phong.com.example.project3.repository.ProductRepository;
import phong.com.example.project3.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/showProduct")
    public String search(Model model){
        model.addAttribute("productList",productRepository.findAll());
        return "product/showProduct" ;
    }
    @PostMapping("/register")
    public String add(@ModelAttribute User user){
        List<UserRole> role = new ArrayList<UserRole>();
        role.add(new UserRole("MEMBER"));
        user.setUsername(user.getUsername());
        user.setPassword(new BCryptPasswordEncoder()
                .encode(user.getPassword()));
        user.setUserRoleList(role);
        userRepository.save(user);

        return "redirect:/dang-nhap";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/showProduct";
    }
    @GetMapping("/register")
    public String danhKi() {
        return "register";
    }

    @GetMapping("/dang-nhap")
    public String login() {
        return "login.html";// view
    }

    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "D:/project3/";

        File file = new File(UPLOAD_FOLDER + filename);

        Files.copy(file.toPath(), response.getOutputStream());
    }


}
