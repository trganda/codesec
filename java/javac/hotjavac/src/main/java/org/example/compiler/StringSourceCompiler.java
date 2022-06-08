package org.example.compiler;

import javax.tools.*;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSourceCompiler {

    /** 使用Pattern 获取类名 */
    private static Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s*");

    public static byte[] compile(String source, DiagnosticCollector<JavaFileObject> compileCollector) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager javaFileManager = new StringJavaFileManager(
                compiler.getStandardFileManager(compileCollector,
                        null, null));

        // 从源码字符串中匹配类名
        Matcher matcher = CLASS_PATTERN.matcher(source);
        String className;
        if (matcher.find()) {
            className = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No valid class");
        }

        JavaFileObject javaFileObject = new StringJavaFileObject(className, source);
        boolean result = compiler.getTask(
                null, javaFileManager, compileCollector, null,
                null, Collections.singletonList(javaFileObject)).call();

        JavaFileObject byteFileObject = StringJavaFileManager.getFileObjectMap().get(className);
        if (result && byteFileObject != null) {
            byte[] classBytes = ((StringJavaFileObject) byteFileObject).getCompiledBytes();
            return classBytes;
        }

        return null;
    }
}
