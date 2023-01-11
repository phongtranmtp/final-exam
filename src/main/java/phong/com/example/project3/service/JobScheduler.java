package phong.com.example.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import phong.com.example.project3.entity.Bill;
import phong.com.example.project3.entity.User;
import phong.com.example.project3.repository.BillRepository;
import phong.com.example.project3.repository.UserRepository;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class JobScheduler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    BillRepository billRepository;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void sendAminEmail(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        Date date = cal.getTime();
        List<Bill> billList = billRepository.searchByDate(date);
        for (Bill b : billList){
            System.out.println(b.getId());
            mailService.sendEmail("phong422000@gmail.com","BILL ID" + b.getId(),"HELLO");
        }
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void sendEmail(){
        System.out.println("HELLO JOB");

        //chi lay ngay
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = calendar.getTime();

        List<User> userList = userRepository.searchByBirthdate(today);
        for (User u : userList){
            System.out.println(u.getName());
            mailService.sendEmail(u.getEmail(), "HBBD","phong");
        }

    }
}

