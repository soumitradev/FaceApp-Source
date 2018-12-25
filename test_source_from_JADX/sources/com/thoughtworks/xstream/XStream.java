package com.thoughtworks.xstream;

import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.parrot.arsdk.armedia.ARMediaManager;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.ConverterRegistry;
import com.thoughtworks.xstream.converters.DataHolder;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.SingleValueConverterWrapper;
import com.thoughtworks.xstream.converters.basic.BigDecimalConverter;
import com.thoughtworks.xstream.converters.basic.BigIntegerConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;
import com.thoughtworks.xstream.converters.basic.ByteConverter;
import com.thoughtworks.xstream.converters.basic.CharConverter;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;
import com.thoughtworks.xstream.converters.basic.ShortConverter;
import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
import com.thoughtworks.xstream.converters.basic.StringConverter;
import com.thoughtworks.xstream.converters.basic.URIConverter;
import com.thoughtworks.xstream.converters.basic.URLConverter;
import com.thoughtworks.xstream.converters.collections.ArrayConverter;
import com.thoughtworks.xstream.converters.collections.BitSetConverter;
import com.thoughtworks.xstream.converters.collections.CharArrayConverter;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.converters.collections.PropertiesConverter;
import com.thoughtworks.xstream.converters.collections.SingletonCollectionConverter;
import com.thoughtworks.xstream.converters.collections.SingletonMapConverter;
import com.thoughtworks.xstream.converters.collections.TreeMapConverter;
import com.thoughtworks.xstream.converters.collections.TreeSetConverter;
import com.thoughtworks.xstream.converters.extended.ColorConverter;
import com.thoughtworks.xstream.converters.extended.DynamicProxyConverter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.converters.extended.FileConverter;
import com.thoughtworks.xstream.converters.extended.FontConverter;
import com.thoughtworks.xstream.converters.extended.GregorianCalendarConverter;
import com.thoughtworks.xstream.converters.extended.JavaClassConverter;
import com.thoughtworks.xstream.converters.extended.JavaFieldConverter;
import com.thoughtworks.xstream.converters.extended.JavaMethodConverter;
import com.thoughtworks.xstream.converters.extended.LocaleConverter;
import com.thoughtworks.xstream.converters.extended.LookAndFeelConverter;
import com.thoughtworks.xstream.converters.extended.SqlDateConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimeConverter;
import com.thoughtworks.xstream.converters.extended.SqlTimestampConverter;
import com.thoughtworks.xstream.converters.extended.TextAttributeConverter;
import com.thoughtworks.xstream.converters.reflection.ExternalizableConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.SerializableConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.DefaultConverterLookup;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.MapBackedDataHolder;
import com.thoughtworks.xstream.core.ReferenceByIdMarshallingStrategy;
import com.thoughtworks.xstream.core.ReferenceByXPathMarshallingStrategy;
import com.thoughtworks.xstream.core.TreeMarshallingStrategy;
import com.thoughtworks.xstream.core.util.CompositeClassLoader;
import com.thoughtworks.xstream.core.util.CustomObjectInputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream;
import com.thoughtworks.xstream.core.util.CustomObjectOutputStream.StreamCallback;
import com.thoughtworks.xstream.core.util.SelfStreamingInstanceChecker;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StatefulWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.mapper.AnnotationConfiguration;
import com.thoughtworks.xstream.mapper.ArrayMapper;
import com.thoughtworks.xstream.mapper.AttributeAliasingMapper;
import com.thoughtworks.xstream.mapper.AttributeMapper;
import com.thoughtworks.xstream.mapper.CachingMapper;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.thoughtworks.xstream.mapper.DefaultImplementationsMapper;
import com.thoughtworks.xstream.mapper.DefaultMapper;
import com.thoughtworks.xstream.mapper.DynamicProxyMapper;
import com.thoughtworks.xstream.mapper.FieldAliasingMapper;
import com.thoughtworks.xstream.mapper.ImmutableTypesMapper;
import com.thoughtworks.xstream.mapper.ImplicitCollectionMapper;
import com.thoughtworks.xstream.mapper.LocalConversionMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.Mapper.Null;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.thoughtworks.xstream.mapper.OuterClassMapper;
import com.thoughtworks.xstream.mapper.PackageAliasingMapper;
import com.thoughtworks.xstream.mapper.SecurityMapper;
import com.thoughtworks.xstream.mapper.SystemAttributeAliasingMapper;
import com.thoughtworks.xstream.mapper.XStream11XmlFriendlyMapper;
import com.thoughtworks.xstream.security.AnyTypePermission;
import com.thoughtworks.xstream.security.ExplicitTypePermission;
import com.thoughtworks.xstream.security.NoPermission;
import com.thoughtworks.xstream.security.RegExpTypePermission;
import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import com.thoughtworks.xstream.security.TypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Pattern;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

