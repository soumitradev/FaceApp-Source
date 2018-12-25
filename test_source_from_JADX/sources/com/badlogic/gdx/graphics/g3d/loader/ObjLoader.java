package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.ModelLoader.ModelParameters;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import javax.jmdns.impl.constants.DNSRecordClass;
import org.catrobat.catroid.common.BrickValues;

public class ObjLoader extends ModelLoader<ObjLoaderParameters> {
    public static boolean logWarning = false;
    final Array<Group> groups;
    final FloatArray norms;
    final FloatArray uvs;
    final FloatArray verts;

    private class Group {
        Array<Integer> faces = new Array(200);
        boolean hasNorms;
        boolean hasUVs;
        Material mat = new Material("");
        String materialName = BrickValues.STRING_VALUE;
        final String name;
        int numFaces = null;

        Group(String name) {
            this.name = name;
        }
    }

    public static class ObjLoaderParameters extends ModelParameters {
        public boolean flipV;

        public ObjLoaderParameters(boolean flipV) {
            this.flipV = flipV;
        }
    }

    public ObjLoader() {
        this(null);
    }

    public ObjLoader(FileHandleResolver resolver) {
        super(resolver);
        this.verts = new FloatArray((int) HttpStatus.SC_MULTIPLE_CHOICES);
        this.norms = new FloatArray((int) HttpStatus.SC_MULTIPLE_CHOICES);
        this.uvs = new FloatArray(200);
        this.groups = new Array(10);
    }

    public Model loadModel(FileHandle fileHandle, boolean flipV) {
        return loadModel(fileHandle, new ObjLoaderParameters(flipV));
    }

    public ModelData loadModelData(FileHandle file, ObjLoaderParameters parameters) {
        return loadModelData(file, parameters == null ? false : parameters.flipV);
    }

