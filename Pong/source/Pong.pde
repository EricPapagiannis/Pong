// Eric Papagainnis
import java.util.Random;
import processing.sound.*;

float x=width/2;
float y=height/2;
float xvel=0;
float yvel=0;
float frict = 0.9;
float vel = 0.6;
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

void setup() {
  size(1200, 800);
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
 
void draw() {
  
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
 
void keyPressed() {
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
 
void keyReleased() {
  setMove(key, false);
}
 
void setMove(int k, boolean b) {
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