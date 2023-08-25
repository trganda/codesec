package com.trganda.xmldecoder;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.security.*;

public class SecurityXMLDecoder {

    public void test() {
        // 创建自定义的安全策略
        Permission permission = new CustomPermission("xmlDecoderPermission");

        // 创建访问控制策略，设置 Context
        AccessControlContext accessControlContext = new AccessControlContext(new ProtectionDomain[] {
            new CustomProtectionDomain(null, permission.newPermissionCollection())
        });

        // 使用访问控制策略执行代码
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream("target/classes/runtime.xml"))) {
            // 执行受限的 XML 解码操作
            Object object = decoder.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SecurityXMLDecoder().test();
    }

    // 自定义权限类
    class CustomPermission extends Permission {
        private static final String CREATE_RUNTIME_PERMISSION = "createRuntime";

        public CustomPermission(String name) {
            super(name);
        }

        @Override
        public boolean implies(Permission permission) {
            if (permission instanceof CustomPermission) {
                CustomPermission customPermission = (CustomPermission) permission;
                if (getName().equals(CREATE_RUNTIME_PERMISSION) && customPermission.getName().equals(CREATE_RUNTIME_PERMISSION)) {
                    // 在此处添加适当的逻辑判断
                    // 例如，如果不允许创建 Runtime 对象，则返回 false
                    return false;
                }
            }
            return true; // 默认情况下允许其他权限
        }

        @Override
        public boolean equals(Object obj) {
            // equals 方法的实现逻辑
            // ...
            return false;
        }

        @Override
        public int hashCode() {
            // hashCode 方法的实现逻辑
            // ...
            return 0;
        }

        @Override
        public String getActions() {
            // getActions 方法的实现逻辑
            // ...
            return null;
        }
    }

    // 自定义 ProtectionDomain 类
    class CustomProtectionDomain extends ProtectionDomain {
        public CustomProtectionDomain(CodeSource codesource, PermissionCollection permissions) {
            super(codesource, permissions);
        }
    }
}

