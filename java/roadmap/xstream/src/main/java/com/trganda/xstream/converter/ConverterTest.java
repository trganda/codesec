package com.trganda.xstream.converter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.trganda.xstream.Square;

public class ConverterTest {
    public static void main(String[] args) {
        Square square = new Square();
        square.setSize(10);

        XStream xStream = new XStream(new DomDriver());
        xStream.registerConverter(new SquareConverter());
        // 此时注解 @XStreamAlias("square") 不生效，需要手动指定
        xStream.alias("square", Square.class);
        System.out.println(xStream.toXML(square));
    }
}
