package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;

public class ShowBubbleActor extends Actor {
    ArrayList<String> bubbleValue;
    private boolean drawRight = true;
    private Image image;
    private Image imageLeft;
    private Image imageRight;
    private Sprite sprite;
    private int type;

    public ShowBubbleActor(String text, Sprite sprite, int type) {
        this.bubbleValue = formatStringForBubbleBricks(text);
        this.sprite = sprite;
        this.type = type;
        init();
    }

    public void draw(Batch batch, float parentAlpha) {
        switchLogic();
        getImageForDraw().draw(batch, parentAlpha);
    }

    private void init() {
        this.imageRight = new Image(new Texture(drawBubbleOnCanvas(this.bubbleValue, true)));
        this.imageLeft = new Image(new Texture(drawBubbleOnCanvas(this.bubbleValue, false)));
        this.image = this.imageRight;
    }

    private Image getImageForDraw() {
        if (this.drawRight) {
            this.image.setX(this.sprite.look.getXInUserInterfaceDimensionUnit() + (this.sprite.look.getWidthInUserInterfaceDimensionUnit() / 2.0f));
        } else {
            this.image.setX((this.sprite.look.getXInUserInterfaceDimensionUnit() - (this.sprite.look.getWidthInUserInterfaceDimensionUnit() / 2.0f)) - this.image.getWidth());
        }
        this.image.setY(this.sprite.look.getYInUserInterfaceDimensionUnit() + (this.sprite.look.getHeightInUserInterfaceDimensionUnit() / 2.0f));
        return this.image;
    }

    private void switchLogic() {
        if (this.drawRight && !drawRight()) {
            this.drawRight = false;
            this.image = this.imageLeft;
        }
        if (!this.drawRight && !drawLeft()) {
            this.drawRight = true;
            this.image = this.imageRight;
        }
    }

    private boolean drawRight() {
        return (this.sprite.look.getX() + this.sprite.look.getWidthInUserInterfaceDimensionUnit()) + this.image.getWidth() < ((float) (ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth / 2));
    }

    private boolean drawLeft() {
        return this.sprite.look.getX() - this.image.getWidth() > ((float) (-(ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth / 2)));
    }

