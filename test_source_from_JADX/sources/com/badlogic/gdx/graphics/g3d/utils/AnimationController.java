package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pool;

public class AnimationController extends BaseAnimationController {
    public boolean allowSameAnimation;
    protected final Pool<AnimationDesc> animationPool = new C07681();
    public AnimationDesc current;
    public boolean inAction;
    private boolean justChangedAnimation = false;
    public boolean paused;
    public AnimationDesc previous;
    public AnimationDesc queued;
    public float queuedTransitionTime;
    public float transitionCurrentTime;
    public float transitionTargetTime;

    public static class AnimationDesc {
        public Animation animation;
        public float duration;
        public AnimationListener listener;
        public int loopCount;
        public float offset;
        public float speed;
        public float time;

        protected AnimationDesc() {
        }

        protected float update(float delta) {
            if (this.loopCount == 0 || this.animation == null) {
                return delta;
            }
            int loops;
            float diff = this.speed * delta;
            float f = 0.0f;
            if (MathUtils.isZero(this.duration)) {
                loops = 1;
            } else {
                this.time += diff;
                loops = (int) Math.abs(this.time / this.duration);
                if (this.time < 0.0f) {
                    loops++;
                    while (this.time < 0.0f) {
                        this.time += this.duration;
                    }
                }
                this.time = Math.abs(this.time % this.duration);
            }
            for (int i = 0; i < loops; i++) {
                if (this.loopCount > 0) {
                    this.loopCount--;
                }
                if (!(this.loopCount == 0 || this.listener == null)) {
                    this.listener.onLoop(this);
                }
                if (this.loopCount == 0) {
                    float result = (((float) ((loops - 1) - i)) * this.duration) + (diff < 0.0f ? this.duration - this.time : this.time);
                    if (diff >= 0.0f) {
                        f = this.duration;
                    }
                    this.time = f;
                    if (this.listener != null) {
                        this.listener.onEnd(this);
                    }
                    return result;
                }
            }
            return 0.0f;
        }
    }

    public interface AnimationListener {
        void onEnd(AnimationDesc animationDesc);

        void onLoop(AnimationDesc animationDesc);
    }

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.AnimationController$1 */
    class C07681 extends Pool<AnimationDesc> {
        C07681() {
        }

        protected AnimationDesc newObject() {
            return new AnimationDesc();
        }
    }

    public AnimationController(ModelInstance target) {
        super(target);
    }

    private AnimationDesc obtain(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        if (anim == null) {
            return null;
        }
        AnimationDesc result = (AnimationDesc) this.animationPool.obtain();
        result.animation = anim;
        result.listener = listener;
        result.loopCount = loopCount;
        result.speed = speed;
        result.offset = offset;
        float f = 0.0f;
        result.duration = duration < 0.0f ? anim.duration - offset : duration;
        if (speed < 0.0f) {
            f = result.duration;
        }
        result.time = f;
        return result;
    }

