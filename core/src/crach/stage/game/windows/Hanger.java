package crach.stage.game.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import crach.stage.game.Assest;
import crach.stage.game.CrachGame;
import crach.stage.game.Screen.Tools.ProgressUi;
import crach.stage.game.Screen.Tools.ViewTank;
import crach.stage.game.windows.dialog.DialogQuestion;

public class Hanger extends WindowsGames{
	private Button parent;
	
	private Table GroupGun;
	private HorizontalGroup SelectColor;
	private ScrollPane[] ScrllGun;
	private Stack StackScrll;
	
	private ViewTank viewTank;
	
	private Label LHpHull,LSpeedHull,LArmorHull,LDamageHull;
	private int speed,energy,protection,rotationGun,refire;
	private Button BcolorA,BcolorB,BcolorC,BcolorD;
	private SelectItems SGunA,SGunB,SHull,STrack;
	private Image ColorI;
	
	private ButtonGroup<Items>[] groupItems;
	private ButtonGroup<SelectItems> SelectItems;
	private ButtonGroup<Button> SeColor;

	public static enum TypeItem{
		Gun_A,Gun_B, Hull,Track
	}
	
	@SuppressWarnings("unchecked")
	public Hanger(Button paButton) {
		super(Assest.StringHanger.getString("title"));
        this.getTitleTable().getCell(getTitleLabel()).maxSize(600,100).padBottom(30);
		this.parent = paButton;
        GroupGun = new Table();
        SelectColor = new HorizontalGroup();
        SelectColor.align(Align.right).space(3);
        StackScrll = new Stack();
        viewTank = new ViewTank();
             
        LHpHull = new Label("", Assest.Style, "inSqure");
        LSpeedHull = new Label("", Assest.Style, "inSqure");
        LArmorHull = new Label("", Assest.Style, "inSqure");
        LDamageHull = new Label("", Assest.Style, "inSqure");
        
        BcolorA = new Button(Assest.Style, "Color_A_BTN");
        BcolorB = new Button(Assest.Style, "Color_B_BTN");
        BcolorC = new Button(Assest.Style, "Color_C_BTN");
        BcolorD = new Button(Assest.Style, "Color_D_BTN");
        
        ColorI = new Image(Assest.Style, "Icon/Color_Icon");

        SGunA = new SelectItems(TypeItem.Gun_A);
        SGunB =  new SelectItems(TypeItem.Gun_B); 
        SHull = new SelectItems(TypeItem.Hull);
        STrack = new SelectItems(TypeItem.Track);
        
        ScrllGun = new ScrollPane[4];
        groupItems = new ButtonGroup[4];
        addContent();
		AddListener();
		updateLabel();
	}

