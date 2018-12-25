package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;

public abstract class Value {
    public static Value maxHeight = new C07966();
    public static Value maxWidth = new C07955();
    public static Value minHeight = new C07922();
    public static Value minWidth = new C07911();
    public static Value prefHeight = new C07944();
    public static Value prefWidth = new C07933();
    public static final Fixed zero = new Fixed(0.0f);

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$1 */
    static class C07911 extends Value {
        C07911() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getMinWidth();
            }
            return context == null ? 0.0f : context.getWidth();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$2 */
    static class C07922 extends Value {
        C07922() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getMinHeight();
            }
            return context == null ? 0.0f : context.getHeight();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$3 */
    static class C07933 extends Value {
        C07933() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getPrefWidth();
            }
            return context == null ? 0.0f : context.getWidth();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$4 */
    static class C07944 extends Value {
        C07944() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getPrefHeight();
            }
            return context == null ? 0.0f : context.getHeight();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$5 */
    static class C07955 extends Value {
        C07955() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getMaxWidth();
            }
            return context == null ? 0.0f : context.getWidth();
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Value$6 */
    static class C07966 extends Value {
        C07966() {
        }

        public float get(Actor context) {
            if (context instanceof Layout) {
                return ((Layout) context).getMaxHeight();
            }
            return context == null ? 0.0f : context.getHeight();
        }
    }

    public static class Fixed extends Value {
        private final float value;

        public Fixed(float value) {
            this.value = value;
        }

        public float get(Actor context) {
            return this.value;
        }
    }

    public abstract float get(Actor actor);

    public static Value percentWidth(final float percent) {
        return new Value() {
            public float get(Actor actor) {
                return actor.getWidth() * percent;
            }
        };
    }

    public static Value percentHeight(final float percent) {
        return new Value() {
            public float get(Actor actor) {
                return actor.getHeight() * percent;
            }
        };
    }

    public static Value percentWidth(final float percent, final Actor actor) {
        if (actor != null) {
            return new Value() {
                public float get(Actor context) {
                    return actor.getWidth() * percent;
                }
            };
        }
        throw new IllegalArgumentException("actor cannot be null.");
    }

    public static Value percentHeight(final float percent, final Actor actor) {
        if (actor != null) {
            return new Value() {
                public float get(Actor context) {
                    return actor.getHeight() * percent;
                }
            };
        }
        throw new IllegalArgumentException("actor cannot be null.");
    }
}
