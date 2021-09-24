/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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

        // HashMap<Integer,Float> hotels = new HashMap<Integer,Float>();
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        for (int i = 0; i < lines.size(); i++) {
            String parts[] = lines.get(i).split(" ");
            hotels.add(new Hotel(i, Integer.parseInt(parts[0]), Float.parseFloat(parts[1])));
            // hotels.put(Integer.parseInt(parts[0]), Float.parseFloat(parts[1]));
        }

        if (totalTime > 1800) {
            totalTime = 1800;
        }

        int currentTravelTime = 0;
        float averageRating;
        ArrayList<Hotel> selectedHotels = new ArrayList<Hotel>();
        Boolean usingAlternateMethod = false;
        // -360 because we can reach end from there
        while (currentTravelTime < totalTime - 360) {
            // try to make it to the end using only the best Hotels within Range
            Hotel currentHotel = getBestHotelWithinRange(hotels, currentTravelTime);
            selectedHotels.add(currentHotel);
            currentTravelTime = currentHotel.distance;
            // if this fails use the other method
            if (selectedHotels.size() > 5) {

                usingAlternateMethod = true;
                break;
            }
        }
        usingAlternateMethod = true;
        if (usingAlternateMethod) {
            // clear values from previous attempt
            selectedHotels = createRoute(totalTime, hotels);
            
            for (Hotel h : selectedHotels)
                System.out.println(h.distance + " " + h.rating);
            System.out.println("------");
            int previousHotelDistance = 0;
            int nextHotelDistance = 0;
            for (int i = selectedHotels.size() - 1; i > -1; i--) {
                // adjust the hotels to try and optimize the average rating
                if (i == 0) {
                    previousHotelDistance = 0;
                } else {
                    previousHotelDistance = selectedHotels.get(i - 1).distance;
                }
                if (i == selectedHotels.size() - 1) {
                    nextHotelDistance = totalTime;
                } else {
                    nextHotelDistance = selectedHotels.get(i + 1).distance;
                }

                Hotel currentHotel = selectedHotels.get(i);
                for (Hotel h : getRemainingHotels(hotels, selectedHotels)) {
                    if (h.distance - previousHotelDistance < 360 && h.distance - previousHotelDistance > 0
                            && nextHotelDistance - h.distance < 360 && nextHotelDistance - h.distance > 0
                            && currentHotel.rating < h.rating) {
                        currentHotel = h;
                        selectedHotels.set(i, currentHotel);
                    }
                }
            }
        }
        averageRating = calculateAverageRating(selectedHotels);

        while (selectedHotels.size() < 5) {
            // if we still have stops left over use them to try and improve our average
            // rating
            averageRating = calculateAverageRating(selectedHotels);
            Hotel bestRemaining = getBestRemainingHotel(hotels, selectedHotels);
            if (bestRemaining.rating > averageRating)
                selectedHotels.add(bestRemaining);
            else
                break;
        }

        Collections.sort(selectedHotels);
        for (Hotel h : selectedHotels)
            System.out.println(h.distance + " " + h.rating);
        System.out.println(averageRating);
    }

    static float calculateAverageRating(ArrayList<Hotel> list) {
        float average = 0;
        int counter = 0;
        for (Hotel h : list) {
            average += h.rating;
            counter++;
        }
        average = average / counter;
        return average;
    }

    static Hotel getBestHotelWithinRange(ArrayList<Hotel> list, int currentTraveltime) {
        Hotel current = null;
        for (Hotel h : list) {
            if (current == null)
                current = h;
            if (current.rating <= h.rating && h.distance - currentTraveltime <= 360
                    && h.distance - currentTraveltime > 0)
                current = h;
        }
        return current;
    }

    static Hotel getFarthestHotelWithinRange(ArrayList<Hotel> list, int currentTraveltime) {
        Hotel current = null;
        for (Hotel h : list) {
            if (current == null)
                current = h;
            if (current.distance <= h.distance && h.distance - currentTraveltime <= 360
                    && h.distance - currentTraveltime > 0)
                current = h;
        }
        return current;
    }

    static Hotel getBestRemainingHotel(ArrayList<Hotel> allHotels, ArrayList<Hotel> selectedHotels) {

        ArrayList<Hotel> remainingHotels = getRemainingHotels(allHotels, selectedHotels);
        Hotel bestHotel = null;
        for (Hotel h : remainingHotels) {
            if (bestHotel == null || bestHotel.rating < h.rating)
                bestHotel = h;
        }
        return bestHotel;
    }

    static ArrayList<Hotel> getRemainingHotels(ArrayList<Hotel> allHotels, ArrayList<Hotel> selectedHotels) {
        ArrayList<Hotel> remainingHotels = allHotels;
        for (Hotel h : selectedHotels) {
            remainingHotels.remove(h);
        }
        return remainingHotels;
    }

    static ArrayList<Hotel> createRoute(int totalTime, ArrayList<Hotel> hotels) {
        int currentTravelTime = 0;
        ArrayList<Hotel> selectedHotels = new ArrayList<Hotel>();
        while (currentTravelTime < totalTime - 360) {
            // use the farthest hotels within Range to make it to the end with certainty
            Hotel currentHotel = getFarthestHotelWithinRange(hotels, currentTravelTime);
            selectedHotels.add(currentHotel);
            currentTravelTime = currentHotel.distance;
        }
        return selectedHotels;
    }
}
