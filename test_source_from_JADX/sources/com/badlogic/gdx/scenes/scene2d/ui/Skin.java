package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import java.util.Iterator;
import org.catrobat.catroid.common.BrickValues;

public class Skin implements Disposable {
    TextureAtlas atlas;
    ObjectMap<Class, ObjectMap<String, Object>> resources = new ObjectMap();

    public Skin(FileHandle skinFile) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(skinFile.nameWithoutExtension());
        stringBuilder.append(".atlas");
        FileHandle atlasFile = skinFile.sibling(stringBuilder.toString());
        if (atlasFile.exists()) {
            this.atlas = new TextureAtlas(atlasFile);
            addRegions(this.atlas);
        }
        load(skinFile);
    }

    public Skin(FileHandle skinFile, TextureAtlas atlas) {
        this.atlas = atlas;
        addRegions(atlas);
        load(skinFile);
    }

    public Skin(TextureAtlas atlas) {
        this.atlas = atlas;
        addRegions(atlas);
    }

    public void load(FileHandle skinFile) {
        try {
            getJsonLoader(skinFile).fromJson(Skin.class, skinFile);
        } catch (SerializationException ex) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error reading file: ");
            stringBuilder.append(skinFile);
            throw new SerializationException(stringBuilder.toString(), ex);
        }
    }

    public void addRegions(TextureAtlas atlas) {
        Array<AtlasRegion> regions = atlas.getRegions();
        int n = regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) regions.get(i);
            add(region.name, region, TextureRegion.class);
        }
    }

    public void add(String name, Object resource) {
        add(name, resource, resource.getClass());
    }

    public void add(String name, Object resource, Class type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (resource == null) {
            throw new IllegalArgumentException("resource cannot be null.");
        } else {
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                typeResources = new ObjectMap();
                this.resources.put(type, typeResources);
            }
            typeResources.put(name, resource);
        }
    }

    public void remove(String name, Class type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        }
        ((ObjectMap) this.resources.get(type)).remove(name);
    }

    public <T> T get(Class<T> type) {
        return get(BrickValues.STRING_VALUE, type);
    }

    public <T> T get(String name, Class<T> type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (type == Drawable.class) {
            return getDrawable(name);
        } else {
            if (type == TextureRegion.class) {
                return getRegion(name);
            }
            if (type == NinePatch.class) {
                return getPatch(name);
            }
            if (type == Sprite.class) {
                return getSprite(name);
            }
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("No ");
                stringBuilder.append(type.getName());
                stringBuilder.append(" registered with name: ");
                stringBuilder.append(name);
                throw new GdxRuntimeException(stringBuilder.toString());
            }
            Object resource = typeResources.get(name);
            if (resource != null) {
                return resource;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("No ");
            stringBuilder2.append(type.getName());
            stringBuilder2.append(" registered with name: ");
            stringBuilder2.append(name);
            throw new GdxRuntimeException(stringBuilder2.toString());
        }
    }

    public <T> T optional(String name, Class<T> type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else {
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                return null;
            }
            return typeResources.get(name);
        }
    }

    public boolean has(String name, Class type) {
        ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
        if (typeResources == null) {
            return false;
        }
        return typeResources.containsKey(name);
    }

    public <T> ObjectMap<String, T> getAll(Class<T> type) {
        return (ObjectMap) this.resources.get(type);
    }

    public Color getColor(String name) {
        return (Color) get(name, Color.class);
    }

    public BitmapFont getFont(String name) {
        return (BitmapFont) get(name, BitmapFont.class);
    }

    public TextureRegion getRegion(String name) {
        TextureRegion region = (TextureRegion) optional(name, TextureRegion.class);
        if (region != null) {
            return region;
        }
        Texture texture = (Texture) optional(name, Texture.class);
        if (texture == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No TextureRegion or Texture registered with name: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        region = new TextureRegion(texture);
        add(name, region, TextureRegion.class);
        return region;
    }

    public TiledDrawable getTiledDrawable(String name) {
        TiledDrawable tiled = (TiledDrawable) optional(name, TiledDrawable.class);
        if (tiled != null) {
            return tiled;
        }
        tiled = new TiledDrawable(getRegion(name));
        tiled.setName(name);
        add(name, tiled, TiledDrawable.class);
        return tiled;
    }

    public NinePatch getPatch(String name) {
        NinePatch patch = (NinePatch) optional(name, NinePatch.class);
        if (patch != null) {
            return patch;
        }
        try {
            TextureRegion region = getRegion(name);
            if (region instanceof AtlasRegion) {
                int[] splits = ((AtlasRegion) region).splits;
                if (splits != null) {
                    patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                    int[] pads = ((AtlasRegion) region).pads;
                    if (pads != null) {
                        patch.setPadding(pads[0], pads[1], pads[2], pads[3]);
                    }
                }
            }
            if (patch == null) {
                patch = new NinePatch(region);
            }
            add(name, patch, NinePatch.class);
            return patch;
        } catch (GdxRuntimeException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No NinePatch, TextureRegion, or Texture registered with name: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public Sprite getSprite(String name) {
        Sprite sprite = (Sprite) optional(name, Sprite.class);
        if (sprite != null) {
            return sprite;
        }
        try {
            TextureRegion textureRegion = getRegion(name);
            if (textureRegion instanceof AtlasRegion) {
                AtlasRegion region = (AtlasRegion) textureRegion;
                if (!(!region.rotate && region.packedWidth == region.originalWidth && region.packedHeight == region.originalHeight)) {
                    sprite = new AtlasSprite(region);
                }
            }
            if (sprite == null) {
                sprite = new Sprite(textureRegion);
            }
            add(name, sprite, Sprite.class);
            return sprite;
        } catch (GdxRuntimeException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No NinePatch, TextureRegion, or Texture registered with name: ");
            stringBuilder.append(name);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
    }

    public Drawable getDrawable(String name) {
        Drawable drawable = (Drawable) optional(name, Drawable.class);
        if (drawable != null) {
            return drawable;
        }
        drawable = (Drawable) optional(name, TiledDrawable.class);
        if (drawable != null) {
            return drawable;
        }
        try {
            TextureRegion textureRegion = getRegion(name);
            if (textureRegion instanceof AtlasRegion) {
                AtlasRegion region = (AtlasRegion) textureRegion;
                if (region.splits != null) {
                    drawable = new NinePatchDrawable(getPatch(name));
                } else if (!(!region.rotate && region.packedWidth == region.originalWidth && region.packedHeight == region.originalHeight)) {
                    drawable = new SpriteDrawable(getSprite(name));
                }
            }
            if (drawable == null) {
                drawable = new TextureRegionDrawable(textureRegion);
            }
        } catch (GdxRuntimeException e) {
        }
        if (drawable == null) {
            NinePatch patch = (NinePatch) optional(name, NinePatch.class);
            if (patch != null) {
                drawable = new NinePatchDrawable(patch);
            } else {
                Sprite sprite = (Sprite) optional(name, Sprite.class);
                if (sprite != null) {
                    drawable = new SpriteDrawable(sprite);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: ");
                    stringBuilder.append(name);
                    throw new GdxRuntimeException(stringBuilder.toString());
                }
            }
        }
        if (drawable instanceof BaseDrawable) {
            ((BaseDrawable) drawable).setName(name);
        }
        add(name, drawable, Drawable.class);
        return drawable;
    }

    public String find(Object resource) {
        if (resource == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(resource.getClass());
        if (typeResources == null) {
            return null;
        }
        return (String) typeResources.findKey(resource, true);
    }

    public Drawable newDrawable(String name) {
        return newDrawable(getDrawable(name));
    }

    public Drawable newDrawable(String name, float r, float g, float b, float a) {
        return newDrawable(getDrawable(name), new Color(r, g, b, a));
    }

    public Drawable newDrawable(String name, Color tint) {
        return newDrawable(getDrawable(name), tint);
    }

    public Drawable newDrawable(Drawable drawable) {
        if (drawable instanceof TextureRegionDrawable) {
            return new TextureRegionDrawable((TextureRegionDrawable) drawable);
        }
        if (drawable instanceof NinePatchDrawable) {
            return new NinePatchDrawable((NinePatchDrawable) drawable);
        }
        if (drawable instanceof SpriteDrawable) {
            return new SpriteDrawable((SpriteDrawable) drawable);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to copy, unknown drawable type: ");
        stringBuilder.append(drawable.getClass());
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public Drawable newDrawable(Drawable drawable, float r, float g, float b, float a) {
        return newDrawable(drawable, new Color(r, g, b, a));
    }

    public Drawable newDrawable(Drawable drawable, Color tint) {
        Drawable newDrawable;
        if (drawable instanceof TextureRegionDrawable) {
            newDrawable = ((TextureRegionDrawable) drawable).tint(tint);
        } else if (drawable instanceof NinePatchDrawable) {
            newDrawable = ((NinePatchDrawable) drawable).tint(tint);
        } else if (drawable instanceof SpriteDrawable) {
            newDrawable = ((SpriteDrawable) drawable).tint(tint);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to copy, unknown drawable type: ");
            stringBuilder.append(drawable.getClass());
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        if (newDrawable instanceof BaseDrawable) {
            BaseDrawable named = (BaseDrawable) newDrawable;
            StringBuilder stringBuilder2;
            if (drawable instanceof BaseDrawable) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(((BaseDrawable) drawable).getName());
                stringBuilder2.append(" (");
                stringBuilder2.append(tint);
                stringBuilder2.append(")");
                named.setName(stringBuilder2.toString());
            } else {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(" (");
                stringBuilder2.append(tint);
                stringBuilder2.append(")");
                named.setName(stringBuilder2.toString());
            }
        }
        return newDrawable;
    }

    public void setEnabled(Actor actor, boolean enabled) {
        Method method = findMethod(actor.getClass(), "getStyle");
        if (method != null) {
            try {
                Object style = method.invoke(actor, new Object[0]);
                String name = find(style);
                if (name != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(name.replace("-disabled", ""));
                    stringBuilder.append(enabled ? "" : "-disabled");
                    style = get(stringBuilder.toString(), style.getClass());
                    method = findMethod(actor.getClass(), "setStyle");
                    if (method != null) {
                        try {
                            method.invoke(actor, new Object[]{style});
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public TextureAtlas getAtlas() {
        return this.atlas;
    }

    public void dispose() {
        if (this.atlas != null) {
            this.atlas.dispose();
        }
        Iterator i$ = this.resources.values().iterator();
        while (i$.hasNext()) {
            Iterator i$2 = ((ObjectMap) i$.next()).values().iterator();
            while (i$2.hasNext()) {
                Object resource = i$2.next();
                if (resource instanceof Disposable) {
                    ((Disposable) resource).dispose();
                }
            }
        }
    }

    protected Json getJsonLoader(FileHandle skinFile) {
        Json json = new Skin$1(this);
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setSerializer(Skin.class, new Skin$2(this, this));
        json.setSerializer(BitmapFont.class, new Skin$3(this, skinFile, this));
        json.setSerializer(Color.class, new Skin$4(this));
        json.setSerializer(Skin$TintedDrawable.class, new Skin$5(this));
        return json;
    }

    private static Method findMethod(Class type, String name) {
        for (Method method : ClassReflection.getMethods(type)) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }
}
