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
public class Mulan extends Characters {
    // Declare variables
    private PImage attackLeft, attackRight, horseRight;
    private String direction = "right";
    private String state = "idle";

    // Constructor
    public Mulan(PApplet p, float x, float y, PImage atkL, PImage atkR, PImage horseR, int size) {
        super(p, x, y, horseR, size); // Default image
        this.attackLeft = atkL;
        this.attackRight = atkR;
        this.horseRight = horseR;
        setState("horse"); // Sets state of Mulan
    }

    /**
     * This method sets the current direction of the character and updates the state image 
     * @param dir The new direction
     */
    public void setDirection(String dir) {
        if (!dir.equals(this.direction)) {
            this.direction = dir;
            setState(this.state); // Refresh image with new direction
        }
    }

    /**
     * This method updates the current state of the character
     * @param s The state to set
     */
    public void setState(String s) {
        this.state = s;

        if (s.equals("attack")) {
            if (direction.equals("left")) {
                setImage(attackLeft);
            } else {
                setImage(attackRight);
            }
        } else if (s.equals("horse")) {
                setImage(horseRight);
        }
    }

    /**
     * This method moves the character horizontally, updates direction and state to walk
     * @param dx The change in x-position
     */
    public void walk(int dx) {
        if (dx > 0) {
            setDirection("right");
        } else if (dx < 0) {
            setDirection("left");
        }
        setState("walk");
        move(dx);
    }

    /**
     * This method sets the characters state to attack
     */
    public void attack() {
        setState("attack");
    }

    /**
     * This method sets the characters state to horse
     */
    public void ride() {
        setState("horse");
    }

    /**
     * This method displays the character onto the screen
     */
    @Override
    public void display() {
        super.display();
    }
}
