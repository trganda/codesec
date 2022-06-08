package org.example;


import org.example.compiler.StringSourceCompiler;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

public class App
{
    public static void main( String[] args )
    {
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<>();

        StringSourceCompiler.compile("public class App{    public static void main( String[] args )" +
                "    {        System.out.println( \"Hello World!\" );    }}", compileCollector);
    }
}
