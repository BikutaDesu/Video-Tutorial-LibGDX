package br.com.victor.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class ProcessadorInput implements InputProcessor{

	private Bolinha bolinha;
	private Jogo jogo;
	
	public ProcessadorInput(Bolinha bolinha, Jogo jogo) {
		this.bolinha = bolinha;
		this.jogo = jogo;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (jogo.getEstadoAtual() == EstadoJogo.MENU) {
			jogo.inicializarVariaveis();
			jogo.setEstadoAtual(EstadoJogo.JOGO);
		}else if (jogo.getEstadoAtual() == EstadoJogo.GAMEOVER) {
			jogo.setEstadoAtual(EstadoJogo.MENU);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == 0 && jogo.getEstadoAtual() == EstadoJogo.JOGO) {
			float distancia = Vector2.dst(bolinha.getX(), bolinha.getY(), screenX, Gdx.graphics.getHeight() - screenY);
			if (distancia <= bolinha.getRaio()) {
				bolinha.teleportar();
				jogo.aumentarPontuacao();
				bolinha.diminuirRaio();
			}else {
				if (jogo.getPontuacao() > 0) {
					jogo.diminuirPontuacao();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
