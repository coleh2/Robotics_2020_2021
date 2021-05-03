package org.firstinspires.ftc.teamcode.managers;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.HashMap;

public class TelemetryManager {
    private HashMap<String, Float> options;

    private ArrayList<ArrayList<Character>> currentBuffer;

    private int[] cursor;

    public TelemetryManager() {
        this.cursor = new int[] {0, 0};
        this.currentBuffer = new ArrayList<ArrayList<Character>>();
        this.options = new HashMap<String, Float>();
    }

    private Telemetry telemetry;

    public void update() {
        reprint();
    }

    private void reprint() {
        clearPhoneScreen();
        printBuffer();
    }

    public void addLineToScreen(String line) {
        ArrayList<Character> lineChars = new ArrayList<Character>();
        for(char c : line.toCharArray()) lineChars.add(c);
        currentBuffer.add(lineChars);
    }

    public void addTextToScreen(String text) {
        ArrayList<Character> line = currentBuffer.get(currentBuffer.size() - 1);
        for(char c : text.toCharArray()) line.add(c);
    }

    private void printBuffer() {
        for(ArrayList<Character> line : currentBuffer) {
            StringBuilder lineText = new StringBuilder();
            for(char c : line) lineText.append(c);
            telemetry.log().add(lineText.toString());
        }
    }

    private void clearPhoneScreen() {
        StringBuilder r = new StringBuilder();
        for(int i = 0; i < currentBuffer.size(); i++) {
            for(int j = 0; j < currentBuffer.get(i).size(); j++) {
                r.append("\b ");
            }
            r.append("\b ");
        }
        telemetry.log().add(r.toString());
    }
}
