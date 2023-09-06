package com.vodacom.er.gui.service;


import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EsUserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //        User user = userRepository.findByUserName(username);
        //        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
        Users user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        MyUserDetails userDetails = new MyUserDetails(user);
        return userDetails;
        // return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public List<Users> allUser() {
        return userRepository.findAll();
    }

//    public Optional<User> getUserById(Integer id) {
//        System.out.println("Route Called :"+userRepository.findById(id));
//        return userRepository.findById(id);
//    }

    public Optional<Users> deleteEsUserById(Integer id) {
        logger.info("CustomUserDetailsService : deleteEsUserById() Method Called : ");
        Optional<Users> delUser = userRepository.findById(id);

        if(delUser.isPresent()){
            logger.info("CustomUserDetailsService : deleteEsUserById() Method Called : Successfully ");
            userRepository.deleteById(id);
            return delUser;
        }
        logger.info("CustomUserDetailsService : deleteEsUserById() Method Called : No record found ");
        return null;
    }

    public Optional<Users> getUserById(Integer id) {
        logger.info("CustomUserDetailsService : getUserById() Method Called : Successfully ");
        return userRepository.findById(id);
    }

//    public User addUser(User user) {
//        User new_user = user;
//        new_user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

    public Optional<Users> getActualUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Users updateUser(String name) {
        return userRepository.findByUserName(name);
    }

}
