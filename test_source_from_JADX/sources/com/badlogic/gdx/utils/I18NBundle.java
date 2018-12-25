package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

public class I18NBundle {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Locale ROOT_LOCALE = new Locale("", "", "");
    private static boolean exceptionOnMissingKey = true;
    private static boolean simpleFormatter = false;
    private TextFormatter formatter;
    private Locale locale;
    private I18NBundle parent;
    private ObjectMap<String, String> properties;

    public static boolean getSimpleFormatter() {
        return simpleFormatter;
    }

    public static void setSimpleFormatter(boolean enabled) {
        simpleFormatter = enabled;
    }

    public static boolean getExceptionOnMissingKey() {
        return exceptionOnMissingKey;
    }

    public static void setExceptionOnMissingKey(boolean enabled) {
        exceptionOnMissingKey = enabled;
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle) {
        return createBundleImpl(baseFileHandle, Locale.getDefault(), "UTF-8");
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale) {
        return createBundleImpl(baseFileHandle, locale, "UTF-8");
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, String encoding) {
        return createBundleImpl(baseFileHandle, Locale.getDefault(), encoding);
    }

    public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale, String encoding) {
        return createBundleImpl(baseFileHandle, locale, encoding);
    }

    private static I18NBundle createBundleImpl(FileHandle baseFileHandle, Locale locale, String encoding) {
        if (!(baseFileHandle == null || locale == null)) {
            if (encoding != null) {
                I18NBundle baseBundle = null;
                I18NBundle bundle = null;
                Locale targetLocale = locale;
                do {
                    List<Locale> candidateLocales = getCandidateLocales(targetLocale);
                    bundle = loadBundleChain(baseFileHandle, encoding, candidateLocales, 0, baseBundle);
                    if (bundle != null) {
                        Locale bundleLocale = bundle.getLocale();
                        boolean isBaseBundle = bundleLocale.equals(ROOT_LOCALE);
                        if (isBaseBundle) {
                            if (!bundleLocale.equals(locale)) {
                                if (candidateLocales.size() == 1 && bundleLocale.equals(candidateLocales.get(0))) {
                                    break;
                                } else if (isBaseBundle && baseBundle == null) {
                                    baseBundle = bundle;
                                }
                            } else {
                                break;
                            }
                        }
                        break;
                    }
                    targetLocale = getFallbackLocale(targetLocale);
                } while (targetLocale != null);
                if (bundle != null) {
                    return bundle;
                }
                if (baseBundle != null) {
                    return baseBundle;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't find bundle for base file handle ");
                stringBuilder.append(baseFileHandle.path());
                stringBuilder.append(", locale ");
                stringBuilder.append(locale);
                String stringBuilder2 = stringBuilder.toString();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(baseFileHandle);
                stringBuilder3.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                stringBuilder3.append(locale);
                throw new MissingResourceException(stringBuilder2, stringBuilder3.toString(), "");
            }
        }
        throw new NullPointerException();
    }

    private static List<Locale> getCandidateLocales(Locale locale) {
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        List<Locale> locales = new ArrayList(4);
        if (variant.length() > 0) {
            locales.add(locale);
        }
        if (country.length() > 0) {
            locales.add(locales.size() == 0 ? locale : new Locale(language, country));
        }
        if (language.length() > 0) {
            locales.add(locales.size() == 0 ? locale : new Locale(language));
        }
        locales.add(ROOT_LOCALE);
        return locales;
    }

    private static Locale getFallbackLocale(Locale locale) {
        Locale defaultLocale = Locale.getDefault();
        return locale.equals(defaultLocale) ? null : defaultLocale;
    }

    private static I18NBundle loadBundleChain(FileHandle baseFileHandle, String encoding, List<Locale> candidateLocales, int candidateIndex, I18NBundle baseBundle) {
        Locale targetLocale = (Locale) candidateLocales.get(candidateIndex);
        I18NBundle parent = null;
        if (candidateIndex != candidateLocales.size() - 1) {
            parent = loadBundleChain(baseFileHandle, encoding, candidateLocales, candidateIndex + 1, baseBundle);
        } else if (baseBundle != null && targetLocale.equals(ROOT_LOCALE)) {
            return baseBundle;
        }
        I18NBundle bundle = loadBundle(baseFileHandle, encoding, targetLocale);
        if (bundle == null) {
            return parent;
        }
        bundle.parent = parent;
        return bundle;
    }

    private static I18NBundle loadBundle(FileHandle baseFileHandle, String encoding, Locale targetLocale) {
        I18NBundle bundle = null;
        Reader reader = null;
        try {
            FileHandle fileHandle = toFileHandle(baseFileHandle, targetLocale);
            if (checkFileExistence(fileHandle)) {
                bundle = new I18NBundle();
                reader = fileHandle.reader(encoding);
                bundle.load(reader);
            }
            StreamUtils.closeQuietly(reader);
            if (bundle != null) {
                bundle.setLocale(targetLocale);
            }
            return bundle;
        } catch (Throwable e) {
            throw new GdxRuntimeException(e);
        } catch (Throwable th) {
            StreamUtils.closeQuietly(null);
        }
    }

    private static boolean checkFileExistence(FileHandle fh) {
        try {
            fh.read().close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void load(Reader reader) throws IOException {
        this.properties = new ObjectMap();
        PropertiesUtils.load(this.properties, reader);
    }

    private static FileHandle toFileHandle(FileHandle baseFileHandle, Locale locale) {
        StringBuilder sb = new StringBuilder(baseFileHandle.name());
        if (!locale.equals(ROOT_LOCALE)) {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String variant = locale.getVariant();
            boolean emptyLanguage = "".equals(language);
            boolean emptyCountry = "".equals(country);
            boolean emptyVariant = "".equals(variant);
            if (!(emptyLanguage && emptyCountry && emptyVariant)) {
                sb.append('_');
                if (!emptyVariant) {
                    sb.append(language).append('_').append(country).append('_').append(variant);
                } else if (emptyCountry) {
                    sb.append(language);
                } else {
                    sb.append(language).append('_').append(country);
                }
            }
        }
        return baseFileHandle.sibling(sb.append(".properties").toString());
    }

    public Locale getLocale() {
        return this.locale;
    }

    private void setLocale(Locale locale) {
        this.locale = locale;
        this.formatter = new TextFormatter(locale, simpleFormatter ^ 1);
    }

    public final String get(String key) {
        String result = (String) this.properties.get(key);
        if (result == null) {
            if (this.parent != null) {
                result = this.parent.get(key);
            }
            if (result == null) {
                if (exceptionOnMissingKey) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Can't find bundle key ");
                    stringBuilder.append(key);
                    throw new MissingResourceException(stringBuilder.toString(), getClass().getName(), key);
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("???");
                stringBuilder2.append(key);
                stringBuilder2.append("???");
                return stringBuilder2.toString();
            }
        }
        return result;
    }

    public String format(String key, Object... args) {
        return this.formatter.format(get(key), args);
    }
}
