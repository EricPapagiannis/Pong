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
  
  void incrementScore() {
    this.score++; 
  }
  
  void setScore(int score) {
    this.score = score;
  }
  
  int getScore() {
    return this.score;
  }
  
  void display() {
    rect(x, y, this.paddleWidth, this.paddleHeight);
    rect(x, y, 5, 5);
  }
}