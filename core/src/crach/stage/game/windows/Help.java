package crach.stage.game.windows;

import crach.stage.game.Assets;

public class Help extends WindowsGames{

	public Help() {
		super(Assets.jsonStringHelp.getString("title"));
		addContent();
		AddListener();
	}

	@Override
	public void addContent() {
		
	}
	
	@Override
	protected void AddListener() {
		super.AddListener();
	}
}