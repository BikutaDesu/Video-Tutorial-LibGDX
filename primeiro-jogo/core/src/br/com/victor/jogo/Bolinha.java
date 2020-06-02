package br.com.victor.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bolinha {

	private ShapeRenderer chapa;
	
	private Float x, y;
	private Float raio;
	
	private Float tempoMaximoTeleporte = 1.2f;
	private Float tempoMinimoTeleporte = .6f;
	private Float tempoProximoTeleporte;
	private Float tempoAtual = 0f;
	
	private Color cor;
	
	public Bolinha(Float x, Float y, Float raio) {
		chapa = new ShapeRenderer();
		cor = new Color(1,1,1,1);
		this.x = x;
		this.y = y;
		this.raio = raio;
		teleportar();
	}
	
	public void update(Float delta) {
		tempoAtual += delta;
		if (tempoAtual >= tempoProximoTeleporte) {
			teleportar();
		}
	}
	
	public void draw() {
		chapa.begin(ShapeType.Filled);
		chapa.setColor(cor);
		chapa.circle(x, y, raio);
		chapa.end();
	}
	
	public void teleportar() {
		x = MathUtils.random(raio, Gdx.graphics.getWidth() - raio);
		y = MathUtils.random(raio, Gdx.graphics.getHeight() - raio);
		cor.r = MathUtils.random(1f);
		cor.b = MathUtils.random(1f);
		cor.g = MathUtils.random(1f);
		tempoProximoTeleporte = MathUtils.random(tempoMinimoTeleporte, tempoMaximoTeleporte);
		tempoAtual = 0f;
	}
	
	public void diminuirRaio() {
		if (raio > 10) {
			raio -= MathUtils.random(3);
		}
	}
	
	public void dispose() {
		chapa.dispose();
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getRaio() {
		return raio;
	}

	public void setRaio(Float raio) {
		this.raio = raio;
	}
	
	
	
}
