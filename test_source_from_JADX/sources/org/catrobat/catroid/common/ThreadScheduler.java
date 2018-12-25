package org.catrobat.catroid.common;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.actions.EventThread;

public class ThreadScheduler {
    private Actor actor;
    private Array<EventThread> startQueue = new Array();
    private Array<Action> stopQueue = new Array();

    public ThreadScheduler(Actor actor) {
        this.actor = actor;
    }

    public void tick(float delta) {
        Array<Action> actions = this.actor.getActions();
        startThreadsInStartQueue();
        runThreadsForOneTick(actions, delta);
        stopThreadsInStopQueue(actions);
    }

    private void startThreadsInStartQueue() {
        Iterator it = this.startQueue.iterator();
        while (it.hasNext()) {
            EventThread thread = (EventThread) it.next();
            thread.restart();
            this.actor.addAction(thread);
        }
        this.startQueue.clear();
    }

    private void runThreadsForOneTick(Array<Action> actions, float delta) {
        for (int i = 0; i < actions.size; i++) {
            Action action = (Action) actions.get(i);
            if (action.act(delta)) {
                this.stopQueue.add(action);
            }
        }
    }

    private void stopThreadsInStopQueue(Array<Action> actions) {
        actions.removeAll(this.stopQueue, true);
        Iterator it = this.stopQueue.iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if (action instanceof EventThread) {
                ((EventThread) action).notifyWaiter();
            }
        }
        this.stopQueue.clear();
    }

    public void startThread(EventThread threadToBeStarted) {
        removeThreadsWithEqualScriptFromStartQueue(threadToBeStarted);
        this.startQueue.add(threadToBeStarted);
    }

    private void removeThreadsWithEqualScriptFromStartQueue(EventThread threadToBeStarted) {
        Iterator<EventThread> iterator = this.startQueue.iterator();
        while (iterator.hasNext()) {
            EventThread action = (EventThread) iterator.next();
            if (action.getScript() == threadToBeStarted.getScript()) {
                action.notifyWaiter();
                iterator.remove();
            }
        }
    }

    public void stopThreadsWithScript(Script script) {
        Iterator it = this.actor.getActions().iterator();
        while (it.hasNext()) {
            Action action = (Action) it.next();
            if ((action instanceof EventThread) && ((EventThread) action).getScript() == script) {
                this.stopQueue.add(action);
            }
        }
    }

    public void stopThreads(Array<Action> actions) {
        this.stopQueue.addAll((Array) actions);
    }

    public boolean haveAllThreadsFinished() {
        return this.startQueue.size + this.actor.getActions().size == 0;
    }
}
