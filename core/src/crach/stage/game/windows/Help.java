package crach.stage.game.windows;

import crach.stage.game.Assest;

public class Help extends WindowsGames{

	public Help() {
		super(Assest.StringHelp.getString("title"));
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