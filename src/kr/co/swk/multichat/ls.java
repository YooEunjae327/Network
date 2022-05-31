package kr.co.swk.multichat;

import java.io.File;

public class ls {
    public static void main(String[] args) {
        File f = null;
        String[] paths;

        try {

            // create new file
            f = new File("/Users/tomato/Documents");

            // array of files and directory
            paths = f.list();

            // for each name in the path array
            for(String path:paths) {

                // prints filename and directory name
                System.out.println(path);
            }

        } catch(Exception e) {
            // if any error occurs
            e.printStackTrace();
        }
    }
}