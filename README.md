# Temple Stadium Builder
### CIS 2168 Project

![Cover Image for Game](http://nrobinson.co/game-cover.png)


This was a freestyle project for CIS 2168. It is a side scrolling game where you collect bricks to score points. 20 bricks will fall, and you must catch at least 10 of them to advance. There are 3 stages, each of which get increasingly faster. If you make it past the 3rd stage, the stadium has been built and you win!

This game takes advantage of the build in slider in SimpleGUI. Which each iteration of a loop, I calculate a random position inside the GUI to place the brick. Then, with another SimpleGUI variable, I can quickly draw and erase the brick every milisecond to simulate the fall. The function startFall(int difficulty) uses a simple switch case to tell which stage the user is currently in, and how fast the brick should drop.


Nick Robinson


