/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package a4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    static ArrayList<int[]> dice = new ArrayList<int[]>();

    public static void main(String[] args) {
        // to run with custom test Files replace yourPath with the path of that file:
        // ./gradlew run --args="yourPath"
        // Standard test file
        File file = new File("src/main/resources/wuerfel0.txt");

        // reading out path from arguments
        if (args.length == 1) {
            try {
                file = new File(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(file.getAbsolutePath());

        ArrayList<String> lines = new ArrayList<String>();

        try {

            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {

                String line = scanner.nextLine();

                if (!line.startsWith("// ")) {
                    lines.add(line);
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < lines.size(); i++) {

            String[] faces = lines.get(i).split(" ");
            int[] die = new int[faces.length-1];

            for (int j = 1; j < faces.length; j++) {

                die[j-1] = Integer.parseInt(faces[j]);
            }

            dice.add(die);
        }

        for(int[] die: dice){
            for (int i = 0; i < die.length; i++) {
                System.out.print(die[i] +" ");
            }
            System.out.println();
        }

        int[] wins = new int[dice.size()-1];

        for(int j = 0; j < dice.size(); j++) {
            for (int i = j+1; i < dice.size(); i++) {
                
                System.out.print(j +" VS "+i+", ");
                //Player p1 = new Player();
                System.out.println(" ");
            }
        }



    }
}
