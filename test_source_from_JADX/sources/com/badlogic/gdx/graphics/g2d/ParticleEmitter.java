package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class ParticleEmitter {
    private static final int UPDATE_ANGLE = 2;
    private static final int UPDATE_GRAVITY = 32;
    private static final int UPDATE_ROTATION = 4;
    private static final int UPDATE_SCALE = 1;
    private static final int UPDATE_TINT = 64;
    private static final int UPDATE_VELOCITY = 8;
    private static final int UPDATE_WIND = 16;
    private float accumulator;
    private boolean[] active;
    private int activeCount;
    private boolean additive = true;
    private boolean aligned;
    private boolean allowCompletion;
    private ScaledNumericValue angleValue = new ScaledNumericValue();
    private boolean attached;
    private boolean behind;
    private BoundingBox bounds;
    boolean cleansUpBlendFunction = true;
    private boolean continuous;
    private float delay;
    private float delayTimer;
    private RangedNumericValue delayValue = new RangedNumericValue();
    public float duration = 1.0f;
    public float durationTimer;
    private RangedNumericValue durationValue = new RangedNumericValue();
    private int emission;
    private int emissionDelta;
    private int emissionDiff;
    private ScaledNumericValue emissionValue = new ScaledNumericValue();
    private boolean firstUpdate;
    private boolean flipX;
    private boolean flipY;
    private ScaledNumericValue gravityValue = new ScaledNumericValue();
    private String imagePath;
    private int life;
    private int lifeDiff;
    private int lifeOffset;
    private int lifeOffsetDiff;
    private ScaledNumericValue lifeOffsetValue = new ScaledNumericValue();
    private ScaledNumericValue lifeValue = new ScaledNumericValue();
    private int maxParticleCount = 4;
    private int minParticleCount;
    private String name;
    private Particle[] particles;
    private boolean premultipliedAlpha = false;
    private ScaledNumericValue rotationValue = new ScaledNumericValue();
    private ScaledNumericValue scaleValue = new ScaledNumericValue();
    private float spawnHeight;
    private float spawnHeightDiff;
    private ScaledNumericValue spawnHeightValue = new ScaledNumericValue();
    private SpawnShapeValue spawnShapeValue = new SpawnShapeValue();
    private float spawnWidth;
    private float spawnWidthDiff;
    private ScaledNumericValue spawnWidthValue = new ScaledNumericValue();
    private Sprite sprite;
    private GradientColorValue tintValue = new GradientColorValue();
    private ScaledNumericValue transparencyValue = new ScaledNumericValue();
    private int updateFlags;
    private ScaledNumericValue velocityValue = new ScaledNumericValue();
    private ScaledNumericValue windValue = new ScaledNumericValue();
    /* renamed from: x */
    private float f67x;
    private RangedNumericValue xOffsetValue = new ScaledNumericValue();
    /* renamed from: y */
    private float f68y;
    private RangedNumericValue yOffsetValue = new ScaledNumericValue();

    public static class ParticleValue {
        boolean active;
        boolean alwaysActive;

        public void setAlwaysActive(boolean alwaysActive) {
            this.alwaysActive = alwaysActive;
        }

        public boolean isAlwaysActive() {
            return this.alwaysActive;
        }

        public boolean isActive() {
            if (!this.alwaysActive) {
                if (!this.active) {
                    return false;
                }
            }
            return true;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public void save(Writer output) throws IOException {
            if (this.alwaysActive) {
                this.active = true;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("active: ");
            stringBuilder.append(this.active);
            stringBuilder.append("\n");
            output.write(stringBuilder.toString());
        }

        public void load(BufferedReader reader) throws IOException {
            if (this.alwaysActive) {
                this.active = true;
            } else {
                this.active = ParticleEmitter.readBoolean(reader, "active");
            }
        }

        public void load(ParticleValue value) {
            this.active = value.active;
            this.alwaysActive = value.alwaysActive;
        }
    }

    public enum SpawnEllipseSide {
        both,
        top,
        bottom
    }

    public enum SpawnShape {
        point,
        line,
        square,
        ellipse
    }

    public static class GradientColorValue extends ParticleValue {
        private static float[] temp = new float[4];
        private float[] colors;
        float[] timeline;

        public GradientColorValue() {
            this.colors = new float[]{1.0f, 1.0f, 1.0f};
            this.timeline = new float[]{0.0f};
            this.alwaysActive = true;
        }

        public float[] getTimeline() {
            return this.timeline;
        }

        public void setTimeline(float[] timeline) {
            this.timeline = timeline;
        }

        public float[] getColors() {
            return this.colors;
        }

        public void setColors(float[] colors) {
            this.colors = colors;
        }

        public float[] getColor(float percent) {
            int endIndex = -1;
            float[] timeline = this.timeline;
            int n = timeline.length;
            int startIndex = 0;
            for (int i = 1; i < n; i++) {
                if (timeline[i] > percent) {
                    endIndex = i;
                    break;
                }
                startIndex = i;
            }
            float startTime = timeline[startIndex];
            startIndex *= 3;
            float r1 = this.colors[startIndex];
            float g1 = this.colors[startIndex + 1];
            float b1 = this.colors[startIndex + 2];
            if (endIndex == -1) {
                temp[0] = r1;
                temp[1] = g1;
                temp[2] = b1;
                return temp;
            }
            float factor = (percent - startTime) / (timeline[endIndex] - startTime);
            endIndex *= 3;
            temp[0] = ((this.colors[endIndex] - r1) * factor) + r1;
            temp[1] = ((this.colors[endIndex + 1] - g1) * factor) + g1;
            temp[2] = ((this.colors[endIndex + 2] - b1) * factor) + b1;
            return temp;
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if (this.active) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("colorsCount: ");
                stringBuilder.append(this.colors.length);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                for (int i = 0; i < this.colors.length; i++) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("colors");
                    stringBuilder2.append(i);
                    stringBuilder2.append(": ");
                    stringBuilder2.append(this.colors[i]);
                    stringBuilder2.append("\n");
                    output.write(stringBuilder2.toString());
                }
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("timelineCount: ");
                stringBuilder3.append(this.timeline.length);
                stringBuilder3.append("\n");
                output.write(stringBuilder3.toString());
                for (int i2 = 0; i2 < this.timeline.length; i2++) {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("timeline");
                    stringBuilder3.append(i2);
                    stringBuilder3.append(": ");
                    stringBuilder3.append(this.timeline[i2]);
                    stringBuilder3.append("\n");
                    output.write(stringBuilder3.toString());
                }
            }
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if (this.active) {
                this.colors = new float[ParticleEmitter.readInt(reader, "colorsCount")];
                for (int i = 0; i < this.colors.length; i++) {
                    float[] fArr = this.colors;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("colors");
                    stringBuilder.append(i);
                    fArr[i] = ParticleEmitter.readFloat(reader, stringBuilder.toString());
                }
                this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
                for (int i2 = 0; i2 < this.timeline.length; i2++) {
                    float[] fArr2 = this.timeline;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("timeline");
                    stringBuilder2.append(i2);
                    fArr2[i2] = ParticleEmitter.readFloat(reader, stringBuilder2.toString());
                }
            }
        }

        public void load(GradientColorValue value) {
            super.load((ParticleValue) value);
            this.colors = new float[value.colors.length];
            System.arraycopy(value.colors, 0, this.colors, 0, this.colors.length);
            this.timeline = new float[value.timeline.length];
            System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
        }
    }

    public static class NumericValue extends ParticleValue {
        private float value;

        public float getValue() {
            return this.value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if (this.active) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("value: ");
                stringBuilder.append(this.value);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
            }
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if (this.active) {
                this.value = ParticleEmitter.readFloat(reader, FirebaseAnalytics$Param.VALUE);
            }
        }

        public void load(NumericValue value) {
            super.load((ParticleValue) value);
            this.value = value.value;
        }
    }

    public static class RangedNumericValue extends ParticleValue {
        private float lowMax;
        private float lowMin;

        public float newLowValue() {
            return this.lowMin + ((this.lowMax - this.lowMin) * MathUtils.random());
        }

        public void setLow(float value) {
            this.lowMin = value;
            this.lowMax = value;
        }

        public void setLow(float min, float max) {
            this.lowMin = min;
            this.lowMax = max;
        }

        public float getLowMin() {
            return this.lowMin;
        }

        public void setLowMin(float lowMin) {
            this.lowMin = lowMin;
        }

        public float getLowMax() {
            return this.lowMax;
        }

        public void setLowMax(float lowMax) {
            this.lowMax = lowMax;
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if (this.active) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("lowMin: ");
                stringBuilder.append(this.lowMin);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("lowMax: ");
                stringBuilder.append(this.lowMax);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
            }
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if (this.active) {
                this.lowMin = ParticleEmitter.readFloat(reader, "lowMin");
                this.lowMax = ParticleEmitter.readFloat(reader, "lowMax");
            }
        }

        public void load(RangedNumericValue value) {
            super.load((ParticleValue) value);
            this.lowMax = value.lowMax;
            this.lowMin = value.lowMin;
        }
    }

    public static class SpawnShapeValue extends ParticleValue {
        boolean edges;
        SpawnShape shape = SpawnShape.point;
        SpawnEllipseSide side = SpawnEllipseSide.both;

        public SpawnShape getShape() {
            return this.shape;
        }

        public void setShape(SpawnShape shape) {
            this.shape = shape;
        }

        public boolean isEdges() {
            return this.edges;
        }

        public void setEdges(boolean edges) {
            this.edges = edges;
        }

        public SpawnEllipseSide getSide() {
            return this.side;
        }

        public void setSide(SpawnEllipseSide side) {
            this.side = side;
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if (this.active) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("shape: ");
                stringBuilder.append(this.shape);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                if (this.shape == SpawnShape.ellipse) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("edges: ");
                    stringBuilder.append(this.edges);
                    stringBuilder.append("\n");
                    output.write(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("side: ");
                    stringBuilder.append(this.side);
                    stringBuilder.append("\n");
                    output.write(stringBuilder.toString());
                }
            }
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if (this.active) {
                this.shape = SpawnShape.valueOf(ParticleEmitter.readString(reader, "shape"));
                if (this.shape == SpawnShape.ellipse) {
                    this.edges = ParticleEmitter.readBoolean(reader, "edges");
                    this.side = SpawnEllipseSide.valueOf(ParticleEmitter.readString(reader, "side"));
                }
            }
        }

        public void load(SpawnShapeValue value) {
            super.load((ParticleValue) value);
            this.shape = value.shape;
            this.edges = value.edges;
            this.side = value.side;
        }
    }

    public static class Particle extends Sprite {
        protected float angle;
        protected float angleCos;
        protected float angleDiff;
        protected float angleSin;
        protected int currentLife;
        protected float gravity;
        protected float gravityDiff;
        protected int life;
        protected float rotation;
        protected float rotationDiff;
        protected float scale;
        protected float scaleDiff;
        protected float[] tint;
        protected float transparency;
        protected float transparencyDiff;
        protected float velocity;
        protected float velocityDiff;
        protected float wind;
        protected float windDiff;

        public Particle(Sprite sprite) {
            super(sprite);
        }
    }

    public static class ScaledNumericValue extends RangedNumericValue {
        private float highMax;
        private float highMin;
        private boolean relative;
        private float[] scaling = new float[]{1.0f};
        float[] timeline = new float[]{0.0f};

        public float newHighValue() {
            return this.highMin + ((this.highMax - this.highMin) * MathUtils.random());
        }

        public void setHigh(float value) {
            this.highMin = value;
            this.highMax = value;
        }

        public void setHigh(float min, float max) {
            this.highMin = min;
            this.highMax = max;
        }

        public float getHighMin() {
            return this.highMin;
        }

        public void setHighMin(float highMin) {
            this.highMin = highMin;
        }

        public float getHighMax() {
            return this.highMax;
        }

        public void setHighMax(float highMax) {
            this.highMax = highMax;
        }

        public float[] getScaling() {
            return this.scaling;
        }

        public void setScaling(float[] values) {
            this.scaling = values;
        }

        public float[] getTimeline() {
            return this.timeline;
        }

        public void setTimeline(float[] timeline) {
            this.timeline = timeline;
        }

        public boolean isRelative() {
            return this.relative;
        }

        public void setRelative(boolean relative) {
            this.relative = relative;
        }

        public float getScale(float percent) {
            int endIndex = -1;
            float[] timeline = this.timeline;
            int n = timeline.length;
            for (int i = 1; i < n; i++) {
                if (timeline[i] > percent) {
                    endIndex = i;
                    break;
                }
            }
            if (endIndex == -1) {
                return this.scaling[n - 1];
            }
            float[] scaling = this.scaling;
            int startIndex = endIndex - 1;
            float startValue = scaling[startIndex];
            float startTime = timeline[startIndex];
            return ((scaling[endIndex] - startValue) * ((percent - startTime) / (timeline[endIndex] - startTime))) + startValue;
        }

        public void save(Writer output) throws IOException {
            super.save(output);
            if (this.active) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("highMin: ");
                stringBuilder.append(this.highMin);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("highMax: ");
                stringBuilder.append(this.highMax);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("relative: ");
                stringBuilder.append(this.relative);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("scalingCount: ");
                stringBuilder.append(this.scaling.length);
                stringBuilder.append("\n");
                output.write(stringBuilder.toString());
                for (int i = 0; i < this.scaling.length; i++) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("scaling");
                    stringBuilder2.append(i);
                    stringBuilder2.append(": ");
                    stringBuilder2.append(this.scaling[i]);
                    stringBuilder2.append("\n");
                    output.write(stringBuilder2.toString());
                }
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("timelineCount: ");
                stringBuilder3.append(this.timeline.length);
                stringBuilder3.append("\n");
                output.write(stringBuilder3.toString());
                for (int i2 = 0; i2 < this.timeline.length; i2++) {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("timeline");
                    stringBuilder3.append(i2);
                    stringBuilder3.append(": ");
                    stringBuilder3.append(this.timeline[i2]);
                    stringBuilder3.append("\n");
                    output.write(stringBuilder3.toString());
                }
            }
        }

        public void load(BufferedReader reader) throws IOException {
            super.load(reader);
            if (this.active) {
                this.highMin = ParticleEmitter.readFloat(reader, "highMin");
                this.highMax = ParticleEmitter.readFloat(reader, "highMax");
                this.relative = ParticleEmitter.readBoolean(reader, "relative");
                this.scaling = new float[ParticleEmitter.readInt(reader, "scalingCount")];
                for (int i = 0; i < this.scaling.length; i++) {
                    float[] fArr = this.scaling;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("scaling");
                    stringBuilder.append(i);
                    fArr[i] = ParticleEmitter.readFloat(reader, stringBuilder.toString());
                }
                this.timeline = new float[ParticleEmitter.readInt(reader, "timelineCount")];
                for (int i2 = 0; i2 < this.timeline.length; i2++) {
                    float[] fArr2 = this.timeline;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("timeline");
                    stringBuilder2.append(i2);
                    fArr2[i2] = ParticleEmitter.readFloat(reader, stringBuilder2.toString());
                }
            }
        }

        public void load(ScaledNumericValue value) {
            super.load((RangedNumericValue) value);
            this.highMax = value.highMax;
            this.highMin = value.highMin;
            this.scaling = new float[value.scaling.length];
            System.arraycopy(value.scaling, 0, this.scaling, 0, this.scaling.length);
            this.timeline = new float[value.timeline.length];
            System.arraycopy(value.timeline, 0, this.timeline, 0, this.timeline.length);
            this.relative = value.relative;
        }
    }

    public ParticleEmitter() {
        initialize();
    }

    public ParticleEmitter(BufferedReader reader) throws IOException {
        initialize();
        load(reader);
    }

    public ParticleEmitter(ParticleEmitter emitter) {
        this.sprite = emitter.sprite;
        this.name = emitter.name;
        this.imagePath = emitter.imagePath;
        setMaxParticleCount(emitter.maxParticleCount);
        this.minParticleCount = emitter.minParticleCount;
        this.delayValue.load(emitter.delayValue);
        this.durationValue.load(emitter.durationValue);
        this.emissionValue.load(emitter.emissionValue);
        this.lifeValue.load(emitter.lifeValue);
        this.lifeOffsetValue.load(emitter.lifeOffsetValue);
        this.scaleValue.load(emitter.scaleValue);
        this.rotationValue.load(emitter.rotationValue);
        this.velocityValue.load(emitter.velocityValue);
        this.angleValue.load(emitter.angleValue);
        this.windValue.load(emitter.windValue);
        this.gravityValue.load(emitter.gravityValue);
        this.transparencyValue.load(emitter.transparencyValue);
        this.tintValue.load(emitter.tintValue);
        this.xOffsetValue.load(emitter.xOffsetValue);
        this.yOffsetValue.load(emitter.yOffsetValue);
        this.spawnWidthValue.load(emitter.spawnWidthValue);
        this.spawnHeightValue.load(emitter.spawnHeightValue);
        this.spawnShapeValue.load(emitter.spawnShapeValue);
        this.attached = emitter.attached;
        this.continuous = emitter.continuous;
        this.aligned = emitter.aligned;
        this.behind = emitter.behind;
        this.additive = emitter.additive;
        this.premultipliedAlpha = emitter.premultipliedAlpha;
        this.cleansUpBlendFunction = emitter.cleansUpBlendFunction;
    }

    private void initialize() {
        this.durationValue.setAlwaysActive(true);
        this.emissionValue.setAlwaysActive(true);
        this.lifeValue.setAlwaysActive(true);
        this.scaleValue.setAlwaysActive(true);
        this.transparencyValue.setAlwaysActive(true);
        this.spawnShapeValue.setAlwaysActive(true);
        this.spawnWidthValue.setAlwaysActive(true);
        this.spawnHeightValue.setAlwaysActive(true);
    }

    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
        this.active = new boolean[maxParticleCount];
        this.activeCount = 0;
        this.particles = new Particle[maxParticleCount];
    }

    public void addParticle() {
        int activeCount = this.activeCount;
        if (activeCount != this.maxParticleCount) {
            boolean[] active = this.active;
            int n = active.length;
            for (int i = 0; i < n; i++) {
                if (!active[i]) {
                    activateParticle(i);
                    active[i] = true;
                    this.activeCount = activeCount + 1;
                    break;
                }
            }
        }
    }

    public void addParticles(int count) {
        count = Math.min(count, this.maxParticleCount - this.activeCount);
        if (count != 0) {
            boolean[] active = this.active;
            int index = 0;
            int n = active.length;
            int i = 0;
            loop0:
            while (i < count) {
                while (index < n) {
                    if (active[index]) {
                        index++;
                    } else {
                        activateParticle(index);
                        int index2 = index + 1;
                        active[index] = true;
                        i++;
                        index = index2;
                    }
                }
                break loop0;
            }
            this.activeCount += count;
        }
    }

    public void update(float delta) {
        this.accumulator += delta * 1000.0f;
        if (this.accumulator >= 1.0f) {
            int deltaMillis = (int) this.accumulator;
            this.accumulator -= (float) deltaMillis;
            if (this.delayTimer < this.delay) {
                this.delayTimer += (float) deltaMillis;
            } else {
                boolean done = false;
                if (this.firstUpdate) {
                    this.firstUpdate = false;
                    addParticle();
                }
                if (this.durationTimer < this.duration) {
                    this.durationTimer += (float) deltaMillis;
                } else {
                    if (this.continuous) {
                        if (!this.allowCompletion) {
                            restart();
                        }
                    }
                    done = true;
                }
                if (!done) {
                    this.emissionDelta += deltaMillis;
                    float emissionTime = ((float) this.emission) + (((float) this.emissionDiff) * this.emissionValue.getScale(this.durationTimer / this.duration));
                    if (emissionTime > 0.0f) {
                        emissionTime = 1000.0f / emissionTime;
                        if (((float) this.emissionDelta) >= emissionTime) {
                            int emitCount = Math.min((int) (((float) this.emissionDelta) / emissionTime), this.maxParticleCount - this.activeCount);
                            this.emissionDelta = (int) (((float) this.emissionDelta) - (((float) emitCount) * emissionTime));
                            this.emissionDelta = (int) (((float) this.emissionDelta) % emissionTime);
                            addParticles(emitCount);
                        }
                    }
                    if (this.activeCount < this.minParticleCount) {
                        addParticles(this.minParticleCount - this.activeCount);
                    }
                }
            }
            boolean[] active = this.active;
            int activeCount = this.activeCount;
            Particle[] particles = this.particles;
            int i = 0;
            int n = active.length;
            while (i < n) {
                if (active[i] && !updateParticle(particles[i], delta, deltaMillis)) {
                    active[i] = false;
                    activeCount--;
                }
                i++;
            }
            this.activeCount = activeCount;
        }
    }

    public void draw(Batch batch) {
        if (this.premultipliedAlpha) {
            batch.setBlendFunction(1, GL20.GL_ONE_MINUS_SRC_ALPHA);
        } else if (this.additive) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, 1);
        } else {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        Particle[] particles = this.particles;
        boolean[] active = this.active;
        int n = active.length;
        for (int i = 0; i < n; i++) {
            if (active[i]) {
                particles[i].draw(batch);
            }
        }
        if (!this.cleansUpBlendFunction) {
            return;
        }
        if (this.additive || this.premultipliedAlpha) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    public void draw(Batch batch, float delta) {
        this.accumulator += delta * 1000.0f;
        if (this.accumulator < 1.0f) {
            draw(batch);
            return;
        }
        int deltaMillis = (int) this.accumulator;
        this.accumulator -= (float) deltaMillis;
        if (this.premultipliedAlpha) {
            batch.setBlendFunction(1, GL20.GL_ONE_MINUS_SRC_ALPHA);
        } else if (this.additive) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, 1);
        } else {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        Particle[] particles = this.particles;
        boolean[] active = this.active;
        int activeCount = this.activeCount;
        int n = active.length;
        for (int i = 0; i < n; i++) {
            if (active[i]) {
                Particle particle = particles[i];
                if (updateParticle(particle, delta, deltaMillis)) {
                    particle.draw(batch);
                } else {
                    active[i] = false;
                    activeCount--;
                }
            }
        }
        this.activeCount = activeCount;
        if (this.cleansUpBlendFunction && (this.additive || this.premultipliedAlpha)) {
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        if (this.delayTimer < this.delay) {
            this.delayTimer += (float) deltaMillis;
            return;
        }
        if (this.firstUpdate) {
            this.firstUpdate = false;
            addParticle();
        }
        if (this.durationTimer < this.duration) {
            this.durationTimer += (float) deltaMillis;
        } else {
            if (this.continuous) {
                if (!this.allowCompletion) {
                    restart();
                }
            }
            return;
        }
        this.emissionDelta += deltaMillis;
        float emissionTime = ((float) this.emission) + (((float) this.emissionDiff) * this.emissionValue.getScale(this.durationTimer / this.duration));
        if (emissionTime > 0.0f) {
            emissionTime = 1000.0f / emissionTime;
            if (((float) this.emissionDelta) >= emissionTime) {
                int emitCount = Math.min((int) (((float) this.emissionDelta) / emissionTime), this.maxParticleCount - activeCount);
                this.emissionDelta = (int) (((float) this.emissionDelta) - (((float) emitCount) * emissionTime));
                this.emissionDelta = (int) (((float) this.emissionDelta) % emissionTime);
                addParticles(emitCount);
            }
        }
        if (activeCount < this.minParticleCount) {
            addParticles(this.minParticleCount - activeCount);
        }
    }

    public void start() {
        this.firstUpdate = true;
        this.allowCompletion = false;
        restart();
    }

    public void reset() {
        this.emissionDelta = 0;
        this.durationTimer = this.duration;
        boolean[] active = this.active;
        int n = active.length;
        for (int i = 0; i < n; i++) {
            active[i] = false;
        }
        this.activeCount = 0;
        start();
    }

    private void restart() {
        this.delay = this.delayValue.active ? this.delayValue.newLowValue() : 0.0f;
        this.delayTimer = 0.0f;
        this.durationTimer -= this.duration;
        this.duration = this.durationValue.newLowValue();
        this.emission = (int) this.emissionValue.newLowValue();
        this.emissionDiff = (int) this.emissionValue.newHighValue();
        if (!this.emissionValue.isRelative()) {
            this.emissionDiff -= this.emission;
        }
        this.life = (int) this.lifeValue.newLowValue();
        this.lifeDiff = (int) this.lifeValue.newHighValue();
        if (!this.lifeValue.isRelative()) {
            this.lifeDiff -= this.life;
        }
        this.lifeOffset = this.lifeOffsetValue.active ? (int) this.lifeOffsetValue.newLowValue() : 0;
        this.lifeOffsetDiff = (int) this.lifeOffsetValue.newHighValue();
        if (!this.lifeOffsetValue.isRelative()) {
            this.lifeOffsetDiff -= this.lifeOffset;
        }
        this.spawnWidth = this.spawnWidthValue.newLowValue();
        this.spawnWidthDiff = this.spawnWidthValue.newHighValue();
        if (!this.spawnWidthValue.isRelative()) {
            this.spawnWidthDiff -= this.spawnWidth;
        }
        this.spawnHeight = this.spawnHeightValue.newLowValue();
        this.spawnHeightDiff = this.spawnHeightValue.newHighValue();
        if (!this.spawnHeightValue.isRelative()) {
            this.spawnHeightDiff -= this.spawnHeight;
        }
        this.updateFlags = 0;
        if (this.angleValue.active && this.angleValue.timeline.length > 1) {
            this.updateFlags |= 2;
        }
        if (this.velocityValue.active) {
            this.updateFlags |= 8;
        }
        if (this.scaleValue.timeline.length > 1) {
            this.updateFlags |= 1;
        }
        if (this.rotationValue.active && this.rotationValue.timeline.length > 1) {
            this.updateFlags |= 4;
        }
        if (this.windValue.active) {
            this.updateFlags |= 16;
        }
        if (this.gravityValue.active) {
            this.updateFlags |= 32;
        }
        if (this.tintValue.timeline.length > 1) {
            this.updateFlags |= 64;
        }
    }

    protected Particle newParticle(Sprite sprite) {
        return new Particle(sprite);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void activateParticle(int r21) {
        /*
        r20 = this;
        r0 = r20;
        r2 = r0.particles;
        r2 = r2[r21];
        if (r2 != 0) goto L_0x001a;
    L_0x0008:
        r3 = r0.particles;
        r4 = r0.sprite;
        r4 = r0.newParticle(r4);
        r2 = r4;
        r3[r21] = r4;
        r3 = r0.flipX;
        r4 = r0.flipY;
        r2.flip(r3, r4);
    L_0x001a:
        r3 = r0.durationTimer;
        r4 = r0.duration;
        r3 = r3 / r4;
        r4 = r0.updateFlags;
        r5 = r0.life;
        r6 = r0.lifeDiff;
        r6 = (float) r6;
        r7 = r0.lifeValue;
        r7 = r7.getScale(r3);
        r6 = r6 * r7;
        r6 = (int) r6;
        r5 = r5 + r6;
        r2.life = r5;
        r2.currentLife = r5;
        r5 = r0.velocityValue;
        r5 = r5.active;
        if (r5 == 0) goto L_0x0059;
    L_0x003a:
        r5 = r0.velocityValue;
        r5 = r5.newLowValue();
        r2.velocity = r5;
        r5 = r0.velocityValue;
        r5 = r5.newHighValue();
        r2.velocityDiff = r5;
        r5 = r0.velocityValue;
        r5 = r5.isRelative();
        if (r5 != 0) goto L_0x0059;
    L_0x0052:
        r5 = r2.velocityDiff;
        r6 = r2.velocity;
        r5 = r5 - r6;
        r2.velocityDiff = r5;
    L_0x0059:
        r5 = r0.angleValue;
        r5 = r5.newLowValue();
        r2.angle = r5;
        r5 = r0.angleValue;
        r5 = r5.newHighValue();
        r2.angleDiff = r5;
        r5 = r0.angleValue;
        r5 = r5.isRelative();
        if (r5 != 0) goto L_0x0078;
    L_0x0071:
        r5 = r2.angleDiff;
        r6 = r2.angle;
        r5 = r5 - r6;
        r2.angleDiff = r5;
    L_0x0078:
        r5 = 0;
        r6 = r4 & 2;
        r7 = 0;
        if (r6 != 0) goto L_0x009a;
    L_0x007e:
        r6 = r2.angle;
        r8 = r2.angleDiff;
        r9 = r0.angleValue;
        r9 = r9.getScale(r7);
        r8 = r8 * r9;
        r5 = r6 + r8;
        r2.angle = r5;
        r6 = com.badlogic.gdx.math.MathUtils.cosDeg(r5);
        r2.angleCos = r6;
        r6 = com.badlogic.gdx.math.MathUtils.sinDeg(r5);
        r2.angleSin = r6;
    L_0x009a:
        r6 = r0.sprite;
        r6 = r6.getWidth();
        r8 = r0.scaleValue;
        r8 = r8.newLowValue();
        r8 = r8 / r6;
        r2.scale = r8;
        r8 = r0.scaleValue;
        r8 = r8.newHighValue();
        r8 = r8 / r6;
        r2.scaleDiff = r8;
        r8 = r0.scaleValue;
        r8 = r8.isRelative();
        if (r8 != 0) goto L_0x00c1;
    L_0x00ba:
        r8 = r2.scaleDiff;
        r9 = r2.scale;
        r8 = r8 - r9;
        r2.scaleDiff = r8;
    L_0x00c1:
        r8 = r2.scale;
        r9 = r2.scaleDiff;
        r10 = r0.scaleValue;
        r10 = r10.getScale(r7);
        r9 = r9 * r10;
        r8 = r8 + r9;
        r2.setScale(r8);
        r8 = r0.rotationValue;
        r8 = r8.active;
        if (r8 == 0) goto L_0x010b;
    L_0x00d7:
        r8 = r0.rotationValue;
        r8 = r8.newLowValue();
        r2.rotation = r8;
        r8 = r0.rotationValue;
        r8 = r8.newHighValue();
        r2.rotationDiff = r8;
        r8 = r0.rotationValue;
        r8 = r8.isRelative();
        if (r8 != 0) goto L_0x00f6;
    L_0x00ef:
        r8 = r2.rotationDiff;
        r9 = r2.rotation;
        r8 = r8 - r9;
        r2.rotationDiff = r8;
    L_0x00f6:
        r8 = r2.rotation;
        r9 = r2.rotationDiff;
        r10 = r0.rotationValue;
        r10 = r10.getScale(r7);
        r9 = r9 * r10;
        r8 = r8 + r9;
        r9 = r0.aligned;
        if (r9 == 0) goto L_0x0108;
    L_0x0107:
        r8 = r8 + r5;
    L_0x0108:
        r2.setRotation(r8);
    L_0x010b:
        r8 = r0.windValue;
        r8 = r8.active;
        if (r8 == 0) goto L_0x0130;
    L_0x0111:
        r8 = r0.windValue;
        r8 = r8.newLowValue();
        r2.wind = r8;
        r8 = r0.windValue;
        r8 = r8.newHighValue();
        r2.windDiff = r8;
        r8 = r0.windValue;
        r8 = r8.isRelative();
        if (r8 != 0) goto L_0x0130;
    L_0x0129:
        r8 = r2.windDiff;
        r9 = r2.wind;
        r8 = r8 - r9;
        r2.windDiff = r8;
    L_0x0130:
        r8 = r0.gravityValue;
        r8 = r8.active;
        if (r8 == 0) goto L_0x0155;
    L_0x0136:
        r8 = r0.gravityValue;
        r8 = r8.newLowValue();
        r2.gravity = r8;
        r8 = r0.gravityValue;
        r8 = r8.newHighValue();
        r2.gravityDiff = r8;
        r8 = r0.gravityValue;
        r8 = r8.isRelative();
        if (r8 != 0) goto L_0x0155;
    L_0x014e:
        r8 = r2.gravityDiff;
        r9 = r2.gravity;
        r8 = r8 - r9;
        r2.gravityDiff = r8;
    L_0x0155:
        r8 = r2.tint;
        if (r8 != 0) goto L_0x015f;
    L_0x0159:
        r9 = 3;
        r9 = new float[r9];
        r8 = r9;
        r2.tint = r9;
    L_0x015f:
        r9 = r0.tintValue;
        r9 = r9.getColor(r7);
        r10 = 0;
        r11 = r9[r10];
        r8[r10] = r11;
        r10 = 1;
        r11 = r9[r10];
        r8[r10] = r11;
        r11 = 2;
        r12 = r9[r11];
        r8[r11] = r12;
        r11 = r0.transparencyValue;
        r11 = r11.newLowValue();
        r2.transparency = r11;
        r11 = r0.transparencyValue;
        r11 = r11.newHighValue();
        r12 = r2.transparency;
        r11 = r11 - r12;
        r2.transparencyDiff = r11;
        r11 = r0.f67x;
        r12 = r0.xOffsetValue;
        r12 = r12.active;
        if (r12 == 0) goto L_0x0196;
    L_0x018f:
        r12 = r0.xOffsetValue;
        r12 = r12.newLowValue();
        r11 = r11 + r12;
    L_0x0196:
        r12 = r0.f68y;
        r13 = r0.yOffsetValue;
        r13 = r13.active;
        if (r13 == 0) goto L_0x01a5;
    L_0x019e:
        r13 = r0.yOffsetValue;
        r13 = r13.newLowValue();
        r12 = r12 + r13;
    L_0x01a5:
        r13 = com.badlogic.gdx.graphics.g2d.ParticleEmitter.C03491.f66x9f4f9cf5;
        r14 = r0.spawnShapeValue;
        r14 = r14.shape;
        r14 = r14.ordinal();
        r13 = r13[r14];
        switch(r13) {
            case 1: goto L_0x0288;
            case 2: goto L_0x01f3;
            case 3: goto L_0x01ba;
            default: goto L_0x01b4;
        };
    L_0x01b4:
        r17 = r5;
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        goto L_0x02b7;
    L_0x01ba:
        r13 = r0.spawnWidth;
        r15 = r0.spawnWidthDiff;
        r10 = r0.spawnWidthValue;
        r10 = r10.getScale(r3);
        r15 = r15 * r10;
        r13 = r13 + r15;
        r10 = r0.spawnHeight;
        r15 = r0.spawnHeightDiff;
        r14 = r0.spawnHeightValue;
        r14 = r14.getScale(r3);
        r15 = r15 * r14;
        r10 = r10 + r15;
        r7 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1));
        if (r7 == 0) goto L_0x01eb;
    L_0x01d8:
        r7 = com.badlogic.gdx.math.MathUtils.random();
        r7 = r7 * r13;
        r11 = r11 + r7;
        r14 = r10 / r13;
        r14 = r14 * r7;
        r12 = r12 + r14;
    L_0x01e5:
        r17 = r5;
    L_0x01e7:
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        goto L_0x02b7;
    L_0x01eb:
        r7 = com.badlogic.gdx.math.MathUtils.random();
        r7 = r7 * r10;
        r12 = r12 + r7;
        goto L_0x01e5;
    L_0x01f3:
        r10 = r0.spawnWidth;
        r13 = r0.spawnWidthDiff;
        r14 = r0.spawnWidthValue;
        r14 = r14.getScale(r3);
        r13 = r13 * r14;
        r10 = r10 + r13;
        r13 = r0.spawnHeight;
        r14 = r0.spawnHeightDiff;
        r15 = r0.spawnHeightValue;
        r15 = r15.getScale(r3);
        r14 = r14 * r15;
        r13 = r13 + r14;
        r14 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r15 = r10 / r14;
        r16 = r13 / r14;
        r14 = (r15 > r7 ? 1 : (r15 == r7 ? 0 : -1));
        if (r14 == 0) goto L_0x01b4;
    L_0x0217:
        r7 = (r16 > r7 ? 1 : (r16 == r7 ? 0 : -1));
        if (r7 != 0) goto L_0x021c;
    L_0x021b:
        goto L_0x01e5;
    L_0x021c:
        r7 = r15 / r16;
        r14 = r0.spawnShapeValue;
        r14 = r14.edges;
        if (r14 == 0) goto L_0x0266;
    L_0x0224:
        r14 = com.badlogic.gdx.graphics.g2d.ParticleEmitter.C03491.f65xd8c64ca9;
        r1 = r0.spawnShapeValue;
        r1 = r1.side;
        r1 = r1.ordinal();
        r1 = r14[r1];
        r14 = 1127415808; // 0x43330000 float:179.0 double:5.570174193E-315;
        switch(r1) {
            case 1: goto L_0x0241;
            case 2: goto L_0x023c;
            default: goto L_0x0235;
        };
    L_0x0235:
        r1 = 1135869952; // 0x43b40000 float:360.0 double:5.611943214E-315;
        r1 = com.badlogic.gdx.math.MathUtils.random(r1);
        goto L_0x0247;
    L_0x023c:
        r1 = com.badlogic.gdx.math.MathUtils.random(r14);
        goto L_0x0247;
    L_0x0241:
        r1 = com.badlogic.gdx.math.MathUtils.random(r14);
        r1 = -r1;
    L_0x0247:
        r14 = com.badlogic.gdx.math.MathUtils.cosDeg(r1);
        r17 = r5;
        r5 = com.badlogic.gdx.math.MathUtils.sinDeg(r1);
        r18 = r14 * r15;
        r11 = r11 + r18;
        r18 = r5 * r15;
        r18 = r18 / r7;
        r12 = r12 + r18;
        r18 = r4 & 2;
        if (r18 != 0) goto L_0x0265;
    L_0x025f:
        r2.angle = r1;
        r2.angleCos = r14;
        r2.angleSin = r5;
    L_0x0265:
        goto L_0x01e7;
    L_0x0266:
        r17 = r5;
        r1 = r15 * r15;
    L_0x026a:
        r5 = com.badlogic.gdx.math.MathUtils.random(r10);
        r5 = r5 - r15;
        r14 = com.badlogic.gdx.math.MathUtils.random(r13);
        r14 = r14 - r16;
        r18 = r5 * r5;
        r19 = r14 * r14;
        r18 = r18 + r19;
        r18 = (r18 > r1 ? 1 : (r18 == r1 ? 0 : -1));
        if (r18 > 0) goto L_0x0287;
    L_0x027f:
        r11 = r11 + r5;
        r18 = r14 / r7;
        r12 = r12 + r18;
        goto L_0x01e7;
    L_0x0287:
        goto L_0x026a;
    L_0x0288:
        r17 = r5;
        r1 = r0.spawnWidth;
        r5 = r0.spawnWidthDiff;
        r7 = r0.spawnWidthValue;
        r7 = r7.getScale(r3);
        r5 = r5 * r7;
        r1 = r1 + r5;
        r5 = r0.spawnHeight;
        r7 = r0.spawnHeightDiff;
        r10 = r0.spawnHeightValue;
        r10 = r10.getScale(r3);
        r7 = r7 * r10;
        r5 = r5 + r7;
        r7 = com.badlogic.gdx.math.MathUtils.random(r1);
        r10 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r13 = r1 / r10;
        r7 = r7 - r13;
        r11 = r11 + r7;
        r7 = com.badlogic.gdx.math.MathUtils.random(r5);
        r13 = r5 / r10;
        r7 = r7 - r13;
        r12 = r12 + r7;
    L_0x02b7:
        r1 = r0.sprite;
        r1 = r1.getHeight();
        r5 = r6 / r10;
        r5 = r11 - r5;
        r7 = r1 / r10;
        r7 = r12 - r7;
        r2.setBounds(r5, r7, r6, r1);
        r5 = r0.lifeOffset;
        r5 = (float) r5;
        r7 = r0.lifeOffsetDiff;
        r7 = (float) r7;
        r10 = r0.lifeOffsetValue;
        r10 = r10.getScale(r3);
        r7 = r7 * r10;
        r5 = r5 + r7;
        r5 = (int) r5;
        if (r5 <= 0) goto L_0x02ea;
    L_0x02da:
        r7 = r2.currentLife;
        if (r5 < r7) goto L_0x02e3;
    L_0x02de:
        r7 = r2.currentLife;
        r10 = 1;
        r5 = r7 + -1;
    L_0x02e3:
        r7 = (float) r5;
        r10 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r7 = r7 / r10;
        r0.updateParticle(r2, r7, r5);
    L_0x02ea:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.g2d.ParticleEmitter.activateParticle(int):void");
    }

    private boolean updateParticle(Particle particle, float delta, int deltaMillis) {
        int life = particle.currentLife - deltaMillis;
        if (life <= 0) {
            return false;
        }
        float angle;
        float[] color;
        particle.currentLife = life;
        float alphaMultiplier = 1.0f;
        float percent = 1.0f - (((float) particle.currentLife) / ((float) particle.life));
        int updateFlags = this.updateFlags;
        if ((updateFlags & 1) != 0) {
            particle.setScale(particle.scale + (particle.scaleDiff * this.scaleValue.getScale(percent)));
        }
        if ((updateFlags & 8) != 0) {
            float velocityX;
            float velocityY;
            float velocity = (particle.velocity + (particle.velocityDiff * this.velocityValue.getScale(percent))) * delta;
            if ((updateFlags & 2) != 0) {
                angle = particle.angle + (particle.angleDiff * this.angleValue.getScale(percent));
                velocityX = MathUtils.cosDeg(angle) * velocity;
                velocityY = MathUtils.sinDeg(angle) * velocity;
                if ((updateFlags & 4) != 0) {
                    float rotation = particle.rotation + (particle.rotationDiff * this.rotationValue.getScale(percent));
                    if (this.aligned) {
                        rotation += angle;
                    }
                    particle.setRotation(rotation);
                }
            } else {
                velocityX = velocity * particle.angleCos;
                velocityY = velocity * particle.angleSin;
                if (this.aligned || (updateFlags & 4) != 0) {
                    angle = particle.rotation + (particle.rotationDiff * this.rotationValue.getScale(percent));
                    if (this.aligned) {
                        angle += particle.angle;
                    }
                    particle.setRotation(angle);
                }
            }
            if ((updateFlags & 16) != 0) {
                velocityX += (particle.wind + (particle.windDiff * this.windValue.getScale(percent))) * delta;
            }
            if ((updateFlags & 32) != 0) {
                velocityY += (particle.gravity + (particle.gravityDiff * this.gravityValue.getScale(percent))) * delta;
            }
            particle.translate(velocityX, velocityY);
        } else if ((updateFlags & 4) != 0) {
            particle.setRotation(particle.rotation + (particle.rotationDiff * this.rotationValue.getScale(percent)));
        }
        if ((updateFlags & 64) != 0) {
            color = this.tintValue.getColor(percent);
        } else {
            color = particle.tint;
        }
        if (this.premultipliedAlpha) {
            if (this.additive) {
                alphaMultiplier = 0.0f;
            }
            angle = particle.transparency + (particle.transparencyDiff * this.transparencyValue.getScale(percent));
            particle.setColor(color[0] * angle, color[1] * angle, color[2] * angle, angle * alphaMultiplier);
        } else {
            particle.setColor(color[0], color[1], color[2], particle.transparency + (particle.transparencyDiff * this.transparencyValue.getScale(percent)));
        }
        return true;
    }

    public void setPosition(float x, float y) {
        if (this.attached) {
            float xAmount = x - this.f67x;
            float yAmount = y - this.f68y;
            boolean[] active = this.active;
            int n = active.length;
            for (int i = 0; i < n; i++) {
                if (active[i]) {
                    this.particles[i].translate(xAmount, yAmount);
                }
            }
        }
        this.f67x = x;
        this.f68y = y;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        if (sprite != null) {
            float originX = sprite.getOriginX();
            float originY = sprite.getOriginY();
            Texture texture = sprite.getTexture();
            for (Particle particle : this.particles) {
                if (particle == null) {
                    break;
                }
                particle.setTexture(texture);
                particle.setOrigin(originX, originY);
            }
        }
    }

    public void allowCompletion() {
        this.allowCompletion = true;
        this.durationTimer = this.duration;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScaledNumericValue getLife() {
        return this.lifeValue;
    }

    public ScaledNumericValue getScale() {
        return this.scaleValue;
    }

    public ScaledNumericValue getRotation() {
        return this.rotationValue;
    }

    public GradientColorValue getTint() {
        return this.tintValue;
    }

    public ScaledNumericValue getVelocity() {
        return this.velocityValue;
    }

    public ScaledNumericValue getWind() {
        return this.windValue;
    }

    public ScaledNumericValue getGravity() {
        return this.gravityValue;
    }

    public ScaledNumericValue getAngle() {
        return this.angleValue;
    }

    public ScaledNumericValue getEmission() {
        return this.emissionValue;
    }

    public ScaledNumericValue getTransparency() {
        return this.transparencyValue;
    }

    public RangedNumericValue getDuration() {
        return this.durationValue;
    }

    public RangedNumericValue getDelay() {
        return this.delayValue;
    }

    public ScaledNumericValue getLifeOffset() {
        return this.lifeOffsetValue;
    }

    public RangedNumericValue getXOffsetValue() {
        return this.xOffsetValue;
    }

    public RangedNumericValue getYOffsetValue() {
        return this.yOffsetValue;
    }

    public ScaledNumericValue getSpawnWidth() {
        return this.spawnWidthValue;
    }

    public ScaledNumericValue getSpawnHeight() {
        return this.spawnHeightValue;
    }

    public SpawnShapeValue getSpawnShape() {
        return this.spawnShapeValue;
    }

    public boolean isAttached() {
        return this.attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public boolean isContinuous() {
        return this.continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public boolean isAligned() {
        return this.aligned;
    }

    public void setAligned(boolean aligned) {
        this.aligned = aligned;
    }

    public boolean isAdditive() {
        return this.additive;
    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    public boolean cleansUpBlendFunction() {
        return this.cleansUpBlendFunction;
    }

    public void setCleansUpBlendFunction(boolean cleansUpBlendFunction) {
        this.cleansUpBlendFunction = cleansUpBlendFunction;
    }

    public boolean isBehind() {
        return this.behind;
    }

    public void setBehind(boolean behind) {
        this.behind = behind;
    }

    public boolean isPremultipliedAlpha() {
        return this.premultipliedAlpha;
    }

    public void setPremultipliedAlpha(boolean premultipliedAlpha) {
        this.premultipliedAlpha = premultipliedAlpha;
    }

    public int getMinParticleCount() {
        return this.minParticleCount;
    }

    public void setMinParticleCount(int minParticleCount) {
        this.minParticleCount = minParticleCount;
    }

    public int getMaxParticleCount() {
        return this.maxParticleCount;
    }

    public boolean isComplete() {
        boolean z = false;
        if (this.delayTimer < this.delay) {
            return false;
        }
        if (this.durationTimer >= this.duration && this.activeCount == 0) {
            z = true;
        }
        return z;
    }

    public float getPercentComplete() {
        if (this.delayTimer < this.delay) {
            return 0.0f;
        }
        return Math.min(1.0f, this.durationTimer / this.duration);
    }

    public float getX() {
        return this.f67x;
    }

    public float getY() {
        return this.f68y;
    }

    public int getActiveCount() {
        return this.activeCount;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
        if (this.particles != null) {
            for (Particle particle : this.particles) {
                if (particle != null) {
                    particle.flip(flipX, flipY);
                }
            }
        }
    }

    public void flipY() {
        this.angleValue.setHigh(-this.angleValue.getHighMin(), -this.angleValue.getHighMax());
        this.angleValue.setLow(-this.angleValue.getLowMin(), -this.angleValue.getLowMax());
        this.gravityValue.setHigh(-this.gravityValue.getHighMin(), -this.gravityValue.getHighMax());
        this.gravityValue.setLow(-this.gravityValue.getLowMin(), -this.gravityValue.getLowMax());
        this.windValue.setHigh(-this.windValue.getHighMin(), -this.windValue.getHighMax());
        this.windValue.setLow(-this.windValue.getLowMin(), -this.windValue.getLowMax());
        this.rotationValue.setHigh(-this.rotationValue.getHighMin(), -this.rotationValue.getHighMax());
        this.rotationValue.setLow(-this.rotationValue.getLowMin(), -this.rotationValue.getLowMax());
        this.yOffsetValue.setLow(-this.yOffsetValue.getLowMin(), -this.yOffsetValue.getLowMax());
    }

    public BoundingBox getBoundingBox() {
        if (this.bounds == null) {
            this.bounds = new BoundingBox();
        }
        Particle[] particles = this.particles;
        boolean[] active = this.active;
        BoundingBox bounds = this.bounds;
        bounds.inf();
        int n = active.length;
        for (int i = 0; i < n; i++) {
            if (active[i]) {
                Rectangle r = particles[i].getBoundingRectangle();
                bounds.ext(r.f12x, r.f13y, 0.0f);
                bounds.ext(r.f12x + r.width, r.f13y + r.height, 0.0f);
            }
        }
        return bounds;
    }

    public void save(Writer output) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        output.write("- Delay -\n");
        this.delayValue.save(output);
        output.write("- Duration - \n");
        this.durationValue.save(output);
        output.write("- Count - \n");
        stringBuilder = new StringBuilder();
        stringBuilder.append("min: ");
        stringBuilder.append(this.minParticleCount);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("max: ");
        stringBuilder.append(this.maxParticleCount);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        output.write("- Emission - \n");
        this.emissionValue.save(output);
        output.write("- Life - \n");
        this.lifeValue.save(output);
        output.write("- Life Offset - \n");
        this.lifeOffsetValue.save(output);
        output.write("- X Offset - \n");
        this.xOffsetValue.save(output);
        output.write("- Y Offset - \n");
        this.yOffsetValue.save(output);
        output.write("- Spawn Shape - \n");
        this.spawnShapeValue.save(output);
        output.write("- Spawn Width - \n");
        this.spawnWidthValue.save(output);
        output.write("- Spawn Height - \n");
        this.spawnHeightValue.save(output);
        output.write("- Scale - \n");
        this.scaleValue.save(output);
        output.write("- Velocity - \n");
        this.velocityValue.save(output);
        output.write("- Angle - \n");
        this.angleValue.save(output);
        output.write("- Rotation - \n");
        this.rotationValue.save(output);
        output.write("- Wind - \n");
        this.windValue.save(output);
        output.write("- Gravity - \n");
        this.gravityValue.save(output);
        output.write("- Tint - \n");
        this.tintValue.save(output);
        output.write("- Transparency - \n");
        this.transparencyValue.save(output);
        output.write("- Options - \n");
        stringBuilder = new StringBuilder();
        stringBuilder.append("attached: ");
        stringBuilder.append(this.attached);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("continuous: ");
        stringBuilder.append(this.continuous);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("aligned: ");
        stringBuilder.append(this.aligned);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("additive: ");
        stringBuilder.append(this.additive);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("behind: ");
        stringBuilder.append(this.behind);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("premultipliedAlpha: ");
        stringBuilder.append(this.premultipliedAlpha);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
        output.write("- Image Path -\n");
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.imagePath);
        stringBuilder.append("\n");
        output.write(stringBuilder.toString());
    }

    public void load(BufferedReader reader) throws IOException {
        try {
            this.name = readString(reader, "name");
            reader.readLine();
            this.delayValue.load(reader);
            reader.readLine();
            this.durationValue.load(reader);
            reader.readLine();
            setMinParticleCount(readInt(reader, "minParticleCount"));
            setMaxParticleCount(readInt(reader, "maxParticleCount"));
            reader.readLine();
            this.emissionValue.load(reader);
            reader.readLine();
            this.lifeValue.load(reader);
            reader.readLine();
            this.lifeOffsetValue.load(reader);
            reader.readLine();
            this.xOffsetValue.load(reader);
            reader.readLine();
            this.yOffsetValue.load(reader);
            reader.readLine();
            this.spawnShapeValue.load(reader);
            reader.readLine();
            this.spawnWidthValue.load(reader);
            reader.readLine();
            this.spawnHeightValue.load(reader);
            reader.readLine();
            this.scaleValue.load(reader);
            reader.readLine();
            this.velocityValue.load(reader);
            reader.readLine();
            this.angleValue.load(reader);
            reader.readLine();
            this.rotationValue.load(reader);
            reader.readLine();
            this.windValue.load(reader);
            reader.readLine();
            this.gravityValue.load(reader);
            reader.readLine();
            this.tintValue.load(reader);
            reader.readLine();
            this.transparencyValue.load(reader);
            reader.readLine();
            this.attached = readBoolean(reader, "attached");
            this.continuous = readBoolean(reader, "continuous");
            this.aligned = readBoolean(reader, "aligned");
            this.additive = readBoolean(reader, "additive");
            this.behind = readBoolean(reader, "behind");
            String line = reader.readLine();
            if (line.startsWith("premultipliedAlpha")) {
                this.premultipliedAlpha = readBoolean(line);
                reader.readLine();
            }
            setImagePath(reader.readLine());
        } catch (RuntimeException ex) {
            if (this.name == null) {
                throw ex;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error parsing emitter: ");
            stringBuilder.append(this.name);
            throw new RuntimeException(stringBuilder.toString(), ex);
        }
    }

    static String readString(String line) throws IOException {
        return line.substring(line.indexOf(":") + 1).trim();
    }

    static String readString(BufferedReader reader, String name) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            return readString(line);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Missing value: ");
        stringBuilder.append(name);
        throw new IOException(stringBuilder.toString());
    }

    static boolean readBoolean(String line) throws IOException {
        return Boolean.parseBoolean(readString(line));
    }

    static boolean readBoolean(BufferedReader reader, String name) throws IOException {
        return Boolean.parseBoolean(readString(reader, name));
    }

    static int readInt(BufferedReader reader, String name) throws IOException {
        return Integer.parseInt(readString(reader, name));
    }

    static float readFloat(BufferedReader reader, String name) throws IOException {
        return Float.parseFloat(readString(reader, name));
    }
}
