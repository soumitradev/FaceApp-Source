package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.google.common.primitives.Ints;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.BrickLayout;
import org.catrobat.catroid.ui.fragment.UserBrickElementEditorFragment;
import org.catrobat.catroid.utils.Utils;

public class UserScriptDefinitionBrick extends BrickBaseType implements ScriptBrick, OnClickListener {
    private static final String LINE_BREAK = "linebreak";
    private static final String TAG = UserScriptDefinitionBrick.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private StartScript script = new StartScript();
    @XStreamAlias("userBrickElements")
    private List<UserScriptDefinitionBrickElement> userScriptDefinitionBrickElements = new ArrayList();

    public boolean equals(Object obj) {
        if (!(obj instanceof UserScriptDefinitionBrick)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        List<UserScriptDefinitionBrickElement> elements = ((UserScriptDefinitionBrick) obj).getUserScriptDefinitionBrickElements();
        if (this.userScriptDefinitionBrickElements.size() != elements.size()) {
            return false;
        }
        for (int elementPosition = 0; elementPosition < this.userScriptDefinitionBrickElements.size(); elementPosition++) {
            if (!((UserScriptDefinitionBrickElement) elements.get(elementPosition)).equals((UserScriptDefinitionBrickElement) this.userScriptDefinitionBrickElements.get(elementPosition))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return super.hashCode() * TAG.hashCode();
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        Iterator it = this.script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if (!(brick instanceof UserBrick) || ((UserBrick) brick).getDefinitionBrick() != this) {
                brick.addRequiredResources(requiredResourcesSet);
            }
        }
    }

    public CheckBox getCheckBox() {
        return null;
    }

    public int getViewResource() {
        return R.layout.brick_user_definition;
    }

    public View getView(Context context) {
        super.getView(context);
        onLayoutChanged();
        return this.view;
    }

    public void onLayoutChanged() {
        Context context = this.view.getContext();
        LinearLayout layout = (LinearLayout) this.view.findViewById(R.id.brick_user_definition_layout);
        layout.setFocusable(false);
        layout.setFocusableInTouchMode(false);
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
        ImageView preview = getBorderedPreview(getBrickImage(getUserBrickPrototypeView(context)));
        TextView define = new TextView(context);
        define.setText(context.getString(R.string.define).concat(FormatHelper.SPACE));
        layout.addView(define);
        ((LayoutParams) define.getLayoutParams()).gravity = 16;
        define.setFocusable(false);
        define.setFocusableInTouchMode(false);
        define.setClickable(true);
        layout.setFocusable(false);
        layout.setFocusableInTouchMode(false);
        layout.setClickable(true);
        preview.setClickable(true);
        preview.setOnClickListener(this);
        layout.setOnClickListener(this);
        define.setOnClickListener(this);
        layout.addView(preview);
    }

    private View getUserBrickPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        BrickLayout layout = (BrickLayout) prototypeView.findViewById(R.id.brick_user_flow_layout);
        if (layout.getChildCount() > 0) {
            layout.removeAllViews();
        }
        for (UserScriptDefinitionBrickElement element : getUserScriptDefinitionBrickElements()) {
            if (!element.isLineBreak()) {
                TextView currentTextView;
                if (element.isVariable()) {
                    currentTextView = new EditText(context);
                    currentTextView.setText(String.valueOf(BrickValues.SET_COLOR_TO));
                    currentTextView.setVisibility(0);
                } else {
                    currentTextView = new TextView(context);
                    currentTextView.setTextAppearance(context, R.style.BrickText.Multiple);
                    currentTextView.setText(element.getText());
                }
                currentTextView.setFocusable(false);
                currentTextView.setFocusableInTouchMode(false);
                currentTextView.setClickable(false);
                layout.addView(currentTextView);
                if (element.isNewLineHint()) {
                    BrickLayout.LayoutParams params = (BrickLayout.LayoutParams) currentTextView.getLayoutParams();
                    params.setNewLine(true);
                    currentTextView.setLayoutParams(params);
                }
            }
        }
        return prototypeView;
    }

    private Bitmap getBrickImage(View view) {
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(ScreenValues.SCREEN_WIDTH, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(Utils.getPhysicalPixels(400, view.getContext()), Integer.MIN_VALUE));
        view.layout(0, 0, ScreenValues.SCREEN_WIDTH, view.getMeasuredHeight());
        view.setDrawingCacheBackgroundColor(0);
        view.buildDrawingCache(true);
        if (view.getDrawingCache() == null) {
            view.setDrawingCacheEnabled(drawingCacheEnabled);
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        return bitmap;
    }

    public ImageView getBorderedPreview(Bitmap bitmap) {
        ImageView imageView = new ImageView(this.view.getContext());
        imageView.setBackgroundColor(0);
        imageView.setImageBitmap(getWithBorder(7, bitmap, Color.argb(Math.round(63.75f), 0, 0, Math.round(25.5f))));
        return imageView;
    }

    public Bitmap getWithBorder(int radius, Bitmap bitmap, int color) {
        int i = radius;
        int borderedWidth = (i * 2) + bitmap.getWidth();
        int borderedHeight = (i * 2) + bitmap.getHeight();
        Bitmap toReturn = Bitmap.createBitmap(borderedWidth, borderedHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(toReturn);
        Bitmap border = Bitmap.createBitmap(borderedWidth, borderedHeight, Config.ARGB_8888);
        Canvas borderCanvas = new Canvas(border);
        Bitmap alpha = bitmap.extractAlpha();
        Paint paintBorder = new Paint();
        paintBorder.setColor(-1);
        Paint paintBorder2 = new Paint();
        paintBorder2.setColor(color);
        Paint paint = new Paint();
        borderCanvas.drawBitmap(alpha, 0.0f, 0.0f, paintBorder);
        borderCanvas.drawBitmap(alpha, (float) (i * 2), 0.0f, paintBorder);
        borderCanvas.drawBitmap(alpha, 0.0f, (float) (i * 2), paintBorder);
        borderCanvas.drawBitmap(alpha, (float) (i * 2), (float) (i * 2), paintBorder);
        canvas.drawBitmap(border.extractAlpha(), 0.0f, 0.0f, paintBorder2);
        canvas.drawBitmap(bitmap, (float) i, (float) i, paint);
        return toReturn;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }

    public void onClick(View eventOrigin) {
        if (this.checkbox.getVisibility() != 0) {
            UserBrickElementEditorFragment.showFragment(this.view, this);
        }
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        UserScriptDefinitionBrick clone = (UserScriptDefinitionBrick) super.clone();
        clone.script = null;
        return clone;
    }

    public Script getScript() {
        return getUserScript();
    }

    public Script getUserScript() {
        return this.script;
    }

    public int addUIText(String text) {
        UserScriptDefinitionBrickElement element = new UserScriptDefinitionBrickElement();
        element.setIsText();
        element.setText(text);
        int toReturn = this.userScriptDefinitionBrickElements.size();
        this.userScriptDefinitionBrickElements.add(element);
        return toReturn;
    }

    public void addUILineBreak() {
        UserScriptDefinitionBrickElement element = new UserScriptDefinitionBrickElement();
        element.setIsLineBreak();
        element.setText(LINE_BREAK);
        this.userScriptDefinitionBrickElements.add(element);
    }

    public int addUILocalizedVariable(String name) {
        UserScriptDefinitionBrickElement element = new UserScriptDefinitionBrickElement();
        element.setIsVariable();
        element.setText(name);
        int toReturn = this.userScriptDefinitionBrickElements.size();
        this.userScriptDefinitionBrickElements.add(element);
        return toReturn;
    }

    public void renameUIElement(UserScriptDefinitionBrickElement element, String oldName, String newName, Context context) {
        if (element.getText().equals(oldName)) {
            element.setText(newName);
            if (element.isVariable()) {
                Scene currentScene = ProjectManager.getInstance().getCurrentlyEditedScene();
                Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
                DataContainer dataContainer = currentScene.getDataContainer();
                if (dataContainer != null) {
                    for (UserBrick userBrick : currentSprite.getUserBricksByDefinitionBrick(this, true, true)) {
                        UserVariable userVariable = dataContainer.getUserVariable(currentSprite, userBrick, oldName);
                        if (userVariable != null) {
                            userVariable.setName(newName);
                        }
                    }
                }
            }
        }
        renameVariablesInFormulasAndBricks(oldName, newName, context);
    }

    public void removeDataAt(int id, Context context) {
        removeVariablesInFormulas(((UserScriptDefinitionBrickElement) getUserScriptDefinitionBrickElements().get(id)).getText(), context);
        this.userScriptDefinitionBrickElements.remove(id);
    }

    public void reorderUIData(int from, int to) {
        if (to == -1) {
            this.userScriptDefinitionBrickElements.add(0, (UserScriptDefinitionBrickElement) getUserScriptDefinitionBrickElements().remove(from));
        } else if (from <= to) {
            this.userScriptDefinitionBrickElements.add(to, (UserScriptDefinitionBrickElement) getUserScriptDefinitionBrickElements().remove(from));
        } else {
            this.userScriptDefinitionBrickElements.add(to + 1, (UserScriptDefinitionBrickElement) getUserScriptDefinitionBrickElements().remove(from));
        }
    }

    public CharSequence getName() {
        CharSequence name = "";
        for (UserScriptDefinitionBrickElement element : getUserScriptDefinitionBrickElements()) {
            if (!element.isVariable()) {
                return element.getText();
            }
        }
        return name;
    }

    public List<UserScriptDefinitionBrickElement> getUserScriptDefinitionBrickElements() {
        return this.userScriptDefinitionBrickElements;
    }

    public void renameVariablesInFormulasAndBricks(String oldName, String newName, Context context) {
        for (Brick brick : this.script.getBrickList()) {
            if (brick instanceof UserBrick) {
                for (Formula formula : ((UserBrick) brick).getFormulas()) {
                    formula.updateVariableReferences(oldName, newName, context);
                }
            }
            if (brick instanceof FormulaBrick) {
                for (Formula formula2 : ((FormulaBrick) brick).getFormulas()) {
                    formula2.updateVariableReferences(oldName, newName, context);
                }
            }
            if ((brick instanceof ShowTextBrick) && ((ShowTextBrick) brick).getUserVariable().getName().equals(oldName)) {
                ((ShowTextBrick) brick).getUserVariable().setName(newName);
            }
            if ((brick instanceof HideTextBrick) && ((HideTextBrick) brick).getUserVariable().getName().equals(oldName)) {
                ((HideTextBrick) brick).getUserVariable().setName(newName);
            }
        }
    }

    public void removeVariablesInFormulas(String name, Context context) {
        if (ProjectManager.getInstance().getCurrentScript() != null) {
            for (Brick brick : ProjectManager.getInstance().getCurrentScript().getBrickList()) {
                if (brick instanceof UserBrick) {
                    for (Formula formula : ((UserBrick) brick).getFormulas()) {
                        formula.removeVariableReferences(name, context);
                    }
                }
                if (brick instanceof FormulaBrick) {
                    for (Formula formula2 : ((FormulaBrick) brick).getFormulas()) {
                        formula2.removeVariableReferences(name, context);
                    }
                }
            }
        }
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