	@Override
	public void addContent() {
		//SelectColor
		SelectColor.addActor(BcolorA);
		SelectColor.addActor(BcolorB);
		SelectColor.addActor(BcolorC);
		SelectColor.addActor(BcolorD);
		SelectColor.addActor(ColorI);
		SeColor = new ButtonGroup<Button>(BcolorA,BcolorB,BcolorC,BcolorD);
		SeColor.getButtons().get(CrachGame.getColorSelect()).setChecked(true);

		//select Type Items
		GroupGun.defaults().size(190,142).pad(1).space(1).fill();
		GroupGun.add(SGunA).row();
		GroupGun.add(SGunB).row();
		GroupGun.add(SHull).row();
		GroupGun.add(STrack).row();
		GroupGun.pack();
		SelectItems = new ButtonGroup<SelectItems>(SGunA,SGunB,SHull,STrack);
		SelectItems.getButtons().get(MathUtils.random(3)).setChecked(true);
		//add Items
		VerticalGroup Items;
		for(int i = 0 ;i<ScrllGun.length ;i++) {
		Items = new VerticalGroup();
		Items.space(16);
		groupItems[i] = new ButtonGroup<Items>();
		for(int j=0;j<sizeByItems(TypeItem.values()[i]) ;j++) {
			Items items = new Items(j,SelectItems.getButtons().get(i).getType());
			Items.addActor(items);
			groupItems[i].add(items);
		}
		ScrllGun[i] =new ScrollPane(Items, Assest.Style);
		ScrllGun[i].setScrollingDisabled(true, false);
		ScrllGun[i].setScrollBarPositions(true,false);
		ScrllGun[i].setVariableSizeKnobs(false);
		ScrllGun[i].setFadeScrollBars(false);
		
		StackScrll.add(ScrllGun[i]);
		}
		groupItems[0].getButtons().get(CrachGame.getGunASelect()).setChecked(true);
		groupItems[1].getButtons().get(CrachGame.getGunBSelect()).setChecked(true);
		groupItems[2].getButtons().get(CrachGame.getHullSelect()).setChecked(true);
		groupItems[3].getButtons().get(CrachGame.getTrackSelect()).setChecked(true);
				
		VisibleTable();
        float sizeR1 = 90,spaceR1=5,sizeR4=120,spaceR4=20;		
		float heightC3= sizeR1*4+spaceR1*3;
		float padC3=heightC3-sizeR1;
		float heightC4= heightC3+spaceR4+sizeR4;
		float padC4= heightC4-sizeR1;
		padBottom(40);
        defaults().minSize(sizeR1).center().space(spaceR1);
        columnDefaults(0).prefSize(90);
	    add().size(55).row();
        add(new Image(Assest.Style, "Icon/HP_Icon")).spaceRight(10);add(LHpHull).prefSize(200,90);
        
        add(viewTank).prefSize(300,heightC3+30).padBottom(-padC3).padTop(-30);
        add(StackScrll).prefSize(250,heightC4+30).padBottom(-padC4).padTop(-30);
        add(GroupGun).size(200,heightC4+60).padBottom(-padC4).padTop(-60).padRight(20).fill().row();

        add(new Image(Assest.Style, "Icon/Speed_Icon")).spaceRight(10);add(LSpeedHull).prefWidth(200).row();
        add(new Image(Assest.Style, "Icon/Armor_Icon")).spaceRight(10);add(LArmorHull).prefWidth(200).row();
        add(new Image(Assest.Style, "Icon/Damage_Icon")).spaceRight(10);add(LDamageHull).prefWidth(200).row();
        add(SelectColor).colspan(3).spaceTop(spaceR4).prefHeight(sizeR4).fillX().right();
        pack();
	}
	
