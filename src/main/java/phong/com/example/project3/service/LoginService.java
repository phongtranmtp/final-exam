package phong.com.example.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import phong.com.example.project3.entity.User;
import phong.com.example.project3.entity.UserRole;
import phong.com.example.project3.repository.UserRepository;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User st = userRepository.findByUsername(username);
        if (st == null) {
            throw new UsernameNotFoundException("not found");
        }

        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();

        for (UserRole ur : st.getUserRoleList()){
            list.add(new SimpleGrantedAuthority(ur.getRole()));
        }
        // tao user cua security
        //user dang nhap hien tai
        org.springframework.security.core.userdetails.User currentUser =
                new org.springframework.security.core.userdetails.User(username,st.getPassword(),list);
        return currentUser;
    }
}
