/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstsketch;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author Allen Zhang
 */
public class Enemy extends Characters {
    // Declare variables
    public boolean dead;
    private boolean type1;

    // Constructor
    public Enemy(String name, float x, float y, boolean isType1, PApplet p, PImage enemy1Img, PImage enemy2Img, int size) {
        super(p, x, y, isType1 ? enemy1Img.copy() : enemy2Img.copy(), size); // Default image
        this.dead = false;
        this.type1 = isType1;
    }

    /**
     * This method displays the enemy on the screen if it is not marked as dead
     */
    @Override
    public void display() {
        if (!dead) {
            super.display();
        }
    }
}
