package drive2;

import java.awt.Rectangle;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class Drive2 extends PApplet {
	private static final long serialVersionUID = 1L;

	public boolean inMenu, inHelp, inPlay, inP, inH, inE, isDone, inB, goingUp,
			inPA, inPause, inPAB, inEAP, inR, inGO, inEAPG, inRG = false;
	public int yPos = -300;
	public int xPos1, xPos2, score, timer, x, j, imageType, imageType2, highscore, color, done = 0;
	float multiplier = 0;
	public PImage carIcon, menuBackground, road, car, obst1, obst2, obst3, obst4, obst5, obst6,
			image, image2, car1, car2, car3;
	public AudioPlayer aPlayer;
	public Minim minim;
	public Rectangle obs1, obs2;
	public PFont font;

	public void setup() {
		multiplier = 1;
		frameRate(60);
		size(400, 600);
		background(0, 0, 0);
		
		font = createFont("/fonts/Oswald-Bold.ttf", 50);

		carIcon = loadImage("/images/CarIcon.png");
		menuBackground = loadImage("/images/MainMenuBack.png");
		road = loadImage("/images/Road.jpg");
		
		car1 = loadImage("/images/Car.png");
		car2 = loadImage("/images/Car2.png");
		car3 = loadImage("/images/Car3.png");
		
		car = car1;

		obst1 = loadImage("/images/Barrels.png");
		obst2 = loadImage("/images/CarTraffic.png");
		obst3 = loadImage("/images/Fence.png");
		
		obst4 = loadImage("/images/Elite.png");
		obst5 = loadImage("/images/Ghost.png");
		obst6 = loadImage("/images/Grunt.png");

		stroke(0, 0, 0);
		fill(0, 0, 0);
		tint(0, 0);

		obs1 = new Rectangle(-100, -100, 5, 5);
		obs2 = new Rectangle(-100, -100, 5, 5);

		image = car;
		image2 = car;

		Random r = new Random();
		int mState = r.nextInt(2);
		System.out.println("Playing music ID: " + mState);

		minim = new Minim(this);

		if (mState == 0) {
			aPlayer = minim.loadFile("/music/MenuMusic.mp3", 2048);
		} else if (mState == 1) {
			aPlayer = minim.loadFile("/music/MainMenuMusic2.mp3", 2048);
		}
		aPlayer.loop();
		aPlayer.play();

		// ENABLE WHEN READY!
		goingUp = true;

		// TEMP DEV SPEEDUP
		inMenu = false;
	}

	public void draw() {
		textFont(font);
		// Splashscreen
		if (goingUp) {
			if (x < 256)
				splashScreen(true, false);
			if (x == 255)
				goingUp = false;
		} else if (!goingUp) {
			if (x == 255)
				splashScreen(false, true);
			if (x > -1)
				splashScreen(false, false);
			if (x == 0)
				inMenu = true;
		}

		// Menu
		if (inMenu) {
			checkOverlap();
		}

		if (inHelp) {
			helpScreen();
		}

		if (inPlay) {
			playGame();
		}

		if (inPause) {
			pauseMenu();
		}

		if (inGO) {
			gameOver();
		}

	}

	public void mousePressed() {
		if (inP) {
			inMenu = false;
			inPlay = true;
			inP = false;
			aPlayer.pause();
			aPlayer = minim.loadFile("/music/GameMusic.mp3", 2048);
			aPlayer.loop();
			aPlayer.play();
			image(car, 180, 470);
		}

		if (inH) {
			inMenu = false;
			inHelp = true;
			inH = false;
		}

		if (inE) {
			System.exit(0);
		}

		if (inB) {
			inB = false;
			inMenu = true;
			inHelp = false;
		}

		if (inPA) {
			inPA = false;
			inPlay = false;
			inPause = true;

			aPlayer.pause();

		}

		if (inPAB) {
			inPAB = false;
			inPause = false;
			inPlay = true;

			aPlayer.pause();
			aPlayer = minim.loadFile("/music/GameMusic.mp3", 2048);
			aPlayer.loop();
			aPlayer.play();
			image(car, 180, 470);
		}

		if (inEAP) {
			inEAP = false;
			reset();
			inPlay = false;
			inPause = false;
			inMenu = true;
			
			Random r = new Random();
			int mState = r.nextInt(2);
			System.out.println("Playing music ID: " + mState);

			if (mState == 0) {
				aPlayer = minim.loadFile("/music/MenuMusic.mp3", 2048);
			} else if (mState == 1) {
				aPlayer = minim.loadFile("/music/MainMenuMusic2.mp3", 2048);
			}
			aPlayer.loop();
			aPlayer.play();
		}

		if (inR) {
			inR = false;

			inPause = false;
			
			aPlayer.pause();
			aPlayer = minim.loadFile("/music/GameMusic.mp3", 2048);
			aPlayer.loop();
			aPlayer.play();
			
			reset();
		}
		
		if (inEAPG) {
			inEAPG = false;
			reset();
			inGO = false;
			inPlay = false;
			inMenu = true;
			
			Random r = new Random();
			int mState = r.nextInt(2);
			System.out.println("Playing music ID: " + mState);

			if (mState == 0) {
				aPlayer = minim.loadFile("/music/MenuMusic.mp3", 2048);
			} else if (mState == 1) {
				aPlayer = minim.loadFile("/music/MainMenuMusic2.mp3", 2048);
			}
			aPlayer.loop();
			aPlayer.play();
		}

		if (inRG) {
			inRG = false;
			
			aPlayer.pause();
			aPlayer = minim.loadFile("/music/GameMusic.mp3", 2048);
			aPlayer.loop();
			aPlayer.play();

			inGO = false;
			reset();
		}
	}

	public void helpScreen() {
		inB = false;
		image(menuBackground, 0, 0);
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(255, 0, 255);
		rect(95, 65, 200, 100, 20);
		textSize(50);
		fill(0, 255, 255);
		text("Help", 130, 130);
		cursor(POINT);

		// BACK BUTTON
		fill(0, 255, 255);
		rect(95, 480, 200, 100, 20);

		fill(0, 255, 0);
		textSize(30);
		text("Press Left and \n Right arrows \n to move and \n avoid obstacles.", 70, 275);

		// BACK TEXT
		fill(255, 0, 0);
		textSize(50);
		text("Back", 145, 545);

		if (mouseX > 95 && mouseX < 295 && mouseY > 480 && mouseY < 580) {
			fill(0, 240, 240);
			strokeWeight(30);
			stroke(240, 240, 0);

			// EXIT
			rect(95, 480, 200, 100, 20);

			fill(240, 0, 0);
			textSize(60);

			// EXIT
			text("Back", 135, 545);

			cursor(HAND);
			inB = true;
		}
	}

	public void playGame() {
		pushMatrix();
		translate(0, yPos + 200);
		rect(obs1.x, yPos - 300, obs1.width, obs1.height, 5);
		rect(obs2.x, yPos - 300, obs2.width, obs2.height, 5);
		popMatrix();
		
		if(score > 66000) {
			if(done == 0) {
			aPlayer = minim.loadFile("/music/GameMusic.mp3");
			aPlayer.loop();
			aPlayer.play();
			}
			
			done++;
			
		}

		timer++;
		inPA = false;
		cursor(POINT);
		image(road, 0, 0);

		pushMatrix();
		translate(0, yPos);
		image(road, 0, yPos);
		popMatrix();
		yPos += 2;

		if (yPos == 0)
			yPos = -300;
		
		if(score > highscore) highscore = score;

		textSize(30);
		fill(0, 255, 255);
		text("Score: " + score, 10, 50);
		text("Highscore: " + highscore, 10, 100);

		spawnObst();

		pushMatrix();
		translate(0, yPos + 300);
		image(image, obs1.x, yPos + 300);
		image(image2, obs2.x, yPos + 300);
		popMatrix();

		pushMatrix();
		translate(180, 470);
		image(car, -30 + xPos1, -60);
		popMatrix();

		if (xPos1 == -109)
			xPos2 = 50;
		if (xPos1 == 0)
			xPos2 = 170;
		if (xPos1 == 129)
			xPos2 = 295;

		j++;

		if (j > 91 && j < 112) {
			System.out.println("MARK");
			if (xPos2 > obs1.x && xPos2 < obs1.x + obs1.width) {
				System.out.println("Hit obstacle at: " + obs1.x);
				inGO = true;
			} else if (xPos2 > obs2.x && xPos2 < obs2.x + obs2.width) {
				System.out.println("Hit obstacle at: " + obs2.x);
				inGO = true;
			} else {
				score += 5 * multiplier;
			}
		}

		// PAUSE BUTTON
		strokeWeight(10);
		stroke(255, 255, 0);
		fill(0, 255, 255);
		rect(340, 540, 50, 50, 5);
		strokeWeight(0);
		fill(255, 0, 0);
		rect(352.5f, 550, 10, 30, 5);
		rect(367.5f, 550, 10, 30, 5);

		if (mouseX > 340 && mouseX < 390 && mouseY > 540 && mouseY < 590) {
			inPA = true;
			cursor(HAND);

			strokeWeight(15);
			stroke(240, 240, 0);
			fill(0, 240, 240);
			rect(340, 540, 50, 50, 5);
			strokeWeight(0);
			fill(240, 0, 0);
			rect(352.5f, 550, 10, 30, 5);
			rect(367.5f, 550, 10, 30, 5);
		}
		
		
		if(score > highscore) highscore = score;

		textSize(30);
		fill(0, 255, 255);
		text("Score: " + score, 10, 50);
		text("Highscore: " + highscore, 10, 100);
		text("Multiplier: " + multiplier, 10, 580);

	}

	public void gameOver() {
		image(menuBackground, 0, 0);
		inPlay = false;
		inRG = false;
		inEAPG = true;
		aPlayer.pause();
		
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(255, 0, 255);
		rect(45, 65, 300, 100, 20);
		tint(255, 255);
		fill(0, 255, 255);
		textSize(50);
		text(" Game Over", 65, 130);
		
		text("Your Score: " + score, 20, 550);

		// EXIT / RESTART
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(0, 255, 255);
		textSize(50);

		// RESTART
		rect(95, 215, 200, 100, 20);

		// EXIT
		rect(95, 345, 200, 100, 20);

		fill(255, 0, 0);

		// RESTART
		text("Restart", 110, 280);

		// EXIT
		text("Exit", 140, 410);

		if (mouseX > 95 && mouseX < 295 && mouseY > 215 && mouseY < 315) {
			inRG = true;
			cursor(HAND);

			strokeWeight(30);
			stroke(240, 240, 0);
			textSize(50);
			fill(0, 240, 240);
			rect(95, 215, 200, 100, 20);

			fill(240, 0, 0);
			text("Restart", 110, 280);
		}

		// EXIT
		if (mouseX > 95 && mouseX < 295 && mouseY > 345 && mouseY < 445) {
			inEAPG = true;
			cursor(HAND);

			strokeWeight(30);
			stroke(240, 240, 0);
			textSize(60);
			fill(0, 240, 240);
			rect(95, 345, 200, 100, 20);

			fill(240, 0, 0);
			text("Exit", 140, 410);
		}

	}

	public void reset() {
		cursor(POINT);
		yPos = -300;
		xPos1 = 0;
		xPos2 = 0;
		score = 0;
		timer = 0;
		j = 0;
		multiplier = 1;
		done = 0;

		obs1 = new Rectangle(-100, -100, 5, 5);
		obs2 = new Rectangle(-100, -100, 5, 5);

		image = car;
		image2 = car;

		inPlay = true;
	}

	public void pauseMenu() {
		image(road, 0, 0);
		cursor(POINT);
		inPlay = false;
		inEAP = false;
		inPAB = false;
		inR = false;
		
		textSize(30);
		fill(0, 255, 255);
		text("Score: " + score, 10, 50);
		
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(255, 0, 255);
		rect(95, 65, 200, 100, 20);
		tint(255, 255);
		fill(0, 255, 255);
		textSize(50);
		text("Paused", 125, 130);

		// EXIT / RESTART
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(0, 255, 255);
		textSize(50);

		// RESTART
		rect(95, 215, 200, 100, 20);

		// EXIT
		rect(95, 345, 200, 100, 20);

		fill(255, 0, 0);

		// RESTART
		text("Restart", 110, 280);

		// EXIT
		text("Exit", 140, 410);

		// PAUSE BUTTON
		strokeWeight(10);
		stroke(255, 255, 0);
		fill(0, 255, 255);
		rect(340, 540, 50, 50, 5);
		strokeWeight(0);
		fill(255, 0, 0);
		rect(352.5f, 550, 10, 30, 5);
		rect(367.5f, 550, 10, 30, 5);

		if (mouseX > 95 && mouseX < 295 && mouseY > 215 && mouseY < 315) {
			inR = true;
			inEAP = false;
			inPAB = false;
			cursor(HAND);

			strokeWeight(30);
			stroke(240, 240, 0);
			textSize(50);
			fill(0, 240, 240);
			rect(95, 215, 200, 100, 20);

			fill(240, 0, 0);
			text("Restart", 110, 280);
		}

		// EXIT
		if (mouseX > 95 && mouseX < 295 && mouseY > 345 && mouseY < 445) {
			inEAP = true;
			inR = false;
			inPAB = false;
			cursor(HAND);

			strokeWeight(30);
			stroke(240, 240, 0);
			textSize(60);
			fill(0, 240, 240);
			rect(95, 345, 200, 100, 20);

			fill(240, 0, 0);
			text("Exit", 140, 410);
		}

		if (mouseX > 340 && mouseX < 390 && mouseY > 540 && mouseY < 590) {
			inPAB = true;
			inR = false;
			inEAP = false;
			cursor(HAND);

			strokeWeight(15);
			stroke(240, 240, 0);
			fill(0, 240, 240);
			rect(340, 540, 50, 50, 5);
			strokeWeight(0);
			fill(240, 0, 0);
			rect(352.5f, 550, 10, 30, 5);
			rect(367.5f, 550, 10, 30, 5);
		}
	}

	public void spawnObst() {
		Random r = new Random();
		int row1 = r.nextInt(3);
		int row2 = r.nextInt(3);
		imageType = r.nextInt(6);
		imageType2 = r.nextInt(6);

		if (row2 == row1)
			row2 = -100;

		if (row1 == 0)
			row1 = 40;
		if (row1 == 1)
			row1 = 165;
		if (row1 == 2)
			row1 = 290;

		if (row2 == 0)
			row2 = 40;
		if (row2 == 1)
			row2 = 165;
		if (row2 == 2)
			row2 = 290;

		if (timer == 150) {
			System.out.println("[" + millis() + "]"
					+ "Spawning obstacle(s) at: " + row1 + " and at: " + row2);
			obs1 = new Rectangle(row1, 100, 50, 50);
			obs2 = new Rectangle(row2, 100, 50, 50);
			timer = 0;
			j = 0;
			multiplier += 0.5;

			if (imageType == 0)
				image = obst1;
			if (imageType == 1)
				image = obst2;
			if (imageType == 2)
				image = obst3;
			if(imageType == 3)
				image = obst4;
			if(imageType == 4)
				image = obst5;
			if(imageType == 5)
				image = obst6;

			if (imageType2 == 0)
				image2 = obst1;
			if (imageType2 == 1)
				image2 = obst2;
			if (imageType2 == 2)
				image2 = obst3;
			if(imageType2 == 3)
				image2 = obst4;
			if(imageType2 == 4)
				image2 = obst5;
			if(imageType2 == 5)
				image2 = obst6;
		}
	}

	public void keyPressed() {
		if (key == 'd' || keyCode == RIGHT) {

			if (xPos1 == 0) {
				xPos1 = 129;
			}

			if (xPos1 == -109) {
				xPos1 = 0;
			}

		}
		if (key == 'a' || keyCode == LEFT) {
			if (xPos1 == 0)
				xPos1 = -109;

			if (xPos1 == 129)
				xPos1 = 0;
		}
		
		if(keyCode == TAB) {
			color++;
			if(color == 1) car = car1;
			if(color == 2) car = car2;
			if(color == 3) car = car3;
			if(color == 4) color = 0;
		}
	}

	public void splashScreen(boolean isUp, boolean sleep) {
		if (isUp) {
			image(menuBackground, 0, 0);
			strokeWeight(20);
			stroke(x, x, 0);
			rect(95, 65, 200, 100, 20);
			textSize(50);
			fill(x, 0, 0);
			text("Drive", 130, 130);
			fill(0, x, x);
			image(carIcon, 130, 225);
			tint(x, x);
			text("Patrick J", 100, 500);
			textSize(20);
			text("All rights reserved � 2015", 70, 550);
			x++;
		}
		if (!isUp) {
			if (sleep) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			image(menuBackground, 0, 0);
			strokeWeight(20);
			stroke(x, x, 0);
			rect(95, 65, 200, 100, 20);
			textSize(50);
			fill(x, 0, 0);
			text("Drive", 130, 130);
			fill(0, x, x);
			image(carIcon, 130, 225);
			tint(x, x);
			text("Patrick J", 100, 500);
			textSize(20);
			text("All rights reserved � 2015", 70, 550);
			x--;
		}
	}

	public void checkOverlap() {
		inE = false;
		drawButtons();
		cursor(POINT);

		if (mouseX > 95 && mouseX < 295 && mouseY > 215 && mouseY < 315) {
			fill(0, 240, 240);
			strokeWeight(30);
			stroke(240, 240, 0);

			// PLAY
			rect(95, 215, 200, 100, 20);

			fill(240, 0, 0);
			textSize(60);

			// PLAY
			text("Play", 135, 280);

			cursor(HAND);
			inP = true;
			inH = false;
			inE = false;
		}

		if (mouseX > 95 && mouseX < 295 && mouseY > 345 && mouseY < 445) {
			fill(0, 240, 240);
			strokeWeight(30);
			stroke(240, 240, 0);

			// HELP
			rect(95, 345, 200, 100, 20);

			fill(240, 0, 0);
			textSize(60);

			// HELP
			text("Help", 135, 410);

			cursor(HAND);
			inP = false;
			inH = true;
			inE = false;
		}

		if (mouseX > 95 && mouseX < 295 && mouseY > 480 && mouseY < 580) {
			fill(0, 240, 240);
			strokeWeight(30);
			stroke(240, 240, 0);

			// EXIT
			rect(95, 480, 200, 100, 20);

			fill(240, 0, 0);
			textSize(60);

			// EXIT
			text("Exit", 135, 545);

			cursor(HAND);
			inP = false;
			inH = false;
			inE = true;
		}
	}

	public void drawButtons() {
		image(menuBackground, 0, 0);
		strokeWeight(20);
		stroke(255, 255, 0);
		fill(255, 0, 255);
		rect(95, 65, 200, 100, 20);
		tint(255, 255);
		fill(0, 255, 255);
		textSize(50);
		text("Drive", 130, 130);

		// Drawing Buttons
		fill(0, 255, 255);

		// PLAY
		rect(95, 215, 200, 100, 20);

		// HELP
		rect(95, 345, 200, 100, 20);

		// EXIT
		rect(95, 480, 200, 100, 20);

		// Drawing Text
		fill(255, 0, 0);

		// PLAY
		text("Play", 140, 280);

		// HELP
		text("Help", 140, 410);

		// EXIT
		text("Exit", 145, 545);
	}

	public void stop() {
		aPlayer.close();
		minim.stop();
		super.stop();
	}

}
