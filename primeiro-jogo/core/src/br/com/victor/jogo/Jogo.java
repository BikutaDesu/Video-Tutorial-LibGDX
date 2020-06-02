package br.com.victor.jogo;

import java.text.DecimalFormat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Jogo extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Integer pontuacao;
	private String txtPontuacao;
	
	private Float tempoRestante;
	private String txtTempoRestante;
	
	private Bolinha bolinha;
	
	private ProcessadorInput processadorInput;
	
	private BitmapFont fonte;
	
	private EstadoJogo estadoAtual = EstadoJogo.MENU;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		bolinha = new Bolinha(100f, 100f, 64f);
		
		processadorInput = new ProcessadorInput(bolinha, this);
		
		FreeTypeFontGenerator geradorFonte = new FreeTypeFontGenerator(Gdx.files.internal("fonte-pixel.ttf"));
		FreeTypeFontParameter configuracaoFonte = new FreeTypeFontParameter();
		configuracaoFonte.size = 22;
		configuracaoFonte.color = new Color(1,1,1,1);
		fonte = geradorFonte.generateFont(configuracaoFonte);
		
		resetarVariaveis();
		Gdx.input.setInputProcessor(processadorInput);
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined);
		
		switch (estadoAtual) {
		case MENU:
			Gdx.gl.glClearColor(.35f, .35f, .35f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			fonte.draw(batch, "JOGO DA BOLINHA", 270, Gdx.graphics.getHeight() - 200);
			fonte.draw(batch, "APERTE UMA TECLA PARA INICIAR", 250, Gdx.graphics.getHeight() - 350);
			batch.end();
			break;

		case JOGO:
			Gdx.gl.glClearColor(.35f, .35f, .35f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			update(Gdx.graphics.getDeltaTime());
			
			bolinha.draw();
			batch.begin();
			fonte.draw(batch, txtPontuacao, 20, Gdx.graphics.getHeight() - 20);
			fonte.draw(batch, txtTempoRestante, 20, Gdx.graphics.getHeight() - 60);
			batch.end();
			break;
		case GAMEOVER:
			Gdx.gl.glClearColor(0, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			fonte.draw(batch, "GAMEOVER", 270, Gdx.graphics.getHeight() - 200);
			fonte.draw(batch, "APERTE UMA TECLA PARA VOLTAR AO MENU", 250, Gdx.graphics.getHeight() - 350);
			batch.end();
			break;
		}
	}
	
	public void resetarVariaveis() {
		tempoRestante = 10f;
		pontuacao = 0;
		
		txtTempoRestante = "Tempo Restante: " + tempoRestante;
		atualizarTexto();
	}
	
	public void aumentarPontuacao() {
		pontuacao++;
		tempoRestante += 0.5f;
		atualizarTexto();
	}
	
	public void diminuirPontuacao() {
		pontuacao--;
		tempoRestante -= 0.3f;
		atualizarTexto();
	}
	
	private void atualizarTexto() {
		txtPontuacao = "Pontuacao: " + pontuacao;
	}
	
	public void update(Float delta) {
		if (tempoRestante <= 0) {
			estadoAtual = EstadoJogo.GAMEOVER;
		}
		bolinha.update(delta);
		tempoRestante -= delta;
		txtTempoRestante = "Tempo Restante: " + new DecimalFormat("###.#").format(tempoRestante);
	}
	
	@Override
	public void dispose () {
		fonte.dispose();
		batch.dispose();
		bolinha.dispose();
	}

	public EstadoJogo getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(EstadoJogo estadoAtual) {
		this.estadoAtual = estadoAtual;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}
}
