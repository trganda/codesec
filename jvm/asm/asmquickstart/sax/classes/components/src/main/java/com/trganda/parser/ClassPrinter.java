package com.trganda.parser;

import org.objectweb.asm.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ClassPrinter extends ClassVisitor {
    static Logger logger = LogManager.getLogger();

    public ClassPrinter(int api) {
        super(api);
    }

    public ClassPrinter(int api, ClassVisitor cv) {
        super(api, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        logger.info("=== Call Visit STA ===");
        logger.info(String.format("name = %s", name));
        logger.info(String.format("signature = %s", signature));
        logger.info(String.format("superName = %s", superName));
        for (String inf : interfaces) {
            logger.info(String.format("interface = %s", inf));
        }
        super.visit(version, access, name, signature, superName, interfaces);
        logger.info("=== Call Visit END ===");
    }

    @Override
    public void visitSource(String source, String debug) {
        logger.info("=== Call visitSource STA ===");
        logger.info(String.format("source = %s", source));
        super.visitSource(source, debug);
        logger.info("=== Call visitSource END ===");
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        logger.info("=== Call visitOuterClass STA ===");
        logger.info(String.format("owner = %s", owner));
        logger.info(String.format("name = %s", name));
        logger.info(String.format("desc = %s", desc));
        super.visitOuterClass(owner, name, desc);
        logger.info("=== Call visitOuterClass END ===");
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        logger.info("=== Call visitAttribute STA ===");
        logger.info(attr);
        super.visitAttribute(attr);
        logger.info("=== Call visitAttribute END ===");
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        logger.info("=== Call visitField STA ===");
        logger.info(String.format("access = %d", access));
        logger.info(String.format("name = %s", name));
        logger.info(String.format("desc = %s", desc));
        logger.info(String.format("signature = %s", signature));
        logger.info(value);
        logger.info("=== Call visitField END ===");
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        logger.info("=== Call visitMethod STA ===");
        logger.info(String.format("access = %d", access));
        logger.info(String.format("name = %s", name));
        logger.info(String.format("desc = %s", desc));
        logger.info(String.format("signature = %s", signature));
        try {
            for (String except : exceptions) {
                logger.info(String.format("exception = %s", except));
            }
        } catch (Exception except) {
            logger.error(except.getMessage());
        }

        logger.info("=== Call visitMethod END ===");
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        logger.info("=== Call visitEnd STA ===");
        super.visitEnd();
        logger.info("=== Call visitEnd END ===");
    }
}