public class XStream {
    private static final String ANNOTATION_MAPPER_TYPE = "com.thoughtworks.xstream.mapper.AnnotationMapper";
    public static final int ID_REFERENCES = 1002;
    private static final Pattern IGNORE_ALL = Pattern.compile(".*");
    public static final int NO_REFERENCES = 1001;
    public static final int PRIORITY_LOW = -10;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_VERY_HIGH = 10000;
    public static final int PRIORITY_VERY_LOW = -20;
    public static final int SINGLE_NODE_XPATH_ABSOLUTE_REFERENCES = 1006;
    public static final int SINGLE_NODE_XPATH_RELATIVE_REFERENCES = 1005;
    public static final int XPATH_ABSOLUTE_REFERENCES = 1004;
    public static final int XPATH_RELATIVE_REFERENCES = 1003;
    private AnnotationConfiguration annotationConfiguration;
    private AttributeAliasingMapper attributeAliasingMapper;
    private AttributeMapper attributeMapper;
    private ClassAliasingMapper classAliasingMapper;
    private ClassLoaderReference classLoaderReference;
    private ConverterLookup converterLookup;
    private ConverterRegistry converterRegistry;
    private DefaultImplementationsMapper defaultImplementationsMapper;
    private FieldAliasingMapper fieldAliasingMapper;
    private HierarchicalStreamDriver hierarchicalStreamDriver;
    private ImmutableTypesMapper immutableTypesMapper;
    private ImplicitCollectionMapper implicitCollectionMapper;
    private LocalConversionMapper localConversionMapper;
    private Mapper mapper;
    private MarshallingStrategy marshallingStrategy;
    private PackageAliasingMapper packageAliasingMapper;
    private ReflectionProvider reflectionProvider;
    private SecurityMapper securityMapper;
    private SystemAttributeAliasingMapper systemAttributeAliasingMapper;

    /* renamed from: com.thoughtworks.xstream.XStream$1 */
    class C20521 implements ConverterLookup {
        final /* synthetic */ DefaultConverterLookup val$defaultConverterLookup;

        C20521(DefaultConverterLookup defaultConverterLookup) {
            this.val$defaultConverterLookup = defaultConverterLookup;
        }

        public Converter lookupConverterForType(Class type) {
            return this.val$defaultConverterLookup.lookupConverterForType(type);
        }
    }

    /* renamed from: com.thoughtworks.xstream.XStream$2 */
    class C20532 implements ConverterRegistry {
        final /* synthetic */ DefaultConverterLookup val$defaultConverterLookup;

        C20532(DefaultConverterLookup defaultConverterLookup) {
            this.val$defaultConverterLookup = defaultConverterLookup;
        }

        public void registerConverter(Converter converter, int priority) {
            this.val$defaultConverterLookup.registerConverter(converter, priority);
        }
    }

    public static class InitializationException extends XStreamException {
        public InitializationException(String message, Throwable cause) {
            super(message, cause);
        }

        public InitializationException(String message) {
            super(message);
        }
    }

    public XStream() {
        this(null, (Mapper) null, new XppDriver());
    }

    public XStream(ReflectionProvider reflectionProvider) {
        this(reflectionProvider, (Mapper) null, new XppDriver());
    }

    public XStream(HierarchicalStreamDriver hierarchicalStreamDriver) {
        this(null, (Mapper) null, hierarchicalStreamDriver);
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver hierarchicalStreamDriver) {
        this(reflectionProvider, (Mapper) null, hierarchicalStreamDriver);
    }

    public XStream(ReflectionProvider reflectionProvider, Mapper mapper, HierarchicalStreamDriver driver) {
        this(reflectionProvider, driver, new CompositeClassLoader(), mapper);
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoaderReference classLoaderReference) {
        this(reflectionProvider, driver, classLoaderReference, null);
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader) {
        this(reflectionProvider, driver, classLoader, null);
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader, Mapper mapper) {
        this(reflectionProvider, driver, new ClassLoaderReference(classLoader), mapper, new DefaultConverterLookup());
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoaderReference classLoaderReference, Mapper mapper) {
        this(reflectionProvider, driver, classLoaderReference, mapper, new DefaultConverterLookup());
    }

