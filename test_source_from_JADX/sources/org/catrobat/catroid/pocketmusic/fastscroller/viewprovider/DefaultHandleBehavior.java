package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

public class DefaultHandleBehavior implements ViewBehavior {
    private final VisibilityAnimationManager animationManager;

    public DefaultHandleBehavior(VisibilityAnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public void onHandleGrabbed() {
        this.animationManager.show();
    }

    public void onHandleReleased() {
        this.animationManager.hide();
    }

    public void onScrollStarted() {
        this.animationManager.show();
    }

    public void onScrollFinished() {
        this.animationManager.hide();
    }
}
