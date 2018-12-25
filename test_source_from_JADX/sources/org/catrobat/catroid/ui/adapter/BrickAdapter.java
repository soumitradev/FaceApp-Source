package org.catrobat.catroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.AllowedAfterDeadEndBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.BrickViewProvider;
import org.catrobat.catroid.content.bricks.DeadEndBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;
import org.catrobat.catroid.content.bricks.LoopEndlessBrick;
import org.catrobat.catroid.content.bricks.NestingBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.bricks.UserBrickParameter;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrick;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.ViewSwitchLock;
import org.catrobat.catroid.ui.dragndrop.BrickListView;
import org.catrobat.catroid.ui.dragndrop.DragAndDropListener;
import org.catrobat.catroid.ui.fragment.CategoryBricksFactory;
import org.catrobat.catroid.ui.fragment.ScriptFragment;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.UtilDeviceInfo;

public class BrickAdapter extends BrickBaseAdapter implements DragAndDropListener, OnClickListener {
    private static final String TAG = BrickAdapter.class.getSimpleName();
    private BrickAdapter$ActionModeEnum actionMode = BrickAdapter$ActionModeEnum.NO_ACTION;
    private boolean addingNewBrick;
    private AlertDialog alertDialog = null;
    private List<Brick> animatedBricks;
    private BrickListView brickDragAndDropListView;
    private int clickItemPosition = 0;
    private int dragTargetPosition;
    private Brick draggedBrick;
    private boolean firstDrag;
    private int fromBeginDrag;
    private boolean initInsertedBrick;
    private View insertionView;
    public boolean isDragging = false;
    public int listItemCount = 0;
    private int positionOfInsertedBrick;
    private boolean retryScriptDragging;
    private Script script;
    private Script scriptToDelete;
    private int selectMode;
    private boolean showDetails = false;
    private Sprite sprite;
    private int toEndDrag;
    private UserBrick userBrick;
    private Lock viewSwitchLock = new ViewSwitchLock();

    public BrickAdapter(ScriptFragment scriptFragment, Sprite sprite, BrickListView listView) {
        this.scriptFragment = scriptFragment;
        this.context = scriptFragment.getActivity();
        this.sprite = sprite;
        this.brickDragAndDropListView = listView;
        this.insertionView = View.inflate(this.context, R.layout.brick_insert, null);
        this.initInsertedBrick = false;
        this.addingNewBrick = false;
        this.firstDrag = true;
        this.retryScriptDragging = false;
        this.animatedBricks = new ArrayList();
        this.selectMode = 0;
        initBrickList();
    }

    public Context getContext() {
        return this.context;
    }

