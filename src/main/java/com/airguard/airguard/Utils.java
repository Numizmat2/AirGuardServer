package com.airguard.airguard;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Utils {

    public static String getHashForPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
