package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.reflection.AbstractAttributedCharacterIteratorAttributeConverter;
import java.awt.font.TextAttribute;

public class TextAttributeConverter extends AbstractAttributedCharacterIteratorAttributeConverter {
    public TextAttributeConverter() {
        super(TextAttribute.class);
    }
}