	protected void AddListener() {
		super.AddListener();
  		ClickListener C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  				Assest.checkOnClick.play();
  				VisibleTable();
  			}
  		};
  		for(Button B : SelectItems.getButtons())
  			B.addListener(C);
  		
  		C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  				Assest.checkOnClick.play();
  				updateTrackColor();
  			}
  		};
  		

  		for(Button B : SeColor.getButtons())
  			B.addListener(C);
  		
  		C = new ClickListener() {
  			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
  				final Items items = ((Items) (event.getListenerActor()));
  				if(items.isDisabled()) {				
  	  				Assest.checkOfClick.play();
					addActor(new DialogQuestion(Assest.StringHanger.getString("dialoge"),
							Assest.StringHanger.getString("dialogeM") + " \n" + items.code + "" + items.type + " $"
									+ items.prix + "") {
						@Override
						public void Refuse() {
						}
						@Override
						public void Accepte() {
							if (CrachGame.getCoin() > items.prix) {
								//Assest.soundCoin.get(MathUtils.random(1)).play();
								Assest.soundPurchase.play();
								items.setDisabled(false);
								setPaid(items.type, items.code);
								CrachGame.addCoin(-items.prix);
							}
							else 
								Assest.soundErrour.play();
						}
					});
  				} else { 	
  	  				Assest.checkOnClick.play();
  					updateTrak();
  				}
  			}
  		};
  		
  		for(ButtonGroup<Items> G : groupItems)
  			for(Button B : G.getButtons())
  				B.addListener(C);
	}
	public void updateTrackColor() {
		CrachGame.setColorSelect(SeColor.getCheckedIndex());
		viewTank.updateColor(SeColor.getCheckedIndex());
	}
	private void updateLabel() {
		speed =energy=protection=rotationGun=refire=0;
		for(ButtonGroup<Items> Items : groupItems) {
			speed += Items.getChecked().speed;
			energy += Items.getChecked().energy;
			protection += Items.getChecked().protection;
			rotationGun += Items.getChecked().rotation;
			refire += Items.getChecked().refire;
		}
		
        LHpHull    .setText(energy+"/100");
        LSpeedHull .setText(speed+"/100");
        LArmorHull .setText(protection+"/100");
        LDamageHull.setText(protection+"/100");
	}

	public void VisibleTable() {
      for(int i=0;i<ScrllGun.length ;i++)
    	  ScrllGun[i].setVisible(false);
      if(SGunA.isChecked())
    	  ScrllGun[0].setVisible(true);
      else if(SGunB.isChecked())
    	  ScrllGun[1].setVisible(true);
      else if(SHull.isChecked())
    	  ScrllGun[2].setVisible(true);
      else if(STrack.isChecked())
    	  ScrllGun[3].setVisible(true);
	}
	
	public void updateTrak() {
		int STypeItems = SelectItems.getCheckedIndex();
		TypeItem type = TypeItem.values()[STypeItems];
		int nItem = groupItems[STypeItems].getCheckedIndex();
        switch (type) {
        case Gun_A:
        			CrachGame.setGunASelect(nItem);
        	        viewTank.updateGunPoss(nItem);
        	break;
        case Gun_B:
			        CrachGame.setGunBSelect(nItem);
        	        viewTank.updateGun(nItem);
        	break;
        case Hull:
	        		CrachGame.setHullSelect(nItem);
        	        viewTank.updateTrank(nItem);
        	break;
        case Track:
    				CrachGame.setTrackSelect(nItem);
        	        viewTank.updateTrack(nItem);
        	break;
		} 
        updateLabel();
	}
	public Drawable getDrawableItemTank(TypeItem type,int code,int color) {
        TextureRegion itemTexture = null;
        switch (type) {
        case Gun_A:
        	itemTexture = Assest.Gun_A.get(code);
        	break;
        case Gun_B:
        	itemTexture = Assest.Gun_B.get(color).get(code);
        	break;
        case Hull:
        	itemTexture  = Assest.Hull.get(color).get(code);
        	break;
        case Track:
        	itemTexture  = Assest.Track_A.get(code);
        	break;
		} 
		return new TextureRegionDrawable(itemTexture);
	}
    public static int sizeByItems(TypeItem tyItem) {
    	switch (tyItem) {
    	case Gun_A:
    		return Assest.Gun_A.size;
    	case Gun_B:
    		return Assest.Gun_B.get(0).size;
    	case Hull:
    		return Assest.Hull.get(0).size;
    	case Track:
    		return Assest.Track_A.size;
    	}
    	return 0;
    }
    public  boolean isPaid(TypeItem type ,int code) {
    	switch (type) {
    	case Gun_A:
    		return CrachGame.isPaidGun_A(code);
    	case Gun_B:
    		return CrachGame.isPaidGun_B(code);
    	case Hull:
    		return CrachGame.isPaidHull(code);
    	case Track:
    		return CrachGame.isPaidTrack(code);
    	}
    	return false;
    }
    public  void setPaid(TypeItem type ,int code) {
    	switch (type) {
    	case Gun_A:
    		CrachGame.setPaidGun_A(code);	break; 
    	case Gun_B:
    		CrachGame.setPaidGun_B(code);	break; 
    	case Hull:
    		CrachGame.setPaidHull(code);	break; 
    	case Track:
    		CrachGame.setPaidTrack(code);	break; 
    	}
    }
	public class Items extends Button{
		public int prix,speed,energy,protection,rotation,refire;
		public ProgressUi.ProgressText proText[];
		private int code;
		private TypeItem type;
		
        public Items(int Code,TypeItem type) {
           super(Assest.Style, "items");
           this.code = Code;
           this.type = type;
           this.prix = getDataOfItems(Code, type, "prix");
           this.speed =getDataOfItems(Code, type, "speed");
           this.energy=getDataOfItems(Code, type, "energy");
           this.protection=getDataOfItems(Code, type, "protection");
           this.rotation=getDataOfItems(Code, type, "rotation");
           this.refire=getDataOfItems(Code, type, "refire");
        defaults().minSize(30).pad(2);
        row();
        add(new Image(getDrawableItemTank(type,code,0),Scaling.fit)).prefWidth(160).row();
	    LabelStyle SCo = new LabelStyle(Assest.Style.get("extra", LabelStyle.class));
	    SCo.fontColor = new Color(Assest.Style.getColor("CoinColor"));
	    addProgressbar();
        if(!isPaid(type, code)) {
        	add(new Label("$"+prix, SCo)).padBottom(-20).bottom();
            setDisabled(true);
        }
        pack();
        }
    	private int getDataOfItems(int Code,TypeItem type,String val) {
        	switch (type) {
        	case Gun_A:
        		return Assest.DataGunA.get(Code).getInt(val); 
        	case Gun_B:
        		return Assest.DataGunB.get(Code).getInt(val);
        	case Hull:
        		return Assest.DataHull.get(Code).getInt(val); 
        	case Track:
        		return Assest.DataTrack.get(Code).getInt(val);
			}
        	return 0;
    	}
    	private void addProgressbar() {
    		if(this.speed != 0) add(setProgressValeur(this.speed, "speed","A", Color.RED)).row();
    		if(this.energy != 0) add(setProgressValeur(this.energy, "energy","B", Color.SKY)).row();
    		if(this.protection != 0) add(setProgressValeur(this.protection,"protection","C" , Color.BLUE)).row();
    		if(this.rotation != 0) add(setProgressValeur(this.rotation, "rotation","B", Color.FIREBRICK)).row();
    		if(this.refire != 0) add(setProgressValeur(this.refire, "refire","C",Color.GOLDENROD)).row();
    	}
    	private ProgressUi.ProgressText setProgressValeur(int value,String text,String typrBro,Color color) {
    		ProgressUi.ProgressText textPro = new ProgressUi.ProgressText(100, Assest.Style, typrBro+"-horizontal", color);
    		textPro.setTextAndValue(value, text);
    		return textPro;
    	}
	}
	
	public class SelectItems extends Button{
		private final int codeSelect = 2;
		private  TypeItem type;
		public SelectItems(TypeItem type) {
	        super(Assest.Style, "SelectItems");
	        this.type=type;
	        row();
	        add(new Image(getDrawableItemTank(type,codeSelect,0),Scaling.fit));
	        pack();
		}
		public TypeItem getType() {
			return type;
		}
	}
	
    public void setVisible(boolean visible) {
        if(parent != null) parent.setChecked(visible);
    	super.setVisible(visible);
    }

	public int getSpeed() {
		return speed;
	}

	public int getEnergy() {
		return energy;
	}

	public int getProtection() {
		return protection;
	}

	public int getRotationGun() {
		return rotationGun;
	}

	public int getRefire() {
		return refire;
	}
	@Override
	public void resize() {
		Group P = parent.getParent();
		PoseY = P.getY()+P.getHeight()-this.getHeight();
		PoseX = P.getX()+P.getWidth();
		if(PoseY < 0) PoseY=0;
		float pad = getStage().getWidth() - (PoseX+this.getWidth());
		pad = (pad<0)?pad:0;
		PoseX += pad;
		setPosition(PoseX,PoseY);
	}
}
