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
    private PImage attackLeft, attackRight, horseRight;
    private String direction = "right";
    private String state = "idle";

    public Mulan(PApplet p, float x, float y, PImage atkL, PImage atkR, PImage horseR, int size) {
        super(p, x, y, horseR, size); // default image
        this.attackLeft = atkL;
        this.attackRight = atkR;
        this.horseRight = horseR;
        setState("horse");
    }

    public void setDirection(String dir) {
        if (!dir.equals(this.direction)) {
            this.direction = dir;
            setState(this.state); // refresh image with new direction
        }
    }

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

    public void walk(int dx) {
        if (dx > 0) {
            setDirection("right");
        } else if (dx < 0) {
            setDirection("left");
        }

        setState("walk");
        move(dx);
    }

    public void attack() {
        setState("attack");
    }

    public void ride() {
        setState("horse");
    }

    @Override
    public void display() {
        super.display();
    }
}
