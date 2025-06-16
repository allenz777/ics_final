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
    public float x,y;
    public float width, height;
    public PImage image;
    public PApplet app;
    public static float speed;
    public int size;
    
    Characters(PApplet p, float x, float y, PImage i){
        this.app = p;
        this.x = x;
        this.y = y;
        this.image = i;
    }
    Characters(PApplet p, float x,float y, PImage i, int size){
        this(p,x,y,i);
        this.size = size;
        this.image.resize(0, size); // safe to resize now
        this.width = image.width;
        this.height = image.height;
    }
    public void setImage(PImage i){
        image = i;
        this.image.resize(0, size);
        height = image.pixelHeight;
        width = image.pixelWidth;
    }
 
    public void move(int dx){
        x+=dx*speed;
    }
    public void move(int dx,int dy){
        x+=dx*speed;
        y+=dy*speed;
    }
    public void update(){
        x-=speed;
    }
    public void setSpeed(float s) {
        speed = s;
    }
    
    public void display(){
        app.image(image,x,y);
    }
    
    public boolean isCollidingWith(Characters other){
        boolean isLeftOfOtherRight = x < other.x + other.width;
        boolean isRightOfOtherLeft = x + width > other.x;
        boolean isAboveOtherBottom = y < other.y + other.height;
        boolean isBelowOtherTop = y + height > other.y;
        return isLeftOfOtherRight && isRightOfOtherLeft && isAboveOtherBottom && isBelowOtherTop;
    }
}