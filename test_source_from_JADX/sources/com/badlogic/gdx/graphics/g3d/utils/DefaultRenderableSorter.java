package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import java.util.Comparator;

public class DefaultRenderableSorter implements RenderableSorter, Comparator<Renderable> {
    private Camera camera;
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();

    public void sort(Camera camera, Array<Renderable> renderables) {
        this.camera = camera;
        renderables.sort(this);
    }

    public int compare(Renderable o1, Renderable o2) {
        int result = 0;
        int i = 1;
        boolean b1 = o1.material.has(BlendingAttribute.Type) && ((BlendingAttribute) o1.material.get(BlendingAttribute.Type)).blended;
        boolean b2 = o2.material.has(BlendingAttribute.Type) && ((BlendingAttribute) o2.material.get(BlendingAttribute.Type)).blended;
        if (b1 != b2) {
            if (!b1) {
                i = -1;
            }
            return i;
        }
        o1.worldTransform.getTranslation(this.tmpV1);
        o2.worldTransform.getTranslation(this.tmpV2);
        float dst = (float) (((int) (this.camera.position.dst2(this.tmpV1) * 1000.0f)) - ((int) (this.camera.position.dst2(this.tmpV2) * 1000.0f)));
        if (dst < 0.0f) {
            result = -1;
        } else if (dst > 0.0f) {
            result = 1;
        }
        return b1 ? -result : result;
    }
}
