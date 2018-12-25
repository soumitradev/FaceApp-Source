package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.core.util.ObjectIdDictionary;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.io.path.PathTracker;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.util.Iterator;

public abstract class AbstractReferenceMarshaller extends TreeMarshaller implements MarshallingContext {
    private ObjectIdDictionary implicitElements = new ObjectIdDictionary();
    private Path lastPath;
    private PathTracker pathTracker = new PathTracker();
    private ObjectIdDictionary references = new ObjectIdDictionary();

    private static class Id {
        private Object item;
        private Path path;

        public Id(Object item, Path path) {
            this.item = item;
            this.path = path;
        }

        protected Object getItem() {
            return this.item;
        }

        protected Path getPath() {
            return this.path;
        }
    }

    public static class ReferencedImplicitElementException extends ConversionException {
        public ReferencedImplicitElementException(Object item, Path path) {
            super("Cannot reference implicit element");
            add("implicit-element", item.toString());
            add("referencing-element", path.toString());
        }
    }

    protected abstract String createReference(Path path, Object obj);

    protected abstract Object createReferenceKey(Path path, Object obj);

    protected abstract void fireValidReference(Object obj);

    public AbstractReferenceMarshaller(HierarchicalStreamWriter writer, ConverterLookup converterLookup, Mapper mapper) {
        super(writer, converterLookup, mapper);
        this.writer = new PathTrackingWriter(writer, this.pathTracker);
    }

    public void convert(Object item, Converter converter) {
        if (getMapper().isImmutableValueType(item.getClass())) {
            converter.marshal(item, this.writer, this);
            return;
        }
        final Path currentPath = this.pathTracker.getPath();
        Id existingReference = (Id) this.references.lookupId(item);
        if (existingReference == null || existingReference.getPath() == currentPath) {
            final Object newReferenceKey = existingReference == null ? createReferenceKey(currentPath, item) : existingReference.getItem();
            if (this.lastPath == null || !currentPath.isAncestor(this.lastPath)) {
                fireValidReference(newReferenceKey);
                this.lastPath = currentPath;
                this.references.associateId(item, new Id(newReferenceKey, currentPath));
            }
            converter.marshal(item, this.writer, new ReferencingMarshallingContext() {
                public void put(Object key, Object value) {
                    AbstractReferenceMarshaller.this.put(key, value);
                }

                public Iterator keys() {
                    return AbstractReferenceMarshaller.this.keys();
                }

                public Object get(Object key) {
                    return AbstractReferenceMarshaller.this.get(key);
                }

                public void convertAnother(Object nextItem, Converter converter) {
                    AbstractReferenceMarshaller.this.convertAnother(nextItem, converter);
                }

                public void convertAnother(Object nextItem) {
                    AbstractReferenceMarshaller.this.convertAnother(nextItem);
                }

                public void replace(Object original, Object replacement) {
                    AbstractReferenceMarshaller.this.references.associateId(replacement, new Id(newReferenceKey, currentPath));
                }

                public Object lookupReference(Object item) {
                    return ((Id) AbstractReferenceMarshaller.this.references.lookupId(item)).getItem();
                }

                public Path currentPath() {
                    return AbstractReferenceMarshaller.this.pathTracker.getPath();
                }

                public void registerImplicit(Object item) {
                    if (AbstractReferenceMarshaller.this.implicitElements.containsId(item)) {
                        throw new ReferencedImplicitElementException(item, currentPath);
                    }
                    AbstractReferenceMarshaller.this.implicitElements.associateId(item, newReferenceKey);
                }
            });
            return;
        }
        String attributeName = getMapper().aliasForSystemAttribute("reference");
        if (attributeName != null) {
            this.writer.addAttribute(attributeName, createReference(currentPath, existingReference.getItem()));
        }
    }
}
