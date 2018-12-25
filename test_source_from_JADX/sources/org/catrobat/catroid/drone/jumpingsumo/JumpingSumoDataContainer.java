package org.catrobat.catroid.drone.jumpingsumo;

public final class JumpingSumoDataContainer {
    public static final String TAG = JumpingSumoDataContainer.class.getSimpleName();
    private static JumpingSumoDataContainer ourInstance = new JumpingSumoDataContainer();
    private boolean positionHeadUp = true;

    public static JumpingSumoDataContainer getInstance() {
        return ourInstance;
    }

    private JumpingSumoDataContainer() {
    }

    public void setPostion(boolean pos) {
        this.positionHeadUp = pos;
    }

    public boolean getPostion() {
        return this.positionHeadUp;
    }
}
