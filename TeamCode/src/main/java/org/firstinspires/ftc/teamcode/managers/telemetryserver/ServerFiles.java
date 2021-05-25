package org.firstinspires.ftc.teamcode.managers.telemetryserver;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ServerFiles {
    public static String indexDotHtml;
    public static void loadIndexDotHtml() throws IOException {
        Context context = null;
        try {
            context = ((Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null, (Object[]) null)).getApplicationContext();
        } catch(Exception e) {
            FeatureManager.logger.log("Error getting context");
        }

        AssetManager assets = context.getAssets();

        InputStream file = assets.open("index.html");

        if(file == null) FeatureManager.logger.log("aa resource is null");

        Scanner sc = new Scanner(file);

        StringBuilder r = new StringBuilder();
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            r.append(line).append("\n");
        }

        file.close();
        ServerFiles.indexDotHtml = r.toString();



    }
}
