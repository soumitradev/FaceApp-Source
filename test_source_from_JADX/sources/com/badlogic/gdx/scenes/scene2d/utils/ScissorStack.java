package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class ScissorStack {
    private static Array<Rectangle> scissors = new Array();
    static Vector3 tmp = new Vector3();
    static final Rectangle viewport = new Rectangle();

    public static boolean pushScissors(Rectangle scissor) {
        fix(scissor);
        if (scissors.size == 0) {
            if (scissor.width >= 1.0f) {
                if (scissor.height >= 1.0f) {
                    Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
                }
            }
            return false;
        }
        Rectangle parent = (Rectangle) scissors.get(scissors.size - 1);
        float minX = Math.max(parent.f12x, scissor.f12x);
        float maxX = Math.min(parent.f12x + parent.width, scissor.f12x + scissor.width);
        if (maxX - minX < 1.0f) {
            return false;
        }
        float minY = Math.max(parent.f13y, scissor.f13y);
        float maxY = Math.min(parent.f13y + parent.height, scissor.f13y + scissor.height);
        if (maxY - minY < 1.0f) {
            return false;
        }
        scissor.f12x = minX;
        scissor.f13y = minY;
        scissor.width = maxX - minX;
        scissor.height = Math.max(1.0f, maxY - minY);
        scissors.add(scissor);
        Gdx.gl.glScissor((int) scissor.f12x, (int) scissor.f13y, (int) scissor.width, (int) scissor.height);
        return true;
    }

    public static Rectangle popScissors() {
        Rectangle old = (Rectangle) scissors.pop();
        if (scissors.size == 0) {
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        } else {
            Rectangle scissor = (Rectangle) scissors.peek();
            Gdx.gl.glScissor((int) scissor.f12x, (int) scissor.f13y, (int) scissor.width, (int) scissor.height);
        }
        return old;
    }

    public static Rectangle peekScissors() {
        return (Rectangle) scissors.peek();
    }

    private static void fix(Rectangle rect) {
        rect.f12x = (float) Math.round(rect.f12x);
        rect.f13y = (float) Math.round(rect.f13y);
        rect.width = (float) Math.round(rect.width);
        rect.height = (float) Math.round(rect.height);
        if (rect.width < 0.0f) {
            rect.width = -rect.width;
            rect.f12x -= rect.width;
        }
        if (rect.height < 0.0f) {
            rect.height = -rect.height;
            rect.f13y -= rect.height;
        }
    }

    public static void calculateScissors(Camera camera, Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        calculateScissors(camera, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight(), batchTransform, area, scissor);
    }

    public static void calculateScissors(Camera camera, float viewportX, float viewportY, float viewportWidth, float viewportHeight, Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        Matrix4 matrix4 = batchTransform;
        Rectangle rectangle = area;
        Rectangle rectangle2 = scissor;
        tmp.set(rectangle.f12x, rectangle.f13y, 0.0f);
        tmp.mul(matrix4);
        camera.project(tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        rectangle2.f12x = tmp.f120x;
        rectangle2.f13y = tmp.f121y;
        tmp.set(rectangle.f12x + rectangle.width, rectangle.f13y + rectangle.height, 0.0f);
        tmp.mul(matrix4);
        camera.project(tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        rectangle2.width = tmp.f120x - rectangle2.f12x;
        rectangle2.height = tmp.f121y - rectangle2.f13y;
    }

    public static Rectangle getViewport() {
        if (scissors.size == 0) {
            viewport.set(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
            return viewport;
        }
        viewport.set((Rectangle) scissors.peek());
        return viewport;
    }
}
