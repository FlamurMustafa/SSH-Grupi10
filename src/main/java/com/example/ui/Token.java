package com.example.ui;

import java.io.File;
import java.io.FileReader;

public class Token {
    public static String getToken(){
        String token = null;
        try {
            FileReader fr = new FileReader("src/main/resources/files/token.txt");
            char[] c = new char[136];
            fr.read(c);
            String s = String.valueOf(c);
            s.replaceAll("\0", "");
            if(s.charAt(0)==0) throw new Exception();
            token = "Bearer " + s;
        }catch (Exception e){
            return null;
        }
        return token;
    }

    public static void deleteToken(){
        File file = new File("src/main/resources/files/token.txt");
        file.delete();
    }
}
