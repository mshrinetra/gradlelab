package com.mshri.gradlelab;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyUtil {
    
    private static final Logger log = LogManager.getLogger(MyUtil.class);
    
    public static void printMsg(String msg) {
        System.out.println("Message:\n" + msg);
    }

    public static void testError(String msg){
        try {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10);
            if(randomNum % 2 == 0){
                System.out.println(msg);
            }else{
                throw new IOException("Odd number");
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

}
