package com.example.netmetering;

import com.example.netmetering.configuration.JpaConfig;
import com.example.netmetering.entities.EnergyAccount;
import com.example.netmetering.entities.User;
import com.example.netmetering.repository.UserRepository;
import com.example.netmetering.service.TransactionService;
import com.example.netmetering.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;

//@ContextConfiguration(classes = JpaConfig.class)
@SpringBootTest
class NetMeteringApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateUser(){
        User user = new User();
        user.setLocation("Coruscant");

        User user1 = userService.createUser();

        System.out.println(user);

        // 验证数据是否被保存
        System.out.println("Generated User ID: " + user1);
    }

    @Test
    void testFind(){
        String email = "lloyddonegan@gmail.com";
        String email2 = "qiao0174@gmail.com";
        Optional<User> byEmail = userRepository.findByEmail(email2);
        System.out.println(byEmail.get());
    }

    @Test
    void testAdd(){
        User user = userService.createUser();
    }

    @Test
    void testDelete1(){
        userRepository.deleteById(1);
//        userRepository.deleteById(2);
    }

    @Test
    void testDelete(){
        User user = userService.createUser();
        userService.deleteUser(user);
    }

    @Test
    void testT(){
        Optional<User> ahsoka = userRepository.findById(1);
        Optional<User> anakin = userRepository.findById(2);

        boolean transfer = userService.transfer(ahsoka.get(), anakin.get(), new BigDecimal("5000"));
        if(transfer){
            System.out.println("Success");
        }else{
            System.out.println("Failed");
        }
    }

    @Test
    void testTransaction(){
        User ahsoka = new User();

        ahsoka.setLocation("Coruscant");


        User anakin = new User();


        anakin.setLocation("Coruscant");


        EnergyAccount account = new EnergyAccount();

        account.setEnergyBalance(new BigDecimal("5000"));
        account.setUser(ahsoka);
        ahsoka.setAccount(account);

        EnergyAccount account1 = new EnergyAccount();

        account1.setEnergyBalance(new BigDecimal("3000"));
        account1.setUser(anakin);
        anakin.setAccount(account1);

        boolean transfer = userService.transfer(ahsoka, anakin, new BigDecimal("2000"));
        if(transfer){
            System.out.println("Success");
        }else{
            System.out.println("Failed");
        }

    }
}
