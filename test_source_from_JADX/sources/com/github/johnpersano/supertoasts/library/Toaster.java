package com.github.johnpersano.supertoasts.library;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import com.github.johnpersano.supertoasts.library.utils.AnimationUtils;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

class Toaster extends Handler {
    private static final String ERROR_SAT_VIEWGROUP_NULL = "The SuperActivityToast's ViewGroup was null, could not show.";
    private static final String ERROR_ST_WINDOWMANAGER_NULL = "The SuperToast's WindowManager was null when trying to remove the SuperToast.";
    private static Toaster mToaster;
    private final PriorityQueue<SuperToast> superToastPriorityQueue = new PriorityQueue(10, new SuperToastComparator());

    private static final class Messages {
        private static final int DISPLAY_SUPERTOAST = 4477780;
        private static final int REMOVE_SUPERTOAST = 5395284;
        private static final int SHOW_NEXT = 4281172;

        private Messages() {
        }
    }

    private class SuperToastComparator implements Comparator<SuperToast> {
        private SuperToastComparator() {
        }

        public int compare(SuperToast x, SuperToast y) {
            int i = -1;
            if (x.isShowing() || x.getStyle().priorityLevel < y.getStyle().priorityLevel) {
                return -1;
            }
            if (x.getStyle().priorityLevel > y.getStyle().priorityLevel) {
                return 1;
            }
            if (x.getStyle().timestamp > y.getStyle().timestamp) {
                i = 1;
            }
            return i;
        }
    }

    static synchronized Toaster getInstance() {
        synchronized (Toaster.class) {
            if (mToaster != null) {
                Toaster toaster = mToaster;
                return toaster;
            }
            mToaster = new Toaster();
            toaster = mToaster;
            return toaster;
        }
    }

    private Toaster() {
    }

    void add(SuperToast superToast) {
        this.superToastPriorityQueue.add(superToast);
        showNextSuperToast();
    }

    private void showNextSuperToast() {
        if (!this.superToastPriorityQueue.isEmpty()) {
            SuperToast superToast = (SuperToast) this.superToastPriorityQueue.peek();
            if (!superToast.isShowing()) {
                Message message = obtainMessage(4477780);
                message.obj = superToast;
                sendMessage(message);
            }
        }
    }

    private void sendDelayedMessage(SuperToast superToast, int messageId, long delay) {
        Message message = obtainMessage(messageId);
        message.obj = superToast;
        sendMessageDelayed(message, delay);
    }

    public void handleMessage(Message message) {
        SuperToast superToast = message.obj;
        int i = message.what;
        if (i == 4281172) {
            showNextSuperToast();
        } else if (i == 4477780) {
            displaySuperToast(superToast);
        } else if (i != 5395284) {
            super.handleMessage(message);
        } else {
            removeSuperToast(superToast);
        }
    }

    private void displaySuperToast(SuperToast superToast) {
        if (!superToast.isShowing()) {
            if (!(superToast instanceof SuperActivityToast)) {
                WindowManager windowManager = (WindowManager) superToast.getContext().getApplicationContext().getSystemService("window");
                if (windowManager != null) {
                    windowManager.addView(superToast.getView(), superToast.getWindowManagerParams());
                }
                sendDelayedMessage(superToast, 5395284, ((long) superToast.getDuration()) + 250);
            } else if (((SuperActivityToast) superToast).getViewGroup() == null) {
                Log.e(getClass().getName(), ERROR_SAT_VIEWGROUP_NULL);
            } else {
                try {
                    ((SuperActivityToast) superToast).getViewGroup().addView(superToast.getView());
                    if (!((SuperActivityToast) superToast).isFromOrientationChange()) {
                        AnimationUtils.getShowAnimation((SuperActivityToast) superToast).start();
                    }
                } catch (IllegalStateException illegalStateException) {
                    Log.e(getClass().getName(), illegalStateException.toString());
                }
                if (!((SuperActivityToast) superToast).isIndeterminate()) {
                    sendDelayedMessage(superToast, 5395284, ((long) superToast.getDuration()) + 250);
                }
            }
        }
    }

    void removeSuperToast(final SuperToast superToast) {
        if (!(superToast instanceof SuperActivityToast)) {
            WindowManager windowManager = (WindowManager) superToast.getContext().getSystemService("window");
            if (windowManager == null) {
                throw new IllegalStateException(ERROR_ST_WINDOWMANAGER_NULL);
            }
            try {
                windowManager.removeView(superToast.getView());
            } catch (IllegalArgumentException illegalArgumentException) {
                Log.e(getClass().getName(), illegalArgumentException.toString());
            }
            if (superToast.getOnDismissListener() != null) {
                superToast.getOnDismissListener().onDismiss(superToast.getView(), superToast.getStyle().dismissToken);
            }
            sendDelayedMessage(superToast, 4281172, 250);
        } else if (superToast.isShowing()) {
            Animator animator = AnimationUtils.getHideAnimation((SuperActivityToast) superToast);
            animator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    if (superToast.getOnDismissListener() != null) {
                        superToast.getOnDismissListener().onDismiss(superToast.getView(), superToast.getStyle().dismissToken);
                    }
                    ((SuperActivityToast) superToast).getViewGroup().removeView(superToast.getView());
                    Toaster.this.showNextSuperToast();
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator.start();
        } else {
            this.superToastPriorityQueue.remove(superToast);
            return;
        }
        this.superToastPriorityQueue.poll();
    }

    void cancelAllSuperToasts() {
        removeMessages(4281172);
        removeMessages(4477780);
        removeMessages(5395284);
        Iterator it = this.superToastPriorityQueue.iterator();
        while (it.hasNext()) {
            SuperToast superToast = (SuperToast) it.next();
            if (!(superToast instanceof SuperActivityToast)) {
                WindowManager windowManager = (WindowManager) superToast.getContext().getApplicationContext().getSystemService("window");
                if (superToast.isShowing()) {
                    try {
                        windowManager.removeView(superToast.getView());
                    } catch (RuntimeException exception) {
                        Log.e(getClass().getName(), exception.toString());
                    }
                }
            } else if (superToast.isShowing()) {
                try {
                    ((SuperActivityToast) superToast).getViewGroup().removeView(superToast.getView());
                    ((SuperActivityToast) superToast).getViewGroup().invalidate();
                } catch (RuntimeException exception2) {
                    Log.e(getClass().getName(), exception2.toString());
                }
            }
        }
        this.superToastPriorityQueue.clear();
    }

    public PriorityQueue<SuperToast> getQueue() {
        return this.superToastPriorityQueue;
    }
}
