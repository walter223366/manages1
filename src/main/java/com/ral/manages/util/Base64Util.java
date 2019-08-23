package com.ral.manages.util;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Util {

    private static final Base64 base64 = new Base64();
    private static final String ISNULL = "";
    private static final String charset = "utf-8";

    public Base64Util() { }


    public static final String Base64Encode(String origin) {
        try {
            return origin != null && !ISNULL.equals(origin)?Base64Encode(origin.getBytes(charset)):ISNULL;
        } catch (Exception var2) {
            return null;
        }
    }

    public static final String Base64Encode(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            try {
                return new String(base64.encode(bytes),charset);
            } catch (Exception var2) {
                return ISNULL;
            }
        } else {
            return ISNULL;
        }
    }

    public static final String Base64Decode(String origin) {
        try {
            return origin != null && !ISNULL.equals(origin)?Base64Decode(origin.getBytes(charset)):ISNULL;
        } catch (Exception var2) {
            return null;
        }
    }

    public static final String Base64Decode(byte[] bytes) {
        try {
            byte[] decodeBytes = base64.decode(bytes);
            return new String(decodeBytes,charset);
        } catch (Exception var2) {
            return ISNULL;
        }
    }
}
