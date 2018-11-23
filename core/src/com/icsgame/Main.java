package com.icsgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.icsgame.screens.*;

/* ======================== Main ================================
Extends a Game
Runs the program
Controls the Screens
================================================================== */

public class Main extends Game {

	// Screens
	ScrMenu scrMenu;
	ScrHighscores scrHighscores;
	ScrScratch scrScratch;
	public ScrSetup scrSetup;
	public ScrGame scrScrGame;
	public ScrLineOfSight scrScrLineOfSight;

	// Width and Height Of Screen
	public int nWidth, nHeight;

	@Override
	public void create () {
		nWidth = Gdx.graphics.getWidth();
		nHeight = Gdx.graphics.getHeight();

		scrMenu = new ScrMenu(this);
		scrHighscores = new ScrHighscores(this);
		scrScratch = new ScrScratch(this);
		scrScrGame = new ScrGame(this);
		scrScrLineOfSight = new ScrLineOfSight(this);

		scrSetup = new com.icsgame.screens.ScrSetup(this, scrScrGame);
		changeScreen(0);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
	}

	public void changeScreen(int nScreen) {
		switch (nScreen) {
			case 0:
				// Main Menu
				setScreen(scrMenu);
				break;
			case 1:
				// Highscores
				setScreen(scrHighscores);
				break;
			case 2:
				// Game Setup
				setScreen(scrSetup);
				break;
			case 3:
				// Game
				scrSetup.setScrGame(scrScrGame);
				setScreen(scrScrGame);
				break;
			case 4:
				// Scratch Screen
				setScreen(scrScratch);
				break;
			case 5:
				// Scratch Line Of Sight
				scrSetup.setScrGame(scrScrLineOfSight);
				scrScrLineOfSight.setupGame();
				setScreen(scrScrLineOfSight);
				break;
			default:
				break;
		}
	}
}
