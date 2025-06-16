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
    PImage home, grassfield, palace, insidePalace, scroll;
    PImage mulanHorseR, mulanAtkL, mulanAtkR;
    PImage enemy1Img, enemy2Img;
    
    Gamesave gameSave;
    Mulan mulan;
    int stage = 0;
    int enemiesRemaining = 10;
    Enemy[] enemies;

    ArrayList<ArrayList<String>> dialogues = new ArrayList<>();
    int dialogueLine = 0;

    boolean movingLeft = false;
    boolean movingRight = false;
    boolean startScreen = true; 
    
    public void settings() {
        size(800, 400);
    }

    public void setup() {
        textSize(20);

        PImage atkL = loadImage("images/mulan_atkL.png");
        PImage atkR = loadImage("images/mulan_atkR.png");
        PImage horseR = loadImage("images/mulan_horseR.png");

        mulan = new Mulan(this, 50, 280, atkL, atkR, horseR, 80);
        mulan.setSpeed(3);

        home = loadImage("images/home.png");
        grassfield = loadImage("images/grassfield.png");
        palace = loadImage("images/palace.png");
        insidePalace = loadImage("images/insidePalace.png");
        scroll = loadImage("images/scroll.png");

        home.resize(width, height);
        grassfield.resize(width, height);
        palace.resize(width, height);
        insidePalace.resize(width, height);

        enemy1Img = loadImage("images/enemy.png");
        enemy2Img = loadImage("images/enemy2.png");

        loadDialogues("dialogues.txt");
    }

    public void draw() {
        if (startScreen) {
            showStartMenu();
            return; // prevent rest of draw() from running
        }
        drawBackground();

        if (stage == 3) {
            mulanRidesHorse();
            if (mulan.x > width) {
                stage = 4;
                dialogueLine = 0;
                mulan.x = 50;
            }

        } else if (stage == 6) {
            mulanFightsEnemies();
        } else {
            if (dialogueLine < dialogues.get(stage).size()) {
                showDialogue(dialogues.get(stage).get(dialogueLine));
            }
        }
        if (stage == 9 && dialogueLine == dialogues.get(stage).size() - 1) {
            Gamesave.saveProgress(stage);
        }

        if (movingLeft) {
            mulan.walk(-1);
        }
        if (movingRight) {
            mulan.walk(1);
        }
    }

    public void drawBackground() {
        switch (stage) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
                background(150); 
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

    public void showDialogue(String line) {
        image(scroll, 20, 250, 760, 120);
        fill(0);
        text(line, 120, 300);
        text("Press SPACE to continue", 120, 330);
    }

    public void keyPressed() {
        if (keyCode == LEFT) 
            movingLeft = true;
        if (keyCode == RIGHT) 
            movingRight = true;

        if (key == ' ') {
            if (stage == 6 && enemiesRemaining > 0) 
                return;

            if (dialogueLine < dialogues.get(stage).size() - 1) {
                dialogueLine++;
            } else {
                dialogueLine = 0;
                stage++;

                if (stage == 6) {
                    enemies = new Enemy[10];
                    for (int i = 0; i < enemies.length; i++) {
                        enemies[i] = new Enemy("Enemy" + i, random(100, 700), 300, i % 2 == 0, this, enemy1Img, enemy2Img, 70);
                    }
                }
            }
        }
        if (keyCode == ESC) {
            key = 0; // prevent default Processing ESC behavior
            Gamesave.saveProgress(stage);
            return; 
        }
        if (startScreen) {
            if (key == 'n' || key == 'N') {
                stage = 0;
                startScreen = false;
            } else if (key == 'l' || key == 'L') {
                stage = Gamesave.loadSavedStage(); // load progress
                startScreen = false;
            }
            return;
        }
    }

    public void keyReleased() {
        if (keyCode == LEFT) 
            movingLeft = false;
        if (keyCode == RIGHT) 
            movingRight = false;
    }

    public void mulanRidesHorse() {
        fill(0);
        text("Use RIGHT arrow to ride to the palace", 50, 50);
        mulan.ride();
        mulan.setDirection("left");
        mulan.display();
    }

    public void mulanFightsEnemies() {
        fill(0);
        text("Fight enemies! Use LEFT/RIGHT. Touch to defeat.", 20, 30);

        mulan.attack();
        mulan.display();

        for (Enemy e : enemies) {
            e.display();
        }

        if (enemiesRemaining <= 0) {
            text("All defeated! Press SPACE to return.", 50, 350);
        }

        for (Enemy e : enemies) {
            if (!e.dead && mulan.isCollidingWith(e)) {
                e.dead = true;
                enemiesRemaining--;
            }
        }
    }

    public void loadDialogues(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));
            ArrayList<String> currentStage = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().equals("---")) {
                    dialogues.add(currentStage);
                    currentStage = new ArrayList<>();
                } else {
                    currentStage.add(line);
                }
            }
            if (!currentStage.isEmpty()) {
                dialogues.add(currentStage);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not find dialogue file.");
            exit();
        }
    }
    public void showStartMenu() {
        background(0);
        fill(255);
  
        textAlign(CENTER, CENTER);
        textSize(28);
        text("Mulan Story", width / 2, height / 3);

        textSize(20);
        text("Press N for New Game", width / 2, height / 2);
        text("Press L to Load Game", width / 2, height / 2 + 30);
        textAlign(LEFT, BASELINE); 
    }
}