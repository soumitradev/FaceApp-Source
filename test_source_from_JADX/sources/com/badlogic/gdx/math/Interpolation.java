package com.badlogic.gdx.math;

public abstract class Interpolation {
    public static final Bounce bounce = new Bounce(4);
    public static final BounceIn bounceIn = new BounceIn(4);
    public static final BounceOut bounceOut = new BounceOut(4);
    public static final Interpolation circle = new C07796();
    public static final Interpolation circleIn = new C07807();
    public static final Interpolation circleOut = new C07818();
    public static final Elastic elastic = new Elastic(2.0f, 10.0f, 7, 1.0f);
    public static final ElasticIn elasticIn = new ElasticIn(2.0f, 10.0f, 6, 1.0f);
    public static final ElasticOut elasticOut = new ElasticOut(2.0f, 10.0f, 7, 1.0f);
    public static final Exp exp10 = new Exp(2.0f, 10.0f);
    public static final ExpIn exp10In = new ExpIn(2.0f, 10.0f);
    public static final ExpOut exp10Out = new ExpOut(2.0f, 10.0f);
    public static final Exp exp5 = new Exp(2.0f, 5.0f);
    public static final ExpIn exp5In = new ExpIn(2.0f, 5.0f);
    public static final ExpOut exp5Out = new ExpOut(2.0f, 5.0f);
    public static final Interpolation fade = new C07752();
    public static final Interpolation linear = new C07741();
    public static final Pow pow2 = new Pow(2);
    public static final PowIn pow2In = new PowIn(2);
    public static final PowOut pow2Out = new PowOut(2);
    public static final Pow pow3 = new Pow(3);
    public static final PowIn pow3In = new PowIn(3);
    public static final PowOut pow3Out = new PowOut(3);
    public static final Pow pow4 = new Pow(4);
    public static final PowIn pow4In = new PowIn(4);
    public static final PowOut pow4Out = new PowOut(4);
    public static final Pow pow5 = new Pow(5);
    public static final PowIn pow5In = new PowIn(5);
    public static final PowOut pow5Out = new PowOut(5);
    public static final Interpolation sine = new C07763();
    public static final Interpolation sineIn = new C07774();
    public static final Interpolation sineOut = new C07785();
    public static final Swing swing = new Swing(1.5f);
    public static final SwingIn swingIn = new SwingIn(2.0f);
    public static final SwingOut swingOut = new SwingOut(2.0f);

    /* renamed from: com.badlogic.gdx.math.Interpolation$1 */
    static class C07741 extends Interpolation {
        C07741() {
        }

