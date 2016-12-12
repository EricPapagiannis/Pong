// Eric Papagainnis
import java.util.Random;
import java.lang.Math;
import processing.sound.*;
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
    this.collision.amp(0.5);
  }
  
  void move() {
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

  void setIsLeft(boolean isLeft) {
    this.isLeft = isLeft;
  }
  
  void setIsRight(boolean isRight) {
    this.isRight = isRight;
  }
  
  void setIsUp(boolean isUp) {
    this.isUp = isUp;
  }
  
  void setIsDown(boolean isDown) {
    this.isDown = isDown;
  }
  
  
  void setXVel(float xvel) {
    this.xvel = xvel;
  }
  
  void setYVel(float yvel) {
    this.yvel = yvel;
  }
  
  void resetDirection(float xvel, float yvel) {
    this.x = width/2-10;
    this.y = height/2;
    this.xvel = xvel;
    this.yvel = yvel;
  }
  
  void resetFull() {
    this.xvel = 0;
    this.yvel = 0;
    this.x = width/2-10;
    this.y = height/2;
  }
  
  void display() {
    rect(x, y, this.ballWidth, this.ballHeight);
    rect(x, y, 5, 5);
  }
}