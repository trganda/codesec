package com.trganda.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.trganda.xstream.Square;

public class SquareConverter implements Converter {

    public boolean canConvert(Class clazz) {
        // 表示只支持 Square 类型
        return clazz.equals(Square.class);
    }

    public void marshal(
        Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        Square square = (Square) value;
        writer.startNode("squareSize");
        writer.setValue(String.valueOf(square.getSize()));
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Square square = new Square();
        reader.moveDown();
        square.setSize(Integer.parseInt(reader.getValue()));
        reader.moveUp();
        return square;
    }
}
