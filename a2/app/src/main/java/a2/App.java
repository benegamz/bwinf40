/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        
        File file = new File("src/main/resources/hotels1.txt");

        System.out.println(file.getAbsolutePath());

        ArrayList<String> lines = new ArrayList<String>();

        try {

            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(lines);

        int num_of_hotels = Integer.parseInt(lines.get(0));
        int totalTime = Integer.parseInt(lines.get(1));

        lines.remove(1);
        lines.remove(0);

        System.out.println(num_of_hotels);
        System.out.println(totalTime);

        //HashMap<Integer,Float> hotels = new HashMap<Integer,Float>();
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        for (int i = 0; i<lines.size(); i++) {
            String parts[] = lines.get(i).split(" ");
            hotels.add(new Hotel(i, Integer.parseInt(parts[0]), Float.parseFloat(parts[1])));
            //hotels.put(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]));
        }

        System.out.println(hotels);

        if (totalTime > 1800) {
            totalTime = 1800;
        }

        int currentTravelTime = 0;
        float averageRating;
        ArrayList<Hotel> selectedHotels = new ArrayList<Hotel>();
        while (currentTravelTime < totalTime-600){
            Hotel currentHotel = getBestHotelWithinRange(hotels, currentTravelTime);
            selectedHotels.add(currentHotel);
            currentTravelTime = currentHotel.distance;
        }

    }

    static float calculateAverageRating (ArrayList<Hotel> list){
        float average = 0;
        int counter = 0;
        for (Hotel h: list){
            average += h.rating;
            counter++;
        }
        average = average/counter;
        return average;
    }

    static Hotel getBestHotelWithinRange (ArrayList<Hotel> list, int currentTraveltime){
        Hotel current = null;
        for (Hotel h: list){
            if (current == null) current = h;
            //TODO account for negative travel time
            if (current.rating <= h.rating && current.distance-currentTraveltime <= 600) current = h;
        }
        return current;
    }
}
