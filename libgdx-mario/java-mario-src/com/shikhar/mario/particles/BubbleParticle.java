package com.shikhar.mario.particles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.shikhar.mario.core.tile.TileMap;
import com.shikhar.mario.objects.base.Creature;

public class BubbleParticle extends Creature {
	private float r = 4.0f;
	private Color borderColor = new Color(150, 150, 160, 240);
	private Color fillColor = new Color(90, 250, 250, 245);

	/**
	 * 
	 * @param x
	 *            Origin-X of particles
	 * @param y
	 *            Origin-Y of particles
	 * @param vx
	 *            Velocity-X of particles
	 * @param vy
	 *            Velocity-Y of particles
	 * @param timeSpan
	 */
	public BubbleParticle(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
		dy = -0.1f;
		setIsItem(true);
		setIsAlwaysRelevant(true);
		setIsCollidable(false);
		this.setGravityFactor(-0.6f);

	}

	public void update(int time) {
		if (y < 0)
			kill();
		dx = -1f + (float) (2 * Math.random());
		r += time / 1200.0f;
		if (dy > -3f) { // apply gravity...this must be done first
			dy = dy + getGravityFactor() * GRAVITY * time;
		}
		x += dx;
		y += dy;
		// super.update(time);

	}

	public void draw(Graphics g, int x, int y) {
		g.setColor(fillColor);
		//g.fillOval(x, y, (int) r, (int) r);
		g.setColor(borderColor);
		g.drawOval(x, y, (int) r, (int) r);
	}

	public void draw(Graphics g, int x, int y, int offsetX, int offsetY) {
		draw(g, x + offsetX, y + offsetY);
	}

	public BufferedImage getImage() {
		return null;
	}

	public int getHeight() {
		return 8;
	}

	public int getWidth() {
		return 8;
	}

	public void xCollide(Point p) {
	}

	@Override
	public void creatureXCollide() {
	}

	public void updateCreature(TileMap map, int time) {
		update(time);
	}
}
