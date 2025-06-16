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
public class Characters {
    // Declare variables
    public float x,y;
    public float width, height;
    public PImage image;
    public PApplet app;
    public static float speed;
    public int size;
    
    // Constructor
    Characters(PApplet p, float x, float y, PImage i){
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = i;
    }
    
    // Constructor
    Characters(PApplet p, float x,float y, PImage i, int size){
        this(p,x,y,i);
        this.size = size;
        this.image.resize(0, size); // safe to resize now
        this.width = image.width;
        this.height = image.height;
    }
    
    /**
     * This method sets and resizes the image
     * @param i The image to be set as the current image
     */
    public void setImage(PImage i){
        image = i;
        this.image.resize(0, size);
        height = image.pixelHeight;
        width = image.pixelWidth;
    }
 
    /**
     * This method moves the character horizontally based on the given direction and speed
     * @param dx The horizontal direction to move in
     */
    public void move(int dx){
        x+=dx*speed;
    }
    
    /**
     * This method moves the character in both horizontal and vertical directions
     * @param dx The horizontal direction to move in
     * @param dy The vertical direction to move in
     */
    public void move(int dx,int dy){
        x+=dx*speed;
        y+=dy*speed;
    }
    
    /**
     * This method updates the characters position by moving it left at the current speed
     */
    public void update(){
        x-=speed;
    }
    
    /**
     * This method sets the speed of the character
     * @param s The speed value to be assigned
     */
    public void setSpeed(float s) {
        speed = s;
    }
    
    /**
     * This method displays the characters current image at its (x,y) position on the screen
     */
    public void display(){
        app.image(image,x,y);
    }
    
    /**
     * This method determines if the character is colliding with another character
     * @param other The other character to be checked
     * @return true if the two characters are colliding, false if they aren't
     */
    public boolean isCollidingWith(Characters other){
        boolean isLeftOfOtherRight = x < other.x + other.width;
        boolean isRightOfOtherLeft = x + width > other.x;
        boolean isAboveOtherBottom = y < other.y + other.height;
        boolean isBelowOtherTop = y + height > other.y;
        return isLeftOfOtherRight && isRightOfOtherLeft && isAboveOtherBottom && isBelowOtherTop;
    }
}