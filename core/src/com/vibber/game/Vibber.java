package com.vibber.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Vibber extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    Vector2 target = new Vector2(960, 540);
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
        Gdx.input.cancelVibrate();

        /// target updates
        int time = (int)(System.currentTimeMillis() % 100000);
        double newx = 960 + Math.cos(time / 1000f) * 300;
        double newy = 540 + Math.sin(time / 1000f) * 300;
        target = new Vector2((float)newx, (float)newy);
        Gdx.app.log("Vibber", "time: " + System.currentTimeMillis() / 10f);

        /// vibration

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        double strength = 0;

        double dist = Math.sqrt(Math.pow((double)target.x - x, 2) + Math.pow((double)target.y - y, 2));

        strength = dist / 1400;

        if (Gdx.input.isTouched()) {
            //Gdx.input.vibrate(genVibList(Gdx.input.getX() / 1920.0), 0);
            Gdx.input.vibrate(genVibList(strength), 0);
        }

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /// drawing

		batch.begin();

		//batch.draw(img, target.x - 128, target.y - 128);

        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public long[] genVibList(double strength) {
        int base = 25;

        long on = (long)(strength * base);

        Gdx.app.log("Vibber", "on: " + on);

        long[] vibList = {0, on, base - on, on, base - on};

        return vibList;
    }
}