    public void initBrickList() {
        this.brickList = new ArrayList();
        if (this.userBrick != null) {
            initBrickListUserScript();
            return;
        }
        Sprite sprite = ProjectManager.getInstance().getCurrentSprite();
        int numberOfScripts = sprite.getNumberOfScripts();
        for (int scriptPosition = 0; scriptPosition < numberOfScripts; scriptPosition++) {
            Script script = sprite.getScript(scriptPosition);
            this.brickList.add(script.getScriptBrick());
            script.getScriptBrick().setBrickAdapter(this);
            Iterator it = script.getBrickList().iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                this.brickList.add(brick);
                brick.setBrickAdapter(this);
            }
        }
    }

    private void initBrickListUserScript() {
        this.script = getUserScript();
        this.brickList = new ArrayList();
        this.brickList.add(this.script.getScriptBrick());
        this.script.getScriptBrick().setBrickAdapter(this);
        Iterator it = this.script.getBrickList().iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            this.brickList.add(brick);
            brick.setBrickAdapter(this);
        }
    }

    private Script getUserScript() {
        return this.userBrick.getDefinitionBrick().getScript();
    }

    public void resetAlphas() {
        for (Brick brick : this.brickList) {
            brick.setAlpha(255);
        }
        notifyDataSetChanged();
    }

    public BrickAdapter$ActionModeEnum getActionMode() {
        return this.actionMode;
    }

    public void setActionMode(BrickAdapter$ActionModeEnum actionMode) {
        this.actionMode = actionMode;
    }

    public List<Brick> getBrickList() {
        return this.brickList;
    }

    public void setBrickList(List<Brick> brickList) {
        this.brickList = brickList;
    }

    public void drag(int from, int to) {
        int toOriginal = to;
        if (to < 0 || to >= this.brickList.size()) {
            to = this.brickList.size() - 1;
        }
        if (from < 0 || from >= this.brickList.size()) {
            from = this.brickList.size() - 1;
        }
        if (this.draggedBrick == null) {
            this.draggedBrick = (Brick) getItem(from);
            notifyDataSetChanged();
        }
        if (this.firstDrag) {
            this.fromBeginDrag = from;
            this.firstDrag = false;
        }
        if (this.draggedBrick instanceof NestingBrick) {
            NestingBrick nestingBrick = this.draggedBrick;
            if (nestingBrick.isInitialized()) {
                if (nestingBrick.getAllNestingBrickParts().get(0) == nestingBrick) {
                    to = adjustNestingBrickDraggedPosition(nestingBrick, this.fromBeginDrag, to);
                } else {
                    to = getDraggedNestingBricksToPosition(nestingBrick, to);
                }
            }
        } else if (this.draggedBrick instanceof ScriptBrick) {
            int currentPosition = to;
            this.brickList.remove(this.draggedBrick);
            this.brickList.add(to, this.draggedBrick);
            to = getNewPositionForScriptBrick(to, this.draggedBrick);
            this.dragTargetPosition = to;
            if (currentPosition != to) {
                this.retryScriptDragging = true;
            } else {
                this.retryScriptDragging = false;
            }
        }
        to = getNewPositionIfEndingBrickIsThere(to, this.draggedBrick);
        if (!(this.draggedBrick instanceof ScriptBrick)) {
            if (to != 0) {
                this.dragTargetPosition = to;
            } else {
                this.dragTargetPosition = 1;
                to = 1;
            }
        }
        this.brickList.remove(this.draggedBrick);
        if (this.dragTargetPosition < 0 || this.dragTargetPosition > this.brickList.size()) {
            this.brickList.add(toOriginal, this.draggedBrick);
            this.toEndDrag = toOriginal;
        } else {
            this.brickList.add(this.dragTargetPosition, this.draggedBrick);
            this.toEndDrag = to;
        }
        this.animatedBricks.clear();
        notifyDataSetChanged();
    }

    private int getNewPositionIfEndingBrickIsThere(int to, Brick brick) {
        int currentPosition = this.brickList.indexOf(brick);
        if (!(getItem(to) instanceof AllowedAfterDeadEndBrick) || (getItem(to) instanceof DeadEndBrick) || !(getItem(to - 1) instanceof DeadEndBrick)) {
            if (getItem(to) instanceof DeadEndBrick) {
                int i = to - 1;
                while (i >= 0) {
                    if (getItem(i) instanceof DeadEndBrick) {
                        i--;
                    } else if (currentPosition > i) {
                        return i + 1;
                    } else {
                        return i;
                    }
                }
            }
            return to;
        } else if (currentPosition > to) {
            return to + 1;
        } else {
            return to;
        }
    }

    private int adjustNestingBrickDraggedPosition(NestingBrick nestingBrick, int from, int to) {
        List<NestingBrick> nestingBrickList = nestingBrick.getAllNestingBrickParts();
        boolean isNewPositionBetweenStartAndEndNestedBrick = true;
        int endBrickPosition = this.brickList.indexOf((NestingBrick) nestingBrickList.get(nestingBrickList.size() - 1));
        if (to <= from || to >= endBrickPosition) {
            isNewPositionBetweenStartAndEndNestedBrick = false;
        }
        if (isNewPositionBetweenStartAndEndNestedBrick) {
            return endBrickPosition;
        }
        return to;
    }

    private int getDraggedNestingBricksToPosition(NestingBrick nestingBrick, int to) {
        int i;
        List<NestingBrick> nestingBrickList = nestingBrick.getAllNestingBrickParts();
        int restrictedTop = 0;
        int restrictedBottom = this.brickList.size();
        int currentPosition = to;
        boolean passedBrick = false;
        for (NestingBrick temp : nestingBrickList) {
            int tempPosition = this.brickList.indexOf(temp);
            if (temp != nestingBrick) {
                if (!passedBrick) {
                    restrictedTop = tempPosition;
                }
                if (passedBrick) {
                    restrictedBottom = tempPosition;
                    break;
                }
            } else {
                passedBrick = true;
                currentPosition = tempPosition;
            }
        }
        for (i = currentPosition; i > restrictedTop; i--) {
            if (checkIfScriptOrOtherNestingBrick((Brick) this.brickList.get(i), nestingBrickList)) {
                restrictedTop = i;
                break;
            }
        }
        for (i = currentPosition; i < restrictedBottom; i++) {
            if (checkIfScriptOrOtherNestingBrick((Brick) this.brickList.get(i), nestingBrickList)) {
                restrictedBottom = i;
                break;
            }
        }
        to = to <= restrictedTop ? restrictedTop + 1 : to;
        return to >= restrictedBottom ? restrictedBottom - 1 : to;
    }

    private boolean checkIfScriptOrOtherNestingBrick(Brick brick, List<NestingBrick> nestingBrickList) {
        if (brick instanceof ScriptBrick) {
            return true;
        }
        if (!(brick instanceof NestingBrick) || nestingBrickList.contains(brick)) {
            return false;
        }
        return true;
    }

    public void drop() {
        int to = this.toEndDrag;
        if (to < 0 || to >= this.brickList.size()) {
            to = this.brickList.size() - 1;
        }
        if (!this.retryScriptDragging) {
            if (to == getNewPositionForScriptBrick(to, this.draggedBrick)) {
                int tempTo = getNewPositionIfEndingBrickIsThere(to, this.draggedBrick);
                if (to != tempTo) {
                    to = tempTo;
                }
                if (this.addingNewBrick) {
                    if (this.draggedBrick instanceof ScriptBrick) {
                        addScriptToProject(to, (ScriptBrick) this.draggedBrick);
                    } else if (this.script != null) {
                        addBrickToPositionInUserScript(to, this.draggedBrick);
                    } else {
                        addBrickToPositionInProject(to, this.draggedBrick);
                    }
                    if (!this.draggedBrick.isCommentedOut()) {
                        enableCorrespondingScriptBrick(to);
                    }
                    if (this.draggedBrick instanceof UserBrick) {
                        ((UserBrick) this.draggedBrick).updateUserBrickParametersAndVariables();
                    }
                    this.addingNewBrick = false;
                } else {
                    if (this.script != null) {
                        moveUserBrick(this.fromBeginDrag, this.toEndDrag);
                    } else if (this.draggedBrick instanceof NestingBrick) {
                        moveNestingBrick(this.fromBeginDrag, this.toEndDrag);
                    } else {
                        moveExistingProjectBrick(this.fromBeginDrag, this.toEndDrag);
                    }
                    if (!this.draggedBrick.isCommentedOut()) {
                        enableCorrespondingScriptBrick(this.toEndDrag);
                    }
                }
                this.draggedBrick = null;
                this.firstDrag = true;
                initBrickList();
                notifyDataSetChanged();
                int scrollTo = to;
                if (scrollTo >= this.brickList.size() - 1) {
                    scrollTo = getCount() - 1;
                }
                this.brickDragAndDropListView.smoothScrollToPosition(scrollTo);
                setSpinnersEnabled(true);
                this.isDragging = false;
                SnackbarUtil.showHintSnackbar((Activity) getContext(), R.string.hint_brick_added);
                return;
            }
        }
        scrollToPosition(this.dragTargetPosition);
        this.draggedBrick = null;
        this.initInsertedBrick = true;
        this.positionOfInsertedBrick = this.dragTargetPosition;
        notifyDataSetChanged();
        this.retryScriptDragging = false;
    }

    private void addScriptToProject(int position, ScriptBrick scriptBrick) {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int[] temp = getScriptAndBrickIndexFromProject(position);
        int addScriptTo = 0;
        int scriptPosition = temp[0];
        int brickPosition = temp[1];
        Script newScript = scriptBrick.getScript();
        if (currentSprite.getNumberOfBricks() > 0) {
            if (position != 0) {
                addScriptTo = scriptPosition + 1;
            }
            currentSprite.addScript(addScriptTo, newScript);
        } else {
            currentSprite.addScript(newScript);
        }
        Script previousScript = currentSprite.getScript(scriptPosition);
        if (previousScript != null) {
            int size = previousScript.getBrickList().size();
            for (int i = brickPosition; i < size; i++) {
                Brick brick = previousScript.getBrick(brickPosition);
                previousScript.removeBrick(brick);
                newScript.addBrick(brick);
            }
        }
        ProjectManager.getInstance().setCurrentScript(newScript);
    }

    private void moveUserBrick(int from, int to) {
        Brick brick = this.script.getBrick(getPositionInUserScript(from));
        this.script.removeBrick(brick);
        this.script.addBrick(getPositionInUserScript(to), brick);
    }

    private void moveNestingBrick(int from, int to) {
        int i = from;
        int i2 = to;
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int[] tempFrom = getScriptAndBrickIndexFromProject(from);
        int scriptPositionFrom = tempFrom[0];
        int brickPositionFrom = tempFrom[1];
        Script fromScript = currentSprite.getScript(scriptPositionFrom);
        List<NestingBrick> nestingBricks = ((NestingBrick) fromScript.getBrick(brickPositionFrom)).getAllNestingBrickParts();
        NestingBrick endNestingBrick = (NestingBrick) nestingBricks.get(nestingBricks.size() - 1);
        List<Brick> fromScriptBrickList = fromScript.getBrickList();
        int endPosition = fromScriptBrickList.indexOf(endNestingBrick);
        int count = endPosition - brickPositionFrom;
        boolean isNewPositionBetweenStartAndEndNestedBrick = i2 > i && count > i2 - i;
        if (!isNewPositionBetweenStartAndEndNestedBrick) {
            List<Brick> block = fromScriptBrickList.subList(brickPositionFrom, endPosition + 1);
            List<Brick> removedBlock = new ArrayList();
            removedBlock.add(block.remove(0));
            int[] tempTo = getScriptAndBrickIndexFromProject(i2);
            brickPositionFrom = tempTo[0];
            boolean moveBrickInSameScript = true;
            int brickPositionTo = tempTo[1];
            Script toScript = currentSprite.getScript(brickPositionFrom);
            removedBlock.addAll(block);
            block.clear();
            int finalBrickPositionTo = brickPositionTo;
            if (brickPositionFrom != scriptPositionFrom || i2 <= i) {
                moveBrickInSameScript = false;
            }
            if (moveBrickInSameScript) {
                finalBrickPositionTo -= count;
            }
            toScript.getBrickList().addAll(finalBrickPositionTo, removedBlock);
        }
    }

    private void moveExistingProjectBrick(int from, int to) {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int[] tempFrom = getScriptAndBrickIndexFromProject(from);
        int scriptPositionFrom = tempFrom[0];
        int brickPositionFrom = tempFrom[1];
        Script fromScript = currentSprite.getScript(scriptPositionFrom);
        Brick brick = fromScript.getBrick(brickPositionFrom);
        if (this.draggedBrick != brick) {
            Log.e(TAG, "Want to save wrong brick");
            return;
        }
        fromScript.removeBrick(brick);
        int[] tempTo = getScriptAndBrickIndexFromProject(to);
        int scriptPositionTo = tempTo[0];
        currentSprite.getScript(scriptPositionTo).addBrick(tempTo[1], brick);
    }

    private void addBrickToPositionInProject(int position, Brick brick) {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int[] temp = getScriptAndBrickIndexFromProject(position);
        int i = 0;
        int scriptPosition = temp[0];
        int brickPosition = temp[1];
        Script script = currentSprite.getScript(scriptPosition);
        if (brick instanceof NestingBrick) {
            ((NestingBrick) this.draggedBrick).initialize();
            List<NestingBrick> nestingBrickList = ((NestingBrick) this.draggedBrick).getAllNestingBrickParts();
            while (i < nestingBrickList.size()) {
                if (nestingBrickList.get(i) instanceof DeadEndBrick) {
                    if (i < nestingBrickList.size() - 1) {
                        Log.w(TAG, "Adding a DeadEndBrick in the middle of the NestingBricks");
                    }
                    position = getPositionForDeadEndBrick(position);
                    script.addBrick(getScriptAndBrickIndexFromProject(position)[1], (Brick) nestingBrickList.get(i));
                } else {
                    script.addBrick(brickPosition + i, (Brick) nestingBrickList.get(i));
                }
                i++;
            }
            return;
        }
        script.addBrick(brickPosition, brick);
    }

    private void addBrickToPositionInUserScript(int position, Brick brick) {
        position = getPositionInUserScript(position);
        if (brick instanceof NestingBrick) {
            ((NestingBrick) this.draggedBrick).initialize();
            List<NestingBrick> nestingBrickList = ((NestingBrick) this.draggedBrick).getAllNestingBrickParts();
            for (int i = 0; i < nestingBrickList.size(); i++) {
                this.script.addBrick(position + i, (Brick) nestingBrickList.get(i));
            }
            return;
        }
        this.script.addBrick(position, brick);
    }

    private int getPositionForDeadEndBrick(int position) {
        int i = position + 1;
        while (i < this.brickList.size()) {
            if (!(this.brickList.get(i) instanceof AllowedAfterDeadEndBrick)) {
                if (!(this.brickList.get(i) instanceof DeadEndBrick)) {
                    if (this.brickList.get(i) instanceof NestingBrick) {
                        List<NestingBrick> tempList = ((NestingBrick) this.brickList.get(i)).getAllNestingBrickParts();
                        int currentPosition = i;
                        i = this.brickList.indexOf(tempList.get(tempList.size() - 1)) + 1;
                        if (i < 0) {
                            i = currentPosition;
                        } else if (i >= this.brickList.size()) {
                            return this.brickList.size();
                        }
                    }
                    if (!(this.brickList.get(i) instanceof AllowedAfterDeadEndBrick)) {
                        if (!(this.brickList.get(i) instanceof DeadEndBrick)) {
                            i++;
                        }
                    }
                    return i;
                }
            }
            return i;
        }
        return this.brickList.size();
    }

    private int getPositionInUserScript(int position) {
        position--;
        if (position < 0) {
            position = 0;
        }
        if (position >= this.brickList.size()) {
            return this.brickList.size() - 1;
        }
        Brick scriptBrick;
        List<Brick> brickListFromScript = this.script.getBrickList();
        if (brickListFromScript.size() == 0 || position >= brickListFromScript.size()) {
            scriptBrick = null;
        } else {
            scriptBrick = (Brick) brickListFromScript.get(position);
        }
        int returnValue = this.script.getBrickList().indexOf(scriptBrick);
        if (returnValue < 0) {
            returnValue = this.script.getBrickList().size();
        }
        return returnValue;
    }

    private int[] getScriptAndBrickIndexFromProject(int position) {
        int[] returnValue = new int[2];
        if (position >= this.brickList.size()) {
            returnValue[0] = this.sprite.getNumberOfScripts() - 1;
            if (returnValue[0] < 0) {
                returnValue[0] = 0;
                returnValue[1] = 0;
            } else {
                Script script = this.sprite.getScript(returnValue[0]);
                if (script != null) {
                    returnValue[1] = script.getBrickList().size();
                } else {
                    returnValue[1] = 0;
                }
            }
            return returnValue;
        }
        Brick brickFromProject;
        int scriptPosition = 0;
        int scriptOffset = 0;
        while (scriptOffset < position) {
            scriptOffset += this.sprite.getScript(scriptPosition).getBrickList().size() + 1;
            if (scriptOffset < position) {
                scriptPosition++;
            }
        }
        scriptOffset -= this.sprite.getScript(scriptPosition).getBrickList().size();
        returnValue[0] = scriptPosition;
        List<Brick> brickListFromProject = this.sprite.getScript(scriptPosition).getBrickList();
        int brickPosition = position;
        if (scriptOffset > 0) {
            brickPosition -= scriptOffset;
        }
        if (brickListFromProject.size() == 0 || brickPosition >= brickListFromProject.size()) {
            brickFromProject = null;
        } else {
            brickFromProject = (Brick) brickListFromProject.get(brickPosition);
        }
        returnValue[1] = this.sprite.getScript(scriptPosition).getBrickList().indexOf(brickFromProject);
        if (returnValue[1] < 0) {
            returnValue[1] = this.sprite.getScript(scriptPosition).getBrickList().size();
        }
        return returnValue;
    }

    private void scrollToPosition(int position) {
        BrickListView list = this.brickDragAndDropListView;
        if (list.getFirstVisiblePosition() >= position || position >= list.getLastVisiblePosition()) {
            list.setIsScrolling();
            if (position <= list.getFirstVisiblePosition()) {
                list.smoothScrollToPosition(0, position + 2);
            } else {
                list.smoothScrollToPosition(this.brickList.size() - 1, position - 2);
            }
        }
    }

    public void addNewBrick(int position, Brick brickToBeAdded, boolean initInsertedBrick) {
        if (this.draggedBrick != null) {
            Log.w(TAG, "Want to add Brick while there is another one currently dragged.");
            return;
        }
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        int scriptCount = currentSprite.getNumberOfScripts();
        if (scriptCount == 0 && (brickToBeAdded instanceof ScriptBrick)) {
            currentSprite.addScript(((ScriptBrick) brickToBeAdded).getScript());
            initBrickList();
            notifyDataSetChanged();
            return;
        }
        if (position < 0) {
            position = 0;
        } else if (position > this.brickList.size()) {
            position = this.brickList.size();
        }
        if (brickToBeAdded instanceof ScriptBrick) {
            this.brickList.add(position, brickToBeAdded);
            position = getNewPositionForScriptBrick(position, brickToBeAdded);
            this.brickList.remove(brickToBeAdded);
            this.brickList.add(position, brickToBeAdded);
            scrollToPosition(position);
        } else {
            position = getNewPositionIfEndingBrickIsThere(position, brickToBeAdded);
            position = position <= 0 ? 1 : position;
            position = position > this.brickList.size() ? this.brickList.size() : position;
            this.brickList.add(position, brickToBeAdded);
        }
        this.initInsertedBrick = initInsertedBrick;
        this.positionOfInsertedBrick = position;
        if (scriptCount == 0 && this.userBrick == null) {
            Script script = new StartScript();
            currentSprite.addScript(script);
            this.brickList.add(0, script.getScriptBrick());
            ProjectManager.getInstance().setCurrentScript(script);
            clearCheckedItems();
            this.positionOfInsertedBrick = 1;
        }
        notifyDataSetChanged();
    }

    private int getNewPositionForScriptBrick(int position, Brick brick) {
        if (this.brickList.size() == 0) {
            return 0;
        }
        if (!(brick instanceof ScriptBrick)) {
            return position;
        }
        int i = position;
        int nextPossiblePosition = position;
        int lastPossiblePosition = i;
        while (i < this.brickList.size()) {
            if (this.brickList.get(i) instanceof NestingBrick) {
                List<NestingBrick> bricks = ((NestingBrick) this.brickList.get(i)).getAllNestingBrickParts();
                int beginningPosition = this.brickList.indexOf(bricks.get(0));
                int endingPosition = this.brickList.indexOf(bricks.get(bricks.size() - 1));
                if (position >= beginningPosition && position <= endingPosition) {
                    lastPossiblePosition = beginningPosition;
                    nextPossiblePosition = endingPosition;
                    i = endingPosition;
                }
            }
            if ((this.brickList.get(i) instanceof ScriptBrick) && this.brickList.get(i) != brick) {
                break;
            }
            i++;
        }
        if (position <= lastPossiblePosition) {
            return position;
        }
        if (position - lastPossiblePosition < nextPossiblePosition - position) {
            return lastPossiblePosition;
        }
        return nextPossiblePosition;
    }

    public void remove(int iWillBeIgnored) {
        removeFromBrickListAndProject(this.fromBeginDrag, false);
    }

    public void removeFromBrickListAndProject(int index, boolean removeScript) {
        if (this.addingNewBrick) {
            this.brickList.remove(this.draggedBrick);
        } else if (this.script == null) {
            int[] temp = getScriptAndBrickIndexFromProject(index);
            Script script = ProjectManager.getInstance().getCurrentSprite().getScript(temp[0]);
            if (script != null) {
                BrickBaseType brick = (BrickBaseType) script.getBrick(temp[1]);
                if (brick instanceof NestingBrick) {
                    for (NestingBrick tempBrick : ((NestingBrick) brick).getAllNestingBrickParts()) {
                        script.removeBrick((Brick) tempBrick);
                    }
                } else {
                    script.removeBrick(brick);
                }
                if (removeScript) {
                    this.brickList.remove(script);
                }
            }
        } else {
            BrickBaseType brick2 = (BrickBaseType) this.script.getBrick(getPositionInUserScript(index));
            if (brick2 instanceof NestingBrick) {
                for (NestingBrick tempBrick2 : ((NestingBrick) brick2).getAllNestingBrickParts()) {
                    this.script.removeBrick((Brick) tempBrick2);
                }
            } else {
                this.script.removeBrick(brick2);
            }
        }
        this.firstDrag = true;
        this.draggedBrick = null;
        this.addingNewBrick = false;
        initBrickList();
        notifyDataSetChanged();
    }

    public void removeDraggedBrick() {
        if (this.addingNewBrick) {
            this.brickList.remove(this.draggedBrick);
            this.firstDrag = true;
            this.draggedBrick = null;
            this.addingNewBrick = false;
            initBrickList();
            notifyDataSetChanged();
            return;
        }
        this.draggedBrick = null;
        this.firstDrag = true;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.brickList.size();
    }

    public Object getItem(int element) {
        if (element >= 0) {
            if (element < this.brickList.size()) {
                return this.brickList.get(element);
            }
        }
        return null;
    }

    public long getItemId(int index) {
        return (long) index;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.draggedBrick != null && this.dragTargetPosition == position) {
            return this.insertionView;
        }
        this.listItemCount = position + 1;
        BrickBaseType brick = (BrickBaseType) getItem(position);
        View currentBrickView = brick.getView(this.context);
        BrickViewProvider.setSaturationOnView(currentBrickView, brick.isCommentedOut());
        currentBrickView.setOnClickListener(this);
        if (!(brick instanceof ScriptBrick)) {
            currentBrickView.setOnLongClickListener(this.brickDragAndDropListView);
        }
        boolean enableSpinners = !this.isDragging && this.actionMode == BrickAdapter$ActionModeEnum.NO_ACTION;
        setSpinnersEnabled(enableSpinners);
        if (position != this.positionOfInsertedBrick || !this.initInsertedBrick || this.selectMode != 0) {
            return currentBrickView;
        }
        this.initInsertedBrick = false;
        this.addingNewBrick = true;
        this.brickDragAndDropListView.setInsertedBrick(position);
        this.brickDragAndDropListView.setDraggingNewBrick();
        this.brickDragAndDropListView.onLongClick(currentBrickView);
        return this.insertionView;
    }

    public void updateProjectBrickList() {
        initBrickList();
        notifyDataSetChanged();
    }

    public void setTouchedScript(int index) {
        if (index >= 0 && index < this.brickList.size() && (this.brickList.get(index) instanceof ScriptBrick) && this.draggedBrick == null) {
            int scriptIndex = getScriptIndexFromProject(index);
            if (scriptIndex == -1) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setTouchedScript() Could not get ScriptIndex. index was ");
                stringBuilder.append(index);
                Log.e(str, stringBuilder.toString());
                return;
            }
            ProjectManager.getInstance().setCurrentScript(this.sprite.getScript(scriptIndex));
        }
    }

    private int getScriptIndexFromProject(int index) {
        int scriptIndex = 0;
        int i = 0;
        while (i < index) {
            Script temporaryScript = this.sprite.getScript(scriptIndex);
            if (temporaryScript == null) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getScriptIndexFromProject() tmpScript was null. Index was ");
                stringBuilder.append(index);
                stringBuilder.append(" scriptIndex was ");
                stringBuilder.append(scriptIndex);
                Log.e(str, stringBuilder.toString());
                return -1;
            }
            i += temporaryScript.getBrickList().size() + 1;
            if (i <= index) {
                scriptIndex++;
            }
        }
        return scriptIndex;
    }

    public int getChildCountFromLastGroup() {
        return ProjectManager.getInstance().getCurrentSprite().getScript(getScriptCount() - 1).getBrickList().size();
    }

    public Brick getChild(int scriptPosition, int brickPosition) {
        return ProjectManager.getInstance().getCurrentSprite().getScript(scriptPosition).getBrick(brickPosition);
    }

    public int getScriptCount() {
        return ProjectManager.getInstance().getCurrentSprite().getNumberOfScripts();
    }

    public AlertDialog getAlertDialog() {
        return this.alertDialog;
    }

    public void onClick(View view) {
        if (this.actionMode == BrickAdapter$ActionModeEnum.NO_ACTION && !this.isDragging && this.viewSwitchLock.tryLock()) {
            this.animatedBricks.clear();
            int itemPosition = calculateItemPositionAndTouchPointY(view);
            List<CharSequence> items = new ArrayList();
            if (this.brickList.get(itemPosition) instanceof ScriptBrick) {
                ProjectManager.getInstance().setCurrentScript(this.sprite.getScript(getScriptIndexFromProject(itemPosition)));
            }
            if (!((this.brickList.get(itemPosition) instanceof DeadEndBrick) || (this.brickList.get(itemPosition) instanceof ScriptBrick))) {
                items.add(this.context.getText(R.string.brick_context_dialog_move_brick));
            }
            if (this.brickList.get(itemPosition) instanceof NestingBrick) {
                items.add(this.context.getText(R.string.brick_context_dialog_animate_bricks));
            }
            if (this.brickList.get(itemPosition) instanceof ScriptBrick) {
                items.add(this.context.getText(R.string.brick_context_dialog_delete_script));
                items.add(this.context.getText(R.string.backpack_add));
            } else {
                items.add(this.context.getText(R.string.brick_context_dialog_copy_brick));
                items.add(this.context.getText(R.string.brick_context_dialog_delete_brick));
            }
            if (brickHasAFormula((Brick) this.brickList.get(itemPosition))) {
                items.add(this.context.getText(R.string.brick_context_dialog_formula_edit_brick));
            }
            if (((Brick) this.brickList.get(itemPosition)).isCommentedOut()) {
                items.add(this.context.getText(R.string.brick_context_dialog_comment_in));
            } else {
                items.add(this.context.getText(R.string.brick_context_dialog_comment_out));
            }
            if (!((this.brickList.get(itemPosition) instanceof UserBrick) || isBrickWithoutDescription((Brick) this.brickList.get(itemPosition)))) {
                items.add(this.context.getText(R.string.brick_context_dialog_help));
            }
            AlertDialog$Builder builder = new AlertDialog$Builder(this.context);
            boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            view.setDrawingCacheBackgroundColor(0);
            view.buildDrawingCache(true);
            if (view.getDrawingCache() != null) {
                Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
                view.setDrawingCacheEnabled(drawingCacheEnabled);
                builder.setCustomTitle(this.brickDragAndDropListView.getGlowingBorder(bitmap));
            }
            builder.setItems((CharSequence[]) items.toArray(new CharSequence[items.size()]), new BrickAdapter$1(this, items, view, itemPosition));
            this.alertDialog = builder.create();
            if (this.selectMode == 0) {
                this.alertDialog.show();
            }
        }
    }

    protected void copyBrickListAndProject(int itemPosition) {
        Brick origin = (Brick) this.brickDragAndDropListView.getItemAtPosition(itemPosition);
        try {
            Brick copy = origin.clone();
            copy.setCommentedOut(origin.isCommentedOut());
            addNewBrick(itemPosition, copy, true);
            notifyDataSetChanged();
        } catch (CloneNotSupportedException exception) {
            Log.e(TAG, Log.getStackTraceString(exception));
        }
    }

    private void showConfirmDeleteDialog(int itemPosition) {
        this.clickItemPosition = itemPosition;
        AlertDialog$Builder builder = new AlertDialog$Builder(this.context);
        if (getItem(this.clickItemPosition) instanceof ScriptBrick) {
            builder.setTitle(this.context.getResources().getQuantityString(R.plurals.delete_scripts, 1));
        } else {
            builder.setTitle(this.context.getResources().getQuantityString(R.plurals.delete_bricks, 1));
        }
        builder.setMessage((int) R.string.dialog_confirm_delete);
        builder.setPositiveButton((int) R.string.yes, new BrickAdapter$2(this));
        builder.setNegativeButton((int) R.string.no, new BrickAdapter$3(this));
        builder.setOnCancelListener(new BrickAdapter$4(this));
        builder.create().show();
    }

    private void clickedEditFormula(Brick brick, View view) {
        FormulaBrick formulaBrick = null;
        if (brick instanceof FormulaBrick) {
            formulaBrick = (FormulaBrick) brick;
        }
        if (brick instanceof UserBrick) {
            List<UserBrickParameter> userBrickParameters = ((UserBrick) brick).getUserBrickParameters();
            if (userBrickParameters != null && userBrickParameters.size() > 0) {
                formulaBrick = (FormulaBrick) userBrickParameters.get(0);
            }
        }
        if (formulaBrick != null) {
            formulaBrick.showFormulaEditorToEditFormula(view);
        }
    }

    private boolean brickHasAFormula(Brick brick) {
        boolean multiFormulaValid = false;
        if (brick instanceof UserBrick) {
            multiFormulaValid = ((UserBrick) brick).getFormulas().size() > 0;
        }
        if (!(brick instanceof FormulaBrick)) {
            if (!multiFormulaValid) {
                return false;
            }
        }
        return true;
    }

    private boolean isBrickWithoutDescription(Brick brick) {
        String name = brick.getClass().getSimpleName();
        if (name.equals(IfLogicElseBrick.class.getSimpleName()) || name.equals(IfLogicEndBrick.class.getSimpleName()) || name.equals(IfThenLogicEndBrick.class.getSimpleName()) || name.equals(LoopEndlessBrick.class.getSimpleName()) || name.equals(LoopEndBrick.class.getSimpleName())) {
            return true;
        }
        return false;
    }

    private void openHelpPageForBrick(Brick brick) {
        CategoryBricksFactory categoryBricksFactory = new CategoryBricksFactory();
        String language = UtilDeviceInfo.getUserLanguageCode();
        String category = categoryBricksFactory.getBrickCategory(brick, this.sprite, this.context);
        String name = brick.getClass().getSimpleName();
        if (!(language.equals("en") || language.equals("de") || language.equals("es"))) {
            language = "en";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://wiki.catrob.at/index.php?title=");
        stringBuilder.append(category);
        stringBuilder.append("_Bricks/");
        stringBuilder.append(language);
        stringBuilder.append(Constants.ACTION_SPRITE_SEPARATOR);
        stringBuilder.append(name);
        getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
    }

    private int calculateItemPositionAndTouchPointY(View view) {
        return this.brickDragAndDropListView.pointToPosition(view.getLeft(), view.getTop());
    }

    public boolean getShowDetails() {
        return this.showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }

    public void setSelectMode(int mode) {
        this.selectMode = mode;
    }

    public int getAmountOfCheckedItems() {
        return getCheckedBricks().size();
    }

    public void clearCheckedItems() {
        this.actionMode = BrickAdapter$ActionModeEnum.NO_ACTION;
        this.checkedBricks.clear();
        enableAllBricks();
        notifyDataSetChanged();
    }

    public void setCheckbox(Brick brick, boolean enabled) {
        CheckBox checkBox = brick.getCheckBox();
        if (checkBox != null) {
            checkBox.setChecked(enabled);
        }
    }

    private void enableAllBricks() {
        unCheckAllItems();
        for (Brick brick : this.brickList) {
            BrickViewProvider.setCheckboxVisibility(brick, 8);
            BrickViewProvider.setAlphaForBrick(brick, 255);
        }
    }

    private void unCheckAllItems() {
        for (Brick brick : this.brickList) {
            setCheckbox(brick, false);
            handleCheck(brick, false);
        }
    }

    public void checkCommentedOutItems() {
        for (Brick brick : this.brickList) {
            if (brick.isCommentedOut()) {
                setCheckbox(brick, true);
            }
        }
    }

    public void setCheckboxVisibility() {
        for (Brick brick : this.brickList) {
            switch (BrickAdapter$5.f1766x261e963[this.actionMode.ordinal()]) {
                case 1:
                    BrickViewProvider.setCheckboxVisibility(brick, 8);
                    break;
                case 2:
                    if (!(brick instanceof ScriptBrick)) {
                        BrickViewProvider.setCheckboxVisibility(brick, 4);
                        break;
                    } else {
                        BrickViewProvider.setCheckboxVisibility(brick, 0);
                        break;
                    }
                case 3:
                case 4:
                    BrickViewProvider.setCheckboxVisibility(brick, 0);
                    break;
                default:
                    break;
            }
        }
    }

    private void commentBrickOut(Brick brick, boolean commentOut) {
        this.actionMode = BrickAdapter$ActionModeEnum.COMMENT_OUT;
        handleCheck(brick, commentOut);
        this.actionMode = BrickAdapter$ActionModeEnum.NO_ACTION;
    }

    public void setSpinnersEnabled(boolean enabled) {
        for (Brick brick : this.brickList) {
            BrickViewProvider.setSpinnerClickability(((BrickBaseType) brick).view, enabled);
        }
    }

    public void handleCheck(Brick brick, boolean checked) {
        int positionFrom = this.brickList.indexOf(brick);
        int positionTo = this.brickList.indexOf(brick);
        if (brick instanceof NestingBrick) {
            List<NestingBrick> nestingBricks = ((NestingBrick) brick).getAllNestingBrickParts();
            NestingBrick firstNestingBrick = (NestingBrick) nestingBricks.get(0);
            NestingBrick lastNestingBrick = (NestingBrick) nestingBricks.get(nestingBricks.size() - 1);
            if (this.actionMode != BrickAdapter$ActionModeEnum.NO_ACTION) {
                setCheckbox((Brick) firstNestingBrick, checked);
            }
            positionFrom = this.brickList.indexOf(firstNestingBrick);
            positionTo = this.brickList.indexOf(lastNestingBrick);
        } else if (brick instanceof ScriptBrick) {
            positionTo = this.brickList.size() - 1;
        }
        if (checked) {
            addElementToCheckedBricks(brick);
        } else {
            this.checkedBricks.remove(brick);
        }
        if (this.actionMode == BrickAdapter$ActionModeEnum.COMMENT_OUT) {
            brick.setCommentedOut(checked);
            BrickViewProvider.setSaturationOnBrick(brick, checked);
        }
        int position = positionFrom + 1;
        while (position <= positionTo) {
            Brick currentBrick = (Brick) this.brickList.get(position);
            if (currentBrick != null) {
                if (!(currentBrick instanceof ScriptBrick)) {
                    if (checked) {
                        addElementToCheckedBricks(currentBrick);
                    } else {
                        this.checkedBricks.remove(currentBrick);
                    }
                    switch (BrickAdapter$5.f1766x261e963[this.actionMode.ordinal()]) {
                        case 1:
                            break;
                        case 2:
                        case 3:
                            BrickViewProvider.setAlphaForBrick(currentBrick, checked ? 100 : 255);
                            setCheckbox(currentBrick, checked);
                            BrickViewProvider.setCheckboxClickability(currentBrick, checked ^ 1);
                            break;
                        case 4:
                            currentBrick.setCommentedOut(checked);
                            BrickViewProvider.setSaturationOnBrick(currentBrick, checked);
                            setCheckbox(currentBrick, checked);
                            BrickViewProvider.setCheckboxClickability(currentBrick, checked ^ 1);
                            break;
                        default:
                            break;
                    }
                    position++;
                }
            }
            this.scriptFragment.updateActionModeTitle();
        }
        this.scriptFragment.updateActionModeTitle();
    }

    void enableCorrespondingScriptBrick(int indexBegin) {
        for (int i = indexBegin; i >= 0; i--) {
            Brick currentBrick = (Brick) this.brickList.get(i);
            if (currentBrick instanceof ScriptBrick) {
                currentBrick.setCommentedOut(false);
                BrickViewProvider.setSaturationOnBrick(currentBrick, false);
                return;
            }
        }
    }

    private void addElementToCheckedBricks(Brick brick) {
        if (!this.checkedBricks.contains(brick) && !(brick instanceof UserScriptDefinitionBrick)) {
            this.checkedBricks.add(brick);
        }
    }

    public void handleScriptDelete(Sprite spriteToEdit, Script scriptToDelete) {
        spriteToEdit.removeScript(scriptToDelete);
        if (spriteToEdit.getNumberOfScripts() == 0) {
            ProjectManager.getInstance().setCurrentScript(null);
            updateProjectBrickList();
            return;
        }
        ProjectManager.getInstance().setCurrentScript(spriteToEdit.getScript(spriteToEdit.getNumberOfScripts() - 1));
        updateProjectBrickList();
    }

    public List<Brick> getCheckedBricks() {
        return this.checkedBricks;
    }

    public List<Brick> getReversedCheckedBrickList() {
        List<Brick> reverseCheckedList = new ArrayList();
        for (int counter = this.checkedBricks.size() - 1; counter >= 0; counter--) {
            reverseCheckedList.add(this.checkedBricks.get(counter));
        }
        return reverseCheckedList;
    }

    public UserBrick getUserBrick() {
        return this.userBrick;
    }

    public void setUserBrick(UserBrick userBrick) {
        this.userBrick = userBrick;
    }
}
