package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import phong.com.example.project3.entity.Bill;
import phong.com.example.project3.entity.BillItems;
import phong.com.example.project3.repository.BillItemsRepository;
import phong.com.example.project3.repository.BillRepository;
import phong.com.example.project3.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class PaymentController {
//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BillRepository billRepo;

    @Autowired
    private BillItemsRepository billItemsRepo;

    @SuppressWarnings("unchecked")
    @GetMapping("/member/payment")
    public String payment(HttpSession session, Principal principal) {

        if (session.getAttribute("cart") != null) {
            Map<Integer, BillItems> map = (Map<Integer, BillItems>) session.getAttribute("cart");

            Bill bill = new Bill();
            bill.setUser(userRepo.findByUsername(principal.getName()));

            billRepo.save(bill);

            for (Map.Entry<Integer, BillItems> entry : map.entrySet()) {
                BillItems billItem = entry.getValue();
                billItem.setBill(bill);
                billItemsRepo.save(billItem);
            }
        }
        return "member/payment.html";
    }

    @GetMapping("/member/bills")
    public String billuser(Model model, Principal principal) {
        model.addAttribute("bills", billRepo.findByUser(principal.getName()));//name la email xem lai loginservice
        return "user/bills.html";
    }

}
