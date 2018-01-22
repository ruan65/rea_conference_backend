package ru.redsys.rea_conference_backend.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.redsys.rea_conference_backend.model.InputMessage;
import ru.redsys.rea_conference_backend.model.OutputMessage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class GitController {


    //    static String fileName = "/home/a/temp/writtenByJava.txt";
    static String fileName = "/home/a/dev/reaproto/reaproto_2/reaproto/ruch_mobile_chat_client/app/src/main/res/raw/text.txt";

    @RequestMapping(value = "/change/text", method = RequestMethod.POST)
    public OutputMessage changeText(@RequestBody InputMessage msg) {

        writeToFileHelper(fileName, msg.getText());

        return new OutputMessage(msg.getFrom(), msg.getText() + " - this is response!", new Date().toString());
    }

    @RequestMapping("/read/text")
    public String readWrittenText() {

        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @RequestMapping("deploy/apk")
    public OutputMessage deployApk() {

        System.out.println("Deploy apk called");
//        deploy();
        notifyUbuntu();
        return createDeployOutput();
    }


    public static OutputMessage createDeployOutput() {
        return new OutputMessage("Redsys deployment tool", "Deployed, Run on the device now!", new Date().toString());
    }

    public static void writeToFileHelper(String fileName, String text) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write(text);

            System.out.println("Done!!!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deploy() {

        String[] cmd = new String[]{"/bin/sh", "/home/a/dev/reaproto/reaproto_2/reaproto/ruch_mobile_chat_client/deployApk.sh > /home/a/dev/reaproto/reaproto_2/reaproto/ruch_mobile_chat_client/out.log"};
        try {
            Process pr = Runtime.getRuntime().exec(cmd);
            System.out.println("Deployed!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void notifyUbuntu() {
        String[] cmd = new String[]{"/bin/sh", "/home/a/dev/reaproto/reaproto_2/reaproto/ruch_mobile_chat_client/notify2.sh"};
        try {
            Process pr = Runtime.getRuntime().exec(cmd);
//            pr.getOutputStream()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
