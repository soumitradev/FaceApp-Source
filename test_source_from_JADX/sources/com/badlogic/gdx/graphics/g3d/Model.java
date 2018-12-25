package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
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
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider.FileTextureProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import java.util.Iterator;

public class Model implements Disposable {
    public final Array<Animation> animations;
    protected final Array<Disposable> disposables;
    public final Array<Material> materials;
    public final Array<MeshPart> meshParts;
    public final Array<Mesh> meshes;
    private ObjectMap<NodePart, ArrayMap<String, Matrix4>> nodePartBones;
    public final Array<Node> nodes;

    public Model() {
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
    }

    public Model(ModelData modelData) {
        this(modelData, new FileTextureProvider());
    }

    public Model(ModelData modelData, TextureProvider textureProvider) {
        this.materials = new Array();
        this.nodes = new Array();
        this.animations = new Array();
        this.meshes = new Array();
        this.meshParts = new Array();
        this.disposables = new Array();
        this.nodePartBones = new ObjectMap();
        load(modelData, textureProvider);
    }

    private void load(ModelData modelData, TextureProvider textureProvider) {
        loadMeshes(modelData.meshes);
        loadMaterials(modelData.materials, textureProvider);
        loadNodes(modelData.nodes);
        loadAnimations(modelData.animations);
        calculateTransforms();
    }

    private void loadAnimations(Iterable<ModelAnimation> modelAnimations) {
        for (ModelAnimation anim : modelAnimations) {
            Animation animation = new Animation();
            animation.id = anim.id;
            Iterator i$ = anim.nodeAnimations.iterator();
            while (i$.hasNext()) {
                ModelNodeAnimation nanim = (ModelNodeAnimation) i$.next();
                Node node = getNode(nanim.nodeId);
                if (node != null) {
                    Iterator i$2;
                    ModelNodeKeyframe<Vector3> kf;
                    NodeAnimation nodeAnim = new NodeAnimation();
                    nodeAnim.node = node;
                    if (nanim.translation != null) {
                        nodeAnim.translation = new Array();
                        nodeAnim.translation.ensureCapacity(nanim.translation.size);
                        i$2 = nanim.translation.iterator();
                        while (i$2.hasNext()) {
                            kf = (ModelNodeKeyframe) i$2.next();
                            if (kf.keytime > animation.duration) {
                                animation.duration = kf.keytime;
                            }
                            nodeAnim.translation.add(new NodeKeyframe(kf.keytime, new Vector3(kf.value == null ? node.translation : (Vector3) kf.value)));
                        }
                    }
                    if (nanim.rotation != null) {
                        nodeAnim.rotation = new Array();
                        nodeAnim.rotation.ensureCapacity(nanim.rotation.size);
                        i$2 = nanim.rotation.iterator();
                        while (i$2.hasNext()) {
                            ModelNodeKeyframe<Quaternion> kf2 = (ModelNodeKeyframe) i$2.next();
                            if (kf2.keytime > animation.duration) {
                                animation.duration = kf2.keytime;
                            }
                            nodeAnim.rotation.add(new NodeKeyframe(kf2.keytime, new Quaternion(kf2.value == null ? node.rotation : (Quaternion) kf2.value)));
                        }
                    }
                    if (nanim.scaling != null) {
                        nodeAnim.scaling = new Array();
                        nodeAnim.scaling.ensureCapacity(nanim.scaling.size);
                        i$2 = nanim.scaling.iterator();
                        while (i$2.hasNext()) {
                            kf = (ModelNodeKeyframe) i$2.next();
                            if (kf.keytime > animation.duration) {
                                animation.duration = kf.keytime;
                            }
                            nodeAnim.scaling.add(new NodeKeyframe(kf.keytime, new Vector3(kf.value == null ? node.scale : (Vector3) kf.value)));
                        }
                    }
                    if ((nodeAnim.translation != null && nodeAnim.translation.size > 0) || ((nodeAnim.rotation != null && nodeAnim.rotation.size > 0) || (nodeAnim.scaling != null && nodeAnim.scaling.size > 0))) {
                        animation.nodeAnimations.add(nodeAnim);
                    }
                }
            }
            if (animation.nodeAnimations.size > 0) {
                this.animations.add(animation);
            }
        }
    }

    private void loadNodes(Iterable<ModelNode> modelNodes) {
        this.nodePartBones.clear();
        for (ModelNode node : modelNodes) {
            this.nodes.add(loadNode(node));
        }
        Iterator i$ = this.nodePartBones.entries().iterator();
        while (i$.hasNext()) {
            Entry<NodePart, ArrayMap<String, Matrix4>> e = (Entry) i$.next();
            if (((NodePart) e.key).invBoneBindTransforms == null) {
                ((NodePart) e.key).invBoneBindTransforms = new ArrayMap(Node.class, Matrix4.class);
            }
            ((NodePart) e.key).invBoneBindTransforms.clear();
            Iterator i$2 = ((ArrayMap) e.value).entries().iterator();
            while (i$2.hasNext()) {
                Entry<String, Matrix4> b = (Entry) i$2.next();
                ((NodePart) e.key).invBoneBindTransforms.put(getNode((String) b.key), new Matrix4((Matrix4) b.value).inv());
            }
        }
    }

