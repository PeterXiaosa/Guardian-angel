package com.peter.guardianangel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class CustomObjectInputStream extends ObjectInputStream {
    protected CustomObjectInputStream() throws IOException, SecurityException {
        super();
    }

    public CustomObjectInputStream(InputStream arg0) throws IOException {
        super(arg0);
    }

    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        String name = desc.getName();
        try {
            if (name.startsWith("bean")){
//            if (name.startsWith("com.peter.guardianangel.bean")) {
//                name = name.replace("com.peter.guardianangel.", "");
                name = name.replace("bean.", "com.peter.guardianangel.bean.");
            }
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return super.resolveClass(desc);
    }
}