/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stuylib.util;

import java.util.Properties;
import java.io.*;

/**
 *
 * @author blake
 */
public class Config {
    Properties p;
    public Config() {
        p = new Properties();
        try {
            p.load(new FileInputStream("stuylib/util/driveTrain.properties"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] a) {
        System.out.printf("h\n");
        Config c = new Config();
        c.p.list(System.out);
    }
}
