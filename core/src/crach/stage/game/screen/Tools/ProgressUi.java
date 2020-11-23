package crach.stage.game.screen.Tools;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;

import crach.stage.game.Assets;

public class ProgressUi extends ProgressBar{
	private float position;
	private float Pad;
	private boolean simple;

	public ProgressUi(float max, Skin skin, String styleName) {
		super(0, max, 1, false, skin, styleName);
		if(max>15)
			simple = true;
	}
	public ProgressUi(float max, Skin skin, String styleName,boolean simple) {
		this(max, 1, skin, styleName, simple);
	}
	public ProgressUi(float max, float stepSize,Skin skin, String styleName,boolean simple) {
		super(0, max, stepSize, false, skin, styleName);
		this.simple = simple;
	}

     @Override
    public void draw(Batch batch, float parentAlpha) {
    	ProgressBarStyle style = this.getStyle();
 		final Drawable knob = getKnobDrawable();
 		final Drawable bg =  style.background;

 		Color color = getColor();
 		float x = getX();
 		float y = getY();
 		float width = getWidth();
 		float height = getHeight();

 		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);


 			float positionWidth = width;
            float positionHeight =0;
 			float bgLeftWidth = 0, bgRightWidth = 0;
 			if (bg != null) {
 				positionHeight = bg.getTopHeight()+bg.getBottomHeight();
 				bg.draw(batch, x, y, width, height);
 				bgLeftWidth = bg.getLeftWidth();
 				bgRightWidth = bg.getRightWidth();
 				positionWidth -= bgLeftWidth + bgRightWidth;
 			}

 	 		float knobWidth = (positionWidth-(getMaxValue()-1)*Pad)/getMaxValue();

 	 		if(simple) {
 	 			float percent = getVisualPercent();
				position = (positionWidth ) * percent;
				position = Math.min(positionWidth, position);
				
				Drawable knobBefore = style.knobBefore;
				
	 			if (knobBefore == null)  if(knob != null) knobBefore = knob;
	 				knobBefore.draw(batch, x + bgLeftWidth, y + (positionHeight) * 0.5f, position ,
	 						height-positionHeight);
 	 		}
 	 		else {
 			if (knob != null) {
 				for(int i=0;i<this.getValue();i++)
 					knob.draw(batch, x + i*knobWidth+i*Pad+bgLeftWidth, y + (positionHeight) * 0.5f, knobWidth, height-positionHeight);
 			}
 	 	  }
 		}

     public void setPad(float pad) {
    	 this.Pad = pad;
     }
     public static class ProgressText extends ProgressUi{
    	 private final GlyphLayout layout = new GlyphLayout();
    	 private final StringBuilder text = new StringBuilder();
    	 private BitmapFontCache cache;
    	 private Color FontColor;

		public ProgressText(float max, Skin skin, String styleName,boolean isSample) {
			super(max, skin, styleName,isSample);
		}
		public ProgressText(float max, Skin skin, String styleName,Color color) {
			this(max, skin, styleName,true);
	    	cache = new BitmapFontCache(Assets.skinStyle.getFont("font-small"));
			FontColor = color;
		}
		 public ProgressText(float max, Skin skin, String styleName,Color color,boolean isSample) {
			 this(max, skin, styleName,isSample);
			 cache = new BitmapFontCache(Assets.skinStyle.getFont("font-small"));
			 FontColor = color;
		 }
		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);
			layout();
			cache.tint(FontColor);
			cache.setPosition(getX(), getY());
			cache.draw(batch);
		}
	    @Override
	    public boolean setValue(float value) {
	    	this.text.clear();
	    	this.text.append((int)clamp(value),4).append(" / ").append((int)getMaxValue(),4);
	    	return super.setValue(value);
	    }
	    
	    public boolean setTextAndValue(float value,String Text) {
	    	this.text.clear();
	    	this.text.append(Text);
	    	return super.setValue(value);
	    }
		public void layout () {
			BitmapFont font = cache.getFont();
			float width = getWidth(), height = getHeight();
			float x = 0, y = 0;
			GlyphLayout layout = this.layout;
			float textWidth, textHeight;
			textWidth = width;
			textHeight = font.getData().capHeight;
		    y += (height - textHeight) / 2;
			if (!cache.getFont().isFlipped()) y += textHeight;
			layout.setText(font, text, 0, text.length, Color.WHITE, textWidth, Align.center, false, null);
			cache.setText(layout, x, y);
		}

		 public StringBuilder getText() {
			 return text;
		 }
	 }
}
