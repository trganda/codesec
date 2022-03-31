package com.trganda.transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;



public class DurationTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte byteCode[] = classfileBuffer;

        Logger logger = LogManager.getLogger(DurationTransformer.class);
        // since this transformer will be called when all the classes are
        // loaded by the classloader, we are restricting the instrumentation
        // using if block only for the com.trganda.app.App class

        if (className.equals("com/trganda/app/App")) {
            logger.info("Instrumenting ...");
            try {
                ClassPool classPool = ClassPool.getDefault();
                CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
                CtMethod[] methods = ctClass.getMethods();
                for (CtMethod method : methods) {
                    if (method.getName().equals("run")) {
                        method.addLocalVariable("startTime", CtClass.longType);
                        method.insertBefore("startTime = System.nanoTime();");
                        method.insertAfter("System.out.println(\"Execution Duration "
                                + "(nano sec): \"+ (System.nanoTime() - startTime) );");
                    }
                }
                byteCode = ctClass.toBytecode();
                ctClass.detach();
                logger.info("Instrument Completed.");
            } catch (Exception ex) {
                logger.error(ex);
                ex.printStackTrace();
            }
        }

        return byteCode;
    }
}
