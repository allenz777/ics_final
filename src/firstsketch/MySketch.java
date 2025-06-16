/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstsketch;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 342348646
 */
public class MySketch extends PApplet {
    // Declare variables
    PImage home, grassfield, palace, insidePalace, scroll; // Background images
    PImage mulanHorseR, mulanAtkL, mulanAtkR; // Mulan character images
    PImage enemy1Img, enemy2Img; // Enemy character images
    
    // Game-related objects
    Gamesave gameSave;
    Mulan mulan;
    
    // Game state variables
    int stage = 0;
    int enemiesRemaining = 10;
    Enemy[] enemies;

    // Dialogue management
    ArrayList<ArrayList<String>> dialogues = new ArrayList<>();
    int dialogueLine = 0;

    // Player movement controls
    boolean movingLeft = false;
    boolean movingRight = false;
    
    // GameUI state
    boolean startScreen = true; 
    
    public void settings() {
        size(800, 400);
    }

    public void setup() {
        textSize(20); // Default text size for in-game text

        // Load mulan's attack and horse image
        PImage atkL = loadImage("images/mulan_atkL.png");
        PImage atkR = loadImage("images/mulan_atkR.png");
        PImage horseR = loadImage("images/mulan_horseR.png");

        // Instatiate 
        mulan = new Mulan(this, 50, 280, atkL, atkR, horseR, 80);
        mulan.setSpeed(3); // Set Mulan's speed

        // Load background images 
        home = loadImage("images/home.png");
        grassfield = loadImage("images/grassfield.png");
        palace = loadImage("images/palace.png");
        insidePalace = loadImage("images/insidePalace.png");
        scroll = loadImage("images/scroll.png");

        // Resize background images to fit the game window dimensions
        home.resize(width, height);
        grassfield.resize(width, height);
        palace.resize(width, height);
        insidePalace.resize(width, height);

        // Load enemy images
        enemy1Img = loadImage("images/enemy.png");
        enemy2Img = loadImage("images/enemy2.png");

        // Load dialogue from text file
        loadDialogues("dialogues.txt");
    }

    public void draw() {
        // Show the start menu if the game hasn't started yet
        if (startScreen) {
            showStartMenu();
            return; // Prevent rest of draw() from running
        }
        
        drawBackground(); // Draw the appropriate background for the current stage

        // Handles stage specific logic
        if (stage == 3) { 
            mulanRidesHorse(); // Method call
            if (mulan.x > width) { // Transitions to next stage when Mulan rides off the screen
                stage = 4;
                dialogueLine = 0; // Reset dialogue
                mulan.x = 50; // Reset Mulan's position
            }
        } else if (stage == 6) {
            mulanFightsEnemies(); // Method call
        } else { // Show dialogue if avaliable for other stages
            if (dialogueLine < dialogues.get(stage).size()) { 
                showDialogue(dialogues.get(stage).get(dialogueLine));
            }
        }
        
        // Save game progress at the end of stage 9 dialogue
        if (stage == 9 && dialogueLine == dialogues.get(stage).size() - 1) {
            Gamesave.saveProgress(stage);
        }

        // Move mulan based on user input
        if (movingLeft) {
            mulan.walk(-1);
        }
        if (movingRight) {
            mulan.walk(1);
        }
    }

