package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.v4.view.ViewCompat;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.facebook.appevents.AppEventsConstants;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;

public class ShowTextActor extends Actor {
    private Sprite sprite;
    private final float textSize = 45.0f;
    private UserBrick userBrick;
    private String variableNameToCompare;
    private UserVariable variableToShow;
    private String variableValueWithoutDecimal;
    private int xPosition;
    private int yPosition;

    public ShowTextActor(UserVariable userVariable, int xPosition, int yPosition, Sprite sprite, UserBrick userBrick) {
        this.variableToShow = userVariable;
        this.variableNameToCompare = this.variableToShow.getName();
        this.variableValueWithoutDecimal = null;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
        this.userBrick = userBrick;
    }

    public static String convertToEnglishDigits(String value) {
        return value.replace("١", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("٢", "2").replace("٣", "3").replace("٤", "4").replace("٥", "5").replace("٦", "6").replace("٧", "7").replace("٨", "8").replace("٩", "9").replace("٠", AppEventsConstants.EVENT_PARAM_VALUE_NO).replace("۱", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5").replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9").replace("۰", AppEventsConstants.EVENT_PARAM_VALUE_NO).replace("१", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("२", "2").replace("३", "3").replace("४", "4").replace("५", "5").replace("६", "6").replace("७", "7").replace("८", "8").replace("९", "9").replace("०", AppEventsConstants.EVENT_PARAM_VALUE_NO).replace("১", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("২", "2").replace("৩", "3").replace("৪", "4").replace("৫", "5").replace("৬", "6").replace("৭", "7").replace("৮", "8").replace("৯", "9").replace("০", AppEventsConstants.EVENT_PARAM_VALUE_NO).replace("௧", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("௦", AppEventsConstants.EVENT_PARAM_VALUE_NO).replace("௨", "2").replace("௩", "3").replace("௪", "4").replace("௫", "5").replace("௬", "6").replace("௭", "7").replace("௮", "8").replace("௯", "9").replace("૧", AppEventsConstants.EVENT_PARAM_VALUE_YES).replace("૨", "2").replace("૩", "3").replace("૪", "4").replace("૫", "5").replace("૬", "6").replace("૭", "7").replace("૮", "8").replace("૯", "9").replace("૦", AppEventsConstants.EVENT_PARAM_VALUE_NO);
    }

    public void draw(Batch batch, float parentAlpha) {
        DataContainer dataContainer = ProjectManager.getInstance().getCurrentlyPlayingScene().getDataContainer();
        drawVariables(dataContainer.getProjectUserVariables(), batch);
        drawVariables(dataContainer.getSpriteUserVariables(this.sprite), batch);
        drawVariables(dataContainer.getUserBrickUserVariables(this.userBrick), batch);
    }

    private void drawVariables(List<UserVariable> variableList, Batch batch) {
        if (variableList != null) {
            if (this.variableToShow.isDummy()) {
                drawText(batch, Constants.NO_VARIABLE_SELECTED, (float) this.xPosition, (float) this.yPosition);
            } else {
                for (UserVariable variable : variableList) {
                    if (variable.getName().equals(this.variableToShow.getName())) {
                        String variableValue = variable.getValue().toString();
                        if (variable.getVisible()) {
                            if (isNumberAndInteger(variableValue)) {
                                drawText(batch, this.variableValueWithoutDecimal, (float) this.xPosition, (float) this.yPosition);
                            } else if (variableValue.isEmpty()) {
                                drawText(batch, Constants.NO_VALUE_SET, (float) this.xPosition, (float) this.yPosition);
                            } else {
                                drawText(batch, variableValue, (float) this.xPosition, (float) this.yPosition);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isNumberAndInteger(String variableValue) {
        if (!variableValue.matches("-?\\d+(\\.\\d+)?")) {
            return false;
        }
        double variableValueIsNumber = Double.parseDouble(convertToEnglishDigits(variableValue));
        if (((double) ((int) variableValueIsNumber)) - variableValueIsNumber != BrickValues.SET_COLOR_TO) {
            return false;
        }
        this.variableValueWithoutDecimal = Integer.toString((int) variableValueIsNumber);
        return true;
    }

    private void drawText(Batch batch, String text, float posX, float posY) {
        Paint paint = new Paint();
        paint.setTextSize(45.0f);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setAntiAlias(true);
        paint.setTextAlign(Align.LEFT);
        float baseline = -paint.ascent();
        Bitmap bitmap = Bitmap.createBitmap((int) paint.measureText(text), (int) (paint.descent() + baseline), Config.ARGB_8888);
        new Canvas(bitmap).drawText(text, 0.0f, baseline, paint);
        Texture tex = new Texture(bitmap.getWidth(), bitmap.getHeight(), Pixmap$Format.RGBA8888);
        GLES20.glBindTexture(GL20.GL_TEXTURE_2D, tex.getTextureObjectHandle());
        GLUtils.texImage2D(GL20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glBindTexture(GL20.GL_TEXTURE_2D, 0);
        bitmap.recycle();
        batch.draw(tex, posX - 3.0f, posY - 45.0f);
        batch.flush();
        tex.dispose();
    }

    public void setPositionX(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setPositionY(int yPosition) {
        this.yPosition = yPosition;
    }

    public String getVariableNameToCompare() {
        return this.variableNameToCompare;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public UserBrick getUserBrick() {
        return this.userBrick;
    }
}
