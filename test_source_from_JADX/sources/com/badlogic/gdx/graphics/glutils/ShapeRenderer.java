package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class ShapeRenderer implements Disposable {
    private boolean autoShapeType;
    private final Color color;
    private final Matrix4 combinedMatrix;
    private float defaultRectLineWidth;
    private boolean matrixDirty;
    private final Matrix4 projectionMatrix;
    private final ImmediateModeRenderer renderer;
    private ShapeType shapeType;
    private final Vector2 tmp;
    private final Matrix4 transformMatrix;

    public enum ShapeType {
        Point(0),
        Line(1),
        Filled(4);
        
        private final int glType;

        private ShapeType(int glType) {
            this.glType = glType;
        }

        public int getGlType() {
            return this.glType;
        }
    }

    public ShapeRenderer() {
        this(5000);
    }

    public ShapeRenderer(int maxVertices) {
        this(maxVertices, null);
    }

    public ShapeRenderer(int maxVertices, ShaderProgram defaultShader) {
        this.matrixDirty = false;
        this.projectionMatrix = new Matrix4();
        this.transformMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.tmp = new Vector2();
        this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        this.defaultRectLineWidth = 0.75f;
        if (defaultShader == null) {
            this.renderer = new ImmediateModeRenderer20(maxVertices, false, true, 0);
        } else {
            this.renderer = new ImmediateModeRenderer20(maxVertices, false, true, 0, defaultShader);
        }
        this.projectionMatrix.setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.matrixDirty = true;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public Color getColor() {
        return this.color;
    }

    public void updateMatrices() {
        this.matrixDirty = true;
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        this.projectionMatrix.set(matrix);
        this.matrixDirty = true;
    }

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public void setTransformMatrix(Matrix4 matrix) {
        this.transformMatrix.set(matrix);
        this.matrixDirty = true;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    public void identity() {
        this.transformMatrix.idt();
        this.matrixDirty = true;
    }

    public void translate(float x, float y, float z) {
        this.transformMatrix.translate(x, y, z);
        this.matrixDirty = true;
    }

    public void rotate(float axisX, float axisY, float axisZ, float degrees) {
        this.transformMatrix.rotate(axisX, axisY, axisZ, degrees);
        this.matrixDirty = true;
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        this.transformMatrix.scale(scaleX, scaleY, scaleZ);
        this.matrixDirty = true;
    }

    public void setAutoShapeType(boolean autoShapeType) {
        this.autoShapeType = autoShapeType;
    }

    public void begin() {
        if (this.autoShapeType) {
            begin(ShapeType.Line);
            return;
        }
        throw new IllegalStateException("autoShapeType must be true to use this method.");
    }

    public void begin(ShapeType type) {
        if (this.shapeType != null) {
            throw new IllegalStateException("Call end() before beginning a new shape batch.");
        }
        this.shapeType = type;
        if (this.matrixDirty) {
            this.combinedMatrix.set(this.projectionMatrix);
            Matrix4.mul(this.combinedMatrix.val, this.transformMatrix.val);
            this.matrixDirty = false;
        }
        this.renderer.begin(this.combinedMatrix, this.shapeType.getGlType());
    }

    public void set(ShapeType type) {
        if (this.shapeType != type) {
            if (this.shapeType == null) {
                throw new IllegalStateException("begin must be called first.");
            } else if (this.autoShapeType) {
                end();
                begin(type);
            } else {
                throw new IllegalStateException("autoShapeType must be enabled.");
            }
        }
    }

    public void point(float x, float y, float z) {
        float size;
        if (this.shapeType == ShapeType.Line) {
            size = this.defaultRectLineWidth * 0.5f;
            line(x - size, y - size, z, x + size, y + size, z);
        } else if (this.shapeType == ShapeType.Filled) {
            size = this.defaultRectLineWidth * 0.5f;
            box(x - size, y - size, z - size, this.defaultRectLineWidth, this.defaultRectLineWidth, this.defaultRectLineWidth);
        } else {
            check(ShapeType.Point, null, 1);
            this.renderer.color(this.color);
            this.renderer.vertex(x, y, z);
        }
    }

    public final void line(float x, float y, float z, float x2, float y2, float z2) {
        line(x, y, z, x2, y2, z2, this.color, this.color);
    }

    public final void line(Vector3 v0, Vector3 v1) {
        line(v0.f120x, v0.f121y, v0.f122z, v1.f120x, v1.f121y, v1.f122z, this.color, this.color);
    }

    public final void line(float x, float y, float x2, float y2) {
        line(x, y, 0.0f, x2, y2, 0.0f, this.color, this.color);
    }

    public final void line(Vector2 v0, Vector2 v1) {
        line(v0.f16x, v0.f17y, 0.0f, v1.f16x, v1.f17y, 0.0f, this.color, this.color);
    }

    public final void line(float x, float y, float x2, float y2, Color c1, Color c2) {
        line(x, y, 0.0f, x2, y2, 0.0f, c1, c2);
    }

    public void line(float x, float y, float z, float x2, float y2, float z2, Color c1, Color c2) {
        Color color = c1;
        Color color2 = c2;
        if (this.shapeType == ShapeType.Filled) {
            rectLine(x, y, x2, y2, r6.defaultRectLineWidth);
            return;
        }
        check(ShapeType.Line, null, 2);
        r6.renderer.color(color.f4r, color.f3g, color.f2b, color.f1a);
        r6.renderer.vertex(x, y, z);
        r6.renderer.color(color2.f4r, color2.f3g, color2.f2b, color2.f1a);
        r6.renderer.vertex(x2, y2, z2);
    }

    public void curve(float x1, float y1, float cx1, float cy1, float cx2, float cy2, float x2, float y2, int segments) {
        float f = x2;
        float f2 = y2;
        int segments2 = segments;
        check(ShapeType.Line, null, (segments2 * 2) + 2);
        float colorBits = this.color.toFloatBits();
        float subdiv_step = 1.0f / ((float) segments2);
        float subdiv_step2 = subdiv_step * subdiv_step;
        float subdiv_step3 = (subdiv_step * subdiv_step) * subdiv_step;
        float pre1 = subdiv_step * 3.0f;
        float pre2 = subdiv_step2 * 3.0f;
        float pre4 = subdiv_step2 * 6.0f;
        float pre5 = 6.0f * subdiv_step3;
        float tmp1x = (x1 - (cx1 * 2.0f)) + cx2;
        float tmp1y = (y1 - (2.0f * cy1)) + cy2;
        float tmp2x = (((cx1 - cx2) * 3.0f) - x1) + f;
        float tmp2y = (((cy1 - cy2) * 3.0f) - y1) + f2;
        float fx = x1;
        float dfx = (((cx1 - x1) * pre1) + (tmp1x * pre2)) + (tmp2x * subdiv_step3);
        float dfy = (((cy1 - y1) * pre1) + (tmp1y * pre2)) + (tmp2y * subdiv_step3);
        float ddfx = (tmp1x * pre4) + (tmp2x * pre5);
        float ddfy = (tmp1y * pre4) + (tmp2y * pre5);
        float dddfx = tmp2x * pre5;
        float dddfy = tmp2y * pre5;
        float fy = y1;
        while (true) {
            int segments3 = segments2 - 1;
            if (segments2 > 0) {
                r0.renderer.color(colorBits);
                r0.renderer.vertex(fx, fy, 0.0f);
                fx += dfx;
                fy += dfy;
                dfx += ddfx;
                dfy += ddfy;
                ddfx += dddfx;
                ddfy += dddfy;
                r0.renderer.color(colorBits);
                r0.renderer.vertex(fx, fy, 0.0f);
                segments2 = segments3;
            } else {
                r0.renderer.color(colorBits);
                r0.renderer.vertex(fx, fy, 0.0f);
                r0.renderer.color(colorBits);
                r0.renderer.vertex(f, f2, 0.0f);
                return;
            }
        }
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        check(ShapeType.Line, ShapeType.Filled, 6);
        float colorBits = this.color.toFloatBits();
        if (this.shapeType == ShapeType.Line) {
            this.renderer.color(colorBits);
            this.renderer.vertex(x1, y1, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x1, y1, 0.0f);
            return;
        }
        this.renderer.color(colorBits);
        this.renderer.vertex(x1, y1, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x2, y2, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x3, y3, 0.0f);
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color col1, Color col2, Color col3) {
        check(ShapeType.Line, ShapeType.Filled, 6);
        if (this.shapeType == ShapeType.Line) {
            this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
            this.renderer.vertex(x1, y1, 0.0f);
            this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
            this.renderer.vertex(x1, y1, 0.0f);
            return;
        }
        this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
        this.renderer.vertex(x1, y1, 0.0f);
        this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
        this.renderer.vertex(x2, y2, 0.0f);
        this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
        this.renderer.vertex(x3, y3, 0.0f);
    }

    public void rect(float x, float y, float width, float height) {
        check(ShapeType.Line, ShapeType.Filled, 8);
        float colorBits = this.color.toFloatBits();
        if (this.shapeType == ShapeType.Line) {
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, 0.0f);
            return;
        }
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, 0.0f);
    }

    public void rect(float x, float y, float width, float height, Color col1, Color col2, Color col3, Color col4) {
        check(ShapeType.Line, ShapeType.Filled, 8);
        if (this.shapeType == ShapeType.Line) {
            this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
            this.renderer.vertex(x, y, 0.0f);
            this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(col4.f4r, col4.f3g, col4.f2b, col4.f1a);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(col4.f4r, col4.f3g, col4.f2b, col4.f1a);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
            this.renderer.vertex(x, y, 0.0f);
            return;
        }
        this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
        this.renderer.vertex(x, y, 0.0f);
        this.renderer.color(col2.f4r, col2.f3g, col2.f2b, col2.f1a);
        this.renderer.vertex(x + width, y, 0.0f);
        this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
        this.renderer.vertex(x + width, y + height, 0.0f);
        this.renderer.color(col3.f4r, col3.f3g, col3.f2b, col3.f1a);
        this.renderer.vertex(x + width, y + height, 0.0f);
        this.renderer.color(col4.f4r, col4.f3g, col4.f2b, col4.f1a);
        this.renderer.vertex(x, y + height, 0.0f);
        this.renderer.color(col1.f4r, col1.f3g, col1.f2b, col1.f1a);
        this.renderer.vertex(x, y, 0.0f);
    }

    public void rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees) {
        rect(x, y, originX, originY, width, height, scaleX, scaleY, degrees, this.color, this.color, this.color, this.color);
    }

    public void rect(float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float degrees, Color col1, Color col2, Color col3, Color col4) {
        float f = originX;
        float f2 = originY;
        Color color = col1;
        Color color2 = col2;
        Color color3 = col3;
        Color color4 = col4;
        check(ShapeType.Line, ShapeType.Filled, 8);
        float cos = MathUtils.cosDeg(degrees);
        float sin = MathUtils.sinDeg(degrees);
        float fx = -f;
        float fy = -f2;
        float fx2 = width - f;
        float fy2 = height - f2;
        if (!(scaleX == 1.0f && scaleY == 1.0f)) {
            fx *= scaleX;
            fy *= scaleY;
            fx2 *= scaleX;
            fy2 *= scaleY;
        }
        float worldOriginX = x + f;
        float worldOriginY = y + f2;
        f = ((cos * fx) - (sin * fy)) + worldOriginX;
        f2 = ((sin * fx) + (cos * fy)) + worldOriginY;
        float x2 = ((cos * fx2) - (sin * fy)) + worldOriginX;
        float y2 = ((sin * fx2) + (cos * fy)) + worldOriginY;
        fx = ((cos * fx2) - (sin * fy2)) + worldOriginX;
        cos = ((sin * fx2) + (cos * fy2)) + worldOriginY;
        sin = f + (fx - x2);
        fy = cos - (y2 - f2);
        if (r0.shapeType == ShapeType.Line) {
            float x4 = sin;
            float y4 = fy;
            r0.renderer.color(color.f4r, color.f3g, color.f2b, color.f1a);
            r0.renderer.vertex(f, f2, 0.0f);
            r0.renderer.color(color2.f4r, color2.f3g, color2.f2b, color2.f1a);
            r0.renderer.vertex(x2, y2, 0.0f);
            r0.renderer.color(color2.f4r, color2.f3g, color2.f2b, color2.f1a);
            r0.renderer.vertex(x2, y2, 0.0f);
            r0.renderer.color(color3.f4r, color3.f3g, color3.f2b, color3.f1a);
            r0.renderer.vertex(fx, cos, 0.0f);
            r0.renderer.color(color3.f4r, color3.f3g, color3.f2b, color3.f1a);
            r0.renderer.vertex(fx, cos, 0.0f);
            r0.renderer.color(color4.f4r, color4.f3g, color4.f2b, color4.f1a);
            fy = x4;
            float y42 = y4;
            r0.renderer.vertex(fy, y42, 0.0f);
            float y3 = cos;
            float x3 = fx;
            r0.renderer.color(color4.f4r, color4.f3g, color4.f2b, color4.f1a);
            r0.renderer.vertex(fy, y42, 0.0f);
            r0.renderer.color(color.f4r, color.f3g, color.f2b, color.f1a);
            r0.renderer.vertex(f, f2, 0.0f);
            float f3 = x2;
            float f4 = y2;
            fx = y3;
            sin = x3;
            return;
        }
        y3 = cos;
        x3 = fx;
        y42 = fy;
        fy = sin;
        r0.renderer.color(color.f4r, color.f3g, color.f2b, color.f1a);
        r0.renderer.vertex(f, f2, 0.0f);
        r0.renderer.color(color2.f4r, color2.f3g, color2.f2b, color2.f1a);
        r0.renderer.vertex(x2, y2, 0.0f);
        r0.renderer.color(color3.f4r, color3.f3g, color3.f2b, color3.f1a);
        fx = y3;
        sin = x3;
        r0.renderer.vertex(sin, fx, 0.0f);
        r0.renderer.color(color3.f4r, color3.f3g, color3.f2b, color3.f1a);
        r0.renderer.vertex(sin, fx, 0.0f);
        r0.renderer.color(color4.f4r, color4.f3g, color4.f2b, color4.f1a);
        r0.renderer.vertex(fy, y42, 0.0f);
        r0.renderer.color(color.f4r, color.f3g, color.f2b, color.f1a);
        r0.renderer.vertex(f, f2, 0.0f);
    }

    public void rectLine(float x1, float y1, float x2, float y2, float width) {
        check(ShapeType.Line, ShapeType.Filled, 8);
        float colorBits = this.color.toFloatBits();
        Vector2 t = this.tmp.set(y2 - y1, x1 - x2).nor();
        width *= 0.5f;
        float tx = t.f16x * width;
        float ty = t.f17y * width;
        if (this.shapeType == ShapeType.Line) {
            this.renderer.color(colorBits);
            this.renderer.vertex(x1 + tx, y1 + ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x1 - tx, y1 - ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2 + tx, y2 + ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2 - tx, y2 - ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2 + tx, y2 + ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x1 + tx, y1 + ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x2 - tx, y2 - ty, 0.0f);
            this.renderer.color(colorBits);
            this.renderer.vertex(x1 - tx, y1 - ty, 0.0f);
            return;
        }
        this.renderer.color(colorBits);
        this.renderer.vertex(x1 + tx, y1 + ty, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x1 - tx, y1 - ty, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x2 + tx, y2 + ty, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x2 - tx, y2 - ty, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x2 + tx, y2 + ty, 0.0f);
        this.renderer.color(colorBits);
        this.renderer.vertex(x1 - tx, y1 - ty, 0.0f);
    }

    public void rectLine(Vector2 p1, Vector2 p2, float width) {
        rectLine(p1.f16x, p1.f17y, p2.f16x, p2.f17y, width);
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        depth = -depth;
        float colorBits = this.color.toFloatBits();
        if (this.shapeType == ShapeType.Line) {
            check(ShapeType.Line, ShapeType.Filled, 24);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x + width, y + height, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y, z + depth);
            this.renderer.color(colorBits);
            this.renderer.vertex(x, y + height, z + depth);
            return;
        }
        check(ShapeType.Line, ShapeType.Filled, 36);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y + height, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(colorBits);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(colorBits);
        this.renderer.vertex(x, y, z);
    }

    /* renamed from: x */
    public void m6x(float x, float y, float size) {
        line(x - size, y - size, x + size, y + size);
        line(x - size, y + size, x + size, y - size);
    }

    /* renamed from: x */
    public void m7x(Vector2 p, float size) {
        m6x(p.f16x, p.f17y, size);
    }

    public void arc(float x, float y, float radius, float start, float degrees) {
        arc(x, y, radius, start, degrees, Math.max(1, (int) ((((float) Math.cbrt((double) radius)) * 6.0f) * (degrees / 360.0f))));
    }

    public void arc(float x, float y, float radius, float start, float degrees, int segments) {
        ShapeRenderer shapeRenderer = this;
        float f = x;
        float f2 = y;
        int i = segments;
        if (i <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }
        float colorBits = shapeRenderer.color.toFloatBits();
        float theta = ((degrees / 360.0f) * 6.2831855f) / ((float) i);
        float cos = MathUtils.cos(theta);
        float sin = MathUtils.sin(theta);
        float cx = MathUtils.cos(start * 0.017453292f) * radius;
        float cy = MathUtils.sin(0.017453292f * start) * radius;
        int i2;
        int i3;
        float temp;
        if (shapeRenderer.shapeType == ShapeType.Line) {
            check(ShapeType.Line, ShapeType.Filled, (i * 2) + 2);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f, f2, 0.0f);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
            i2 = 0;
            while (true) {
                i3 = i2;
                if (i3 >= i) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                temp = cx;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                float f3 = temp;
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                i2 = i3 + 1;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
        } else {
            check(ShapeType.Line, ShapeType.Filled, (i * 3) + 3);
            i2 = 0;
            while (true) {
                i3 = i2;
                if (i3 >= i) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f, f2, 0.0f);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                temp = cx;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                float f4 = temp;
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                i2 = i3 + 1;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f, f2, 0.0f);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
        }
        float temp2 = cx;
        shapeRenderer.renderer.color(colorBits);
        shapeRenderer.renderer.vertex(f + 0.0f, f2 + 0.0f, 0.0f);
    }

    public void circle(float x, float y, float radius) {
        circle(x, y, radius, Math.max(1, (int) (((float) Math.cbrt((double) radius)) * 6.0f)));
    }

    public void circle(float x, float y, float radius, int segments) {
        ShapeRenderer shapeRenderer = this;
        float f = x;
        float f2 = y;
        int segments2 = segments;
        if (segments2 <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }
        float colorBits = shapeRenderer.color.toFloatBits();
        float angle = 6.2831855f / ((float) segments2);
        float cos = MathUtils.cos(angle);
        float sin = MathUtils.sin(angle);
        float cx = radius;
        float cy = 0.0f;
        int i = 0;
        int i2;
        float temp;
        if (shapeRenderer.shapeType == ShapeType.Line) {
            check(ShapeType.Line, ShapeType.Filled, (segments2 * 2) + 2);
            while (true) {
                i2 = i;
                if (i2 >= segments2) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                temp = cx;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                i = i2 + 1;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
        } else {
            check(ShapeType.Line, ShapeType.Filled, (segments2 * 3) + 3);
            segments2--;
            while (true) {
                i2 = i;
                if (i2 >= segments2) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f, f2, 0.0f);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                temp = cx;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
                i = i2 + 1;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f, f2, 0.0f);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, 0.0f);
        }
        float temp2 = cx;
        cx = radius;
        shapeRenderer.renderer.color(colorBits);
        shapeRenderer.renderer.vertex(f + cx, f2 + 0.0f, 0.0f);
    }

    public void ellipse(float x, float y, float width, float height) {
        ellipse(x, y, width, height, Math.max(1, (int) (((float) Math.cbrt((double) Math.max(width * 0.5f, 0.5f * height))) * 12.0f)));
    }

    public void ellipse(float x, float y, float width, float height, int segments) {
        ShapeRenderer shapeRenderer = this;
        int i = segments;
        if (i <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }
        check(ShapeType.Line, ShapeType.Filled, i * 3);
        float colorBits = shapeRenderer.color.toFloatBits();
        float angle = 6.2831855f / ((float) i);
        float cx = (width / 2.0f) + x;
        float cy = (height / 2.0f) + y;
        int i2 = 0;
        float f = 0.5f;
        int i3;
        if (shapeRenderer.shapeType == ShapeType.Line) {
            while (true) {
                i3 = i2;
                if (i3 < i) {
                    shapeRenderer.renderer.color(colorBits);
                    shapeRenderer.renderer.vertex(((width * f) * MathUtils.cos(((float) i3) * angle)) + cx, ((height * f) * MathUtils.sin(((float) i3) * angle)) + cy, 0.0f);
                    shapeRenderer.renderer.color(colorBits);
                    shapeRenderer.renderer.vertex(((width * 0.5f) * MathUtils.cos(((float) (i3 + 1)) * angle)) + cx, ((height * 0.5f) * MathUtils.sin(((float) (i3 + 1)) * angle)) + cy, 0.0f);
                    i2 = i3 + 1;
                    f = 0.5f;
                } else {
                    return;
                }
            }
        }
        while (true) {
            i3 = i2;
            if (i3 < i) {
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(((width * 0.5f) * MathUtils.cos(((float) i3) * angle)) + cx, ((height * 0.5f) * MathUtils.sin(((float) i3) * angle)) + cy, 0.0f);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(cx, cy, 0.0f);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(((width * 0.5f) * MathUtils.cos(((float) (i3 + 1)) * angle)) + cx, ((height * 0.5f) * MathUtils.sin(((float) (i3 + 1)) * angle)) + cy, 0.0f);
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }

    public void cone(float x, float y, float z, float radius, float height) {
        cone(x, y, z, radius, height, Math.max(1, (int) (((float) Math.sqrt((double) radius)) * 4.0f)));
    }

    public void cone(float x, float y, float z, float radius, float height, int segments) {
        ShapeRenderer shapeRenderer = this;
        float f = x;
        float f2 = y;
        float f3 = z;
        int segments2 = segments;
        if (segments2 <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        }
        check(ShapeType.Line, ShapeType.Filled, (segments2 * 4) + 2);
        float colorBits = shapeRenderer.color.toFloatBits();
        float angle = 6.2831855f / ((float) segments2);
        float cos = MathUtils.cos(angle);
        float sin = MathUtils.sin(angle);
        float cx = radius;
        float cy = 0.0f;
        int i = 0;
        float temp;
        int i2;
        if (shapeRenderer.shapeType == ShapeType.Line) {
            while (true) {
                int i3 = i;
                if (i3 >= segments2) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f, f2, f3 + height);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                temp = cx;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                float angle2 = angle;
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                i = i3 + 1;
                angle = angle2;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
            i2 = segments2;
        } else {
            segments2--;
            while (true) {
                int i4 = i;
                if (i4 >= segments2) {
                    break;
                }
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f, f2, f3);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                float temp2 = cx;
                temp = cy;
                cx = (cos * cx) - (sin * cy);
                cy = (sin * temp2) + (cos * cy);
                shapeRenderer.renderer.color(colorBits);
                i2 = segments2;
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + temp2, f2 + temp, f3);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
                shapeRenderer.renderer.color(colorBits);
                shapeRenderer.renderer.vertex(f, f2, f3 + height);
                i = i4 + 1;
                segments2 = i2;
            }
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f, f2, f3);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + cy, f3);
        }
        float temp3 = cx;
        angle = cy;
        cx = radius;
        shapeRenderer.renderer.color(colorBits);
        shapeRenderer.renderer.vertex(f + cx, f2 + 0.0f, f3);
        if (shapeRenderer.shapeType != ShapeType.Line) {
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + temp3, f2 + angle, f3);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f + cx, f2 + 0.0f, f3);
            shapeRenderer.renderer.color(colorBits);
            shapeRenderer.renderer.vertex(f, f2, f3 + height);
        }
    }

    public void polygon(float[] vertices, int offset, int count) {
        if (count < 6) {
            throw new IllegalArgumentException("Polygons must contain at least 3 points.");
        } else if (count % 2 != 0) {
            throw new IllegalArgumentException("Polygons must have an even number of vertices.");
        } else {
            check(ShapeType.Line, null, count);
            float colorBits = this.color.toFloatBits();
            float firstX = vertices[0];
            float firstY = vertices[1];
            int n = offset + count;
            for (int i = offset; i < n; i += 2) {
                float x2;
                float y2;
                float x1 = vertices[i];
                float y1 = vertices[i + 1];
                if (i + 2 >= count) {
                    x2 = firstX;
                    y2 = firstY;
                } else {
                    x2 = vertices[i + 2];
                    y2 = vertices[i + 3];
                }
                this.renderer.color(colorBits);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(colorBits);
                this.renderer.vertex(x2, y2, 0.0f);
            }
        }
    }

    public void polygon(float[] vertices) {
        polygon(vertices, 0, vertices.length);
    }

    public void polyline(float[] vertices, int offset, int count) {
        if (count < 4) {
            throw new IllegalArgumentException("Polylines must contain at least 2 points.");
        } else if (count % 2 != 0) {
            throw new IllegalArgumentException("Polylines must have an even number of vertices.");
        } else {
            check(ShapeType.Line, null, count);
            float colorBits = this.color.toFloatBits();
            int n = (offset + count) - 2;
            for (int i = offset; i < n; i += 2) {
                float x1 = vertices[i];
                float y1 = vertices[i + 1];
                float x2 = vertices[i + 2];
                float y2 = vertices[i + 3];
                this.renderer.color(colorBits);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(colorBits);
                this.renderer.vertex(x2, y2, 0.0f);
            }
        }
    }

    public void polyline(float[] vertices) {
        polyline(vertices, 0, vertices.length);
    }

    private void check(ShapeType preferred, ShapeType other, int newVertices) {
        if (this.shapeType == null) {
            throw new IllegalStateException("begin must be called first.");
        } else if (this.shapeType == preferred || this.shapeType == other) {
            ShapeType type;
            if (this.matrixDirty) {
                type = this.shapeType;
                end();
                begin(type);
            } else if (this.renderer.getMaxVertices() - this.renderer.getNumVertices() < newVertices) {
                type = this.shapeType;
                end();
                begin(type);
            }
        } else if (this.autoShapeType) {
            end();
            begin(preferred);
        } else if (other == null) {
            r1 = new StringBuilder();
            r1.append("Must call begin(ShapeType.");
            r1.append(preferred);
            r1.append(").");
            throw new IllegalStateException(r1.toString());
        } else {
            r1 = new StringBuilder();
            r1.append("Must call begin(ShapeType.");
            r1.append(preferred);
            r1.append(") or begin(ShapeType.");
            r1.append(other);
            r1.append(").");
            throw new IllegalStateException(r1.toString());
        }
    }

    public void end() {
        this.renderer.end();
        this.shapeType = null;
    }

    public void flush() {
        ShapeType type = this.shapeType;
        end();
        begin(type);
    }

    public ShapeType getCurrentType() {
        return this.shapeType;
    }

    public ImmediateModeRenderer getRenderer() {
        return this.renderer;
    }

    public boolean isDrawing() {
        return this.shapeType != null;
    }

    public void dispose() {
        this.renderer.dispose();
    }
}
