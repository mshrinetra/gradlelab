/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.mshri.gradlelab;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger log = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        MyUtil.printMsg("First changes in dev branch.");

        try {
            MyUtil.sendEmail("Test simple java mail", "Hello!", false, "mshrinetra@outlook.com, imsakshisingh26@gmail.com; crazymanvendra@gmail.com;", null, null, null, null);
    
            String mailTemplateFile = "C:\\Users\\mshri\\SandBox\\gradlelab\\app\\src\\main\\resources\\mail_template.html";
            BufferedReader br = new BufferedReader(new FileReader(mailTemplateFile));
            String mailTemplate = br.lines().collect(Collectors.joining());;

            List<MailEmbedObject> embedObjs = new ArrayList<MailEmbedObject>();
            embedObjs.add(new MailEmbedObject("test-embed-img-one","C:\\Users\\mshri\\SandBox\\gradlelab\\devref\\one.jpeg"));
            embedObjs.add(new MailEmbedObject("test-embed-img-two","C:\\Users\\mshri\\SandBox\\gradlelab\\devref\\two.jpeg"));
            embedObjs.add(new MailEmbedObject("test-embed-img-three","C:\\Users\\mshri\\SandBox\\gradlelab\\devref\\three.jpeg"));

            MyUtil.sendEmail("Test embed java mail", mailTemplate, true, "mshrinetra@outlook.com", "imsakshisingh26@gmail.com; crazymanvendra@gmail.com;", null, embedObjs, "C:\\Users\\mshri\\SandBox\\gradlelab\\devref\\10000 Records.csv;C:\\Users\\mshri\\SandBox\\gradlelab\\devref\\10000 Records.prn");

        } catch (Exception e) {
            System.out.println("Error in sending mail");
        }

        System.out.println(String.format("Name: %s\nRepo: %s", Config.getProperty("name"), Config.getProperty("repo")));
        System.out.println(String.format("Env: %s", Config.getProperty("env")));

        log.info("log4j Info message.");

        MyUtil.testError("Testing error logging, no error");
    }
}
