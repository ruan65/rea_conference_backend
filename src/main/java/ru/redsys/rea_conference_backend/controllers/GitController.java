package ru.redsys.rea_conference_backend.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.redsys.rea_conference_backend.model.LoginPageInput;
import ru.redsys.rea_conference_backend.model.OutputMessage;
import ru.redsys.rea_conference_backend.model.SchedulePageInput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class GitController {


    final static String fileNamePrefix = "/home/a/dev/reaproto/conference/RosenergoatomConference/app/src/main/res/raw/";
    final static String eventFileName = fileNamePrefix + "event.txt";
    final static String titleFileName = fileNamePrefix + "title.txt";
    final static String dateFileName = fileNamePrefix + "date.txt";
    final static String scheduleFileName = fileNamePrefix + "schedule.txt";

    @RequestMapping(value = "/update/login", method = RequestMethod.POST)
    public LoginPageInput changeLoginPage(@RequestBody LoginPageInput data) {

        writeToFileHelper(eventFileName, data.event);
        writeToFileHelper(titleFileName, data.title);
        writeToFileHelper(dateFileName, data.date);

        return data;
    }

    @RequestMapping(value = "/update/schedule", method = RequestMethod.POST)
    public SchedulePageInput changeText(@RequestBody SchedulePageInput data) {

        System.out.println("schedule data...." + data);

        writeToFileHelper(scheduleFileName, data.schedule);

        return data;
    }


    @RequestMapping("/read/text")
    public String readWrittenText() {

        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(titleFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @RequestMapping("/")
    public String root() {

        return "Hello my dear friend!!!";
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
