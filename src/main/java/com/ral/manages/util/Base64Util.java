package com.ral.manages.util;

import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Encoder;
import java.io.*;

public class Base64Util {

    private static final Base64 base64 = new Base64();
    public static final String CONST_EMPTY_STRING = "";

    public Base64Util() {
    }

    public static final String Base64Encode(String origin) {
        try {
            return origin != null && !"".equals(origin) ? Base64Encode(origin.getBytes("utf-8")) : "";
        } catch (Exception var2) {
            return null;
        }
    }

    public static final String Base64Encode(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            try {
                return new String(base64.encode(bytes), "utf-8");
            } catch (Exception var2) {
                return "";
            }
        } else {
            return "";
        }
    }

    public static final String Base64Decode(String origin) {
        try {
            return origin != null && !"".equals(origin) ? Base64Decode(origin.getBytes("utf-8")) : "";
        } catch (Exception var2) {
            return null;
        }
    }

    public static final String Base64Decode(byte[] bytes) {
        try {
            byte[] decodeBytes = base64.decode(bytes);
            return new String(decodeBytes, "utf-8");
        } catch (Exception var2) {
            return "";
        }
    }

    public static final InputStream outToin(FileOutputStream fos) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        String fosStr = fos.toString();
        System.out.println(fosStr);
        oos.writeObject(fosStr);
        byte[] bytes = bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        System.out.println(ois.readObject().toString());
        new FileInputStream(ois.readObject().toString());
        return ois;
    }

    public static final String imgToBase64(InputStream is) {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            boolean var3 = false;
            int len;
            while((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] bytes = outStream.toByteArray();
            String var5 = (new BASE64Encoder()).encode(bytes);
            return var5;
        } catch (IOException var15) {
            var15.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }
            }
        }
        return null;
    }
}
