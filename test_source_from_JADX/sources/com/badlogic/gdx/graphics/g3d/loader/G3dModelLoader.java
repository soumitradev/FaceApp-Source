package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader.ModelParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.model.data.ModelAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BaseJsonReader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;

public class G3dModelLoader extends ModelLoader<ModelParameters> {
    public static final short VERSION_HI = (short) 0;
    public static final short VERSION_LO = (short) 1;
    protected final BaseJsonReader reader;
    private final Quaternion tempQ;

    public G3dModelLoader(BaseJsonReader reader) {
        this(reader, null);
    }

    public G3dModelLoader(BaseJsonReader reader, FileHandleResolver resolver) {
        super(resolver);
        this.tempQ = new Quaternion();
        this.reader = reader;
    }

    public ModelData loadModelData(FileHandle fileHandle, ModelParameters parameters) {
        return parseModel(fileHandle);
    }

    public ModelData parseModel(FileHandle handle) {
        JsonValue json = this.reader.parse(handle);
        ModelData model = new ModelData();
        JsonValue version = json.require(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
        model.version[0] = version.getShort(0);
        model.version[1] = version.getShort(1);
        if (model.version[0] == (short) 0) {
            if (model.version[1] == (short) 1) {
                model.id = json.getString(ShareConstants.WEB_DIALOG_PARAM_ID, "");
                parseMeshes(model, json);
                parseMaterials(model, json, handle.parent().path());
                parseNodes(model, json);
                parseAnimations(model, json);
                return model;
            }
        }
        throw new GdxRuntimeException("Model version not supported");
    }

    private void parseMeshes(ModelData model, JsonValue json) {
        G3dModelLoader g3dModelLoader = this;
        ModelData modelData = model;
        JsonValue meshes = json.get("meshes");
        if (meshes != null) {
            modelData.meshes.ensureCapacity(meshes.size);
            JsonValue mesh = meshes.child;
            while (mesh != null) {
                JsonValue jsonValue;
                JsonValue meshes2;
                ModelMesh jsonMesh = new ModelMesh();
                jsonMesh.id = mesh.getString(ShareConstants.WEB_DIALOG_PARAM_ID, "");
                jsonMesh.attributes = parseAttributes(mesh.require("attributes"));
                jsonMesh.vertices = mesh.require("vertices").asFloatArray();
                JsonValue meshParts = mesh.require("parts");
                Array<ModelMeshPart> parts = new Array();
                JsonValue meshPart = meshParts.child;
                while (meshPart != null) {
                    ModelMeshPart jsonPart = new ModelMeshPart();
                    String partId = meshPart.getString(ShareConstants.WEB_DIALOG_PARAM_ID, null);
                    if (partId == null) {
                        throw new GdxRuntimeException("Not id given for mesh part");
                    }
                    Iterator i$ = parts.iterator();
                    while (i$.hasNext()) {
                        if (((ModelMeshPart) i$.next()).id.equals(partId)) {
                            meshes = new StringBuilder();
                            meshes.append("Mesh part with id '");
                            meshes.append(partId);
                            meshes.append("' already in defined");
                            throw new GdxRuntimeException(meshes.toString());
                        }
                        jsonValue = json;
                    }
                    meshes2 = meshes;
                    jsonPart.id = partId;
                    meshes = meshPart.getString("type", null);
                    if (meshes == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("No primitive type given for mesh part '");
                        stringBuilder.append(partId);
                        stringBuilder.append(FormatHelper.QUOTE);
                        throw new GdxRuntimeException(stringBuilder.toString());
                    }
                    jsonPart.primitiveType = parseType(meshes);
                    jsonPart.indices = meshPart.require("indices").asShortArray();
                    parts.add(jsonPart);
                    meshPart = meshPart.next;
                    meshes = meshes2;
                    jsonValue = json;
                }
                meshes2 = meshes;
                jsonMesh.parts = (ModelMeshPart[]) parts.toArray(ModelMeshPart.class);
                modelData.meshes.add(jsonMesh);
                mesh = mesh.next;
                meshes = meshes2;
                jsonValue = json;
            }
        }
    }

    private int parseType(String type) {
        if (type.equals("TRIANGLES")) {
            return 4;
        }
        if (type.equals("LINES")) {
            return 1;
        }
        if (type.equals("POINTS")) {
            return 0;
        }
        if (type.equals("TRIANGLE_STRIP")) {
            return 5;
        }
        if (type.equals("LINE_STRIP")) {
            return 3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown primitive type '");
        stringBuilder.append(type);
        stringBuilder.append("', should be one of triangle, trianglestrip, line, linestrip, lineloop or point");
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    private VertexAttribute[] parseAttributes(JsonValue attributes) {
        Array<VertexAttribute> vertexAttributes = new Array();
        int unit = 0;
        int blendWeightCount = 0;
        for (JsonValue value = attributes.child; value != null; value = value.next) {
            String attr = value.asString();
            if (attr.equals("POSITION")) {
                vertexAttributes.add(VertexAttribute.Position());
            } else if (attr.equals("NORMAL")) {
                vertexAttributes.add(VertexAttribute.Normal());
            } else if (attr.equals("COLOR")) {
                vertexAttributes.add(VertexAttribute.ColorUnpacked());
            } else if (attr.equals("COLORPACKED")) {
                vertexAttributes.add(VertexAttribute.ColorPacked());
            } else if (attr.equals("TANGENT")) {
                vertexAttributes.add(VertexAttribute.Tangent());
            } else if (attr.equals("BINORMAL")) {
                vertexAttributes.add(VertexAttribute.Binormal());
            } else if (attr.startsWith("TEXCOORD")) {
                unit = unit + 1;
                vertexAttributes.add(VertexAttribute.TexCoords(unit));
                unit = unit;
            } else if (attr.startsWith("BLENDWEIGHT")) {
                unit = blendWeightCount + 1;
                vertexAttributes.add(VertexAttribute.BoneWeight(blendWeightCount));
                blendWeightCount = unit;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown vertex attribute '");
                stringBuilder.append(attr);
                stringBuilder.append("', should be one of position, normal, uv, tangent or binormal");
                throw new GdxRuntimeException(stringBuilder.toString());
            }
        }
        return (VertexAttribute[]) vertexAttributes.toArray(VertexAttribute.class);
    }

    private void parseMaterials(ModelData model, JsonValue json, String materialDir) {
        G3dModelLoader g3dModelLoader = this;
        ModelData modelData = model;
        String str = materialDir;
        JsonValue materials = json.get("materials");
        if (materials == null) {
            JsonValue jsonValue = materials;
            return;
        }
        modelData.materials.ensureCapacity(materials.size);
        JsonValue material = materials.child;
        while (material != null) {
            ModelMaterial jsonMaterial = new ModelMaterial();
            String id = material.getString(ShareConstants.WEB_DIALOG_PARAM_ID, null);
            if (id == null) {
                throw new GdxRuntimeException("Material needs an id.");
            }
            String id2;
            JsonValue jsonValue2;
            jsonMaterial.id = id;
            JsonValue diffuse = material.get("diffuse");
            if (diffuse != null) {
                jsonMaterial.diffuse = parseColor(diffuse);
            }
            JsonValue ambient = material.get("ambient");
            if (ambient != null) {
                jsonMaterial.ambient = parseColor(ambient);
            }
            JsonValue emissive = material.get("emissive");
            if (emissive != null) {
                jsonMaterial.emissive = parseColor(emissive);
            }
            JsonValue specular = material.get("specular");
            if (specular != null) {
                jsonMaterial.specular = parseColor(specular);
            }
            JsonValue reflection = material.get("reflection");
            if (reflection != null) {
                jsonMaterial.reflection = parseColor(reflection);
            }
            jsonMaterial.shininess = material.getFloat(FloatAttribute.ShininessAlias, 0.0f);
            jsonMaterial.opacity = material.getFloat("opacity", 1.0f);
            JsonValue textures = material.get("textures");
            if (textures != null) {
                JsonValue texture = textures.child;
                while (texture != null) {
                    ModelTexture jsonTexture = new ModelTexture();
                    jsonValue = materials;
                    String textureId = texture.getString(ShareConstants.WEB_DIALOG_PARAM_ID, null);
                    if (textureId == null) {
                        throw new GdxRuntimeException("Texture has no id.");
                    }
                    id2 = id;
                    jsonTexture.id = textureId;
                    String fileName = texture.getString("filename", null);
                    if (fileName == null) {
                        throw new GdxRuntimeException("Texture needs filename.");
                    }
                    String str2;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str);
                    if (materialDir.length() != 0) {
                        if (!str.endsWith("/")) {
                            id = "/";
                            stringBuilder.append(id);
                            stringBuilder.append(fileName);
                            jsonTexture.fileName = stringBuilder.toString();
                            jsonTexture.uvTranslation = readVector2(texture.get("uvTranslation"), 0.0f, 0.0f);
                            jsonTexture.uvScaling = readVector2(texture.get("uvScaling"), 1.0f, 1.0f);
                            textureId = texture.getString("type", null);
                            if (textureId != null) {
                                throw new GdxRuntimeException("Texture needs type.");
                            }
                            jsonTexture.usage = parseTextureUsage(textureId);
                            if (jsonMaterial.textures == null) {
                                jsonMaterial.textures = new Array();
                            }
                            jsonMaterial.textures.add(jsonTexture);
                            texture = texture.next;
                            str2 = null;
                            materials = jsonValue;
                            id = id2;
                            str = materialDir;
                            jsonValue2 = json;
                        }
                    }
                    id = "";
                    stringBuilder.append(id);
                    stringBuilder.append(fileName);
                    jsonTexture.fileName = stringBuilder.toString();
                    jsonTexture.uvTranslation = readVector2(texture.get("uvTranslation"), 0.0f, 0.0f);
                    jsonTexture.uvScaling = readVector2(texture.get("uvScaling"), 1.0f, 1.0f);
                    textureId = texture.getString("type", null);
                    if (textureId != null) {
                        jsonTexture.usage = parseTextureUsage(textureId);
                        if (jsonMaterial.textures == null) {
                            jsonMaterial.textures = new Array();
                        }
                        jsonMaterial.textures.add(jsonTexture);
                        texture = texture.next;
                        str2 = null;
                        materials = jsonValue;
                        id = id2;
                        str = materialDir;
                        jsonValue2 = json;
                    } else {
                        throw new GdxRuntimeException("Texture needs type.");
                    }
                }
                continue;
            }
            jsonValue = materials;
            id2 = id;
            modelData.materials.add(jsonMaterial);
            material = material.next;
            materials = jsonValue;
            str = materialDir;
            jsonValue2 = json;
        }
    }

    private int parseTextureUsage(String value) {
        if (value.equalsIgnoreCase("AMBIENT")) {
            return 4;
        }
        if (value.equalsIgnoreCase("BUMP")) {
            return 8;
        }
        if (value.equalsIgnoreCase("DIFFUSE")) {
            return 2;
        }
        if (value.equalsIgnoreCase("EMISSIVE")) {
            return 3;
        }
        if (value.equalsIgnoreCase("NONE")) {
            return 1;
        }
        if (value.equalsIgnoreCase("NORMAL")) {
            return 7;
        }
        if (value.equalsIgnoreCase("REFLECTION")) {
            return 10;
        }
        if (value.equalsIgnoreCase("SHININESS")) {
            return 6;
        }
        if (value.equalsIgnoreCase("SPECULAR")) {
            return 5;
        }
        if (value.equalsIgnoreCase("TRANSPARENCY")) {
            return 9;
        }
        return 0;
    }

    private Color parseColor(JsonValue colorArray) {
        if (colorArray.size >= 3) {
            return new Color(colorArray.getFloat(0), colorArray.getFloat(1), colorArray.getFloat(2), 1.0f);
        }
        throw new GdxRuntimeException("Expected Color values <> than three.");
    }

    private Vector2 readVector2(JsonValue vectorArray, float x, float y) {
        if (vectorArray == null) {
            return new Vector2(x, y);
        }
        if (vectorArray.size == 2) {
            return new Vector2(vectorArray.getFloat(0), vectorArray.getFloat(1));
        }
        throw new GdxRuntimeException("Expected Vector2 values <> than two.");
    }

    private Array<ModelNode> parseNodes(ModelData model, JsonValue json) {
        JsonValue nodes = json.get("nodes");
        if (nodes != null) {
            model.nodes.ensureCapacity(nodes.size);
            for (JsonValue node = nodes.child; node != null; node = node.next) {
                model.nodes.add(parseNodesRecursively(node));
            }
        }
        return model.nodes;
    }

    private ModelNode parseNodesRecursively(JsonValue json) {
        G3dModelLoader g3dModelLoader = this;
        JsonValue jsonValue = json;
        ModelNode jsonNode = new ModelNode();
        String str = null;
        String id = jsonValue.getString(ShareConstants.WEB_DIALOG_PARAM_ID, null);
        if (id == null) {
            throw new GdxRuntimeException("Node id missing.");
        }
        jsonNode.id = id;
        JsonValue translation = jsonValue.get("translation");
        if (translation == null || translation.size == 3) {
            jsonNode.translation = translation == null ? null : new Vector3(translation.getFloat(0), translation.getFloat(1), translation.getFloat(2));
            JsonValue rotation = jsonValue.get("rotation");
            if (rotation == null || rotation.size == 4) {
                jsonNode.rotation = rotation == null ? null : new Quaternion(rotation.getFloat(0), rotation.getFloat(1), rotation.getFloat(2), rotation.getFloat(3));
                JsonValue scale = jsonValue.get("scale");
                if (scale == null || scale.size == 3) {
                    JsonValue jsonValue2;
                    JsonValue jsonValue3;
                    String str2;
                    JsonValue jsonValue4;
                    JsonValue val;
                    jsonNode.scale = scale == null ? null : new Vector3(scale.getFloat(0), scale.getFloat(1), scale.getFloat(2));
                    String meshId = jsonValue.getString("mesh", null);
                    if (meshId != null) {
                        jsonNode.meshId = meshId;
                    }
                    JsonValue materials = jsonValue.get("parts");
                    if (materials != null) {
                        jsonNode.parts = new ModelNodePart[materials.size];
                        int i = 0;
                        JsonValue material = materials.child;
                        while (material != null) {
                            ModelNodePart nodePart = new ModelNodePart();
                            String meshPartId = material.getString("meshpartid", str);
                            String materialId = material.getString("materialid", str);
                            String str3;
                            String str4;
                            if (meshPartId == null) {
                                str3 = materialId;
                                str4 = meshPartId;
                                jsonValue2 = rotation;
                                jsonValue3 = scale;
                                str2 = meshId;
                                jsonValue4 = materials;
                            } else if (materialId == null) {
                                r16 = translation;
                                str3 = materialId;
                                str4 = meshPartId;
                                jsonValue2 = rotation;
                                jsonValue3 = scale;
                                str2 = meshId;
                                jsonValue4 = materials;
                            } else {
                                nodePart.materialId = materialId;
                                nodePart.meshPartId = meshPartId;
                                JsonValue bones = material.get("bones");
                                if (bones != null) {
                                    r16 = translation;
                                    jsonValue2 = rotation;
                                    nodePart.bones = new ArrayMap(true, bones.size, String.class, Matrix4.class);
                                    translation = null;
                                    materialId = bones.child;
                                    while (materialId != null) {
                                        meshPartId = materialId.getString("node", null);
                                        if (meshPartId == null) {
                                            throw new GdxRuntimeException("Bone node ID missing");
                                        }
                                        JsonValue bones2;
                                        rotation = new Matrix4();
                                        JsonValue val2 = materialId.get("translation");
                                        if (val2 != null) {
                                            bones2 = bones;
                                            jsonValue3 = scale;
                                            if (val2.size >= 3) {
                                                str2 = meshId;
                                                jsonValue4 = materials;
                                                rotation.translate(val2.getFloat(null), val2.getFloat(1), val2.getFloat(2));
                                            } else {
                                                str2 = meshId;
                                                jsonValue4 = materials;
                                            }
                                        } else {
                                            bones2 = bones;
                                            jsonValue3 = scale;
                                            str2 = meshId;
                                            jsonValue4 = materials;
                                        }
                                        bones = materialId.get("rotation");
                                        if (bones != null && bones.size >= 4) {
                                            rotation.rotate(g3dModelLoader.tempQ.set(bones.getFloat(0), bones.getFloat(1), bones.getFloat(2), bones.getFloat(3)));
                                        }
                                        val = materialId.get("scale");
                                        if (val != null) {
                                            if (val.size >= 3) {
                                                rotation.scale(val.getFloat(0), val.getFloat(1), val.getFloat(2));
                                                nodePart.bones.put(meshPartId, rotation);
                                                materialId = materialId.next;
                                                translation++;
                                                bones = bones2;
                                                scale = jsonValue3;
                                                meshId = str2;
                                                materials = jsonValue4;
                                                g3dModelLoader = this;
                                                jsonValue = json;
                                            }
                                        }
                                        nodePart.bones.put(meshPartId, rotation);
                                        materialId = materialId.next;
                                        translation++;
                                        bones = bones2;
                                        scale = jsonValue3;
                                        meshId = str2;
                                        materials = jsonValue4;
                                        g3dModelLoader = this;
                                        jsonValue = json;
                                    }
                                    jsonValue3 = scale;
                                    str2 = meshId;
                                    jsonValue4 = materials;
                                } else {
                                    r16 = translation;
                                    str3 = materialId;
                                    str4 = meshPartId;
                                    jsonValue2 = rotation;
                                    jsonValue3 = scale;
                                    str2 = meshId;
                                    jsonValue4 = materials;
                                }
                                jsonNode.parts[i] = nodePart;
                                material = material.next;
                                i++;
                                translation = r16;
                                rotation = jsonValue2;
                                scale = jsonValue3;
                                meshId = str2;
                                materials = jsonValue4;
                                g3dModelLoader = this;
                                jsonValue = json;
                                str = null;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Node ");
                            stringBuilder.append(id);
                            stringBuilder.append(" part is missing meshPartId or materialId");
                            throw new GdxRuntimeException(stringBuilder.toString());
                        }
                    }
                    jsonValue2 = rotation;
                    jsonValue3 = scale;
                    str2 = meshId;
                    jsonValue4 = materials;
                    val = json.get("children");
                    if (val != null) {
                        jsonNode.children = new ModelNode[val.size];
                        int i2 = 0;
                        translation = val.child;
                        while (translation != null) {
                            jsonNode.children[i2] = parseNodesRecursively(translation);
                            translation = translation.next;
                            i2++;
                        }
                    }
                    G3dModelLoader g3dModelLoader2 = this;
                    return jsonNode;
                }
                throw new GdxRuntimeException("Node scale incomplete");
            }
            throw new GdxRuntimeException("Node rotation incomplete");
        }
        throw new GdxRuntimeException("Node translation incomplete");
    }

    private void parseAnimations(ModelData model, JsonValue json) {
        ModelData modelData = model;
        JsonValue animations = json.get("animations");
        if (animations != null) {
            modelData.animations.ensureCapacity(animations.size);
            JsonValue anim = animations.child;
            while (anim != null) {
                JsonValue animations2;
                JsonValue keyframe;
                JsonValue nodes = anim.get("bones");
                if (nodes == null) {
                    animations2 = animations;
                } else {
                    ModelAnimation animation = new ModelAnimation();
                    modelData.animations.add(animation);
                    animation.nodeAnimations.ensureCapacity(nodes.size);
                    animation.id = anim.getString(ShareConstants.WEB_DIALOG_PARAM_ID);
                    JsonValue node = nodes.child;
                    while (node != null) {
                        JsonValue nodes2;
                        ModelNodeAnimation nodeAnim = new ModelNodeAnimation();
                        animation.nodeAnimations.add(nodeAnim);
                        nodeAnim.nodeId = node.getString("boneId");
                        JsonValue keyframes = node.get("keyframes");
                        float f = 1000.0f;
                        float f2 = 0.0f;
                        int i = 1;
                        int i2 = 0;
                        int i3 = 3;
                        JsonValue translationKF;
                        ModelNodeKeyframe<Vector3> kf;
                        JsonValue rotation;
                        JsonValue scaling;
                        if (keyframes == null || !keyframes.isArray()) {
                            animations2 = animations;
                            nodes2 = nodes;
                            translationKF = node.get("translation");
                            if (!(translationKF == null || translationKF.isArray() == null)) {
                                nodeAnim.translation = new Array();
                                nodeAnim.translation.ensureCapacity(translationKF.size);
                                for (animations = translationKF.child; animations != null; animations = animations.next) {
                                    kf = new ModelNodeKeyframe();
                                    nodeAnim.translation.add(kf);
                                    kf.keytime = animations.getFloat("keytime", 0.0f) / 1000.0f;
                                    nodes = animations.get(FirebaseAnalytics$Param.VALUE);
                                    if (nodes != null && nodes.size >= 3) {
                                        kf.value = new Vector3(nodes.getFloat(0), nodes.getFloat(1), nodes.getFloat(2));
                                    }
                                }
                            }
                            animations = node.get("rotation");
                            if (animations != null && animations.isArray()) {
                                nodeAnim.rotation = new Array();
                                nodeAnim.rotation.ensureCapacity(animations.size);
                                for (keyframe = animations.child; keyframe != null; keyframe = keyframe.next) {
                                    ModelNodeKeyframe<Quaternion> kf2 = new ModelNodeKeyframe();
                                    nodeAnim.rotation.add(kf2);
                                    kf2.keytime = keyframe.getFloat("keytime", 0.0f) / 1000.0f;
                                    rotation = keyframe.get(FirebaseAnalytics$Param.VALUE);
                                    if (rotation != null && rotation.size >= 4) {
                                        kf2.value = new Quaternion(rotation.getFloat(0), rotation.getFloat(1), rotation.getFloat(2), rotation.getFloat(3));
                                    }
                                }
                            }
                            keyframe = node.get("scaling");
                            if (keyframe != null && keyframe.isArray()) {
                                nodeAnim.scaling = new Array();
                                nodeAnim.scaling.ensureCapacity(keyframe.size);
                                for (nodes = keyframe.child; nodes != null; nodes = nodes.next) {
                                    ModelNodeKeyframe<Vector3> kf3 = new ModelNodeKeyframe();
                                    nodeAnim.scaling.add(kf3);
                                    kf3.keytime = nodes.getFloat("keytime", 0.0f) / 1000.0f;
                                    scaling = nodes.get(FirebaseAnalytics$Param.VALUE);
                                    if (scaling != null && scaling.size >= 3) {
                                        kf3.value = new Vector3(scaling.getFloat(0), scaling.getFloat(1), scaling.getFloat(2));
                                    }
                                }
                            }
                        } else {
                            rotation = keyframes.child;
                            while (rotation != null) {
                                float keytime = rotation.getFloat("keytime", f2) / f;
                                scaling = rotation.get("translation");
                                if (scaling == null || scaling.size != r15) {
                                    animations2 = animations;
                                } else {
                                    if (nodeAnim.translation == null) {
                                        nodeAnim.translation = new Array();
                                    }
                                    ModelNodeKeyframe<Vector3> tkf = new ModelNodeKeyframe();
                                    tkf.keytime = keytime;
                                    animations2 = animations;
                                    tkf.value = new Vector3(scaling.getFloat(i2), scaling.getFloat(i), scaling.getFloat(2));
                                    nodeAnim.translation.add(tkf);
                                }
                                translationKF = rotation.get("rotation");
                                if (translationKF == null || translationKF.size != 4) {
                                    nodes2 = nodes;
                                } else {
                                    if (nodeAnim.rotation == null) {
                                        nodeAnim.rotation = new Array();
                                    }
                                    animations = new ModelNodeKeyframe();
                                    animations.keytime = keytime;
                                    nodes2 = nodes;
                                    animations.value = new Quaternion(translationKF.getFloat(0), translationKF.getFloat(1), translationKF.getFloat(2), translationKF.getFloat(3));
                                    nodeAnim.rotation.add(animations);
                                }
                                animations = rotation.get("scale");
                                if (animations != null && animations.size == 3) {
                                    if (nodeAnim.scaling == null) {
                                        nodeAnim.scaling = new Array();
                                    }
                                    kf = new ModelNodeKeyframe();
                                    kf.keytime = keytime;
                                    kf.value = new Vector3(animations.getFloat(0), animations.getFloat(1), animations.getFloat(2));
                                    nodeAnim.scaling.add(kf);
                                }
                                rotation = rotation.next;
                                animations = animations2;
                                nodes = nodes2;
                                modelData = model;
                                keyframe = json;
                                f = 1000.0f;
                                f2 = 0.0f;
                                i = 1;
                                i2 = 0;
                                i3 = 3;
                            }
                            animations2 = animations;
                            nodes2 = nodes;
                        }
                        node = node.next;
                        animations = animations2;
                        nodes = nodes2;
                        modelData = model;
                        keyframe = json;
                    }
                    animations2 = animations;
                }
                anim = anim.next;
                animations = animations2;
                modelData = model;
                keyframe = json;
            }
        }
    }
}
