/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstsketch;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author 342348646
 */
public class Person {
    int x;
    int y;
    private String name;
    private int age;
    private PApplet app;
    private PImage image;
    private int width, height;
    
    public Person(PApplet p, int x, int y, String name, int age, String imagePath){
        this.app = p;
        this.x = x;
        this.y = y;
        this.name = name;
        this.age = age;
        this.image = app.loadImage(imagePath);
        this.width = image.width;
        this.height = image.height;
    }
    public boolean isCollidingWith(Person other){
        boolean isLeftOfOtherRight = x < other.x + other.width;
        boolean isRightOfOtherLeft = x + width > other.x;
        boolean isAboveOtherBottom = y < other.y + other.height;
        boolean isBelowOtherTop = y + height > other.y;
        return isLeftOfOtherRight && isRightOfOtherLeft && isAboveOtherBottom && isBelowOtherTop;
    }
    public void move(int dx, int dy){
        x += dx;
        y += dy;
    }
    public void draw(){
       app.image(image, x, y);
    }           
    
    public void moveTo(int dx, int dy){
        x = dx;
        y = dy;
    }
}
