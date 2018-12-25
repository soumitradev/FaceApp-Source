package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.math.Rectangle;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.catrobat.catroid.common.Constants;

public class PixmapPackerIO {

    public enum ImageFormat {
        CIM(".cim"),
        PNG(Constants.DEFAULT_IMAGE_EXTENSION);
        
        private final String extension;

        public String getExtension() {
            return this.extension;
        }

        private ImageFormat(String extension) {
            this.extension = extension;
        }
    }

    public static class SaveParameters {
        public ImageFormat format = ImageFormat.PNG;
        public Texture$TextureFilter magFilter = Texture$TextureFilter.Nearest;
        public Texture$TextureFilter minFilter = Texture$TextureFilter.Nearest;
    }

    public void save(FileHandle file, PixmapPacker packer) throws IOException {
        save(file, packer, new SaveParameters());
    }

    public void save(FileHandle file, PixmapPacker packer, SaveParameters parameters) throws IOException {
        Writer writer = file.writer(null);
        int index = 0;
        Iterator i$ = packer.pages.iterator();
        while (i$.hasNext()) {
            Page page = (Page) i$.next();
            if (page.rects.size > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(file.nameWithoutExtension());
                stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                index++;
                stringBuilder.append(index);
                stringBuilder.append(parameters.format.getExtension());
                FileHandle pageFile = file.sibling(stringBuilder.toString());
                switch (parameters.format) {
                    case CIM:
                        PixmapIO.writeCIM(pageFile, page.image);
                        break;
                    case PNG:
                        PixmapIO.writePNG(pageFile, page.image);
                        break;
                    default:
                        break;
                }
                writer.write("\n");
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(pageFile.name());
                stringBuilder2.append("\n");
                writer.write(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("size: ");
                stringBuilder2.append(page.image.getWidth());
                stringBuilder2.append(",");
                stringBuilder2.append(page.image.getHeight());
                stringBuilder2.append("\n");
                writer.write(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("format: ");
                stringBuilder2.append(packer.pageFormat.name());
                stringBuilder2.append("\n");
                writer.write(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("filter: ");
                stringBuilder2.append(parameters.minFilter.name());
                stringBuilder2.append(",");
                stringBuilder2.append(parameters.magFilter.name());
                stringBuilder2.append("\n");
                writer.write(stringBuilder2.toString());
                writer.write("repeat: none\n");
                Iterator i$2 = page.rects.keys().iterator();
                while (i$2.hasNext()) {
                    String name = (String) i$2.next();
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(name);
                    stringBuilder3.append("\n");
                    writer.write(stringBuilder3.toString());
                    Rectangle rect = (Rectangle) page.rects.get(name);
                    writer.write("rotate: false\n");
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("xy: ");
                    stringBuilder4.append((int) rect.f12x);
                    stringBuilder4.append(",");
                    stringBuilder4.append((int) rect.f13y);
                    stringBuilder4.append("\n");
                    writer.write(stringBuilder4.toString());
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("size: ");
                    stringBuilder4.append((int) rect.width);
                    stringBuilder4.append(",");
                    stringBuilder4.append((int) rect.height);
                    stringBuilder4.append("\n");
                    writer.write(stringBuilder4.toString());
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("orig: ");
                    stringBuilder4.append((int) rect.width);
                    stringBuilder4.append(",");
                    stringBuilder4.append((int) rect.height);
                    stringBuilder4.append("\n");
                    writer.write(stringBuilder4.toString());
                    writer.write("offset: 0, 0\n");
                    writer.write("index: -1\n");
                }
            }
        }
        writer.close();
    }
}