    private Pixmap drawBubbleOnCanvas(ArrayList<String> lines, boolean right) {
        int i;
        Paint paint = new Paint();
        paint.setTextSize(30.0f);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        int width = 0;
        int height = 30;
        ArrayList<Float> xPositions = new ArrayList();
        Rect temp = new Rect();
        Iterator it = lines.iterator();
        while (it.hasNext()) {
            String line = (String) it.next();
            height += 3;
            paint.getTextBounds(line, 0, line.length(), temp);
            height += temp.height();
            xPositions.add(Float.valueOf(paint.measureText(line)));
            if (width < temp.width()) {
                width = temp.width();
            }
        }
        width += 55;
        if (width < 148) {
            width = 148;
        }
        float lineHeight = (float) ((height - 30) / lines.size());
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF rect = new RectF(0.0f, 0.0f, (float) width, (float) height);
        canvas.drawRoundRect(rect, 20.0f, 20.0f, paint);
        float y = 40.0f;
        RectF rect2 = new RectF(rect.left + ((float) 5), rect.top + ((float) 5), rect.right - ((float) 5), rect.bottom - ((float) 5));
        paint.setColor(-1);
        canvas.drawRoundRect(rect2, 15.0f, 15.0f, paint);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= xPositions.size()) {
                break;
            }
            xPositions.set(i, Float.valueOf((((float) width) - ((Float) xPositions.get(i)).floatValue()) / 2.0f));
            i2 = i + 1;
        }
        i = 0;
        Iterator it2 = lines.iterator();
        float y2 = y;
        while (it2.hasNext()) {
            canvas.drawText((String) it2.next(), ((Float) xPositions.get(i)).floatValue(), y2, paint);
            y2 += lineHeight;
            i++;
        }
        return getFinalBubble(width, height, bitmap, right);
    }

    private Pixmap getFinalBubble(int width, int height, Bitmap bitmap, boolean right) {
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2.0f);
        Bitmap tempBitmap = Bitmap.createBitmap(width, height + 40, Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        if (this.type == 0) {
            tempCanvas.drawPath(getSayTrianglePath(tempBitmap.getHeight(), tempBitmap.getWidth(), right), paint);
            paint.setColor(-1);
            tempCanvas.drawPath(getSayTrianglePathSmaller(tempBitmap.getHeight(), tempBitmap.getWidth(), right), paint);
        } else {
            tempCanvas.drawBitmap(getThinkBubbles(right), (float) (right ? 0 : tempBitmap.getWidth() - 80), (float) bitmap.getHeight(), null);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        tempBitmap.compress(CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return new Pixmap(bytes, 0, bytes.length);
    }

    private Path getSayTrianglePath(int bitmapHeight, int bitmapWidth, boolean right) {
        int offset = right ? 0 : bitmapWidth;
        Path path = new Path();
        path.setFillType(FillType.EVEN_ODD);
        path.moveTo((float) offset, (float) bitmapHeight);
        path.lineTo((float) Math.abs(offset - 28), (float) (bitmapHeight - 40));
        path.lineTo((float) Math.abs(offset - 118), (float) (bitmapHeight - 40));
        path.lineTo((float) offset, (float) bitmapHeight);
        path.close();
        return path;
    }

    private Path getSayTrianglePathSmaller(int bitmapHeight, int bitmapWidth, boolean right) {
        int offset = right ? 0 : bitmapWidth;
        Path path = new Path();
        path.moveTo((float) Math.abs(offset - 12), (float) (bitmapHeight - 9));
        path.lineTo((float) Math.abs(offset - 37), (float) ((bitmapHeight - 40) - 5));
        path.lineTo((float) Math.abs(offset - 116), (float) ((bitmapHeight - 40) - 5));
        path.lineTo((float) Math.abs(offset - 12), (float) (bitmapHeight - 9));
        path.close();
        return path;
    }

    private Bitmap getThinkBubbles(boolean right) {
        Bitmap bitmap = Bitmap.createBitmap(70, 40, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Style.FILL);
        int length = right ? bitmap.getWidth() : 0;
        for (int i = 0; i <= 3; i++) {
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawCircle((float) Math.abs((length - (i * 15)) - 10), (float) (i * 10), (float) (10 - i), paint);
            paint.setColor(-1);
            canvas.drawCircle((float) Math.abs((length - (i * 15)) - 10), (float) (i * 10), (float) (7 - i), paint);
        }
        return bitmap;
    }

    public static ArrayList<String> formatStringForBubbleBricks(String text) {
        text = text.trim();
        ArrayList<String> words = new ArrayList();
        for (String word : Arrays.asList(text.split(FormatHelper.SPACE))) {
            words.addAll(splitLongWordIntoSubStrings(word, 16));
        }
        return concatWordsIntoLines(words, 16);
    }

    private static ArrayList<String> splitLongWordIntoSubStrings(String word, int maxLength) {
        ArrayList<String> intermediateList = new ArrayList();
        while (word.length() > maxLength) {
            intermediateList.add(word.substring(0, maxLength));
            word = word.substring(maxLength);
        }
        intermediateList.add(word);
        return intermediateList;
    }

    private static ArrayList<String> concatWordsIntoLines(ArrayList<String> words, int maxLineLength) {
        ArrayList<String> lines = new ArrayList();
        String line = (String) words.get(0);
        for (String word : words.subList(1, words.size())) {
            if (line.length() + word.length() < maxLineLength) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(word);
                line = stringBuilder.toString();
            } else {
                lines.add(line);
                line = word;
            }
        }
        lines.add(line);
        return lines;
    }
}
