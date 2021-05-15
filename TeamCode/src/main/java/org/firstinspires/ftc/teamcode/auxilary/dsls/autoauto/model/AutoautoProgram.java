package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.AutoautoRuntime;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AutoautoProgram {
    public HashMap<String, Statepath> paths;
    public Statepath currentPath;
    public String currentPathName;
    int currentPathIndex;

    public AutoautoRuntime autoautoRuntime;

    public void setCurrentPath(String newPath) {
        if(!paths.containsKey(newPath)) FeatureManager.logger.add("[AUTOAUTO ERROR] No such path `" + newPath + "`");
        this.currentPath = paths.get(newPath);
        this.currentPathName = newPath;
        this.currentPath.stepInit();
    }

    public AutoautoProgram(HashMap<String, Statepath> s, String initial) {
        this.paths = s;
        this.currentPathName = initial;
        this.currentPath = paths.get(initial);

        for(Statepath p : this.paths.values()) p.setProgram(this);
    }
    public AutoautoProgram(String src) {
        //first off, nilch the comments
        src = ParserTools.removeComments(src);

        //split into the program's labeled statepaths
        String[] LSPs = ParserTools.groupAwareSplit(src, '#', true);

        paths = new HashMap<String, Statepath>();

        this.currentPathIndex = LSPs.length + 1;

        //process each labeled statepath, one by one
        for(int i = LSPs.length - 1; i >= 0; i--) {
            if(LSPs[i].equals("")) continue;

            int colonIndex = LSPs[i].indexOf(':');
            String lspName = "";
            if(colonIndex < 0) {
                FeatureManager.logger.log("[AUTOAUTO ERROR] Unlabelled statepath `" + LSPs[i] + "`, defaulting to " + i);
                lspName = "" + i;
                colonIndex = LSPs[i].indexOf(' ');
            } else {
                lspName = LSPs[i].substring(0, colonIndex);
            }
            Statepath newPath = new Statepath(LSPs[i].substring(colonIndex + 1), this, lspName);
            paths.put(lspName, newPath);

            if(i < currentPathIndex) {
                this.currentPath = newPath;
                this.currentPathName = lspName;
            }
        }
    }

    public void loop() {
        if(this.currentPath == null) FeatureManager.logger.log("current path is null");
        this.currentPath.loop();
    }

    public void init() {
        for(Statepath p : this.paths.values()) p.init();
    }

    public void stepInit() {
        for(Statepath p : this.paths.values()) p.stepInit();
    }

    @NotNull
    public String toString() {
        StringBuilder pathsStr = new StringBuilder();
        for(Map.Entry<String, Statepath> entry : paths.entrySet()) {
            pathsStr.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n.\n");
        }
        return pathsStr.toString();
    }

    public String getCurrentPathName() {
        return this.currentPathName;
    }
}