    protected ModelData loadModelData(FileHandle file, boolean flipV) {
        int i;
        BufferedReader bufferedReader;
        Group group;
        ObjLoader objLoader = this;
        if (logWarning) {
            Gdx.app.error("ObjLoader", "Wavefront (OBJ) is not fully supported, consult the documentation for more information");
        }
        MtlLoader mtl = new MtlLoader();
        Group activeGroup = new Group(BrickValues.STRING_VALUE);
        objLoader.groups.add(activeGroup);
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 4096);
        int i2 = 0;
        Group activeGroup2 = activeGroup;
        while (true) {
            String line;
            try {
                String readLine = reader.readLine();
                line = readLine;
                int i3 = 1;
                if (readLine != null) {
                    try {
                        String[] tokens = line.split("\\s+");
                        if (tokens.length < 1) {
                            break;
                        }
                        if (tokens[i2].length() != 0) {
                            char charAt = tokens[i2].toLowerCase().charAt(i2);
                            char firstChar = charAt;
                            if (charAt != '#') {
                                if (firstChar == 'v') {
                                    if (tokens[i2].length() == 1) {
                                        objLoader.verts.add(Float.parseFloat(tokens[1]));
                                        objLoader.verts.add(Float.parseFloat(tokens[2]));
                                        objLoader.verts.add(Float.parseFloat(tokens[3]));
                                    } else if (tokens[i2].charAt(1) == 'n') {
                                        objLoader.norms.add(Float.parseFloat(tokens[1]));
                                        objLoader.norms.add(Float.parseFloat(tokens[2]));
                                        objLoader.norms.add(Float.parseFloat(tokens[3]));
                                    } else if (tokens[i2].charAt(1) == 't') {
                                        objLoader.uvs.add(Float.parseFloat(tokens[1]));
                                        objLoader.uvs.add(flipV ? 1.0f - Float.parseFloat(tokens[2]) : Float.parseFloat(tokens[2]));
                                    }
                                } else if (firstChar == 'f') {
                                    Array<Integer> faces = activeGroup2.faces;
                                    i = 1;
                                    while (i < tokens.length - 2) {
                                        String[] parts = tokens[i3].split("/");
                                        faces.add(Integer.valueOf(getIndex(parts[i2], objLoader.verts.size)));
                                        if (parts.length > 2) {
                                            if (i == 1) {
                                                activeGroup2.hasNorms = true;
                                            }
                                            faces.add(Integer.valueOf(getIndex(parts[2], objLoader.norms.size)));
                                        }
                                        if (parts.length > 1 && parts[1].length() > 0) {
                                            if (i == 1) {
                                                activeGroup2.hasUVs = true;
                                            }
                                            faces.add(Integer.valueOf(getIndex(parts[1], objLoader.uvs.size)));
                                        }
                                        i++;
                                        String[] parts2 = tokens[i].split("/");
                                        faces.add(Integer.valueOf(getIndex(parts2[0], objLoader.verts.size)));
                                        if (parts2.length > 2) {
                                            faces.add(Integer.valueOf(getIndex(parts2[2], objLoader.norms.size)));
                                        }
                                        if (parts2.length > 1 && parts2[1].length() > 0) {
                                            faces.add(Integer.valueOf(getIndex(parts2[1], objLoader.uvs.size)));
                                        }
                                        i++;
                                        parts2 = tokens[i].split("/");
                                        faces.add(Integer.valueOf(getIndex(parts2[0], objLoader.verts.size)));
                                        if (parts2.length > 2) {
                                            faces.add(Integer.valueOf(getIndex(parts2[2], objLoader.norms.size)));
                                        }
                                        if (parts2.length > 1 && parts2[1].length() > 0) {
                                            faces.add(Integer.valueOf(getIndex(parts2[1], objLoader.uvs.size)));
                                        }
                                        activeGroup2.numFaces++;
                                        i--;
                                        i2 = 0;
                                        i3 = 1;
                                    }
                                } else {
                                    if (firstChar != 'o') {
                                        if (firstChar != 'g') {
                                            if (tokens[0].equals("mtllib")) {
                                                mtl.load(file.parent().child(tokens[1]));
                                            } else if (tokens[0].equals("usemtl")) {
                                                if (tokens.length == 1) {
                                                    activeGroup2.materialName = BrickValues.STRING_VALUE;
                                                } else {
                                                    activeGroup2.materialName = tokens[1];
                                                }
                                            }
                                        }
                                    }
                                    if (tokens.length > 1) {
                                        activeGroup2 = setActiveGroup(tokens[1]);
                                    } else {
                                        activeGroup2 = setActiveGroup(BrickValues.STRING_VALUE);
                                    }
                                }
                            }
                        }
                        i2 = 0;
                    } catch (IOException e) {
                        bufferedReader = reader;
                        group = activeGroup2;
                        IOException reader2 = e;
                    }
                }
                break;
            } catch (IOException e2) {
                bufferedReader = reader;
                group = activeGroup2;
            }
        }
        String line2;
        try {
            reader.close();
            int i4 = 0;
            while (i4 < objLoader.groups.size) {
                if (((Group) objLoader.groups.get(i4)).numFaces < 1) {
                    objLoader.groups.removeIndex(i4);
                    i4--;
                }
                i4++;
            }
            if (objLoader.groups.size < 1) {
                return null;
            }
            int numGroups;
            i4 = objLoader.groups.size;
            ModelData data = new ModelData();
            int id = 0;
            int g = 0;
            while (g < i4) {
                int i5;
                int numElements;
                int g2;
                Group group2 = (Group) objLoader.groups.get(g);
                Array<Integer> faces2 = group2.faces;
                i = faces2.size;
                int numFaces = group2.numFaces;
                boolean hasNorms = group2.hasNorms;
                bufferedReader = reader;
                reader = group2.hasUVs;
                numGroups = i4;
                float[] finalVerts = new float[((numFaces * 3) * (((hasNorms ? 3 : 0) + 3) + (reader != null ? 2 : 0)))];
                group = activeGroup2;
                int i6 = 0;
                int vi = 0;
                while (i6 < i) {
                    line2 = line;
                    i5 = i6 + 1;
                    i6 = ((Integer) faces2.get(i6)).intValue() * 3;
                    int vi2 = vi + 1;
                    numElements = i;
                    int vertIndex = i6 + 1;
                    finalVerts[vi] = objLoader.verts.get(i6);
                    i6 = vi2 + 1;
                    g2 = g;
                    g = vertIndex + 1;
                    finalVerts[vi2] = objLoader.verts.get(vertIndex);
                    i = i6 + 1;
                    finalVerts[i6] = objLoader.verts.get(g);
                    int vertIndex2;
                    if (hasNorms) {
                        i6 = i5 + 1;
                        i5 = ((Integer) faces2.get(i5)).intValue() * 3;
                        vertIndex = i + 1;
                        vertIndex2 = g;
                        int i7 = i6;
                        i6 = i5 + 1;
                        finalVerts[i] = objLoader.norms.get(i5);
                        g = vertIndex + 1;
                        i = i6 + 1;
                        finalVerts[vertIndex] = objLoader.norms.get(i6);
                        i6 = g + 1;
                        finalVerts[g] = objLoader.norms.get(i);
                        i5 = i7;
                    } else {
                        vertIndex2 = g;
                        i6 = i;
                    }
                    if (reader != null) {
                        g = i5 + 1;
                        i5 = ((Integer) faces2.get(i5)).intValue() * 2;
                        i = i6 + 1;
                        int i8 = g;
                        g = i5 + 1;
                        finalVerts[i6] = objLoader.uvs.get(i5);
                        i6 = i + 1;
                        finalVerts[i] = objLoader.uvs.get(g);
                        vi = i6;
                        i6 = i8;
                    } else {
                        vi = i6;
                        i6 = i5;
                    }
                    line = line2;
                    i = numElements;
                    g = g2;
                }
                g2 = g;
                line2 = line;
                numElements = i;
                g = numFaces * 3 >= DNSRecordClass.CLASS_MASK ? 0 : numFaces * 3;
                short[] finalIndices = new short[g];
                if (g > 0) {
                    for (i5 = 0; i5 < g; i5++) {
                        finalIndices[i5] = (short) i5;
                    }
                }
                Array<VertexAttribute> attributes = new Array();
                attributes.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
                if (hasNorms) {
                    attributes.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
                }
                if (reader != null) {
                    attributes.add(new VertexAttribute(16, 2, "a_texCoord0"));
                }
                String nodeId = new StringBuilder();
                nodeId.append("node");
                id++;
                nodeId.append(id);
                nodeId = nodeId.toString();
                String meshId = new StringBuilder();
                meshId.append("mesh");
                meshId.append(id);
                meshId = meshId.toString();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("part");
                stringBuilder.append(id);
                String partId = stringBuilder.toString();
                ModelNode node = new ModelNode();
                node.id = nodeId;
                node.meshId = meshId;
                boolean hasUVs = reader;
                node.scale = new Vector3(1.0f, 1.0f, 1.0f);
                node.translation = new Vector3();
                node.rotation = new Quaternion();
                ModelNodePart pm = new ModelNodePart();
                pm.meshPartId = partId;
                pm.materialId = group2.materialName;
                int id2 = id;
                node.parts = new ModelNodePart[]{pm};
                reader = new ModelMeshPart();
                reader.id = partId;
                reader.indices = finalIndices;
                reader.primitiveType = 4;
                ModelMesh mesh = new ModelMesh();
                mesh.id = meshId;
                mesh.attributes = (VertexAttribute[]) attributes.toArray(VertexAttribute.class);
                mesh.vertices = finalVerts;
                mesh.parts = new ModelMeshPart[]{reader};
                data.nodes.add(node);
                data.meshes.add(mesh);
                data.materials.add(mtl.getMaterial(group2.materialName));
                g = g2 + 1;
                reader = bufferedReader;
                i4 = numGroups;
                activeGroup2 = group;
                line = line2;
                id = id2;
            }
            numGroups = i4;
            group = activeGroup2;
            line2 = line;
            if (objLoader.verts.size > 0) {
                objLoader.verts.clear();
            }
            if (objLoader.norms.size > 0) {
                objLoader.norms.clear();
            }
            if (objLoader.uvs.size > 0) {
                objLoader.uvs.clear();
            }
            if (objLoader.groups.size > 0) {
                objLoader.groups.clear();
            }
            return data;
        } catch (IOException e3) {
            bufferedReader = reader;
            group = activeGroup2;
            line2 = line;
            reader2 = e3;
            return null;
        }
    }

    private Group setActiveGroup(String name) {
        Iterator i$ = this.groups.iterator();
        while (i$.hasNext()) {
            Group group = (Group) i$.next();
            if (group.name.equals(name)) {
                return group;
            }
        }
        Group group2 = new Group(name);
        this.groups.add(group2);
        return group2;
    }

    private int getIndex(String index, int size) {
        if (index != null) {
            if (index.length() != 0) {
                int idx = Integer.parseInt(index);
                if (idx < 0) {
                    return size + idx;
                }
                return idx - 1;
            }
        }
        return 0;
    }
}
