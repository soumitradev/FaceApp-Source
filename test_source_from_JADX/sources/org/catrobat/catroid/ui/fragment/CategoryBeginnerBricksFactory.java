package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.content.bricks.DeleteThisCloneBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.HideTextBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.content.bricks.ShowTextBrick;
import org.catrobat.catroid.content.bricks.SpeakBrick;
import org.catrobat.catroid.content.bricks.StopAllSoundsBrick;
import org.catrobat.catroid.content.bricks.ThinkBubbleBrick;
import org.catrobat.catroid.content.bricks.ThinkForBubbleBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.content.bricks.WhenTouchDownBrick;
import org.catrobat.catroid.generated70026.R;

public class CategoryBeginnerBricksFactory extends CategoryBricksFactory {
    protected List<Brick> setupEventCategoryList(Context context) {
        List<Brick> eventBrickList = new ArrayList();
        eventBrickList.add(new WhenStartedBrick());
        eventBrickList.add(new WhenTouchDownBrick());
        List<String> broadcastMessages = ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages();
        String broadcastMessage = context.getString(R.string.brick_broadcast_default_value);
        if (broadcastMessages.size() > 0) {
            broadcastMessage = (String) broadcastMessages.get(0);
        }
        eventBrickList.add(new BroadcastReceiverBrick(new BroadcastScript(broadcastMessage)));
        eventBrickList.add(new BroadcastBrick(broadcastMessage));
        return eventBrickList;
    }

    protected List<Brick> setupControlCategoryList(Context context) {
        List<Brick> controlBrickList = new ArrayList();
        controlBrickList.add(new WaitBrick(1000));
        controlBrickList.add(new ForeverBrick());
        controlBrickList.add(new CloneBrick());
        controlBrickList.add(new DeleteThisCloneBrick());
        controlBrickList.add(new WhenClonedBrick());
        return controlBrickList;
    }

    protected List<Brick> setupMotionCategoryList(Sprite sprite, Context context) {
        List<Brick> motionBrickList = new ArrayList();
        motionBrickList.add(new PlaceAtBrick(100, 200));
        motionBrickList.add(new GoToBrick(null));
        if (!isBackground(sprite)) {
            motionBrickList.add(new IfOnEdgeBounceBrick());
        }
        motionBrickList.add(new MoveNStepsBrick(10.0d));
        motionBrickList.add(new TurnLeftBrick(15.0d));
        motionBrickList.add(new TurnRightBrick(15.0d));
        motionBrickList.add(new GlideToBrick(100, 200, 1000));
        return motionBrickList;
    }

    protected List<Brick> setupSoundCategoryList(Context context) {
        List<Brick> soundBrickList = new ArrayList();
        soundBrickList.add(new PlaySoundBrick());
        soundBrickList.add(new StopAllSoundsBrick());
        soundBrickList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
        return soundBrickList;
    }

    protected List<Brick> setupLooksCategoryList(Context context, boolean isBackgroundSprite) {
        List<Brick> looksBrickList = new ArrayList();
        if (!isBackgroundSprite) {
            looksBrickList.add(new SetLookBrick());
        }
        looksBrickList.add(new NextLookBrick());
        looksBrickList.add(new PreviousLookBrick());
        looksBrickList.add(new SetSizeToBrick(60.0d));
        looksBrickList.add(new ChangeSizeByNBrick(10.0d));
        looksBrickList.add(new HideBrick());
        looksBrickList.add(new ShowBrick());
        looksBrickList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        if (!isBackgroundSprite) {
            looksBrickList.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
            looksBrickList.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
            looksBrickList.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
            looksBrickList.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
        }
        looksBrickList.add(new SetColorBrick((double) BrickValues.SET_COLOR_TO));
        looksBrickList.add(new ChangeColorByNBrick(25.0d));
        looksBrickList.add(new SetBackgroundBrick());
        return looksBrickList;
    }

    protected List<Brick> setupDataCategoryList(Context context) {
        List<Brick> dataBrickList = new ArrayList();
        dataBrickList.add(new SetVariableBrick(1.0d));
        dataBrickList.add(new ChangeVariableBrick(1.0d));
        dataBrickList.add(new ShowTextBrick(100, 200));
        dataBrickList.add(new HideTextBrick());
        return dataBrickList;
    }
}
