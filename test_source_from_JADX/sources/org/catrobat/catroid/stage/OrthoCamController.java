package org.catrobat.catroid.stage;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class OrthoCamController extends InputAdapter {
    final OrthographicCamera camera;
    final Vector3 curr = new Vector3();
    final Vector3 delta = new Vector3();
    final Vector3 last = new Vector3(-1.0f, -1.0f, -1.0f);

    public OrthoCamController(OrthographicCamera camera) {
        this.camera = camera;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        this.camera.unproject(this.curr.set((float) x, (float) y, 0.0f));
        if (!(this.last.f120x == -1.0f && this.last.f121y == -1.0f && this.last.f122z == -1.0f)) {
            this.camera.unproject(this.delta.set(this.last.f120x, this.last.f121y, 0.0f));
            this.delta.sub(this.curr);
            this.camera.position.add(this.delta.f120x, this.delta.f121y, 0.0f);
        }
        this.last.set((float) x, (float) y, 0.0f);
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        this.last.set(-1.0f, -1.0f, -1.0f);
        return false;
    }
}
