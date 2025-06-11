/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package firstsketch;
import processing.core.PApplet;

/**
 *
 * @author 342348646
 */
public class MySketch extends PApplet {
    private Person person2;
    private Person person1;
    private boolean showInfo = false;
 
  public void settings() {
    size(400, 400);
  }

  public void setup() {
    background(255);
    person1 = new Person(this, 0, 0, "Mr. Lu", 16, "images/person.png");
    person2 = new Person(this, 100, 100, "Mr. Lu", 88, "images/person.png");
  }
  
  public void draw() {
    background(255);
    person1.draw();
    person2.draw();
    drawCollisions();
    if (keyPressed){
        if (keyCode == LEFT){
            person1.move(-5, 0);
        } else if (keyCode == RIGHT){
            person1.move(5, 0);
        } else if (keyCode == UP){
            person1.move(0,-5);
        } else if (keyCode == DOWN){
            person1.move(0,5);
        }
    }
  }
  public void drawCollisions(){
      if(person1.isCollidingWith(person2)){
          fill(255,0,0);
          this.text("oouch",person2.x, person2.y);
      }
  }
}
