package phong.com.example.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import phong.com.example.project3.entity.BillItems;
import phong.com.example.project3.entity.Product;
import phong.com.example.project3.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = { "/add-to-cart" })
    public String cart(@RequestParam(name = "id") int id,
                       /// co the truyen them so luong, +1 la
                       /// tang, -1 la giam
                       @RequestParam(name = "quantity", required = false) Integer quantity, HttpSession session) {

        Product product = productRepository.getById(id);// tim san pham
        quantity = quantity == null ? 1 : quantity;

        // truong hop chua co gio hang
        if (session.getAttribute("cart") == null) {
            BillItems billItem = new BillItems();
            billItem.setQuantity(quantity);// hoac quantity
            billItem.setBuyPrice(product.getPrice());
            billItem.setProduct(product);

            // dung map (thay list) luu ds sp o gio hang tim kiem tien hon khi doc ra
            Map<Integer, BillItems> map = new HashMap<Integer, BillItems>();
            map.put(id, billItem);
            // set vao session
            // hoac save db BillItemCart(userId)
            session.setAttribute("cart", map);
        } else {
            // TH: da co sp trong gio hang
            // doc ve ep kieu ra map
            Map<Integer, BillItems> map = (Map<Integer, BillItems>) session.getAttribute("cart");
            // lay ra theo id sp
            BillItems billItem = map.get(id);
            if (billItem == null) {
                // neu la sp khac, thi = null, new va put lai vao map
                billItem = new BillItems();
                billItem.setQuantity(quantity);
                billItem.setBuyPrice(product.getPrice());
                billItem.setProduct(product);

                map.put(id, billItem);
            } else {
                billItem.setQuantity(billItem.getQuantity() + quantity);
            }
            // cap nhat vao session
            session.setAttribute("cart", map);
        }

        return "redirect:/view-cart";
    }
    @GetMapping(value = { "/view-cart" })
    public String cart(HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("cart") != null) {
            Map<Integer, BillItems> map = (Map<Integer, BillItems>) session.getAttribute("cart");

            double total = 0;
            for (Map.Entry<Integer, BillItems> entry : map.entrySet()) {
                total += (entry.getValue().getBuyPrice() * entry.getValue().getQuantity());
            }
            // tinh tong tien
            request.setAttribute("total", total);
        } else
            request.setAttribute("total", 0);

        return "view-cart.html";
    }
    // xoa san pham o gio hang theo key
    @GetMapping(value = { "/delete-cart" })
    public String delete(HttpSession session, @RequestParam(name = "id") int id) {
        if (session.getAttribute("cart") != null) {
            Map<Integer, BillItems> map = (Map<Integer, BillItems>) session.getAttribute("cart");
            map.remove(id);
        }
        return "redirect:/view-cart";
    }

    // xoa tat
    @GetMapping(value = { "/clear-cart" })
    public String deletehome(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/view-cart";
    }
}
