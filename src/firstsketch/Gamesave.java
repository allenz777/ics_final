/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstsketch;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

/**
 *
 * @author Allen Zhang
 */
public class Gamesave {
    // Constructor
    public Gamesave(){
    }
    
    /**
     * This method saves the current stage to a file
     * @param stage The stage to be saved
     */
    public static void saveProgress(int stage) {
        try {
            PrintWriter writer = new PrintWriter("stage_save.txt");
            writer.println(stage);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving progress: " + e.getMessage());
        }
    }
    
    /**
     * This method loads the saved game from the file
     * @return The saved stage number, or 0 if loading fails
     */
    public static int loadSavedStage() {
        try {
            Scanner scanner = new Scanner(new File("stage_save.txt"));
            int loadedStage = scanner.nextInt();
            scanner.close();
            return loadedStage;
        } catch (Exception e) {
            System.out.println("Could not load save. Starting from beginning.");
            return 0;
        }
    }
}
