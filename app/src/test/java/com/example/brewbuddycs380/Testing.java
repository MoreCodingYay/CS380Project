package com.example.brewbuddycs380;

import org.junit.Test;

import static org.junit.Assert.*;

public class Testing {
    @Test
    void testCreateAccount(){
        try {
            assertFalse(UserService.createAccount("2","2"));
            assertFalse(UserService.createAccount("jeff'; DROP TABLE LOGINS; --","2"));
            assertTrue(UserService.createAccount((""+(int)Math.random()*100000),(""+(int)Math.random()*100000));
        } catch (AccountTakenException e) {
            throw new RuntimeException(e);
        } catch (UserServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLogin(){
        try {
            assertTrue(UserService.login("2","2"));
            assertEquals(UserService.getUsername(),"2");
            assertTrue(UserService.isLoggedIn());
            assertEquals(UserService.getLoggedInState(),LoggedInState.loggedInPrefsRetrieved);

            assertFalse(UserService.login("2","3"));
            assertFalse(UserService.login("jeff'; DROP TABLE LOGINS; --","3"));

        } catch (UserServiceException e) {
            throw new RuntimeException(e);
        }
    }




}
