package com.example.brewbuddycs380;

import android.widget.ImageView;

import java.util.*;

// Define an enum to represent the different properties that a coffee can have
enum Properties {
    HOT(0), ICED(1), STRONG(2), WEAK(3), SWEET(4),
    CREAMY(5), BLACK(6), FOAM(7), NOFOAM(8), DECAF(9), BITTER(10),
    FLAVOR_FRUIT(11), FLAVOR_CHOCOLATE(12), FLAVOR_CARAMEL(13), FLAVOR_VANILLA(14);

    // Each property has an index value so it can be converted to a series of numbers to go in the database
    private final int index;

    Properties(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

// Define a class to represent a coffee object
class Coffee implements Comparable<Coffee> {
    private  String name; // The name of the coffee
    private String description;
    private int similarityScore; // The similarity score of the coffee with respect to the user's preference
    private Set<Properties> coffeeProperties; // The properties of the coffee
    private int drawableId;

    public String getName(){
        return name;
    }

    public int getDrawableId(){
        return drawableId;
    }

    public String getDescription(){
        return description;
    }
    public void setSimilarityScore(int s){
        this.similarityScore =  s;
    }

    // Define an array of Coffee objects representing the different coffees available in the menu
    static Coffee[] MENU = new Coffee[]{
            new Coffee("Drip Coffee", EnumSet.of(Properties.STRONG, Properties.BLACK, Properties.CREAMY, Properties.NOFOAM, Properties.HOT, Properties.ICED), R.drawable.drip, "A classic coffee made by brewing ground coffee beans with hot water. It has a strong and bold flavor. Drip coffee is a popular choice for those who enjoy a simple and straightforward cup of coffee. It is often served black but can also be enjoyed with sugar or cream."),
            new Coffee("Espresso", EnumSet.of(Properties.STRONG, Properties.BLACK, Properties.NOFOAM, Properties.BITTER, Properties.HOT), R.drawable.latte, "A concentrated coffee made by forcing hot water through finely ground coffee beans. It has a rich and intense flavor. Espresso is often enjoyed on its own or used as a base for other coffee drinks such as lattes and cappuccinos."),
            new Coffee("Americano", EnumSet.of(Properties.WEAK, Properties.BLACK, Properties.NOFOAM, Properties.HOT, Properties.ICED), R.drawable.espresso, "A coffee made by adding hot water to an espresso shot. It has a milder flavor than espresso. Americano is a popular choice for those who enjoy the taste of espresso but prefer a less intense flavor. It can be served hot or iced."),
            new Coffee("Latte", EnumSet.of(Properties.WEAK, Properties.SWEET, Properties.CREAMY, Properties.FOAM, Properties.HOT, Properties.ICED), R.drawable.latte, "A coffee made with espresso and steamed milk. It has a creamy and smooth texture. Latte is a popular choice for those who enjoy the taste of coffee but prefer a milder flavor and a creamy texture. It can be served hot or iced and is often topped with milk foam."),
            new Coffee("Cappuccino", EnumSet.of(Properties.WEAK, Properties.SWEET, Properties.CREAMY, Properties.FOAM, Properties.HOT), R.drawable.cappuccino, "A coffee made with espresso, steamed milk, and milk foam. It has a creamy texture and a frothy top. Cappuccino is a popular choice for those who enjoy the taste of coffee and the texture of milk foam. It is typically served hot and can be topped with cinnamon or cocoa powder."),
            new Coffee("Mocha", EnumSet.of(Properties.WEAK, Properties.SWEET, Properties.CREAMY, Properties.NOFOAM, Properties.FLAVOR_CHOCOLATE, Properties.HOT), R.drawable.mocha, "A coffee made with espresso, steamed milk, and chocolate syrup. It has a sweet and chocolatey flavor. Mocha is a popular choice for those who enjoy the taste of coffee and chocolate. It is typically served hot and can be topped with whipped cream."),
            new Coffee("Macchiato", EnumSet.of(Properties.STRONG, Properties.CREAMY, Properties.FOAM, Properties.BITTER, Properties.FOAM, Properties.HOT), R.drawable.macchiato,"A coffee made with espresso and a small amount of milk foam. It has a strong flavor with a creamy texture. Macchiato is often enjoyed on its own or used as a base for other coffee drinks such as lattes and cappuccinos."),
            new Coffee("Flat White", EnumSet.of(Properties.WEAK, Properties.SWEET, Properties.NOFOAM, Properties.CREAMY, Properties.HOT), R.drawable.latte,"A coffee made with espresso and steamed milk. It has a smooth and velvety texture. Flat White is a popular choice for those who enjoy the taste of coffee but prefer a smooth texture. It is typically served hot.")
    };



    // Constructor to create a new Coffee object
    public Coffee(String name, Set<Properties> coffeeProperties,int drawableId, String description) {
        this.name = name;
        this.coffeeProperties = coffeeProperties;
        this.description = description;
        this.drawableId = drawableId;
    }

    // Method to calculate the similarity score of the coffee with respect to the user's preference
    public int calculateSimilarityScore(Set<Properties> userPreference) {
        int similarityScore = 0;
        for (Properties property : userPreference) {
            if (coffeeProperties.contains(property)) {
                similarityScore++;
            }
        }
        return similarityScore;
    }

    /**
     *
     * @return the properties of this coffee, as a constant.
     */
    public final Set<Properties> getCoffeeProperties(){
        return this.coffeeProperties;
    }
    // Return the difference between the similarity scores of the two coffees
    // A positive value means this coffee has a lower similarity score than the coffee being compared to
    // A negative value means this coffee has a higher similarity score than the coffee being compared to
    // Meaning when you poll() an item from the queue, the first will be the most similar, and so on
    @Override
    public int compareTo(Coffee coffee) {
        return coffee.similarityScore - this.similarityScore;
    }

    public String toString(){
        return this.name;
    }
}


// Define a class with static methods to recommend coffees based on the user's preference
public class CoffeeRecommender {
    public static Coffee[] recommend5Coffee(Set<Properties> userPreference) {
        // Create a priority queue to store the recommended coffee objects
        // I chose this because it automatically sorts the coffee objects
        // based on similarityScore to the userPreferences
        PriorityQueue<Coffee> recommendedCoffees = new PriorityQueue<>();

        // Calculate the similarity score for each coffee object in the menu
        for (Coffee coffee : Coffee.MENU) {
            int s = coffee.calculateSimilarityScore(userPreference);
            coffee.setSimilarityScore(coffee.calculateSimilarityScore(userPreference));
            recommendedCoffees.add(coffee);
        }

        // Create an array to store the top 5 recommended coffee objects
        Coffee[] topRecommendedCoffees = new Coffee[5];
        for (int i = 0; i < topRecommendedCoffees.length; i++) {
            topRecommendedCoffees[i] = recommendedCoffees.poll();
        }
        return topRecommendedCoffees;
    }

    public static Coffee recommendTopCoffee(Set<Properties> userPreference) {
        // Create a priority queue to store the recommended coffee objects
        PriorityQueue<Coffee> recommendedCoffees = new PriorityQueue<>();

        // Calculate the similarity score for each coffee object in the menu
        for (Coffee coffee : Coffee.MENU) {
            coffee.setSimilarityScore(coffee.calculateSimilarityScore(userPreference));
            recommendedCoffees.add(coffee);
        }

        // Return the top recommended coffee object
        return recommendedCoffees.poll();
    }

    // Method to return a String representation of the user prefernces
    public static String getPreferencesString(Set<Properties> userPreferences) {
        StringBuilder sb = new StringBuilder();
        for (Properties property : userPreferences) {
            switch (property) {
                case ICED:
                    sb.append("ice, ");
                    break;
                case CREAMY:
                    sb.append("cream, ");
                    break;
                case SWEET:
                    sb.append("sugar, ");
                    break;
                case FLAVOR_FRUIT:
                    sb.append("strawberry syrup, ");
                    break;
                case FLAVOR_CHOCOLATE:
                    sb.append("chocolate syrup, ");
                    break;
                case FLAVOR_VANILLA:
                    sb.append("vanilla syrup, ");
                    break;
                case FLAVOR_CARAMEL:
                    sb.append("caramel syrup, ");
                    break;
                case DECAF:
                    sb.append("decaf, ");
                    break;
            }
        }
        // this part cuts off the last comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    // To store preferences in the database easier, this method converts the enums to a string representation
    // these correspond to the number of the enums, separate by a comma
    public static String userPreferenceToString(Set<Properties> userPreference) {
        StringBuilder sb = new StringBuilder();
        for (Properties property : userPreference) {
            sb.append(property.getIndex());
            sb.append(":1,");
        }
        // this part cuts off the last comma
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * returns a set of the top properties from a string of enums
     * @param string
     * @return
     */
    public static Set<Properties> stringToUserPreference(String string) {
        // Creat en empty set of properties enums
        Set<Properties> userPreference = EnumSet.noneOf(Properties.class);
        String[] indices = string.split(",");
        int[] intIndicies = new int[indices.length];
        int[] weights = new int[indices.length];
        String index = indices[0];
        System.out.println("String is: "+string);
        System.out.println("username is: "+ UserService.getUsername());
        System.out.println("indicies are: "+Arrays.toString(indices));
        for ( int i = 0; i<indices.length; i++) {
            index = indices[i];
            System.out.println("Splitting:"+index+":I is: "+i );
            String[] indexAndWeight = index.split(":");
            System.out.println("");
            //index is the first part
            intIndicies[i]=Integer.parseInt(indexAndWeight[0]);
            //weight is after the :
            weights[i]=Integer.parseInt(indexAndWeight[1]);
        }

        //finds the max weight
        int maxWeight = weights[0];
        for(int i = 1; i<weights.length;i++){
            if(weights[i]>maxWeight) maxWeight=weights[i];
        }

        //only adds properties with weights within 7 of the max weight
        for(int i = 0; i<indices.length;i++){
            if(maxWeight-weights[i]<7){
                userPreference.add(Properties.values()[i]);
            }

        }


        return userPreference;
    }

    public static Map<Properties, Integer> stringToPreferencesAndWeights(String string){
        Map<Properties, Integer> userPreference = new HashMap<Properties,Integer>();
        String[] indices = string.split(",");
        int[] intIndicies = new int[indices.length];
        int[] weights = new int[indices.length];
        String index = indices[0];
        for ( int i = 0; i<indices.length; i++) {
            index = indices[i];
            String[] indexAndWeight = index.split(":");
            //index is the first part
            intIndicies[i]=Integer.parseInt(indexAndWeight[0]);
            //weight is after the :
            weights[i]=Integer.parseInt(indexAndWeight[1]);
        }

        //finds the max weight
        int maxWeight = weights[0];
        for(int i = 1; i<weights.length;i++){
            if(weights[i]>maxWeight) maxWeight=weights[i];
        }

        //adds all properties and weights
        for(int i = 0; i<indices.length;i++){

                userPreference.put(Properties.values()[i], weights[i]);


        }
        return userPreference;
    }

    /**
     * makes a string like preference:weight,preference:weight
     * @param userPreference
     * @return
     */
    public static String preferencWeightMapToString(Map<Properties, Integer> userPreference) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Properties,Integer> entry : userPreference.entrySet()) {
            sb.append(entry.getKey().getIndex());
            sb.append(":"+entry.getValue()+",");
        }
        // this part cuts off the last comma
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
    /**
     * adds 2 to the value of the map for each property they share, subtracts 1 for each they don't share, removes property if property is 1 and they don't share it
     * makes a concurrent modification exception
     */

    public static void updatePrefWeightHashmapWithCoffee(Map<Properties,Integer> map, Coffee coffee){
        //to avoid concurrent access exceptions, makes arraylists with lists of the properties to change after going through the hashmap
        ArrayList<Properties> inc = new ArrayList<>(), dec = new ArrayList<>();

        for(Map.Entry<Properties, Integer> i : map.entrySet()){
            if(coffee.getCoffeeProperties().contains(i.getKey())){
                inc.add(i.getKey());
                //map.replace(i.getKey(), i.getValue()+2);
            }else{
                //if(i.getValue()>1){
                    dec.add(i.getKey());
                    //map.replace(i.getKey(), i.getValue()-1);
               // }else{
                 //   map.remove(i.getKey());
                //}
            }


        }

        for(Properties i : inc){
            map.replace(i, map.get(i)+2);
        }
        for(Properties i : inc){
            if(map.get(i)>=1){
                map.remove(i);
            }else{
                map.replace(i, map.get(i)-1);
            }

        }
    }
}
