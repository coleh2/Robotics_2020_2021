package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AutoautoProgram {
    public HashMap<String, Statepath> paths;
    String initialPath;

    public AutoautoProgram(String src) {
        //split into the program's labeled statepaths
        String[] LSPs = ParserTools.groupAwareSplit(src, '.', false);

        paths = new HashMap<String, Statepath>();

        //process each labeled statepath, one by one
        for(int i = LSPs.length - 1; i >= 0; i--) {
            int colonIndex = LSPs[i].indexOf(':');
            String lspName = "";
            if(colonIndex < 0) {
                FeatureManager.logger.log("[AUTOAUTO ERROR] Unlabelled statepath `" + LSPs[i] + "`, defaulting to " + i);
                lspName = "" + i;
            } else {
                lspName = LSPs[i].substring(0, colonIndex);
            }
            paths.put(lspName, new Statepath(LSPs[i].substring(colonIndex + 1)));

            if(i == 0) initialPath = lspName;
        }
    }

    @NotNull
    public String toString() {
        StringBuilder pathsStr = new StringBuilder();
        for(Map.Entry<String, Statepath> entry : paths.entrySet()) {
            pathsStr.append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("\n.\n");
        }
        return pathsStr.toString();
    }
}
