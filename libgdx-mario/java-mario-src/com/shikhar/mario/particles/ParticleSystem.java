package com.shikhar.mario.particles;

import java.awt.Graphics;
import java.awt.Image;

import com.shikhar.mario.util.ImageUtilities;

public class ParticleSystem {
	private Particle particles[];
	private Image bitmap;
	public final int numParticles = 6;
	public boolean respawnParticle = false;
	public boolean isBursting = true;
    private boolean active;
    
    public ParticleSystem(int startXPos, int startYPos,	int numParticles) {
		bitmap=ImageUtilities.loadImage("items/particle_brick.png");
		this.active=true;
		particles = new Particle[numParticles];
		float speed=0.9f;
		float dx,dy;
		double theta;
		for (int i = 0; i < particles.length; i++) {
			float v=0.13f+(float) (0.4*speed*Math.random());
			theta=(0.15+0.7*i/particles.length)*Math.PI;
			dx=0.6f*v*(float) Math.cos(theta);
			dy=-v*(float) Math.sin(theta);
			particles[i] = new Particle(startXPos, startYPos,9*dx,8*dy, bitmap);
		}
	}

	public void doDraw(Graphics canvas,int offsetX,int offsetY) {
		for (int i = 0; i < particles.length; i++) {
			particles[i].doDraw(canvas,offsetX,offsetY);
		}
	}

	public void updatePhysics(int time) {
		for (int i = 0; i < particles.length; i++) {
			//Particle particle = particles[i];
			particles[i].updatePhysics(time);
		}
	}
	
	public boolean isActive(){
		return active;
	}
}
