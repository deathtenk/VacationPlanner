import java.util.Scanner;
import java.util.HashMap;
import java.lang.Math;

public class Planner {
    public static void main(String[] args) {
        Scanner i = new Scanner(System.in);
        greeting(i);
        askFinances(i);
        askTimeDifference(i);
        askCountrySize(i);
        askCountryLocation(i);
    }

    public static void greeting(Scanner i) {
        System.out.println("Welcome to the Vacation Planner!");
        System.out.println("What is your name?");
        String name = i.nextLine();

        System.out.println("Nice to meet you " + name + ", where are you headed? ");
        String country = i.nextLine();
        System.out.println("Great! " + country + " seems like an Excellent place to visit.");
        endl();
    }

    public static void askFinances(Scanner i) {
        System.out.println("How many days will you travel?");
        double days = i.nextDouble();
        System.out.println("How much money, in USD, are you planning to spend on this trip?");
        double totalBudget = i.nextDouble();
        System.out.println("What is the three letter currency symbol for your travel destination?");
        String currency = i.next();
        System.out.println("How many " + currency + " are there in 1 USD?");
        double currencyExchange = i.nextDouble();

        HashMap<String,Double> initData = new HashMap<>();
        initData.put("days", days);
        initData.put("totalBudget", totalBudget);
        initData.put("currencyExchange", currencyExchange);
        HashMap <String,Double> cbd = calculateTravelBudget(initData);

        System.out.println("if you are traveling for " + (int) days + " days that is the same as " +
                            cbd.get("hours").intValue() + " hours or " +
                            cbd.get("minutes").intValue() + " minutes");
        System.out.printf("If you are going to spend $%.2f", totalBudget);
        System.out.printf(" USD that means per day you can spend up to %.2f %n", cbd.get("dailyBudgetUSD"));
        System.out.print("Your total budget in " + currency + " is ");
        System.out.printf(" %.2f ", cbd.get("totalBudgetForeign"));
        System.out.print("which means you can spend up to");
        System.out.printf(" %.2f "+ currency + "per day %n", cbd.get("dailyBudgetForeign"));
        endl();
    }

    public static void askTimeDifference(Scanner i) {
        System.out.println("What is the time difference, in hours, between your home and destination?");
        int diff = i.nextInt();
        System.out.println("That means that when it is midnight at home it will be " + calculateTimeOffset(24, diff) +
                            ":00 in your destination and when it is noon at home it will be " +
                            calculateTimeOffset(12, diff) + ":00");
        endl();
    }

    public static void askCountrySize(Scanner i) {
        System.out.println("What is the square area of your destination country in km2? ");
        double km = i.nextDouble();
        System.out.println("In miles2 that is " + convertKm2ToMiles2(km));
        endl();
    }

    public static void askCountryLocation(Scanner i) {
        System.out.println("Let's figure out the distance (in miles) from your place to your destination!");
        HashMap<String,Double> coords = new HashMap<>();
        System.out.println("Enter your home latitude:");
        coords.put("lat1", i.nextDouble());
        System.out.println("Enter your home longitude:");
        coords.put("lon1", i.nextDouble());
        System.out.println("Enter your destination latitude:");
        coords.put("lat2", i.nextDouble());
        System.out.println("Enter your destination longitude:");
        coords.put("lon2", i.nextDouble());
        System.out.println("The distance between your home and your destination is " + haversine(coords) + " in miles.");
    }

    public static HashMap<String,Double> calculateTravelBudget(java.util.HashMap<String,Double> data) {
        double days = data.get("days");
        double totalBudget = data.get("totalBudget");
        double currencyExchange = data.get("currencyExchange");

        HashMap<String,Double> newData = new HashMap<>();
        newData.put("hours", days * 24);
        newData.put("minutes", days * 24 * 60);
        newData.put("days", days);
        newData.put("totalBudgetUSD", totalBudget);
        newData.put("dailyBudgetUSD", totalBudget / days);
        newData.put("dailyBudgetForeign", (currencyExchange * totalBudget) / days);
        newData.put("totalBudgetForeign", (currencyExchange * totalBudget));
        return newData;
    }

    public static double KM2_TO_MILES2 = 0.38610;
    public static double EARTHS_RADIUS_IN_MILES = 3959;


    // TODO: there's a slight discrepancy here, figure out why
    public static double haversine(HashMap<String,Double> cs) {
        double lat1 = cs.get("lat1");
        double lat2 = cs.get("lat2");
        double lon1 = cs.get("lon1");
        double lon2 = cs.get("lon2");
        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);
        double a = (Math.pow(Math.sin(dlat / 2.0) , 2) +
                    Math.pow(Math.sin(dlon / 2.0) , 2)) *
                    Math.cos(Math.toRadians(lat1)) *
                    Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.asin(Math.sqrt(a));
        return (EARTHS_RADIUS_IN_MILES * c);

    }

    public static Integer calculateTimeOffset(Integer currentTime, Integer difference) {
       return ((currentTime + difference) % 24);
    }


    public static double convertKm2ToMiles2(double km) {
        return (km * KM2_TO_MILES2);
    }


    public static void endl() {
        System.out.println("********");
        System.out.println();
    }
}
