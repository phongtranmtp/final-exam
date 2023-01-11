//package phong.com.example.project3;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    @Override
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
//        // chỉ ra url sẽ chuyên den sau khi dn
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        // dung authority
//        List<String> roles = auth.getAuthorities().stream().map(p -> p.getAuthority()).collect(Collectors.toList());
//
//        String redirectURL = "";
//
//         if (roles.contains("ADMIN")) {
//            redirectURL = "/admin/product/search";
//        } else if (roles.contains("MEMBER")) {
//            redirectURL = "/showProduct";
//        }
//
//        return redirectURL;
//    }
//
//}
