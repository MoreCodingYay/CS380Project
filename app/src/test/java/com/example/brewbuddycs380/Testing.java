package com.example.brewbuddycs380;

import org.junit.Test;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Testing {
    @Test
    void testCreateAccount(){
        try {
            assertFalse(UserService.createAccount("2","2"));
            assertFalse(UserService.createAccount("jeff'; DROP TABLE LOGINS; --","2"));
            assertTrue(UserService.createAccount((String.valueOf((int) Math.random() * 100000)),(String.valueOf((int) Math.random() * 100000))));
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

    @Test
    void testPreferences(){
        try {
            if(UserService.createAccount("test","test")){
                assertEquals("",ClientAPI.getAPI().getPreferences("test"));
                assertTrue(ClientAPI.getAPI().setPreferences("test","1:1,2:1,3:5,6:9"));
            }
            assertEquals("1:1,2:1,3:5,6:9",ClientAPI.getAPI().getPreferences("test"));


        } catch (AccountTakenException e) {
            throw new RuntimeException(e);
        } catch (UserServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAPI(){
        assertNotEquals(null,ClientAPI.getAPI());

    }

    @Test
    void testMakePrefString(){
        EnumSet<Properties> properties = EnumSet.noneOf(Properties.class);
        properties.add(Properties.BLACK);
        properties.add(Properties.HOT);
        assertEquals("0:1,6:1",CoffeeRecommender.userPreferenceToString(properties));
    }
    @Test
    void testGetPrefsFromString(){
        HashMap<Properties, Integer> propsAndWeights = new HashMap<Properties,Integer>();
        propsAndWeights.put(Properties.HOT, 1);
        propsAndWeights.put(Properties.BLACK, 1);
        assertEquals("0:1,6:1",  CoffeeRecommender.preferencWeightMapToString(propsAndWeights));
    }

    @Test
    void testUpdatePrefMap(){
        Map<Properties,Integer> map = CoffeeRecommender.stringToPreferencesAndWeights("0:1,6:1,13:2");
        CoffeeRecommender.updatePrefWeightHashmapWithCoffee(map, Coffee.MENU[1]);
        assertEquals(CoffeeRecommender.stringToPreferencesAndWeights("0:3,6:3,13:1,2:2,8:2,10:2,"),map);
    }



}
