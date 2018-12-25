package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public abstract class Camera {
    public final Matrix4 combined = new Matrix4();
    public final Vector3 direction = new Vector3(0.0f, 0.0f, -1.0f);
    public float far = 100.0f;
    public final Frustum frustum = new Frustum();
    public final Matrix4 invProjectionView = new Matrix4();
    public float near = 1.0f;
    public final Vector3 position = new Vector3();
    public final Matrix4 projection = new Matrix4();
    private final Ray ray = new Ray(new Vector3(), new Vector3());
    private final Vector3 tmpVec = new Vector3();
    public final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
    public final Matrix4 view = new Matrix4();
    public float viewportHeight = 0.0f;
    public float viewportWidth = 0.0f;

    public abstract void update();

    public abstract void update(boolean z);

    public void lookAt(float x, float y, float z) {
        this.tmpVec.set(x, y, z).sub(this.position).nor();
        if (!this.tmpVec.isZero()) {
            float dot = this.tmpVec.dot(this.up);
            if (Math.abs(dot - 1.0f) < 1.0E-9f) {
                this.up.set(this.direction).scl(-1.0f);
            } else if (Math.abs(1.0f + dot) < 1.0E-9f) {
                this.up.set(this.direction);
            }
            this.direction.set(this.tmpVec);
            normalizeUp();
        }
    }

    public void lookAt(Vector3 target) {
        lookAt(target.f120x, target.f121y, target.f122z);
    }

    public void normalizeUp() {
        this.tmpVec.set(this.direction).crs(this.up).nor();
        this.up.set(this.tmpVec).crs(this.direction).nor();
    }

    public void rotate(float angle, float axisX, float axisY, float axisZ) {
        this.direction.rotate(angle, axisX, axisY, axisZ);
        this.up.rotate(angle, axisX, axisY, axisZ);
    }

    public void rotate(Vector3 axis, float angle) {
        this.direction.rotate(axis, angle);
        this.up.rotate(axis, angle);
    }

    public void rotate(Matrix4 transform) {
        this.direction.rot(transform);
        this.up.rot(transform);
    }

    public void rotate(Quaternion quat) {
        quat.transform(this.direction);
        quat.transform(this.up);
    }

    public void rotateAround(Vector3 point, Vector3 axis, float angle) {
        this.tmpVec.set(point);
        this.tmpVec.sub(this.position);
        translate(this.tmpVec);
        rotate(axis, angle);
        this.tmpVec.rotate(axis, angle);
        translate(-this.tmpVec.f120x, -this.tmpVec.f121y, -this.tmpVec.f122z);
    }

    public void transform(Matrix4 transform) {
        this.position.mul(transform);
        rotate(transform);
    }

    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void translate(Vector3 vec) {
        this.position.add(vec);
    }

    public Vector3 unproject(Vector3 screenCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        float x = screenCoords.f120x;
        float y = ((((float) Gdx.graphics.getHeight()) - screenCoords.f121y) - 1.0f) - viewportY;
        screenCoords.f120x = (((x - viewportX) * 2.0f) / viewportWidth) - 1.0f;
        screenCoords.f121y = ((y * 2.0f) / viewportHeight) - 1.0f;
        screenCoords.f122z = (screenCoords.f122z * 2.0f) - 1.0f;
        screenCoords.prj(this.invProjectionView);
        return screenCoords;
    }

    public Vector3 unproject(Vector3 screenCoords) {
        unproject(screenCoords, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        return screenCoords;
    }

    public Vector3 project(Vector3 worldCoords) {
        project(worldCoords, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        return worldCoords;
    }

    public Vector3 project(Vector3 worldCoords, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        worldCoords.prj(this.combined);
        worldCoords.f120x = (((worldCoords.f120x + 1.0f) * viewportWidth) / 2.0f) + viewportX;
        worldCoords.f121y = (((worldCoords.f121y + 1.0f) * viewportHeight) / 2.0f) + viewportY;
        worldCoords.f122z = (worldCoords.f122z + 1.0f) / 2.0f;
        return worldCoords;
    }

    public Ray getPickRay(float screenX, float screenY, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        float f = viewportX;
        float f2 = viewportY;
        float f3 = viewportWidth;
        float f4 = viewportHeight;
        unproject(this.ray.origin.set(screenX, screenY, 0.0f), f, f2, f3, f4);
        unproject(this.ray.direction.set(screenX, screenY, 1.0f), f, f2, f3, f4);
        this.ray.direction.sub(this.ray.origin).nor();
        return this.ray;
    }

    public Ray getPickRay(float screenX, float screenY) {
        return getPickRay(screenX, screenY, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }
}