    private AnimationDesc obtain(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        if (id == null) {
            return null;
        }
        Animation anim = this.target.getAnimation(id);
        if (anim != null) {
            return obtain(anim, offset, duration, loopCount, speed, listener);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown animation: ");
        stringBuilder.append(id);
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    private AnimationDesc obtain(AnimationDesc anim) {
        return obtain(anim.animation, anim.offset, anim.duration, anim.loopCount, anim.speed, anim.listener);
    }

    public void update(float delta) {
        if (!this.paused) {
            float f;
            if (this.previous != null) {
                f = this.transitionCurrentTime + delta;
                this.transitionCurrentTime = f;
                if (f >= this.transitionTargetTime) {
                    removeAnimation(this.previous.animation);
                    this.justChangedAnimation = true;
                    this.animationPool.free(this.previous);
                    this.previous = null;
                }
            }
            if (this.justChangedAnimation) {
                this.target.calculateTransforms();
                this.justChangedAnimation = false;
            }
            if (!(this.current == null || this.current.loopCount == 0)) {
                if (this.current.animation != null) {
                    f = this.current.update(delta);
                    if (f == 0.0f || this.queued == null) {
                        if (this.previous != null) {
                            applyAnimations(this.previous.animation, this.previous.offset + this.previous.time, this.current.animation, this.current.offset + this.current.time, this.transitionCurrentTime / this.transitionTargetTime);
                        } else {
                            applyAnimation(this.current.animation, this.current.offset + this.current.time);
                        }
                        return;
                    }
                    this.inAction = false;
                    animate(this.queued, this.queuedTransitionTime);
                    this.queued = null;
                    update(f);
                }
            }
        }
    }

    public AnimationDesc setAnimation(String id) {
        return setAnimation(id, 1, 1.0f, null);
    }

    public AnimationDesc setAnimation(String id, int loopCount) {
        return setAnimation(id, loopCount, 1.0f, null);
    }

    public AnimationDesc setAnimation(String id, AnimationListener listener) {
        return setAnimation(id, 1, 1.0f, listener);
    }

    public AnimationDesc setAnimation(String id, int loopCount, AnimationListener listener) {
        return setAnimation(id, loopCount, 1.0f, listener);
    }

    public AnimationDesc setAnimation(String id, int loopCount, float speed, AnimationListener listener) {
        return setAnimation(id, 0.0f, -1.0f, loopCount, speed, listener);
    }

    public AnimationDesc setAnimation(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        return setAnimation(obtain(id, offset, duration, loopCount, speed, listener));
    }

    protected AnimationDesc setAnimation(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener) {
        return setAnimation(obtain(anim, offset, duration, loopCount, speed, listener));
    }

    protected AnimationDesc setAnimation(AnimationDesc anim) {
        if (this.current == null) {
            this.current = anim;
        } else {
            if (this.allowSameAnimation || anim == null || this.current.animation != anim.animation) {
                removeAnimation(this.current.animation);
            } else {
                anim.time = this.current.time;
            }
            this.animationPool.free(this.current);
            this.current = anim;
        }
        this.justChangedAnimation = true;
        return anim;
    }

    public AnimationDesc animate(String id, float transitionTime) {
        return animate(id, 1, 1.0f, null, transitionTime);
    }

    public AnimationDesc animate(String id, AnimationListener listener, float transitionTime) {
        return animate(id, 1, 1.0f, listener, transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, AnimationListener listener, float transitionTime) {
        return animate(id, loopCount, 1.0f, listener, transitionTime);
    }

    public AnimationDesc animate(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return animate(id, 0.0f, -1.0f, loopCount, speed, listener, transitionTime);
    }

    public AnimationDesc animate(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return animate(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc animate(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return animate(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc animate(AnimationDesc anim, float transitionTime) {
        if (this.current == null) {
            this.current = anim;
        } else if (this.inAction) {
            queue(anim, transitionTime);
        } else if (this.allowSameAnimation || anim == null || this.current.animation != anim.animation) {
            if (this.previous != null) {
                removeAnimation(this.previous.animation);
                this.animationPool.free(this.previous);
            }
            this.previous = this.current;
            this.current = anim;
            this.transitionCurrentTime = 0.0f;
            this.transitionTargetTime = transitionTime;
        } else {
            anim.time = this.current.time;
            this.animationPool.free(this.current);
            this.current = anim;
        }
        return anim;
    }

    public AnimationDesc queue(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return queue(id, 0.0f, -1.0f, loopCount, speed, listener, transitionTime);
    }

    public AnimationDesc queue(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return queue(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc queue(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return queue(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc queue(AnimationDesc anim, float transitionTime) {
        if (this.current != null) {
            if (this.current.loopCount != 0) {
                if (this.queued != null) {
                    this.animationPool.free(this.queued);
                }
                this.queued = anim;
                this.queuedTransitionTime = transitionTime;
                if (this.current.loopCount < 0) {
                    this.current.loopCount = 1;
                }
                return anim;
            }
        }
        animate(anim, transitionTime);
        return anim;
    }

    public AnimationDesc action(String id, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return action(id, 0.0f, -1.0f, loopCount, speed, listener, transitionTime);
    }

    public AnimationDesc action(String id, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return action(obtain(id, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc action(Animation anim, float offset, float duration, int loopCount, float speed, AnimationListener listener, float transitionTime) {
        return action(obtain(anim, offset, duration, loopCount, speed, listener), transitionTime);
    }

    protected AnimationDesc action(AnimationDesc anim, float transitionTime) {
        if (anim.loopCount < 0) {
            throw new GdxRuntimeException("An action cannot be continuous");
        }
        if (this.current != null) {
            if (this.current.loopCount != 0) {
                AnimationDesc toQueue = this.inAction ? null : obtain(this.current);
                this.inAction = false;
                animate(anim, transitionTime);
                this.inAction = true;
                if (toQueue != null) {
                    queue(toQueue, transitionTime);
                }
                return anim;
            }
        }
        animate(anim, transitionTime);
        return anim;
    }
}