    /**
     * This method draws the background image according to the current stage
     */
    public void drawBackground() {
        switch (stage) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
                background(150); // Set background colour to gray
                image(home, 0, 0);
                break;
            case 3:
            case 6:
                image(grassfield, 0, 0);
                break;
            case 4:
                image(palace, 0, 0);
                break;
            case 5:
            case 7:
                image(insidePalace, 0, 0);
                break;
        }
    }

    /**
     * This method displays a dialogue box with the given text line
     * @param line The dialogue text to be displayed
     */
    public void showDialogue(String line) {
        image(scroll, 20, 250, 760, 120); // Draw scroll background image for dialogue
        fill(0); // Set text colour to black
        text(line, 120, 300); // Draw the dialogue text
        text("Press SPACE to continue", 120, 330);
    }

    /**
     * This method handles key press events
     */
    public void keyPressed() {
        // Movement controls
        if (keyCode == LEFT) 
            movingLeft = true;
        if (keyCode == RIGHT) 
            movingRight = true;

        // Space key for progressing dialogue or stage
        if (key == ' ') {
            if (stage == 6 && enemiesRemaining > 0) // Only allow progression if all enemies are defeated
                return;

            // Move to next dialogue line if avaliable
            if (dialogueLine < dialogues.get(stage).size() - 1) {
                dialogueLine++;
            } else {
                // Reset dialogue and advance stage after current stage
                dialogueLine = 0;
                stage++;

                if (stage == 6) {
                    enemies = new Enemy[10]; // Initalize enemy array
                    for (int i = 0; i < enemies.length; i++) {
                        enemies[i] = new Enemy("Enemy" + i, random(100, 700), 300, i % 2 == 0, this, enemy1Img, enemy2Img, 70); // Create enemy objects
                    }
                }
            }
        }
        // If escape key is pressed, game is saved
        if (keyCode == ESC) {
            key = 0; // Prevents window from closing
            Gamesave.saveProgress(stage);
            return; 
        }
        // Start screen input: N for new game, L for loading saved game
        if (startScreen) {
            if (key == 'n' || key == 'N') {
                stage = 0;
                startScreen = false;
            } else if (key == 'l' || key == 'L') {
                stage = Gamesave.loadSavedStage(); // Load saved stage progress
                startScreen = false;
            }
            return; // Ignore other keys on start screen
        }
    }

    /**
     * This method handles key release events to stop character movement
     */
    public void keyReleased() {
        if (keyCode == LEFT) 
            movingLeft = false;
        if (keyCode == RIGHT) 
            movingRight = false;
    }

    /**
     * This method handles Mulan riding her horse
     */
    public void mulanRidesHorse() {
        fill(0);
        text("Use RIGHT arrow to ride to the palace", 50, 50);
        mulan.ride(); // Method call
        mulan.setDirection("left");
        mulan.display(); // Draw Mulan
    }

    /**
     * This method handles the combat scene
     */
    public void mulanFightsEnemies() {
        fill(0);
        text("Fight enemies! Use LEFT/RIGHT. Touch to defeat.", 20, 30);

        mulan.attack(); // Method call
        mulan.display(); // Draw Mulan

        // For loop to draw enemies
        for (Enemy e : enemies) {
            e.display();
        }

        // Prompt user to continue after all enemies are defeated
        if (enemiesRemaining <= 0) {
            text("All defeated! Press SPACE to return.", 50, 350);
        }

        // Checks collision between Mulan and each enemy
        for (Enemy e : enemies) {
            if (!e.dead && mulan.isCollidingWith(e)) {
                e.dead = true; // Marks enemy as dead
                enemiesRemaining--; // Enemy counter
            }
        }
    }

    /**
     * This method loads dialogue from a text file
     * @param filename The file path for the dialogue text file
     */
    public void loadDialogues(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            ArrayList<String> currentStage = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().equals("---")) {
                    dialogues.add(currentStage); // Add current stage dialogue text
                    currentStage = new ArrayList<>(); // Start new stage dialogue
                } else {
                    currentStage.add(line); // Add line to current stage
                }
            }
            if (!currentStage.isEmpty()) {
                dialogues.add(currentStage); // Add dialogue for last stage
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find dialogue file.");
            exit(); // Stops program if file is missing
        }
    }
    
    /**
     * This method displays the start menu screen
     */
    public void showStartMenu() {
        background(0);
        fill(255);
  
        textAlign(CENTER, CENTER);
        textSize(28);
        text("Mulan Story", width / 2, height / 3);

        textSize(20);
        text("Press N for New Game", width / 2, height / 2);
        text("Press L to Load Game", width / 2, height / 2 + 30);
        text("Press Esc to Save Game", width / 2, height / 2 + 60);
        textAlign(LEFT, BASELINE); // Resets text alignment for normal drawing
    }
}