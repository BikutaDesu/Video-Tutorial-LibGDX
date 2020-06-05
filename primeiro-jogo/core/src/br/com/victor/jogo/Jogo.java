package br.com.victor.jogo;

import java.text.DecimalFormat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Jogo extends ApplicationAdapter {
	SpriteBatch batch;

	private Integer pontuacao;
	private String txtPontuacao;

	private Float tempoRestante;
	private String txtTempoRestante;

	private Bolinha bolinha;

	private ProcessadorInput processadorInput;

	private BitmapFont font;

	private EstadoJogo estadoAtual = EstadoJogo.MENU;

	@Override
	public void create() {
		batch = new SpriteBatch();

		bolinha = new Bolinha(100f, 100f, 64f);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonte-pixel.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 22;
		parameter.color = Color.WHITE;
		font = generator.generateFont(parameter);

		processadorInput = new ProcessadorInput(bolinha, this);
		Gdx.input.setInputProcessor(processadorInput);
	}

	@Override
	public void render() {
		switch (estadoAtual) {
		case MENU:
			Gdx.gl.glClearColor(0.35f, 0.35f, 0.35f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "JOGO DAS BOLINHAS", 300, 400);
			font.draw(batch, "APERTE UMA TECLA PARA INICIAR!!", 250, 300);
			batch.end();
			break;
		case JOGO:
			update(Gdx.graphics.getDeltaTime());
			
			Gdx.gl.glClearColor(0.35f, 0.35f, 0.35f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, txtPontuacao, 20, Gdx.graphics.getHeight() - 20);
			font.draw(batch, txtTempoRestante, 20, Gdx.graphics.getHeight() - 50);
			batch.end();
			bolinha.draw();
			break;
		case GAMEOVER:
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "GAMEOVER", 300, 400);
			font.draw(batch, "APERTE UMA TECLA PARA VOLTAR AO MENU!!", 200, 300);
			batch.end();
			break;
		}
		
	}

	private void update(float delta) {
		bolinha.update(delta);
		tempoRestante -= delta;
		if (tempoRestante <= 0) {
			estadoAtual = EstadoJogo.GAMEOVER;
		}
		txtTempoRestante = "Tempo: " + new DecimalFormat("##.#").format(tempoRestante);
	}

	public void aumentarPontuacao() {
		pontuacao++;
		tempoRestante += 0.5f;
		atualizarPontuacao();
	}

	public void diminuirPontuacao() {
		pontuacao--;
		tempoRestante -= 0.3f;
		atualizarPontuacao();
	}

	private void atualizarPontuacao() {
		txtPontuacao = "Pontos: " + pontuacao;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void inicializarVariaveis() {
		pontuacao = 0;
		tempoRestante = 10f;
		txtPontuacao = "Pontos: 0";
		txtTempoRestante = "Tempo: ";
		bolinha.setRaio(64f);
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public EstadoJogo getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(EstadoJogo estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
	
	

}
