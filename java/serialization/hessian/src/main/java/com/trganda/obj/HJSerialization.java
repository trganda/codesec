package com.trganda.obj;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HJSerialization {
    public static <T> byte[] hserialize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HessianOutput output = new HessianOutput(os);
            output.writeObject(t);
            data = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T hdeserialize(byte[] data) {
        if (data == null) {
            return null;
        }
        Object result = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            HessianInput input = new HessianInput(is);
            result = input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    public static <T> byte[] jdkSerialize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(os);
            output.writeObject(t);
            output.flush();
            output.close();
            data = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static <T> T jdkDeserialize(byte[] data) {
        if (data == null) {
            return null;
        }
        Object result = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            ObjectInputStream input = new ObjectInputStream(is);
            result = input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) result;
    }

    public static void main(String[] args) {
        Student stu = new Student(1, "hessian", "boy");

        long htime1 = System.currentTimeMillis();
        byte[] hdata = hserialize(stu);
        long htime2 = System.currentTimeMillis();
        System.out.println("hessian serialize result length = " + hdata.length + "," + "cost time：" + (htime2 - htime1));

        long htime3 = System.currentTimeMillis();
        Student hstudent = hdeserialize(hdata);
        long htime4 = System.currentTimeMillis();
        System.out.println("hessian deserialize result：" + hstudent + "," + "cost time：" + (htime4 - htime3));
        System.out.println();

        long jtime1 = System.currentTimeMillis();
        byte[] jdata = jdkSerialize(stu);
        long jtime2 = System.currentTimeMillis();
        System.out.println("jdk serialize result length = " + jdata.length + "," + "cost time：" + (jtime2 - jtime1));

        long jtime3 = System.currentTimeMillis();
        Student jstudent = jdkDeserialize(jdata);
        long jtime4 = System.currentTimeMillis();
        System.out.println("jdk deserialize result：" + jstudent + "," + "cost time：" + (jtime4 - jtime3));
    }
}
