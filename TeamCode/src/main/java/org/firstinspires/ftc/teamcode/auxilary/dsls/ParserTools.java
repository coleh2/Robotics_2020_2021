package org.firstinspires.ftc.teamcode.auxilary.dsls;

import java.util.ArrayList;

public class ParserTools {
    public static String[] groupAwareSplit(String str, char search) {
        return groupAwareSplit(str, search, true);
    }
    public static String[] groupAwareSplit(String str, char search, boolean includeLast) {
        ArrayList<String> strings = new ArrayList<String>();

        int searchIndex = groupAwareIndexOf(str, search);
        int lastIndex = -1;
        while(searchIndex != -1) {
            strings.add(str.substring(lastIndex + 1, searchIndex));
            lastIndex = searchIndex;
            searchIndex = groupAwareIndexOf(str, search, searchIndex + 1);
        }
        if(str.length() - lastIndex > 2 && (lastIndex < 0 || includeLast)) strings.add(str.substring(lastIndex + 1));

        return strings.toArray(new String[0]);
    }
    public static int groupAwareIndexOf(String str, char search) {
        return groupAwareIndexOf(str, search, 0);
    }
    public static int groupAwareIndexOf(String str, char search, int startIndex) {
        boolean inQuotes = false, inComment = false;
        int level = 0;

        int strlen = str.length();
        for(int i = startIndex; i < strlen; i++) {
            char currentChar = str.charAt(i);

            //comments!
            if(str.substring(i, Math.min(i + 2, strlen)).equals("/*")) inComment = true;
            if(str.substring(Math.max(i - 2, 0), i).equals("*/")) inComment = false;

            if(inComment) continue;

            if(currentChar == '"') inQuotes = !inQuotes;
            if(!inQuotes && (currentChar == '(' || currentChar == '{' || currentChar == '[')) level++;
            if(!inQuotes && (currentChar == ')' || currentChar == '}' || currentChar == ']')) level--;

            if(level == 0 && !inQuotes && currentChar == search) return i;
        }

        return -1;
    }

    public static String removeComments(String str) {
        StringBuilder result = new StringBuilder("");

        boolean inComment = false, inQuotes = false, inSinglelineComment = false;

        int strlen = str.length();
        for(int i = 0; i < strlen; i++) {
            char currentChar = str.charAt(i);

            if(!inQuotes) {
                //comments!
                if (str.substring(i, Math.min(i + 2, strlen)).equals("/*")) inComment = true;
                if (str.substring(Math.max(i - 2, 0), i).equals("*/")) inComment = false;

                if(str.substring(i, Math.min(i + 2, strlen)).equals("//")) inSinglelineComment = true;
                if(str.charAt(i) == '\n') inSinglelineComment = false;
            }

            if(inComment || inSinglelineComment) continue;

            if(currentChar == '"') inQuotes = !inQuotes;

            result.append(str.charAt(i));
        }

        return result.toString();
    }
}