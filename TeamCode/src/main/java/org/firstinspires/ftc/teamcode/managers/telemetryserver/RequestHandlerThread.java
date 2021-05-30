package org.firstinspires.ftc.teamcode.managers.telemetryserver;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.TelemetryManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandlerThread implements Runnable {
    private static final String HTTP_LINE_SEPARATOR = "\r\n";
    private Socket socket;
    TelemetryManager dataSource;

    public RequestHandlerThread(Socket socket, TelemetryManager dataSource) {
        this.socket = socket;
        this.dataSource = dataSource;
    }

    public void run() {
        try {
            FeatureManager.logger.log("debug: i'm a request thread");
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            String requestMeta = reader.readLine();
            FeatureManager.logger.log("debug: request meta is " + requestMeta);
            String[] terms = requestMeta.split(" ");
            String verb = terms[0];
            String path = terms[1];

            if(path.equals("/stream")) {
                writer.print("HTTP/1.1 200 OK" + HTTP_LINE_SEPARATOR + HTTP_LINE_SEPARATOR);

                long streamStartedAt = System.currentTimeMillis();
                while(socket.isConnected() && !socket.isClosed() && FeatureManager.isOpModeRunning) {
                    if(dataSource.hasNewData()) writer.print(dataSource.readData());
                    else writer.print(ControlCodes.DO_NOT_FRET_MOTHER_I_AM_ALIVE_JUST_BORED);

                    writer.print("\n");
                    writer.flush();

                    try {
                        Thread.sleep(1000/60);
                    } catch (InterruptedException e) {
                        writer.write(ControlCodes.WOKEN_UP_AT_4AM);
                    }
                }
                if(!FeatureManager.isOpModeRunning) {
                    writer.print(ControlCodes.I_AM_DYING_BUT_I_MAY_BE_BACK_LATER);
                }
            } else if(path.equals("/")) {
                String file = ServerFiles.indexDotHtml;
                    writer.print("HTTP/1.1 200 OK" + HTTP_LINE_SEPARATOR
                            //+ "Content-Length: " + (file.getBytes(StandardCharsets.UTF_8).length) + HTTP_LINE_SEPARATOR
                            + "Content-Type: " + "text/html; charset=utf-8" + HTTP_LINE_SEPARATOR
                            + HTTP_LINE_SEPARATOR
                            + HTTP_LINE_SEPARATOR
                            + file);
            } else if(path.equals("/command")) {
                //consume & disregard headers
                String line = requestMeta;
                while(!line.equals("")) line = reader.readLine();

                //body time!
                StringBuilder reqBodyBuilder = new StringBuilder();
                int nextChar;
                while((nextChar = reader.read()) != -1) reqBodyBuilder.append((char)nextChar);

                String body = reqBodyBuilder.toString();

                String[] commaSepValues = ParserTools.groupAwareSplit(body, ',');

                if(commaSepValues[0].trim().equals(ControlCodes.STRUCK_WITH_A_TUNING_FORK)) {
                    String tuneName = commaSepValues[1];
                    float[] values = new float[commaSepValues.length - 2];

                    for(int i = 2; i < commaSepValues.length; i++) values[i] = Float.parseFloat(commaSepValues[i]);


                }

            } else {
                String r = "not found";
                writer.print("HTTP/1.1 404 NOT FOUND" + HTTP_LINE_SEPARATOR
                        + "Content-Length: " + (r.getBytes(StandardCharsets.UTF_8).length) + HTTP_LINE_SEPARATOR
                        + HTTP_LINE_SEPARATOR
                        + r);
            }
            writer.flush();
            socket.close();


        } catch(IOException e) {
            dataSource.log().add("dashboard status" + e.toString());
        }
    }
}
