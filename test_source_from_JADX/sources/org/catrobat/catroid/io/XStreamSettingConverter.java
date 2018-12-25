package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.catrobat.catroid.content.Setting;

public class XStreamSettingConverter extends ReflectionConverter {
    private static final String BRICKS_PACKAGE_NAME = "org.catrobat.catroid.content";
    private static final String TAG = XStreamSettingConverter.class.getSimpleName();
    private static final String TYPE = "type";

    public XStreamSettingConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public boolean canConvert(Class type) {
        return Setting.class.isAssignableFrom(type);
    }

    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("type", source.getClass().getSimpleName());
        super.doMarshal(source, writer, context);
    }

    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        try {
            String type = reader.getAttribute("type");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("org.catrobat.catroid.content.");
            stringBuilder.append(type);
            return super.doUnmarshal((Setting) this.reflectionProvider.newInstance(Class.forName(stringBuilder.toString())), reader, context);
        } catch (ClassNotFoundException exception) {
            String str = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Setting class not found : ");
            stringBuilder2.append(result.toString());
            Log.e(str, stringBuilder2.toString(), exception);
            return super.doUnmarshal(result, reader, context);
        }
    }
}
