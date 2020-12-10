package crach.stage.game.utils;


import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * @author Diego Coppetti
 *
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int ALPHA = 0;
	public static final int ROTATE = 1;
	public static final int ROTATION = 2;
	public static final int COLOR = 3;
	public static final int SCALE = 4;
	public static final int POSITION = 5;
	public static final int COLOR_ALPHA = 6;

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case ROTATE:
		case ROTATION:
			returnValues[0] = target.getRotation();
			return 1;
		case COLOR:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case COLOR_ALPHA:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			returnValues[3] = target.getColor().a;
			return 4;
		case SCALE:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;
		case POSITION:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 3;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setAlpha(newValues[0]);
			break;
		case ROTATE:
			target.rotate(newValues[0]);
			break;
		case ROTATION:
			target.setRotation(newValues[0]);
			break;
		case COLOR:
			target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
			break;
		case COLOR_ALPHA:
			target.setColor(newValues[0], newValues[1], newValues[2], newValues[3]);
			break;
		case SCALE:
			target.setScale(newValues[0], newValues[1]);
			break;
		case POSITION:
			target.setPosition(newValues[0], newValues[1]);
			break;
		default:
			assert false;
		}
	}

}
