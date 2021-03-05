package com.zappycode.coinman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	BitmapFont font;
	Texture man[];
	int manstate=0;
	int pause =0;
	float velocity = 0;
	float gravity = 0.2f;
	float manY = 0 ;
	int coinCount =0;
	int bombCount =0 ;
	ArrayList <Integer> coinsX = new ArrayList<>();
	ArrayList <Integer> coinsY = new ArrayList<>();
	ArrayList <Integer> bombsX = new ArrayList<>();
	ArrayList <Integer>  bombsY = new ArrayList<>();
	ArrayList<Rectangle> coinRectangle = new ArrayList<>();
	ArrayList<Rectangle> bombRectangle = new ArrayList<>();
	Texture coin;
	Texture bomb;
	Random random;
	Rectangle manRectangle;
	int score = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		man = new Texture[4];
		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		man[0] = new Texture("frame-1.png");
		man[1] = new Texture("frame-2.png");
		man[2] = new Texture("frame-3.png");
		man[3] = new Texture("frame-4.png");
		manY=Gdx.graphics.getHeight() / 2;
		manRectangle = new Rectangle();
		random = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().scale(10);
	}

	public void makeBomb(){
		float height = random.nextFloat()* (Gdx.graphics.getHeight());
		int width = Gdx.graphics.getWidth();
		bombsX.add(width);
		bombsY.add((int)(height));
	}
	public void makeCoin(){
		float height = random.nextFloat()* (Gdx.graphics.getHeight());
		int width = Gdx.graphics.getWidth();
		coinsX.add(width);
		coinsY.add((int)(height));
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		//bomb
		if(bombCount<260){
			bombCount++;
		}
		else{
			bombCount=0;
			makeBomb();
		}

		bombRectangle.clear();
		for(int i=0;i<bombsX.size();i++){
			batch.draw(bomb,bombsX.get(i),bombsY.get(i));
			bombsX.set(i,bombsX.get(i)-8);
			bombRectangle.add(new Rectangle(bombsX.get(i),bombsY.get(i),bomb.getWidth(),bomb.getHeight()));
		}

		//coins

		if(coinCount < 100){
			coinCount++;
		}
		else{
			coinCount=0;
			makeCoin();
		}

		coinRectangle.clear();
		for(int i=0;i<coinsX.size();i++){
			batch.draw(coin,coinsX.get(i),coinsY.get(i));
			coinsX.set(i,coinsX.get(i)-6);
			coinRectangle.add(new Rectangle(coinsX.get(i),coinsY.get(i),coin.getWidth(),coin.getHeight()));
		}



		if(Gdx.input.justTouched()){
			velocity=-10;
		}
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
			manRectangle = new Rectangle(Gdx.graphics.getWidth() / 2 - man[manstate].getWidth() / 2, manY , man[manstate].getWidth(),man[manstate].getHeight());

			for(int i=0;i<coinRectangle.size();i++){
				if(Intersector.overlaps(manRectangle,coinRectangle.get(i))){
					//Gdx.app.log("coins-Overlapped","COLLISION!!!!!");
					score++;
					coinRectangle.remove(i);
					coinsX.remove(i);
					coinsY.remove(i);
					break;
				}
			}

		for(int i=0;i<bombRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,bombRectangle.get(i))){
				Gdx.app.log("Bomb-Overlapped","COLLISION!!!!!");
			}
		}

		font.draw(batch,String.valueOf(score),100,200);
			batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();

	}
}
