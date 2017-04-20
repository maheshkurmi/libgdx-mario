package com.mygdx.game.mario.objects.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.mario.MarioResourceManager;


public class ParticleSystem {
	private Particle particles[];
	private TextureRegion TextureRegion;
	public final int numParticles = 6;
	public boolean respawnParticle = false;
	public boolean isBursting = true;
    private boolean active;
    public ParticleSystem(int startXPos, int startYPos,	int numParticles) {
		TextureRegion=MarioResourceManager.instance.particles.brick_particle;
		this.active=true;
		particles = new Particle[numParticles];
		float speed=0.2f;
		float dx,dy;
		double theta;
		for (int i = 0; i < particles.length; i++) {
			float v=0.13f+(float) (0.4*speed*Math.random());
			theta=(0.15+0.7*i/particles.length)*Math.PI;
			dx=0.6f*v*(float) Math.cos(theta);
			dy=-v*(float) Math.sin(theta);
			particles[i] = new Particle(startXPos, startYPos,dx,dy, TextureRegion);
		}
	}

	public void doDraw(SpriteBatch batch,int offsetX,int offsetY) {
		//if (count>2000) return;
		for (int i = 0; i < particles.length; i++) {
			particles[i].doDraw(batch,offsetX,offsetY);
		}
	}

	public void updatePhysics(int time) {
		//if (count>2000) return;
		//count++;
		//if (count % 10!=0) return;
		for (int i = 0; i < particles.length; i++) {
			//Particle particle = particles[i];
			particles[i].updatePhysics(time);
		}
	}
	
	
	public boolean isActive(){
		return active;
	}
}