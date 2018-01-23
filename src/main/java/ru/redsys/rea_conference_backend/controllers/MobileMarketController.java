package ru.redsys.rea_conference_backend.controllers;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MobileMarketController {

    final static String APK_PATH = "/home/a/dev/reaproto/conference/RosenergoatomConference/app/release/";

    // Getting .apk file from the server

    @RequestMapping(value = "/download/apk", method = RequestMethod.GET, produces = "application/apk")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getApkFileForFilial() {

        File apkFile = getApkFile(APK_PATH).get(0).toFile();

        System.out.println("Mobile app .apk file: " + apkFile.getAbsolutePath());

        return createApkResponse(apkFile);
    }

    private List<Path> getApkFile(String apkSpecifiedDir) {

        String apkDirectory = APK_PATH;

        Path apkLocationPath = Paths.get(apkDirectory);

        System.out.println(".apk directory: " + apkDirectory + " .apk location path: " + apkLocationPath);

        List<Path> collect = new ArrayList<>();

        try {
            collect = Files.list(apkLocationPath)
                    .filter(path -> path.getFileName().toString().contains(".apk"))
                    .collect(Collectors.toList());

            System.out.println("Apk files: " + collect);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return collect;
    }

    private ResponseEntity<InputStreamResource> createApkResponse(File apkFile) {

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("apk", apkFile.getName());

        InputStreamResource inputStreamResource = readApkFromDisk(apkFile);

        ResponseEntity<InputStreamResource> inputStreamResourceResponseEntity =
                new ResponseEntity<>(inputStreamResource, respHeaders, HttpStatus.OK);

        return inputStreamResourceResponseEntity;
    }

    private InputStreamResource readApkFromDisk(File apkFile) {

        InputStreamResource inputStreamResource = null;

        try {
            inputStreamResource = new InputStreamResource(new FileInputStream(apkFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputStreamResource;
    }
}
