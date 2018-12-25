package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

public class DefaultBubbleBehavior implements ViewBehavior {
    private final VisibilityAnimationManager animationManager;

    public DefaultBubbleBehavior(VisibilityAnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public void onHandleGrabbed() {
        this.animationManager.show();
    }

    public void onHandleReleased() {
        this.animationManager.hide();
    }

    public void onScrollStarted() {
    }

    public void onScrollFinished() {
    }
}