    private XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoaderReference classLoader, Mapper mapper, DefaultConverterLookup defaultConverterLookup) {
        this(reflectionProvider, driver, classLoader, mapper, new C20521(defaultConverterLookup), new C20532(defaultConverterLookup));
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoader classLoader, Mapper mapper, ConverterLookup converterLookup, ConverterRegistry converterRegistry) {
        this(reflectionProvider, driver, new ClassLoaderReference(classLoader), mapper, converterLookup, converterRegistry);
    }

    public XStream(ReflectionProvider reflectionProvider, HierarchicalStreamDriver driver, ClassLoaderReference classLoaderReference, Mapper mapper, ConverterLookup converterLookup, ConverterRegistry converterRegistry) {
        if (reflectionProvider == null) {
            reflectionProvider = JVM.newReflectionProvider();
        }
        this.reflectionProvider = reflectionProvider;
        this.hierarchicalStreamDriver = driver;
        this.classLoaderReference = classLoaderReference;
        this.converterLookup = converterLookup;
        this.converterRegistry = converterRegistry;
        this.mapper = mapper == null ? buildMapper() : mapper;
        setupMappers();
        setupSecurity();
        setupAliases();
        setupDefaultImplementations();
        setupConverters();
        setupImmutableTypes();
        setMode(1003);
    }

    private Mapper buildMapper() {
        Mapper mapper = new DefaultMapper(this.classLoaderReference);
        if (useXStream11XmlFriendlyMapper()) {
            mapper = new XStream11XmlFriendlyMapper(mapper);
        }
        mapper = new AttributeMapper(new DefaultImplementationsMapper(new ArrayMapper(new OuterClassMapper(new ImplicitCollectionMapper(new SystemAttributeAliasingMapper(new AttributeAliasingMapper(new FieldAliasingMapper(new ClassAliasingMapper(new PackageAliasingMapper(new DynamicProxyMapper(mapper)))))))))), this.converterLookup, this.reflectionProvider);
        if (JVM.is15()) {
            mapper = buildMapperDynamically("com.thoughtworks.xstream.mapper.EnumMapper", new Class[]{Mapper.class}, new Object[]{mapper});
        }
        mapper = new SecurityMapper(new ImmutableTypesMapper(new LocalConversionMapper(mapper)));
        if (JVM.is15()) {
            mapper = buildMapperDynamically(ANNOTATION_MAPPER_TYPE, new Class[]{Mapper.class, ConverterRegistry.class, ConverterLookup.class, ClassLoaderReference.class, ReflectionProvider.class}, new Object[]{mapper, this.converterRegistry, this.converterLookup, this.classLoaderReference, this.reflectionProvider});
        }
        return new CachingMapper(wrapMapper((MapperWrapper) mapper));
    }

    private Mapper buildMapperDynamically(String className, Class[] constructorParamTypes, Object[] constructorParamValues) {
        try {
            return (Mapper) Class.forName(className, null, this.classLoaderReference.getReference()).getConstructor(constructorParamTypes).newInstance(constructorParamValues);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate mapper : ");
            stringBuilder.append(className);
            throw new InitializationException(stringBuilder.toString(), e);
        }
    }

    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return next;
    }

    protected boolean useXStream11XmlFriendlyMapper() {
        return false;
    }

    private void setupMappers() {
        this.packageAliasingMapper = (PackageAliasingMapper) this.mapper.lookupMapperOfType(PackageAliasingMapper.class);
        this.classAliasingMapper = (ClassAliasingMapper) this.mapper.lookupMapperOfType(ClassAliasingMapper.class);
        this.fieldAliasingMapper = (FieldAliasingMapper) this.mapper.lookupMapperOfType(FieldAliasingMapper.class);
        this.attributeMapper = (AttributeMapper) this.mapper.lookupMapperOfType(AttributeMapper.class);
        this.attributeAliasingMapper = (AttributeAliasingMapper) this.mapper.lookupMapperOfType(AttributeAliasingMapper.class);
        this.systemAttributeAliasingMapper = (SystemAttributeAliasingMapper) this.mapper.lookupMapperOfType(SystemAttributeAliasingMapper.class);
        this.implicitCollectionMapper = (ImplicitCollectionMapper) this.mapper.lookupMapperOfType(ImplicitCollectionMapper.class);
        this.defaultImplementationsMapper = (DefaultImplementationsMapper) this.mapper.lookupMapperOfType(DefaultImplementationsMapper.class);
        this.immutableTypesMapper = (ImmutableTypesMapper) this.mapper.lookupMapperOfType(ImmutableTypesMapper.class);
        this.localConversionMapper = (LocalConversionMapper) this.mapper.lookupMapperOfType(LocalConversionMapper.class);
        this.securityMapper = (SecurityMapper) this.mapper.lookupMapperOfType(SecurityMapper.class);
        this.annotationConfiguration = (AnnotationConfiguration) this.mapper.lookupMapperOfType(AnnotationConfiguration.class);
    }

    protected void setupSecurity() {
        if (this.securityMapper != null) {
            addPermission(AnyTypePermission.ANY);
        }
    }

    protected void setupAliases() {
        if (this.classAliasingMapper != null) {
            alias("null", Null.class);
            alias("int", Integer.class);
            alias("float", Float.class);
            alias("double", Double.class);
            alias("long", Long.class);
            alias("short", Short.class);
            alias("char", Character.class);
            alias("byte", Byte.class);
            alias("boolean", Boolean.class);
            alias("number", Number.class);
            alias("object", Object.class);
            alias("big-int", BigInteger.class);
            alias("big-decimal", BigDecimal.class);
            alias("string-buffer", StringBuffer.class);
            alias("string", String.class);
            alias("java-class", Class.class);
            alias(FirebaseAnalytics$Param.METHOD, Method.class);
            alias("constructor", Constructor.class);
            alias("field", Field.class);
            alias("date", Date.class);
            alias(ShareConstants.MEDIA_URI, URI.class);
            alias("url", URL.class);
            alias("bit-set", BitSet.class);
            alias("map", Map.class);
            alias("entry", Entry.class);
            alias("properties", Properties.class);
            alias("list", List.class);
            alias("set", Set.class);
            alias("sorted-set", SortedSet.class);
            alias("linked-list", LinkedList.class);
            alias("vector", Vector.class);
            alias("tree-map", TreeMap.class);
            alias("tree-set", TreeSet.class);
            alias("hashtable", Hashtable.class);
            alias("empty-list", Collections.EMPTY_LIST.getClass());
            alias("empty-map", Collections.EMPTY_MAP.getClass());
            alias("empty-set", Collections.EMPTY_SET.getClass());
            alias("singleton-list", Collections.singletonList(this).getClass());
            alias("singleton-map", Collections.singletonMap(this, null).getClass());
            alias("singleton-set", Collections.singleton(this).getClass());
            if (JVM.isAWTAvailable()) {
                alias("awt-color", JVM.loadClassForName("java.awt.Color", false));
                alias("awt-font", JVM.loadClassForName("java.awt.Font", false));
                alias("awt-text-attribute", JVM.loadClassForName("java.awt.font.TextAttribute"));
            }
            if (JVM.isSQLAvailable()) {
                alias("sql-timestamp", JVM.loadClassForName("java.sql.Timestamp"));
                alias("sql-time", JVM.loadClassForName("java.sql.Time"));
                alias("sql-date", JVM.loadClassForName("java.sql.Date"));
            }
            alias(PocketMusicActivity.ABSOLUTE_FILE_PATH, File.class);
            alias(Constants.LOCALE, Locale.class);
            alias("gregorian-calendar", Calendar.class);
            if (JVM.is14()) {
                aliasDynamically("auth-subject", "javax.security.auth.Subject");
                alias("linked-hash-map", JVM.loadClassForName("java.util.LinkedHashMap"));
                alias("linked-hash-set", JVM.loadClassForName("java.util.LinkedHashSet"));
                alias("trace", JVM.loadClassForName("java.lang.StackTraceElement"));
                alias(FirebaseAnalytics$Param.CURRENCY, JVM.loadClassForName("java.util.Currency"));
                aliasType(HttpRequest.PARAM_CHARSET, JVM.loadClassForName("java.nio.charset.Charset"));
            }
            if (JVM.is15()) {
                aliasDynamically("duration", "javax.xml.datatype.Duration");
                alias("concurrent-hash-map", JVM.loadClassForName("java.util.concurrent.ConcurrentHashMap"));
                alias("enum-set", JVM.loadClassForName("java.util.EnumSet"));
                alias("enum-map", JVM.loadClassForName("java.util.EnumMap"));
                alias("string-builder", JVM.loadClassForName("java.lang.StringBuilder"));
                alias(ARMediaManager.ARMediaManagerPVATuuidKey, JVM.loadClassForName("java.util.UUID"));
            }
        }
    }

    private void aliasDynamically(String alias, String className) {
        Class type = JVM.loadClassForName(className);
        if (type != null) {
            alias(alias, type);
        }
    }

    protected void setupDefaultImplementations() {
        if (this.defaultImplementationsMapper != null) {
            addDefaultImplementation(HashMap.class, Map.class);
            addDefaultImplementation(ArrayList.class, List.class);
            addDefaultImplementation(HashSet.class, Set.class);
            addDefaultImplementation(TreeSet.class, SortedSet.class);
            addDefaultImplementation(GregorianCalendar.class, Calendar.class);
        }
    }

    protected void setupConverters() {
        registerConverter(new ReflectionConverter(this.mapper, this.reflectionProvider), -20);
        registerConverter(new SerializableConverter(this.mapper, this.reflectionProvider, this.classLoaderReference), -10);
        registerConverter(new ExternalizableConverter(this.mapper, this.classLoaderReference), -10);
        registerConverter(new NullConverter(), 10000);
        registerConverter(new IntConverter(), 0);
        registerConverter(new FloatConverter(), 0);
        registerConverter(new DoubleConverter(), 0);
        registerConverter(new LongConverter(), 0);
        registerConverter(new ShortConverter(), 0);
        registerConverter(new CharConverter(), 0);
        registerConverter(new BooleanConverter(), 0);
        registerConverter(new ByteConverter(), 0);
        registerConverter(new StringConverter(), 0);
        registerConverter(new StringBufferConverter(), 0);
        registerConverter(new DateConverter(), 0);
        registerConverter(new BitSetConverter(), 0);
        registerConverter(new URIConverter(), 0);
        registerConverter(new URLConverter(), 0);
        registerConverter(new BigIntegerConverter(), 0);
        registerConverter(new BigDecimalConverter(), 0);
        registerConverter(new ArrayConverter(this.mapper), 0);
        registerConverter(new CharArrayConverter(), 0);
        registerConverter(new CollectionConverter(this.mapper), 0);
        registerConverter(new MapConverter(this.mapper), 0);
        registerConverter(new TreeMapConverter(this.mapper), 0);
        registerConverter(new TreeSetConverter(this.mapper), 0);
        registerConverter(new SingletonCollectionConverter(this.mapper), 0);
        registerConverter(new SingletonMapConverter(this.mapper), 0);
        registerConverter(new PropertiesConverter(), 0);
        registerConverter(new EncodedByteArrayConverter(), 0);
        registerConverter(new FileConverter(), 0);
        if (JVM.isSQLAvailable()) {
            registerConverter(new SqlTimestampConverter(), 0);
            registerConverter(new SqlTimeConverter(), 0);
            registerConverter(new SqlDateConverter(), 0);
        }
        registerConverter(new DynamicProxyConverter(this.mapper, this.classLoaderReference), 0);
        registerConverter(new JavaClassConverter(this.classLoaderReference), 0);
        registerConverter(new JavaMethodConverter(this.classLoaderReference), 0);
        registerConverter(new JavaFieldConverter(this.classLoaderReference), 0);
        if (JVM.isAWTAvailable()) {
            registerConverter(new FontConverter(this.mapper), 0);
            registerConverter(new ColorConverter(), 0);
            registerConverter(new TextAttributeConverter(), 0);
        }
        if (JVM.isSwingAvailable()) {
            registerConverter(new LookAndFeelConverter(this.mapper, this.reflectionProvider), 0);
        }
        registerConverter(new LocaleConverter(), 0);
        registerConverter(new GregorianCalendarConverter(), 0);
        if (JVM.is14()) {
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.SubjectConverter", 0, new Class[]{Mapper.class}, new Object[]{this.mapper});
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.ThrowableConverter", 0, new Class[]{ConverterLookup.class}, new Object[]{this.converterLookup});
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.StackTraceElementConverter", 0, null, null);
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.CurrencyConverter", 0, null, null);
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.RegexPatternConverter", 0, null, null);
            registerConverterDynamically("com.thoughtworks.xstream.converters.extended.CharsetConverter", 0, null, null);
        }
        if (JVM.is15()) {
            if (JVM.loadClassForName("javax.xml.datatype.Duration") != null) {
                registerConverterDynamically("com.thoughtworks.xstream.converters.extended.DurationConverter", 0, null, null);
            }
            registerConverterDynamically("com.thoughtworks.xstream.converters.enums.EnumConverter", 0, null, null);
            registerConverterDynamically("com.thoughtworks.xstream.converters.enums.EnumSetConverter", 0, new Class[]{Mapper.class}, new Object[]{this.mapper});
            registerConverterDynamically("com.thoughtworks.xstream.converters.enums.EnumMapConverter", 0, new Class[]{Mapper.class}, new Object[]{this.mapper});
            registerConverterDynamically("com.thoughtworks.xstream.converters.basic.StringBuilderConverter", 0, null, null);
            registerConverterDynamically("com.thoughtworks.xstream.converters.basic.UUIDConverter", 0, null, null);
        }
        registerConverter(new SelfStreamingInstanceChecker(this.converterLookup, (Object) this), 0);
    }

    private void registerConverterDynamically(String className, int priority, Class[] constructorParamTypes, Object[] constructorParamValues) {
        try {
            Object instance = Class.forName(className, null, this.classLoaderReference.getReference()).getConstructor(constructorParamTypes).newInstance(constructorParamValues);
            if (instance instanceof Converter) {
                registerConverter((Converter) instance, priority);
            } else if (instance instanceof SingleValueConverter) {
                registerConverter((SingleValueConverter) instance, priority);
            }
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not instantiate converter : ");
            stringBuilder.append(className);
            throw new InitializationException(stringBuilder.toString(), e);
        }
    }

    protected void setupImmutableTypes() {
        if (this.immutableTypesMapper != null) {
            addImmutableType(Boolean.TYPE);
            addImmutableType(Boolean.class);
            addImmutableType(Byte.TYPE);
            addImmutableType(Byte.class);
            addImmutableType(Character.TYPE);
            addImmutableType(Character.class);
            addImmutableType(Double.TYPE);
            addImmutableType(Double.class);
            addImmutableType(Float.TYPE);
            addImmutableType(Float.class);
            addImmutableType(Integer.TYPE);
            addImmutableType(Integer.class);
            addImmutableType(Long.TYPE);
            addImmutableType(Long.class);
            addImmutableType(Short.TYPE);
            addImmutableType(Short.class);
            addImmutableType(Null.class);
            addImmutableType(BigDecimal.class);
            addImmutableType(BigInteger.class);
            addImmutableType(String.class);
            addImmutableType(URI.class);
            addImmutableType(URL.class);
            addImmutableType(File.class);
            addImmutableType(Class.class);
            addImmutableType(Collections.EMPTY_LIST.getClass());
            addImmutableType(Collections.EMPTY_SET.getClass());
            addImmutableType(Collections.EMPTY_MAP.getClass());
            if (JVM.isAWTAvailable()) {
                addImmutableTypeDynamically("java.awt.font.TextAttribute");
            }
            if (JVM.is14()) {
                addImmutableTypeDynamically("java.nio.charset.Charset");
                addImmutableTypeDynamically("java.util.Currency");
            }
        }
    }

    private void addImmutableTypeDynamically(String className) {
        Class type = JVM.loadClassForName(className);
        if (type != null) {
            addImmutableType(type);
        }
    }

    public void setMarshallingStrategy(MarshallingStrategy marshallingStrategy) {
        this.marshallingStrategy = marshallingStrategy;
    }

    public String toXML(Object obj) {
        Writer writer = new StringWriter();
        toXML(obj, writer);
        return writer.toString();
    }

    public void toXML(Object obj, Writer out) {
        HierarchicalStreamWriter writer = this.hierarchicalStreamDriver.createWriter(out);
        try {
            marshal(obj, writer);
        } finally {
            writer.flush();
        }
    }

    public void toXML(Object obj, OutputStream out) {
        HierarchicalStreamWriter writer = this.hierarchicalStreamDriver.createWriter(out);
        try {
            marshal(obj, writer);
        } finally {
            writer.flush();
        }
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer) {
        marshal(obj, writer, null);
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer, DataHolder dataHolder) {
        this.marshallingStrategy.marshal(writer, obj, this.converterLookup, this.mapper, dataHolder);
    }

    public Object fromXML(String xml) {
        return fromXML(new StringReader(xml));
    }

    public Object fromXML(Reader reader) {
        return unmarshal(this.hierarchicalStreamDriver.createReader(reader), null);
    }

    public Object fromXML(InputStream input) {
        return unmarshal(this.hierarchicalStreamDriver.createReader(input), null);
    }

    public Object fromXML(URL url) {
        return fromXML(url, null);
    }

    public Object fromXML(File file) {
        return fromXML(file, null);
    }

    public Object fromXML(String xml, Object root) {
        return fromXML(new StringReader(xml), root);
    }

    public Object fromXML(Reader xml, Object root) {
        return unmarshal(this.hierarchicalStreamDriver.createReader(xml), root);
    }

    public Object fromXML(URL url, Object root) {
        return unmarshal(this.hierarchicalStreamDriver.createReader(url), root);
    }

    public Object fromXML(File file, Object root) {
        HierarchicalStreamReader reader = this.hierarchicalStreamDriver.createReader(file);
        try {
            Object unmarshal = unmarshal(reader, root);
            return unmarshal;
        } finally {
            reader.close();
        }
    }

    public Object fromXML(InputStream input, Object root) {
        return unmarshal(this.hierarchicalStreamDriver.createReader(input), root);
    }

    public Object unmarshal(HierarchicalStreamReader reader) {
        return unmarshal(reader, null, null);
    }

    public Object unmarshal(HierarchicalStreamReader reader, Object root) {
        return unmarshal(reader, root, null);
    }

    public Object unmarshal(HierarchicalStreamReader reader, Object root, DataHolder dataHolder) {
        try {
            return this.marshallingStrategy.unmarshal(root, reader, dataHolder, this.converterLookup, this.mapper);
        } catch (ConversionException e) {
            Package pkg = getClass().getPackage();
            String version = pkg != null ? pkg.getImplementationVersion() : null;
            e.add(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, version != null ? version : "not available");
            throw e;
        }
    }

    public void alias(String name, Class type) {
        if (this.classAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ClassAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.classAliasingMapper.addClassAlias(name, type);
    }

    public void aliasType(String name, Class type) {
        if (this.classAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ClassAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.classAliasingMapper.addTypeAlias(name, type);
    }

    public void alias(String name, Class type, Class defaultImplementation) {
        alias(name, type);
        addDefaultImplementation(defaultImplementation, type);
    }

    public void aliasPackage(String name, String pkgName) {
        if (this.packageAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(PackageAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.packageAliasingMapper.addPackageAlias(name, pkgName);
    }

    public void aliasField(String alias, Class definedIn, String fieldName) {
        if (this.fieldAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(FieldAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.fieldAliasingMapper.addFieldAlias(alias, definedIn, fieldName);
    }

    public void aliasAttribute(String alias, String attributeName) {
        if (this.attributeAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(AttributeAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.attributeAliasingMapper.addAliasFor(attributeName, alias);
    }

    public void aliasSystemAttribute(String alias, String systemAttributeName) {
        if (this.systemAttributeAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(SystemAttributeAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.systemAttributeAliasingMapper.addAliasFor(systemAttributeName, alias);
    }

    public void aliasAttribute(Class definedIn, String attributeName, String alias) {
        aliasField(alias, definedIn, attributeName);
        useAttributeFor(definedIn, attributeName);
    }

    public void useAttributeFor(String fieldName, Class type) {
        if (this.attributeMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(AttributeMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.attributeMapper.addAttributeFor(fieldName, type);
    }

    public void useAttributeFor(Class definedIn, String fieldName) {
        if (this.attributeMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(AttributeMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.attributeMapper.addAttributeFor(definedIn, fieldName);
    }

    public void useAttributeFor(Class type) {
        if (this.attributeMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(AttributeMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.attributeMapper.addAttributeFor(type);
    }

    public void addDefaultImplementation(Class defaultImplementation, Class ofType) {
        if (this.defaultImplementationsMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(DefaultImplementationsMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.defaultImplementationsMapper.addDefaultImplementation(defaultImplementation, ofType);
    }

    public void addImmutableType(Class type) {
        if (this.immutableTypesMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ImmutableTypesMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.immutableTypesMapper.addImmutableType(type);
    }

    public void registerConverter(Converter converter) {
        registerConverter(converter, 0);
    }

    public void registerConverter(Converter converter, int priority) {
        if (this.converterRegistry != null) {
            this.converterRegistry.registerConverter(converter, priority);
        }
    }

    public void registerConverter(SingleValueConverter converter) {
        registerConverter(converter, 0);
    }

    public void registerConverter(SingleValueConverter converter, int priority) {
        if (this.converterRegistry != null) {
            this.converterRegistry.registerConverter(new SingleValueConverterWrapper(converter), priority);
        }
    }

    public void registerLocalConverter(Class definedIn, String fieldName, Converter converter) {
        if (this.localConversionMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(LocalConversionMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.localConversionMapper.registerLocalConverter(definedIn, fieldName, converter);
    }

    public void registerLocalConverter(Class definedIn, String fieldName, SingleValueConverter converter) {
        registerLocalConverter(definedIn, fieldName, new SingleValueConverterWrapper(converter));
    }

    public Mapper getMapper() {
        return this.mapper;
    }

    public ReflectionProvider getReflectionProvider() {
        return this.reflectionProvider;
    }

    public ConverterLookup getConverterLookup() {
        return this.converterLookup;
    }

    public void setMode(int mode) {
        switch (mode) {
            case 1001:
                setMarshallingStrategy(new TreeMarshallingStrategy());
                return;
            case 1002:
                setMarshallingStrategy(new ReferenceByIdMarshallingStrategy());
                return;
            case 1003:
                setMarshallingStrategy(new ReferenceByXPathMarshallingStrategy(ReferenceByXPathMarshallingStrategy.RELATIVE));
                return;
            case 1004:
                setMarshallingStrategy(new ReferenceByXPathMarshallingStrategy(ReferenceByXPathMarshallingStrategy.ABSOLUTE));
                return;
            case SINGLE_NODE_XPATH_RELATIVE_REFERENCES /*1005*/:
                setMarshallingStrategy(new ReferenceByXPathMarshallingStrategy(ReferenceByXPathMarshallingStrategy.RELATIVE | ReferenceByXPathMarshallingStrategy.SINGLE_NODE));
                return;
            case 1006:
                setMarshallingStrategy(new ReferenceByXPathMarshallingStrategy(ReferenceByXPathMarshallingStrategy.ABSOLUTE | ReferenceByXPathMarshallingStrategy.SINGLE_NODE));
                return;
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown mode : ");
                stringBuilder.append(mode);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void addImplicitCollection(Class ownerType, String fieldName) {
        addImplicitCollection(ownerType, fieldName, null, null);
    }

    public void addImplicitCollection(Class ownerType, String fieldName, Class itemType) {
        addImplicitCollection(ownerType, fieldName, null, itemType);
    }

    public void addImplicitCollection(Class ownerType, String fieldName, String itemFieldName, Class itemType) {
        addImplicitMap(ownerType, fieldName, itemFieldName, itemType, null);
    }

    public void addImplicitArray(Class ownerType, String fieldName) {
        addImplicitCollection(ownerType, fieldName);
    }

    public void addImplicitArray(Class ownerType, String fieldName, Class itemType) {
        addImplicitCollection(ownerType, fieldName, itemType);
    }

    public void addImplicitArray(Class ownerType, String fieldName, String itemName) {
        addImplicitCollection(ownerType, fieldName, itemName, null);
    }

    public void addImplicitMap(Class ownerType, String fieldName, Class itemType, String keyFieldName) {
        addImplicitMap(ownerType, fieldName, null, itemType, keyFieldName);
    }

    public void addImplicitMap(Class ownerType, String fieldName, String itemName, Class itemType, String keyFieldName) {
        if (this.implicitCollectionMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(ImplicitCollectionMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.implicitCollectionMapper.add(ownerType, fieldName, itemName, itemType, keyFieldName);
    }

    public DataHolder newDataHolder() {
        return new MapBackedDataHolder();
    }

    public ObjectOutputStream createObjectOutputStream(Writer writer) throws IOException {
        return createObjectOutputStream(this.hierarchicalStreamDriver.createWriter(writer), "object-stream");
    }

    public ObjectOutputStream createObjectOutputStream(HierarchicalStreamWriter writer) throws IOException {
        return createObjectOutputStream(writer, "object-stream");
    }

    public ObjectOutputStream createObjectOutputStream(Writer writer, String rootNodeName) throws IOException {
        return createObjectOutputStream(this.hierarchicalStreamDriver.createWriter(writer), rootNodeName);
    }

    public ObjectOutputStream createObjectOutputStream(OutputStream out) throws IOException {
        return createObjectOutputStream(this.hierarchicalStreamDriver.createWriter(out), "object-stream");
    }

    public ObjectOutputStream createObjectOutputStream(OutputStream out, String rootNodeName) throws IOException {
        return createObjectOutputStream(this.hierarchicalStreamDriver.createWriter(out), rootNodeName);
    }

    public ObjectOutputStream createObjectOutputStream(HierarchicalStreamWriter writer, String rootNodeName) throws IOException {
        final StatefulWriter statefulWriter = new StatefulWriter(writer);
        statefulWriter.startNode(rootNodeName, null);
        return new CustomObjectOutputStream(new StreamCallback() {
            public void writeToStream(Object object) {
                XStream.this.marshal(object, statefulWriter);
            }

            public void writeFieldsToStream(Map fields) throws NotActiveException {
                throw new NotActiveException("not in call to writeObject");
            }

            public void defaultWriteObject() throws NotActiveException {
                throw new NotActiveException("not in call to writeObject");
            }

            public void flush() {
                statefulWriter.flush();
            }

            public void close() {
                if (statefulWriter.state() != StatefulWriter.STATE_CLOSED) {
                    statefulWriter.endNode();
                    statefulWriter.close();
                }
            }
        });
    }

    public ObjectInputStream createObjectInputStream(Reader xmlReader) throws IOException {
        return createObjectInputStream(this.hierarchicalStreamDriver.createReader(xmlReader));
    }

    public ObjectInputStream createObjectInputStream(InputStream in) throws IOException {
        return createObjectInputStream(this.hierarchicalStreamDriver.createReader(in));
    }

    public ObjectInputStream createObjectInputStream(final HierarchicalStreamReader reader) throws IOException {
        return new CustomObjectInputStream(new CustomObjectInputStream.StreamCallback() {
            public Object readFromStream() throws EOFException {
                if (reader.hasMoreChildren()) {
                    reader.moveDown();
                    Object result = XStream.this.unmarshal(reader);
                    reader.moveUp();
                    return result;
                }
                throw new EOFException();
            }

            public Map readFieldsFromStream() throws IOException {
                throw new NotActiveException("not in call to readObject");
            }

            public void defaultReadObject() throws NotActiveException {
                throw new NotActiveException("not in call to readObject");
            }

            public void registerValidation(ObjectInputValidation validation, int priority) throws NotActiveException {
                throw new NotActiveException("stream inactive");
            }

            public void close() {
                reader.close();
            }
        }, this.classLoaderReference);
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoaderReference.setReference(classLoader);
    }

    public ClassLoader getClassLoader() {
        return this.classLoaderReference.getReference();
    }

    public ClassLoaderReference getClassLoaderReference() {
        return this.classLoaderReference;
    }

    public void omitField(Class definedIn, String fieldName) {
        if (this.fieldAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(FieldAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.fieldAliasingMapper.omitField(definedIn, fieldName);
    }

    public void ignoreUnknownElements() {
        ignoreUnknownElements(IGNORE_ALL);
    }

    public void ignoreUnknownElements(String pattern) {
        ignoreUnknownElements(Pattern.compile(pattern));
    }

    private void ignoreUnknownElements(Pattern pattern) {
        if (this.fieldAliasingMapper == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No ");
            stringBuilder.append(FieldAliasingMapper.class.getName());
            stringBuilder.append(" available");
            throw new InitializationException(stringBuilder.toString());
        }
        this.fieldAliasingMapper.addFieldsToIgnore(pattern);
    }

    public void processAnnotations(Class[] types) {
        if (this.annotationConfiguration == null) {
            throw new InitializationException("No com.thoughtworks.xstream.mapper.AnnotationMapper available");
        }
        this.annotationConfiguration.processAnnotations(types);
    }

    public void processAnnotations(Class type) {
        processAnnotations(new Class[]{type});
    }

    public void autodetectAnnotations(boolean mode) {
        if (this.annotationConfiguration != null) {
            this.annotationConfiguration.autodetectAnnotations(mode);
        }
    }

    public void addPermission(TypePermission permission) {
        if (this.securityMapper != null) {
            this.securityMapper.addPermission(permission);
        }
    }

    public void allowTypes(String[] names) {
        addPermission(new ExplicitTypePermission(names));
    }

    public void allowTypes(Class[] types) {
        addPermission(new ExplicitTypePermission(types));
    }

    public void allowTypeHierarchy(Class type) {
        addPermission(new TypeHierarchyPermission(type));
    }

    public void allowTypesByRegExp(String[] regexps) {
        addPermission(new RegExpTypePermission(regexps));
    }

    public void allowTypesByRegExp(Pattern[] regexps) {
        addPermission(new RegExpTypePermission(regexps));
    }

    public void allowTypesByWildcard(String[] patterns) {
        addPermission(new WildcardTypePermission(patterns));
    }

    public void denyPermission(TypePermission permission) {
        addPermission(new NoPermission(permission));
    }

    public void denyTypes(String[] names) {
        denyPermission(new ExplicitTypePermission(names));
    }

    public void denyTypes(Class[] types) {
        denyPermission(new ExplicitTypePermission(types));
    }

    public void denyTypeHierarchy(Class type) {
        denyPermission(new TypeHierarchyPermission(type));
    }

    public void denyTypesByRegExp(String[] regexps) {
        denyPermission(new RegExpTypePermission(regexps));
    }

    public void denyTypesByRegExp(Pattern[] regexps) {
        denyPermission(new RegExpTypePermission(regexps));
    }

    public void denyTypesByWildcard(String[] patterns) {
        denyPermission(new WildcardTypePermission(patterns));
    }
}