    private Node loadNode(ModelNode modelNode) {
        int len$;
        Node node = new Node();
        node.id = modelNode.id;
        if (modelNode.translation != null) {
            node.translation.set(modelNode.translation);
        }
        if (modelNode.rotation != null) {
            node.rotation.set(modelNode.rotation);
        }
        if (modelNode.scale != null) {
            node.scale.set(modelNode.scale);
        }
        if (modelNode.parts != null) {
            ModelNodePart[] arr$ = modelNode.parts;
            len$ = arr$.length;
            int i$ = 0;
            while (i$ < len$) {
                Iterator i$2;
                ModelNodePart modelNodePart = arr$[i$];
                MeshPart meshPart = null;
                Material meshMaterial = null;
                if (modelNodePart.meshPartId != null) {
                    i$2 = this.meshParts.iterator();
                    while (i$2.hasNext()) {
                        MeshPart part = (MeshPart) i$2.next();
                        if (modelNodePart.meshPartId.equals(part.id)) {
                            meshPart = part;
                            break;
                        }
                    }
                }
                if (modelNodePart.materialId != null) {
                    i$2 = this.materials.iterator();
                    while (i$2.hasNext()) {
                        Material material = (Material) i$2.next();
                        if (modelNodePart.materialId.equals(material.id)) {
                            meshMaterial = material;
                            break;
                        }
                    }
                }
                if (meshPart != null) {
                    if (meshMaterial != null) {
                        if (!(meshPart == null || meshMaterial == null)) {
                            NodePart nodePart = new NodePart();
                            nodePart.meshPart = meshPart;
                            nodePart.material = meshMaterial;
                            node.parts.add(nodePart);
                            if (modelNodePart.bones != null) {
                                this.nodePartBones.put(nodePart, modelNodePart.bones);
                            }
                        }
                        i$++;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid node: ");
                stringBuilder.append(node.id);
                throw new GdxRuntimeException(stringBuilder.toString());
            }
        }
        if (modelNode.children != null) {
            for (ModelNode child : modelNode.children) {
                node.addChild(loadNode(child));
            }
        }
        return node;
    }

    private void loadMeshes(Iterable<ModelMesh> meshes) {
        for (ModelMesh mesh : meshes) {
            convertMesh(mesh);
        }
    }

    private void convertMesh(ModelMesh modelMesh) {
        int numIndices = 0;
        for (ModelMeshPart part : modelMesh.parts) {
            numIndices += part.indices.length;
        }
        VertexAttributes attributes = new VertexAttributes(modelMesh.attributes);
        Mesh mesh = new Mesh(true, modelMesh.vertices.length / (attributes.vertexSize / 4), numIndices, attributes);
        this.meshes.add(mesh);
        this.disposables.add(mesh);
        BufferUtils.copy(modelMesh.vertices, mesh.getVerticesBuffer(), modelMesh.vertices.length, 0);
        mesh.getIndicesBuffer().clear();
        int offset = 0;
        for (ModelMeshPart part2 : modelMesh.parts) {
            MeshPart meshPart = new MeshPart();
            meshPart.id = part2.id;
            meshPart.primitiveType = part2.primitiveType;
            meshPart.indexOffset = offset;
            meshPart.numVertices = part2.indices.length;
            meshPart.mesh = mesh;
            mesh.getIndicesBuffer().put(part2.indices);
            offset += meshPart.numVertices;
            this.meshParts.add(meshPart);
        }
        mesh.getIndicesBuffer().position(0);
    }

    private void loadMaterials(Iterable<ModelMaterial> modelMaterials, TextureProvider textureProvider) {
        for (ModelMaterial mtl : modelMaterials) {
            this.materials.add(convertMaterial(mtl, textureProvider));
        }
    }

    private Material convertMaterial(ModelMaterial mtl, TextureProvider textureProvider) {
        Model model;
        TextureProvider textureProvider2;
        ModelMaterial modelMaterial = mtl;
        Material result = new Material();
        result.id = modelMaterial.id;
        if (modelMaterial.ambient != null) {
            result.set(new ColorAttribute(ColorAttribute.Ambient, modelMaterial.ambient));
        }
        if (modelMaterial.diffuse != null) {
            result.set(new ColorAttribute(ColorAttribute.Diffuse, modelMaterial.diffuse));
        }
        if (modelMaterial.specular != null) {
            result.set(new ColorAttribute(ColorAttribute.Specular, modelMaterial.specular));
        }
        if (modelMaterial.emissive != null) {
            result.set(new ColorAttribute(ColorAttribute.Emissive, modelMaterial.emissive));
        }
        if (modelMaterial.reflection != null) {
            result.set(new ColorAttribute(ColorAttribute.Reflection, modelMaterial.reflection));
        }
        if (modelMaterial.shininess > 0.0f) {
            result.set(new FloatAttribute(FloatAttribute.Shininess, modelMaterial.shininess));
        }
        if (modelMaterial.opacity != 1.0f) {
            result.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, modelMaterial.opacity));
        }
        ObjectMap<String, Texture> textures = new ObjectMap();
        if (modelMaterial.textures != null) {
            Iterator i$ = modelMaterial.textures.iterator();
            while (i$.hasNext()) {
                Texture texture;
                ModelTexture tex = (ModelTexture) i$.next();
                if (textures.containsKey(tex.fileName)) {
                    texture = (Texture) textures.get(tex.fileName);
                    model = this;
                    textureProvider2 = textureProvider;
                } else {
                    texture = textureProvider.load(tex.fileName);
                    textures.put(tex.fileName, texture);
                    this.disposables.add(texture);
                }
                TextureDescriptor descriptor = new TextureDescriptor(texture);
                descriptor.minFilter = texture.getMinFilter();
                descriptor.magFilter = texture.getMagFilter();
                descriptor.uWrap = texture.getUWrap();
                descriptor.vWrap = texture.getVWrap();
                float offsetU = tex.uvTranslation == null ? 0.0f : tex.uvTranslation.f16x;
                float offsetV = tex.uvTranslation == null ? 0.0f : tex.uvTranslation.f17y;
                float scaleU = tex.uvScaling == null ? 1.0f : tex.uvScaling.f16x;
                float scaleV = tex.uvScaling == null ? 1.0f : tex.uvScaling.f17y;
                switch (tex.usage) {
                    case 2:
                        result.set(new TextureAttribute(TextureAttribute.Diffuse, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 3:
                        result.set(new TextureAttribute(TextureAttribute.Emissive, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 4:
                        result.set(new TextureAttribute(TextureAttribute.Ambient, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 5:
                        result.set(new TextureAttribute(TextureAttribute.Specular, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 7:
                        result.set(new TextureAttribute(TextureAttribute.Normal, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 8:
                        result.set(new TextureAttribute(TextureAttribute.Bump, descriptor, offsetU, offsetV, scaleU, scaleV));
                        break;
                    case 10:
                        Attribute attribute = r11;
                        Attribute textureAttribute = new TextureAttribute(TextureAttribute.Reflection, descriptor, offsetU, offsetV, scaleU, scaleV);
                        result.set(attribute);
                        break;
                    default:
                        break;
                }
            }
        }
        model = this;
        textureProvider2 = textureProvider;
        return result;
    }

    public void manageDisposable(Disposable disposable) {
        if (!this.disposables.contains(disposable, true)) {
            this.disposables.add(disposable);
        }
    }

    public Iterable<Disposable> getManagedDisposables() {
        return this.disposables;
    }

    public void dispose() {
        Iterator i$ = this.disposables.iterator();
        while (i$.hasNext()) {
            ((Disposable) i$.next()).dispose();
        }
    }

    public void calculateTransforms() {
        int n = this.nodes.size;
        for (int i = 0; i < n; i++) {
            ((Node) this.nodes.get(i)).calculateTransforms(true);
        }
        for (int i2 = 0; i2 < n; i2++) {
            ((Node) this.nodes.get(i2)).calculateBoneTransforms(true);
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out) {
        out.inf();
        return extendBoundingBox(out);
    }

    public BoundingBox extendBoundingBox(BoundingBox out) {
        int n = this.nodes.size;
        for (int i = 0; i < n; i++) {
            ((Node) this.nodes.get(i)).extendBoundingBox(out);
        }
        return out;
    }

    public Animation getAnimation(String id) {
        return getAnimation(id, true);
    }

    public Animation getAnimation(String id, boolean ignoreCase) {
        int n = this.animations.size;
        int i = 0;
        Animation animation;
        Animation animation2;
        if (ignoreCase) {
            while (i < n) {
                animation = (Animation) this.animations.get(i);
                animation2 = animation;
                if (animation.id.equalsIgnoreCase(id)) {
                    return animation2;
                }
                i++;
            }
        } else {
            while (i < n) {
                animation = (Animation) this.animations.get(i);
                animation2 = animation;
                if (animation.id.equals(id)) {
                    return animation2;
                }
                i++;
            }
        }
        return null;
    }

    public Material getMaterial(String id) {
        return getMaterial(id, true);
    }

    public Material getMaterial(String id, boolean ignoreCase) {
        int n = this.materials.size;
        int i = 0;
        Material material;
        Material material2;
        if (ignoreCase) {
            while (i < n) {
                material = (Material) this.materials.get(i);
                material2 = material;
                if (material.id.equalsIgnoreCase(id)) {
                    return material2;
                }
                i++;
            }
        } else {
            while (i < n) {
                material = (Material) this.materials.get(i);
                material2 = material;
                if (material.id.equals(id)) {
                    return material2;
                }
                i++;
            }
        }
        return null;
    }

    public Node getNode(String id) {
        return getNode(id, true);
    }

    public Node getNode(String id, boolean recursive) {
        return getNode(id, recursive, false);
    }

    public Node getNode(String id, boolean recursive, boolean ignoreCase) {
        return Node.getNode(this.nodes, id, recursive, ignoreCase);
    }
}
