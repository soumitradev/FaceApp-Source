package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

public interface ViewBehavior {
    void onHandleGrabbed();

    void onHandleReleased();

    void onScrollFinished();

    void onScrollStarted();
}
