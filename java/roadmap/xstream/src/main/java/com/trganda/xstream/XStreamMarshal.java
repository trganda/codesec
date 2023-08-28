package com.trganda.xstream;

import com.thoughtworks.xstream.XStream;

public class XStreamMarshal {
    private static final XStream xstream =
            new XStream() {
                {
                    //processAnnotations(Square.class);
                }
            };

    public static void main(String[] args) {
        Square sq = new Square();
        sq.setSize(5);

        String resultXML = xstream.toXML(sq);
        System.out.println(resultXML);
    }
}
