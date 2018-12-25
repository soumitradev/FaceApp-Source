package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Plane.PlaneSide;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Frustum {
    protected static final Vector3[] clipSpacePlanePoints;
    protected static final float[] clipSpacePlanePointsArray = new float[24];
    private static final Vector3 tmpV = new Vector3();
    public final Vector3[] planePoints;
    protected final float[] planePointsArray;
    public final Plane[] planes = new Plane[6];

    static {
        r0 = new Vector3[8];
        int i$ = 0;
        r0[0] = new Vector3(-1.0f, -1.0f, -1.0f);
        r0[1] = new Vector3(1.0f, -1.0f, -1.0f);
        r0[2] = new Vector3(1.0f, 1.0f, -1.0f);
        r0[3] = new Vector3(-1.0f, 1.0f, -1.0f);
        r0[4] = new Vector3(-1.0f, -1.0f, 1.0f);
        r0[5] = new Vector3(1.0f, -1.0f, 1.0f);
        r0[6] = new Vector3(1.0f, 1.0f, 1.0f);
        r0[7] = new Vector3(-1.0f, 1.0f, 1.0f);
        clipSpacePlanePoints = r0;
        int j = 0;
        Vector3[] arr$ = clipSpacePlanePoints;
        int len$ = arr$.length;
        while (i$ < len$) {
            Vector3 v = arr$[i$];
            int j2 = j + 1;
            clipSpacePlanePointsArray[j] = v.f120x;
            int j3 = j2 + 1;
            clipSpacePlanePointsArray[j2] = v.f121y;
            j2 = j3 + 1;
            clipSpacePlanePointsArray[j3] = v.f122z;
            i$++;
            j = j2;
        }
    }

    public Frustum() {
        r1 = new Vector3[8];
        int i = 0;
        r1[0] = new Vector3();
        r1[1] = new Vector3();
        r1[2] = new Vector3();
        r1[3] = new Vector3();
        r1[4] = new Vector3();
        r1[5] = new Vector3();
        r1[6] = new Vector3();
        r1[7] = new Vector3();
        this.planePoints = r1;
        this.planePointsArray = new float[24];
        while (true) {
            int i2 = i;
            if (i2 < 6) {
                this.planes[i2] = new Plane(new Vector3(), 0.0f);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public void update(Matrix4 inverseProjectionView) {
        System.arraycopy(clipSpacePlanePointsArray, 0, this.planePointsArray, 0, clipSpacePlanePointsArray.length);
        Matrix4.prj(inverseProjectionView.val, this.planePointsArray, 0, 8, 3);
        int i = 0;
        int j = 0;
        while (i < 8) {
            Vector3 v = this.planePoints[i];
            int j2 = j + 1;
            v.f120x = this.planePointsArray[j];
            int j3 = j2 + 1;
            v.f121y = this.planePointsArray[j2];
            j2 = j3 + 1;
            v.f122z = this.planePointsArray[j3];
            i++;
            j = j2;
        }
        this.planes[0].set(this.planePoints[1], this.planePoints[0], this.planePoints[2]);
        this.planes[1].set(this.planePoints[4], this.planePoints[5], this.planePoints[7]);
        this.planes[2].set(this.planePoints[0], this.planePoints[4], this.planePoints[3]);
        this.planes[3].set(this.planePoints[5], this.planePoints[1], this.planePoints[6]);
        this.planes[4].set(this.planePoints[2], this.planePoints[3], this.planePoints[6]);
        this.planes[5].set(this.planePoints[4], this.planePoints[0], this.planePoints[1]);
    }

    public boolean pointInFrustum(Vector3 point) {
        for (PlaneSide result : this.planes) {
            if (result.testPoint(point) == PlaneSide.Back) {
                return false;
            }
        }
        return true;
    }

    public boolean pointInFrustum(float x, float y, float z) {
        for (PlaneSide result : this.planes) {
            if (result.testPoint(x, y, z) == PlaneSide.Back) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustum(Vector3 center, float radius) {
        for (int i = 0; i < 6; i++) {
            if (((this.planes[i].normal.f120x * center.f120x) + (this.planes[i].normal.f121y * center.f121y)) + (this.planes[i].normal.f122z * center.f122z) < (-radius) - this.planes[i].f84d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustum(float x, float y, float z, float radius) {
        for (int i = 0; i < 6; i++) {
            if (((this.planes[i].normal.f120x * x) + (this.planes[i].normal.f121y * y)) + (this.planes[i].normal.f122z * z) < (-radius) - this.planes[i].f84d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustumWithoutNearFar(Vector3 center, float radius) {
        for (int i = 2; i < 6; i++) {
            if (((this.planes[i].normal.f120x * center.f120x) + (this.planes[i].normal.f121y * center.f121y)) + (this.planes[i].normal.f122z * center.f122z) < (-radius) - this.planes[i].f84d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustumWithoutNearFar(float x, float y, float z, float radius) {
        for (int i = 2; i < 6; i++) {
            if (((this.planes[i].normal.f120x * x) + (this.planes[i].normal.f121y * y)) + (this.planes[i].normal.f122z * z) < (-radius) - this.planes[i].f84d) {
                return false;
            }
        }
        return true;
    }

    public boolean boundsInFrustum(BoundingBox bounds) {
        int len2 = this.planes.length;
        for (int i = 0; i < len2; i++) {
            if (this.planes[i].testPoint(bounds.getCorner000(tmpV)) == PlaneSide.Back) {
                if (this.planes[i].testPoint(bounds.getCorner001(tmpV)) == PlaneSide.Back) {
                    if (this.planes[i].testPoint(bounds.getCorner010(tmpV)) == PlaneSide.Back) {
                        if (this.planes[i].testPoint(bounds.getCorner011(tmpV)) == PlaneSide.Back) {
                            if (this.planes[i].testPoint(bounds.getCorner100(tmpV)) == PlaneSide.Back) {
                                if (this.planes[i].testPoint(bounds.getCorner101(tmpV)) == PlaneSide.Back) {
                                    if (this.planes[i].testPoint(bounds.getCorner110(tmpV)) == PlaneSide.Back) {
                                        if (this.planes[i].testPoint(bounds.getCorner111(tmpV)) == PlaneSide.Back) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean boundsInFrustum(Vector3 center, Vector3 dimensions) {
        return boundsInFrustum(center.f120x, center.f121y, center.f122z, dimensions.f120x / 2.0f, dimensions.f121y / 2.0f, dimensions.f122z / 2.0f);
    }

    public boolean boundsInFrustum(float x, float y, float z, float halfWidth, float halfHeight, float halfDepth) {
        int len2 = this.planes.length;
        for (int i = 0; i < len2; i++) {
            if (this.planes[i].testPoint(x + halfWidth, y + halfHeight, z + halfDepth) == PlaneSide.Back) {
                if (this.planes[i].testPoint(x + halfWidth, y + halfHeight, z - halfDepth) == PlaneSide.Back) {
                    if (this.planes[i].testPoint(x + halfWidth, y - halfHeight, z + halfDepth) == PlaneSide.Back) {
                        if (this.planes[i].testPoint(x + halfWidth, y - halfHeight, z - halfDepth) == PlaneSide.Back) {
                            if (this.planes[i].testPoint(x - halfWidth, y + halfHeight, z + halfDepth) == PlaneSide.Back) {
                                if (this.planes[i].testPoint(x - halfWidth, y + halfHeight, z - halfDepth) == PlaneSide.Back) {
                                    if (this.planes[i].testPoint(x - halfWidth, y - halfHeight, z + halfDepth) == PlaneSide.Back) {
                                        if (this.planes[i].testPoint(x - halfWidth, y - halfHeight, z - halfDepth) == PlaneSide.Back) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
