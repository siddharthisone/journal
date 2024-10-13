package com.project.myFirstProject.controller;


import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.repository.UserRepo;
import com.project.myFirstProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

//    @GetMapping("/allUsers") //ADMIN WILL SEE THIS
//    public List<User> getAllUsers()
//    {
//        return userService.getAll();
//    }

    @PostMapping()
    public void createUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb =  userService.findByUsername(userName);
          if(userInDb!=null)
          {
              userInDb.setUsername(user.getUsername());
              userInDb.setPassword(user.getPassword());
              userService.saveNewUser(userInDb);
          }
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


