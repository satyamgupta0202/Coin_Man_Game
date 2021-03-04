package com.zappycode.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture man[];
	int manstate=0;
	int pause =0;
	float velocity = 0;
	float gravity = 0.2f;
	float manY = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		man = new Texture[4];
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");
		manY=Gdx.graphics.getHeight() / 2;
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(pause<8){
			pause++;
		}
		else {
			pause=0 ;
			if (manstate < 3) {
				manstate++;
			} else {
				manstate = 0;
			}
		}
		velocity+=gravity;
		manY-=velocity;
		if(manY<=0){
			manY=0;
		}
			batch.draw(man[manstate], Gdx.graphics.getWidth() / 2 - man[manstate].getWidth() / 2, manY);
			batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();

	}
}