        public float apply(float a) {
            return a;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$2 */
    static class C07752 extends Interpolation {
        C07752() {
        }

        public float apply(float a) {
            return MathUtils.clamp(((a * a) * a) * ((((6.0f * a) - 15.0f) * a) + 10.0f), 0.0f, 1.0f);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$3 */
    static class C07763 extends Interpolation {
        C07763() {
        }

        public float apply(float a) {
            return (1.0f - MathUtils.cos(3.1415927f * a)) / 2.0f;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$4 */
    static class C07774 extends Interpolation {
        C07774() {
        }

        public float apply(float a) {
            return 1.0f - MathUtils.cos((3.1415927f * a) / 2.0f);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$5 */
    static class C07785 extends Interpolation {
        C07785() {
        }

        public float apply(float a) {
            return MathUtils.sin((3.1415927f * a) / 2.0f);
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$6 */
    static class C07796 extends Interpolation {
        C07796() {
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return (1.0f - ((float) Math.sqrt((double) (1.0f - (a * a))))) / 2.0f;
            }
            a = (a - 1.0f) * 2.0f;
            return (((float) Math.sqrt((double) (1.0f - (a * a)))) + 1.0f) / 2.0f;
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$7 */
    static class C07807 extends Interpolation {
        C07807() {
        }

        public float apply(float a) {
            return 1.0f - ((float) Math.sqrt((double) (1.0f - (a * a))));
        }
    }

    /* renamed from: com.badlogic.gdx.math.Interpolation$8 */
    static class C07818 extends Interpolation {
        C07818() {
        }

        public float apply(float a) {
            a -= 1.0f;
            return (float) Math.sqrt((double) (1.0f - (a * a)));
        }
    }

    public static class BounceOut extends Interpolation {
        final float[] heights;
        final float[] widths;

        public BounceOut(float[] widths, float[] heights) {
            if (widths.length != heights.length) {
                throw new IllegalArgumentException("Must be the same number of widths and heights.");
            }
            this.widths = widths;
            this.heights = heights;
        }

        public BounceOut(int bounces) {
            if (bounces >= 2) {
                if (bounces <= 5) {
                    this.widths = new float[bounces];
                    this.heights = new float[bounces];
                    this.heights[0] = 1.0f;
                    switch (bounces) {
                        case 2:
                            this.widths[0] = 0.6f;
                            this.widths[1] = 0.4f;
                            this.heights[1] = 0.33f;
                            break;
                        case 3:
                            this.widths[0] = 0.4f;
                            this.widths[1] = 0.4f;
                            this.widths[2] = 0.2f;
                            this.heights[1] = 0.33f;
                            this.heights[2] = 0.1f;
                            break;
                        case 4:
                            this.widths[0] = 0.34f;
                            this.widths[1] = 0.34f;
                            this.widths[2] = 0.2f;
                            this.widths[3] = 0.15f;
                            this.heights[1] = 0.26f;
                            this.heights[2] = 0.11f;
                            this.heights[3] = 0.03f;
                            break;
                        case 5:
                            this.widths[0] = 0.3f;
                            this.widths[1] = 0.3f;
                            this.widths[2] = 0.2f;
                            this.widths[3] = 0.1f;
                            this.widths[4] = 0.1f;
                            this.heights[1] = 0.45f;
                            this.heights[2] = 0.3f;
                            this.heights[3] = 0.15f;
                            this.heights[4] = 0.06f;
                            break;
                        default:
                            break;
                    }
                    float[] fArr = this.widths;
                    fArr[0] = fArr[0] * 2.0f;
                    return;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bounces cannot be < 2 or > 5: ");
            stringBuilder.append(bounces);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public float apply(float a) {
            a += this.widths[0] / 2.0f;
            float width = 0.0f;
            float height = 0.0f;
            int n = this.widths.length;
            for (int i = 0; i < n; i++) {
                width = this.widths[i];
                if (a <= width) {
                    height = this.heights[i];
                    break;
                }
                a -= width;
            }
            a /= width;
            float z = ((4.0f / width) * height) * a;
            return 1.0f - ((z - (z * a)) * width);
        }
    }

    public static class Elastic extends Interpolation {
        final float bounces;
        final float power;
        final float scale;
        final float value;

        public Elastic(float value, float power, int bounces, float scale) {
            this.value = value;
            this.power = power;
            this.scale = scale;
            this.bounces = (((float) bounces) * 3.1415927f) * ((float) (bounces % 2 == 0 ? 1 : -1));
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return ((((float) Math.pow((double) this.value, (double) (this.power * (a - 1.0f)))) * MathUtils.sin(this.bounces * a)) * this.scale) / 2.0f;
            }
            a = (1.0f - a) * 2.0f;
            return 1.0f - (((((float) Math.pow((double) this.value, (double) (this.power * (a - 1.0f)))) * MathUtils.sin(this.bounces * a)) * this.scale) / 2.0f);
        }
    }

    public static class Exp extends Interpolation {
        final float min;
        final float power;
        final float scale = (1.0f / (1.0f - this.min));
        final float value;

        public Exp(float value, float power) {
            this.value = value;
            this.power = power;
            this.min = (float) Math.pow((double) value, (double) (-power));
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return ((((float) Math.pow((double) this.value, (double) (this.power * ((a * 2.0f) - 1.0f)))) - this.min) * this.scale) / 2.0f;
            }
            return (2.0f - ((((float) Math.pow((double) this.value, (double) ((-this.power) * ((a * 2.0f) - 1.0f)))) - this.min) * this.scale)) / 2.0f;
        }
    }

    public static class Pow extends Interpolation {
        final int power;

        public Pow(int power) {
            this.power = power;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return ((float) Math.pow((double) (a * 2.0f), (double) this.power)) / 2.0f;
            }
            float pow = (float) Math.pow((double) ((a - 1.0f) * 2.0f), (double) this.power);
            int i = 2;
            if (this.power % 2 == 0) {
                i = -2;
            }
            return (pow / ((float) i)) + 1.0f;
        }
    }

    public static class Swing extends Interpolation {
        private final float scale;

        public Swing(float scale) {
            this.scale = 2.0f * scale;
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                a *= 2.0f;
                return ((a * a) * (((this.scale + 1.0f) * a) - this.scale)) / 2.0f;
            }
            a = (a - 1.0f) * 2.0f;
            return (((a * a) * (((this.scale + 1.0f) * a) + this.scale)) / 2.0f) + 1.0f;
        }
    }

    public static class SwingIn extends Interpolation {
        private final float scale;

        public SwingIn(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            return (a * a) * (((this.scale + 1.0f) * a) - this.scale);
        }
    }

    public static class SwingOut extends Interpolation {
        private final float scale;

        public SwingOut(float scale) {
            this.scale = scale;
        }

        public float apply(float a) {
            a -= 1.0f;
            return ((a * a) * (((this.scale + 1.0f) * a) + this.scale)) + 1.0f;
        }
    }

    public static class Bounce extends BounceOut {
        public Bounce(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public Bounce(int bounces) {
            super(bounces);
        }

        private float out(float a) {
            float test = (this.widths[0] / 2.0f) + a;
            if (test < this.widths[0]) {
                return (test / (this.widths[0] / 2.0f)) - 1.0f;
            }
            return super.apply(a);
        }

        public float apply(float a) {
            if (a <= 0.5f) {
                return (1.0f - out(1.0f - (a * 2.0f))) / 2.0f;
            }
            return (out((a * 2.0f) - 1.0f) / 2.0f) + 0.5f;
        }
    }

    public static class BounceIn extends BounceOut {
        public BounceIn(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public BounceIn(int bounces) {
            super(bounces);
        }

        public float apply(float a) {
            return 1.0f - super.apply(1.0f - a);
        }
    }

    public static class ElasticIn extends Elastic {
        public ElasticIn(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            if (((double) a) >= 0.99d) {
                return 1.0f;
            }
            return (((float) Math.pow((double) this.value, (double) (this.power * (a - 1.0f)))) * MathUtils.sin(this.bounces * a)) * this.scale;
        }
    }

    public static class ElasticOut extends Elastic {
        public ElasticOut(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        public float apply(float a) {
            a = 1.0f - a;
            return 1.0f - ((((float) Math.pow((double) this.value, (double) (this.power * (a - 1.0f)))) * MathUtils.sin(this.bounces * a)) * this.scale);
        }
    }

    public static class ExpIn extends Exp {
        public ExpIn(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return (((float) Math.pow((double) this.value, (double) (this.power * (a - 1.0f)))) - this.min) * this.scale;
        }
    }

    public static class ExpOut extends Exp {
        public ExpOut(float value, float power) {
            super(value, power);
        }

        public float apply(float a) {
            return 1.0f - ((((float) Math.pow((double) this.value, (double) ((-this.power) * a))) - this.min) * this.scale);
        }
    }

    public static class PowIn extends Pow {
        public PowIn(int power) {
            super(power);
        }

        public float apply(float a) {
            return (float) Math.pow((double) a, (double) this.power);
        }
    }

    public static class PowOut extends Pow {
        public PowOut(int power) {
            super(power);
        }

        public float apply(float a) {
            return (((float) Math.pow((double) (a - 1.0f), (double) this.power)) * ((float) (this.power % 2 == 0 ? -1 : 1))) + 1.0f;
        }
    }

    public abstract float apply(float f);

    public float apply(float start, float end, float a) {
        return ((end - start) * apply(a)) + start;
    }
}
