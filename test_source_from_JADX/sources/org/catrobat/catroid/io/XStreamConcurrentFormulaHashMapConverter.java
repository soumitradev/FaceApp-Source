package org.catrobat.catroid.io;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;

public class XStreamConcurrentFormulaHashMapConverter implements Converter {
    private static final String CATEGORY = "category";
    private static final String FORMULA = "formula";

    public boolean canConvert(Class type) {
        return type.equals(ConcurrentFormulaHashMap.class);
    }

    public void marshal(Object object, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = (ConcurrentFormulaHashMap) object;
        for (BrickField brickField : concurrentFormulaHashMap.keySet()) {
            hierarchicalStreamWriter.startNode(FORMULA);
            hierarchicalStreamWriter.addAttribute(CATEGORY, brickField.toString());
            marshallingContext.convertAnother(((Formula) concurrentFormulaHashMap.get(brickField)).getRoot());
            hierarchicalStreamWriter.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            Formula formula;
            hierarchicalStreamReader.moveDown();
            BrickField brickField = BrickField.valueOf(hierarchicalStreamReader.getAttribute(CATEGORY));
            if (FORMULA.equals(hierarchicalStreamReader.getNodeName())) {
                formula = new Formula((FormulaElement) unmarshallingContext.convertAnother(concurrentFormulaHashMap, FormulaElement.class));
            } else {
                formula = new Formula(Integer.valueOf(0));
            }
            hierarchicalStreamReader.moveUp();
            concurrentFormulaHashMap.putIfAbsent(brickField, formula);
        }
        return concurrentFormulaHashMap;
    }
}
