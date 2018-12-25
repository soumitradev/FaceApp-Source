package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.utils.CrashReporter;

public class XStreamBrickConverter extends ReflectionConverter {
    private static final String[] BRICKS_PACKAGE_NAMES = new String[]{"org.catrobat.catroid.content.bricks", "org.catrobat.catroid.physics.content.bricks"};
    private static final String TAG = XStreamBrickConverter.class.getSimpleName();
    private static final String TYPE = "type";

    public XStreamBrickConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public boolean canConvert(Class type) {
        return Brick.class.isAssignableFrom(type);
    }

    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("type", source.getClass().getSimpleName());
        super.doMarshal(source, writer, context);
    }

    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        StringBuilder stringBuilder;
        String type = reader.getAttribute("type");
        int index = 0;
        while (index < BRICKS_PACKAGE_NAMES.length) {
            try {
                stringBuilder = new StringBuilder();
                stringBuilder.append(BRICKS_PACKAGE_NAMES[index]);
                stringBuilder.append(".");
                stringBuilder.append(type);
                return super.doUnmarshal((Brick) this.reflectionProvider.newInstance(Class.forName(stringBuilder.toString())), reader, context);
            } catch (ClassNotFoundException e) {
                String str = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Brick ");
                stringBuilder2.append(type);
                stringBuilder2.append(" not found in ");
                stringBuilder2.append(BRICKS_PACKAGE_NAMES[index]);
                Log.d(str, stringBuilder2.toString());
                index++;
            }
        }
        String str2 = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Brick ");
        stringBuilder.append(type);
        stringBuilder.append(" not found in packages");
        Log.e(str2, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Brick ");
        stringBuilder.append(type);
        stringBuilder.append(" not found in packages");
        CrashReporter.logException(new RuntimeException(stringBuilder.toString()));
        return super.doUnmarshal(result, reader, context);
    }
}
