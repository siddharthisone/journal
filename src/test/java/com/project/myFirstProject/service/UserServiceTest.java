package com.project.myFirstProject.service;

import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserRepo userRepository;

    @Test
    public void testFindByUsername()
    {
        //expected, actual
//        assertEquals(3,2+1); //passes
        User user = userRepository.findByUsername("ram");
        assertNotNull(user);
//        assertTrue(4>1);
        assertTrue(!user.getJournalEntries().isEmpty());


    }

    @ParameterizedTest
    @CsvSource({
            "Ram",
            "ram",
            "siddharth",
            "shivang"
    })
    public void testFindByUser(String user)
    {
        assertNotNull(userRepository.findByUsername(user),"Failed for username :- "+user);
    }

    @ParameterizedTest
    @CsvSource({
            "3,1,4",
            "23,3,26",
            "2,2,3"
    })
    public void test(int a , int b , int expected)
    {
        assertEquals(expected,a+b,"failed for "+a+" "+b);

    }


}
