package crach.stage.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import crach.stage.game.CrachGame;

public class ScreenGame extends ScreenAdapter {
	
	
	protected Viewport viewGamePort;
	protected Stage stage;
	
	public ScreenGame() {
		viewGamePort = new ExtendViewport(CrachGame.Width, CrachGame.Height);
		stage = new Stage(viewGamePort);
		//stage.getBatch().setShader();
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
	}
		
	@Override
	public void resize(int width, int height) {
		viewGamePort.update(width, height,true);
	}
	
	protected void drawStage(float delta) {
		stage.draw();
		stage.act(delta);
	}
	public void addActor(Actor actor) {
		stage.addActor(actor);
	}
	public Viewport getViewGamePort() {
		return viewGamePort;
	}
	public Stage getStage() {
		return stage;
	}
}
