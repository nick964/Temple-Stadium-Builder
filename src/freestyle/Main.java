/*
 * Assignment #8 - Visual Hash Table
 * Nick Robinson - CIS 2168 Section 1
 * Main.java - This file builds the structure of the hash table
 */
package freestyle;

import java.awt.Color;
import simplegui.*;


public class Main implements GUIListener, SGMouseListener, KeyboardListener, TimerListener{
    private SimpleGUI sg;
    private int clickcount;
    public int boxWid = 70;
    public int boxHeight = 20;
    public int score = 0;
    public int count = 0;
    public int level = 0;
    public boolean midgame = false;
    
    int fallingx;
    int fallingy = 30;
    DrwImage brick = new DrwImage("brick-hi.png");

    
    public Main(){
        
       clickcount = 0;
       //sg = new SimpleGUI();
       createGUI();
       sg.registerToGUI(this);
       sg.registerToMouse(this);
       sg.registerToKeyboard(this);
       sg.registerToTimer(this);

        
    }
    
  public void createGUI() {
        
        int r = 211;
        Color lightgrey = new Color(r,r,r);
        sg = new SimpleGUI(700, 400);
        sg.setBackgroundColor(lightgrey);
       // sg.drawText("NICKS AMAZING GAME COMING OUT SOON", 200, 20);
        
        sg.labelButton1(("Start Game"));
        sg.labelButton2("Reset");
        reactToSlider(50);
        sg.drawText("Use the slider to catch the bricks!", 350, 60, Color.black, 1.0,"welcometext");
        sg.drawImage("game-cover.png", 20, 0, 600, 435, "cover");
        
        
       
    }
    
    public static void main(String args[]) {
        Main mn = new Main();
    }
    
    public void startFall(int difficulty) {
        fallingy = 30;
        fallingx =  (int) (Math.random() * 630);
        //sg.drawDot(fallingx, fallingy, 10, Color.red, 1.0, "mydot");
        sg.drawImage(brick, fallingx, fallingy, 30, 30, "brick");
        switch (difficulty) {
            case 0:
                sg.timerStart(20);
                break;
            case 1:
                sg.timerStart(15);
                break;
            case 2:
                sg.timerStart(12);
                break;
            default:
                sg.eraseAllDrawables();
                sg.drawText("YOU WIN THE GAME!! CONGRATS!", 40, 40);
                break;
        }
        
    }
    
    
    @Override
    public void reactToButton1() {
       sg.eraseAllDrawables("welcometext");
       sg.eraseAllDrawables("cover");
       if(!midgame) {
       startFall(level);
       }
        
    }
    
    public void levelCheck() {
        if(count > 20) {
            boolean result = didTheyWin();
            midgame = false;
            sg.timerPause();
            sg.eraseAllDrawables();
            reactToSlider(50);
            resetScore();
            resetCount();
            
            
            if(result) {
                level++;
                sg.drawText("YOU WIN AND ADVANCE TO LEVEL " + (level + 1), 310, 100, Color.black, 1.0,"welcometext");
                 sg.drawText("Speed will increase!! ", 310, 150, Color.black, 1.0,"welcometext");
                sg.drawText("Click START GAME to continue ", 310, 130, Color.black, 1.0,"welcometext");
            } else {
                level = 0;
                sg.drawText("YOU LOSE! BACK TO LEVEL 1", 310, 130, Color.black, 1.0,"welcometext");
                
            }
           sg.eraseAllDrawables("brick");
        } else {
            midgame = true;
        }
    }
    

    @Override
    public void reactToButton2() {
      System.out.println("not supported yet");
    }


    @Override
    public void reactToSwitch(boolean bln) {
        System.out.println("This is not supported.");
    }

    @Override
    public void reactToSlider(int i) {
        sg.eraseAllDrawables("position");
        sg.eraseAllDrawables("bumper");
        //double boxToDraw = (i * 7 - (boxWid / 2));
        double boxToDraw = Math.round(i * 6.3);
      
     //   sg.drawFilledEllipse(i * 7, 300,100,20, Color.red,1.0,"bumper");
      
        sg.drawFilledBox(boxToDraw, 350, boxWid,20, Color.red,1.0,"bumper");
        //sg.drawText("[x,y]: " +boxToDraw, 20, 380, Color.black, 1.0, "position");
        //sg.drawText("actual i " + i, 20, 210, Color.black, 1.0, "position");
        
        
    }

    @Override
    public void reactToMouseClick(int i, int i1) {
        sg.drawEllipse(i, i1, 10, 10);
       
    } 

    @Override
    public void reactToKeyboardEntry(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToKeyboardSingleKey(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reactToTimer(long l) {
       
       drawBallPosition();
       fallingy = fallingy + 10;
       sg.eraseAllDrawables("mydot");
      // sg.drawDot(fallingx, fallingy, 10, Color.red, 1.0, "mydot");
        sg.drawImage(brick, fallingx, fallingy, 30, 30, "brick");
       if(fallingy > 400) {
           startFall(level);
       }
    }
    
    public void drawBallPosition() {
 
    sg.eraseAllDrawables("ballpos");
    sg.eraseAllDrawables("leveltext");
    sg.eraseAllDrawables("brickcount");
    sg.eraseAllDrawables("score");
    //pass in where slider currently is to see if a point should be added
    if (fallingy >= 350 && fallingy < 355) {
        addCount();
     checkScore(Math.round(sg.getSliderValue() * 6.3), fallingx);
    }
    levelCheck();
     
    String xPos = "" + fallingx;
    String yPos = "" + fallingy;
     sg.drawText("Score: " + score, 20, 60, Color.black, 1.0, "score");
     sg.drawText("Bricks Dropped: " + count, 20, 75, Color.black, 1.0, "brickcount");
     sg.drawText("CURRENT LEVEL: " + (level + 1), 330, 30, Color.red, 1.0, "leveltext");
     
        
        
    }
    
    public void checkScore(double sliderposition, double ballx) {
        //System.out.println("slider is at " + sliderposition);
        //System.out.println("ball is at " + ballx);
         System.out.println(count);
        if(ballx > sliderposition && ballx < sliderposition + boxWid) {
            startFall(level);
            addScore();
            System.out.println("Score is :" + score);
            
        }
    }
    
    public void addScore() {
        this.score++;
    }
    
    public void addCount() {
        this.count++;
    }
    
    public void resetScore() {
        this.score = 0;
    }
    
    public boolean didTheyWin() {
        if (this.score == 21){
            this.score = 20;
        }
        return (this.score >= this.count * 0.5);
    }
    
    public void resetCount() {
        this.count = 0;
    }
}
