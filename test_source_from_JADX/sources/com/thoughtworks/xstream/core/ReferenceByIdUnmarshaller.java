package com.thoughtworks.xstream.core;

import com.facebook.share.internal.ShareConstants;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

public class ReferenceByIdUnmarshaller extends AbstractReferenceUnmarshaller {
    public ReferenceByIdUnmarshaller(Object root, HierarchicalStreamReader reader, ConverterLookup converterLookup, Mapper mapper) {
        super(root, reader, converterLookup, mapper);
    }

    protected Object getReferenceKey(String reference) {
        return reference;
    }

    protected Object getCurrentReferenceKey() {
        String attributeName = getMapper().aliasForSystemAttribute(ShareConstants.WEB_DIALOG_PARAM_ID);
        return attributeName == null ? null : this.reader.getAttribute(attributeName);
    }
}
