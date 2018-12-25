package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.utils.Array;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.catrobat.catroid.common.BrickValues;

/* compiled from: ObjLoader */
class MtlLoader {
    public Array<ModelMaterial> materials = new Array();

    MtlLoader() {
    }

    public void load(FileHandle file) {
        IOException e;
        Color color;
        MtlLoader mtlLoader = this;
        String curMatName = BrickValues.STRING_VALUE;
        String curMatName2 = Color.WHITE;
        Color speccolor = Color.WHITE;
        float opacity = 1.0f;
        float shininess = 0.0f;
        String texFilename = null;
        if (file != null) {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.read()), 4096);
                String line = null;
                while (true) {
                    String str;
                    try {
                        String readLine = reader.readLine();
                        line = readLine;
                        if (readLine == null) {
                            break;
                        }
                        if (line.length() > 0 && line.charAt(0) == '\t') {
                            line = line.substring(1).trim();
                        }
                        try {
                            String[] tokens = line.split("\\s+");
                            if (tokens[0].length() != 0) {
                                if (tokens[0].charAt(0) != '#') {
                                    String key = tokens[0].toLowerCase();
                                    if (key.equals("newmtl")) {
                                        ModelMaterial mat = new ModelMaterial();
                                        mat.id = curMatName;
                                        mat.diffuse = new Color(curMatName2);
                                        mat.specular = new Color(speccolor);
                                        mat.opacity = opacity;
                                        mat.shininess = shininess;
                                        if (texFilename != null) {
                                            ModelTexture tex = new ModelTexture();
                                            tex.usage = 2;
                                            tex.fileName = new String(texFilename);
                                            if (mat.textures == null) {
                                                str = line;
                                                try {
                                                    mat.textures = new Array(1);
                                                } catch (IOException e2) {
                                                    e = e2;
                                                    color = speccolor;
                                                }
                                            } else {
                                                str = line;
                                            }
                                            mat.textures.add(tex);
                                        } else {
                                            str = line;
                                        }
                                        mtlLoader.materials.add(mat);
                                        if (tokens.length > 1) {
                                            curMatName = tokens[1].replace('.', '_');
                                        } else {
                                            curMatName = BrickValues.STRING_VALUE;
                                        }
                                        curMatName2 = Color.WHITE;
                                        speccolor = Color.WHITE;
                                        opacity = 1.0f;
                                        shininess = 0.0f;
                                    } else {
                                        float a;
                                        str = line;
                                        if (!key.equals("kd")) {
                                            if (!key.equals("ks")) {
                                                if (!key.equals("tr")) {
                                                    if (!key.equals("d")) {
                                                        if (key.equals("ns")) {
                                                            shininess = Float.parseFloat(tokens[1]);
                                                        } else if (key.equals("map_kd")) {
                                                            texFilename = file.parent().child(tokens[1]).path();
                                                        }
                                                    }
                                                }
                                                opacity = Float.parseFloat(tokens[1]);
                                            }
                                        }
                                        float r = Float.parseFloat(tokens[1]);
                                        float g = Float.parseFloat(tokens[2]);
                                        float b = Float.parseFloat(tokens[3]);
                                        if (tokens.length > 4) {
                                            a = Float.parseFloat(tokens[4]);
                                        } else {
                                            a = 1.0f;
                                        }
                                        String[] strArr = tokens;
                                        if (tokens[0].toLowerCase().equals("kd")) {
                                            curMatName2 = new Color();
                                            curMatName2.set(r, g, b, a);
                                        } else {
                                            speccolor = new Color();
                                            speccolor.set(r, g, b, a);
                                        }
                                        line = str;
                                    }
                                    line = str;
                                }
                            }
                        } catch (IOException e3) {
                            str = line;
                            color = speccolor;
                            String speccolor2 = curMatName2;
                            curMatName2 = curMatName;
                            IOException curMatName3 = e3;
                        }
                    } catch (IOException e4) {
                        e3 = e4;
                        str = line;
                    }
                }
                reader.close();
                ModelMaterial mat2 = new ModelMaterial();
                mat2.id = curMatName;
                mat2.diffuse = new Color(curMatName2);
                mat2.specular = new Color(speccolor);
                mat2.opacity = opacity;
                mat2.shininess = shininess;
                if (texFilename != null) {
                    ModelTexture tex2 = new ModelTexture();
                    tex2.usage = 2;
                    tex2.fileName = new String(texFilename);
                    if (mat2.textures == null) {
                        mat2.textures = new Array(1);
                    }
                    mat2.textures.add(tex2);
                }
                mtlLoader.materials.add(mat2);
                return;
            }
        }
        return;
        speccolor2 = curMatName2;
        curMatName2 = curMatName;
        curMatName3 = e3;
    }

    public ModelMaterial getMaterial(String name) {
        Iterator i$ = this.materials.iterator();
        while (i$.hasNext()) {
            ModelMaterial m = (ModelMaterial) i$.next();
            if (m.id.equals(name)) {
                return m;
            }
        }
        ModelMaterial mat = new ModelMaterial();
        mat.id = name;
        mat.diffuse = new Color(Color.WHITE);
        this.materials.add(mat);
        return mat;
    }
}
