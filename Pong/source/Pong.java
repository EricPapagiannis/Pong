import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Random; 
import processing.sound.*; 
import java.util.Random; 
import java.lang.Math; 
import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Pong extends PApplet {

// Eric Papagainnis



float x=width/2;
float y=height/2;
float xvel=0;
float yvel=0;
float frict = 0.9f;
float vel = 0.6f;
int ballxvel = 0;
int ballyvel= 0;
Paddle p1;
Paddle p2;
Ball ball;
PFont title;
PFont endMsg;

boolean startMsg = true;
boolean displayScore = false;
boolean paused = false;
int ballDirection;

int MAXSCORE = 3;

public void setup() {
  
  //fullScreen();
  background(100);
  frameRate(60);
  Random rand = new Random();
  ballDirection = rand.nextInt(4);
  
  if (ballDirection == 0) {
    ballxvel = 5;
    ballyvel = 5;
  } else if (ballDirection == 1) {
    ballxvel = 5;
    ballyvel = -5;
  } else if (ballDirection == 2) {
    ballxvel = -5;
    ballyvel = 5;
  } else if (ballDirection == 3) {
    ballxvel = -5;
    ballyvel = -5;
  }
  
  SoundFile collision = new SoundFile(this, "collision.mp3");

  p1 = new Paddle(25, 100, 100, height/2-50, xvel, yvel, frict, vel);
  p2 = new Paddle(25, 100, 1100, height/2-50, xvel, yvel, frict, vel);
  ball = new Ball(25, 25, width/2-10, height/2, 0, 0, 1, vel, ballDirection, p1, p2, collision);

  title = createFont("Lucida Console", 100, true);
  endMsg = createFont("Lucida Console", 24, true);

}
 
public void draw() {
  
  background(0);//cls
  int i;
  boolean skip = false;
  for (i = 0; i <= height; i+=10, skip = !skip) {
    if (!skip) {
        rect(width/2, i, 5, 10);
    }
  }
  if (displayScore) {
    if (p1.getScore() == MAXSCORE) {
      ball.resetFull();
      textAlign(CENTER);
      fill(255,0,0);
      textFont(endMsg);
      text("Player 1 wins", width/2, height/2-100);
      text("Press h to restart", width/2, height/2-5); 
    } else if (p2.getScore() == MAXSCORE) {
      ball.resetFull();
      textAlign(CENTER);
      fill(255,0,0);
      textFont(endMsg);
      text("Player 2 wins", width/2, height/2-100);
      text("Press h to restart", width/2, height/2-5); 
    }
    fill(255);
    textFont(title);
    text(p1.getScore(), width/2-100, 100); 
    text(p2.getScore(), width/2+100, 100);
  }
  p1.move();
  p2.move();
  ball.move();
  p1.display();
  p2.display();
  ball.display();
  if (startMsg) {
    textFont(title);       
    textAlign(CENTER);
    text("PONG", width/2, 100);
    textFont(endMsg);
    text("Press SPACE to begin", width/2, height/2-5);
    text("Press g to pause", width/2, height/2+45);

    text("^", 112, height/2-100);
    text("|", 112, height/2-90);
    text("w", 112, height/2-65);
    
    text("s", 112, height/2+75);
    text("|", 112, height/2+100);
    text("v", 112, height/2+110);

    text("^", 1112, height/2-100);
    text("|", 1112, height/2-90);
    text("o", 1112, height/2-65);
    
    text("l", 1112, height/2+75);
    text("|", 1112, height/2+100);
    text("v", 1112, height/2+110);


  }
}
 
public void keyPressed() {
  if (key == 'g') {
    if (paused == false) {
      noLoop();
      paused = true;
    } else {
      loop();
      paused = false;
    }
  }
  setMove(key, true);
}
 
public void keyReleased() {
  setMove(key, false);
}
 
public void setMove(int k, boolean b) {
  switch (k) {
  case 'w':
    p1.setIsUp(b);
    break;
  case 's':
    p1.setIsDown(b);
    break;
  case 'o':
    p2.setIsUp(b);
    break;
  case 'l':
    p2.setIsDown(b);
    break;
  case ' ':
    if (startMsg) {
    displayScore = true;
    ball.setXVel(ballxvel);
    ball.setYVel(ballyvel);
    startMsg = false;
    }
    break;
  case 'h':
    startMsg = true;
    displayScore = false;
    p1.setScore(0);
    p2.setScore(0);
    setup();
    break;
  }
}
// Eric Papagainnis



class Ball {

  float ballWidth;
  float ballHeight;
  float x;
  float y;
  float xvel;
  float yvel;
  float frict;
  float vel;
  float dir;
  boolean isLeft, isRight, isUp, isDown;
  Paddle p1;
  Paddle p2;
  SoundFile collision;
  
  Ball (float ballWidth, float ballHeight, float x, float y, float xvel, float yvel, float frict,
  float vel, float dir, Paddle p1, Paddle p2, SoundFile collision) {
    this.ballWidth = ballWidth;
    this.ballHeight = ballHeight;
    this.x = x;
    this.y = y;
    this.xvel = xvel;
    this.yvel = yvel;
    this.frict = frict;
    this.vel = vel;
    this.dir = dir;
    this.p1 = p1;
    this.p2 = p2;
    this.collision = collision;
    this.collision.amp(0.5f);
  }
  
  public void move() {
    if (this.isLeft){
      this.xvel=this.xvel-this.frict;
    }
    if (this.isRight){
      this.xvel=this.xvel+this.frict;
    }
    if (this.isDown){
      this.yvel=this.yvel+this.frict;
    }
    if (this.isUp){
      this.yvel=this.yvel-this.frict;
    }
    this.yvel=this.yvel*this.frict;
    this.xvel=xvel*this.frict;
    
    if (this.x < p1.x + p1.paddleWidth &&
       this.x + this.ballHeight > p1.x &&
       this.y < p1.y + p1.paddleHeight &&
       this.ballHeight + this.y > p1.y) {
       collision.play();
       this.xvel = -this.xvel;
       this.yvel += p1.yvel;
    } else if (this.x < p2.x + p2.paddleWidth &&
       this.x + this.ballHeight > p2.x &&
       this.y < p2.y + p2.paddleHeight &&
       this.ballHeight + this.y > p2.y) {
       collision.play();
       this.xvel = -this.xvel;
       this.yvel += p2.yvel;
    }
    if (this.x <= 0) {
      this.p2.incrementScore();
      Random rand = new Random();
      ballDirection = rand.nextInt(2);
      if (ballDirection == 0) {
        this.resetDirection(-5, 5);
      } else if (ballDirection == 1) {
        this.resetDirection(-5, -5);
      }
    } else if (this.x + this.ballWidth >= width) {
      this.p1.incrementScore();
      Random rand = new Random();
      ballDirection = rand.nextInt(2);
      if (ballDirection == 0) {
        this.resetDirection(5, 5);
      } else if (ballDirection == 1) {
        this.resetDirection(5, -5);
      }
    } else {
      this.x=this.x+this.xvel;
    }
    
    
    if (this.y <= 0 && this.yvel < 0) {
      collision.play();
      this.y = 0;
      this.yvel = -this.yvel;
    }
    else if ((this.y + this.ballHeight) >= height && this.yvel > 0) {
      collision.play();
      this.y = height-this.ballHeight;
      this.yvel = -this.yvel;
    } else {
      this.y=this.y+this.yvel;
    }
  }

  public void setIsLeft(boolean isLeft) {
    this.isLeft = isLeft;
  }
  
  public void setIsRight(boolean isRight) {
    this.isRight = isRight;
  }
  
  public void setIsUp(boolean isUp) {
    this.isUp = isUp;
  }
  
  public void setIsDown(boolean isDown) {
    this.isDown = isDown;
  }
  
  
  public void setXVel(float xvel) {
    this.xvel = xvel;
  }
  
  public void setYVel(float yvel) {
    this.yvel = yvel;
  }
  
  public void resetDirection(float xvel, float yvel) {
    this.x = width/2-10;
    this.y = height/2;
    this.xvel = xvel;
    this.yvel = yvel;
  }
  
  public void resetFull() {
    this.xvel = 0;
    this.yvel = 0;
    this.x = width/2-10;
    this.y = height/2;
  }
  
  public void display() {
    rect(x, y, this.ballWidth, this.ballHeight);
    rect(x, y, 5, 5);
  }
}
// Eric Papagainnis
class Paddle {

  float paddleWidth;
  float paddleHeight;
  float x;
  float y;
  float xvel;
  float yvel;
  float frict;
  float vel;
  boolean isLeft, isRight, isUp, isDown;
  int score;

  Paddle (float paddleWidth, float paddleHeight, float x, float y, float xvel, float yvel, float frict, float vel) {
    this.paddleWidth = paddleWidth;
    this.paddleHeight = paddleHeight;
    this.x = x;
    this.y = y;
    this.xvel = xvel;
    this.yvel = yvel;
    this.frict = frict;
    this.vel = vel;
    this.score = 0;
  }
  
  
  
  public void move() {
    if (this.isLeft){
      this.xvel=this.xvel-this.frict;
    }
    if (this.isRight){
      this.xvel=this.xvel+this.frict;
    }
    if (this.isDown){
      this.yvel=this.yvel+this.frict;
    }
    if (this.isUp){
      this.yvel=this.yvel-this.frict;
    }
    this.yvel=this.yvel*this.frict;
    this.xvel=xvel*this.frict;
    
    this.x=this.x+this.xvel;
    if (this.y <= 0 && this.yvel < 0) {
      this.y = 0;
    }
    else if ((this.y + this.paddleHeight) >= height && this.yvel > 0) {
      this.y = height-this.paddleHeight;
    } else {
      this.y=this.y+this.yvel;
    }
    
    
  }
  
  public void setIsLeft(boolean isLeft) {
    this.isLeft = isLeft;
  }
  
  public void setIsRight(boolean isRight) {
    this.isRight = isRight;
  }
  
  public void setIsUp(boolean isUp) {
    this.isUp = isUp;
  }
  
  public void setIsDown(boolean isDown) {
    this.isDown = isDown;
  }
  
  public void incrementScore() {
    this.score++; 
  }
  
  public void setScore(int score) {
    this.score = score;
  }
  
  public int getScore() {
    return this.score;
  }
  
  public void display() {
    rect(x, y, this.paddleWidth, this.paddleHeight);
    rect(x, y, 5, 5);
  }
}
  public void settings() {  size(1200, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Pong" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
