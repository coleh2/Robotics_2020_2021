package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

enum Type {LESS_THAN, LESS_EQUAL_THAN, EQUAL, GREATER_THAN, GREATER_EQUAL_THAN, NOT_EQUAL, HUH}

public class BooleanOperator extends Value {

    Type type;

    Value a;
    Value b;

    Statement statement;

    public BooleanOperator(String src) {
        int lteIndex = src.indexOf("<=");
        int gteIndex = src.indexOf(">=");

        int gtIndex = -1, ltIndex = -1;
        if(lteIndex < 0 && gteIndex < 0) {
            gtIndex = src.indexOf(">");
            ltIndex = src.indexOf("<");
        }

        int eqIndex = src.indexOf("==");
        int neqIndex = src.indexOf("!=");

        int operatorIndex = lteIndex >= 0 ? lteIndex :
                            gteIndex >= 0 ? gteIndex :
                            gtIndex  >= 0 ? gtIndex  :
                            ltIndex  >= 0 ? ltIndex  :
                            eqIndex  >= 0 ? eqIndex  :
                            neqIndex >= 0 ? neqIndex :
                            -1;

        if(operatorIndex == -1) FeatureManager.logger.log("[AUTOAUTO ERROR] Could not parse boolean operator `" + src + "`");

        this.type = lteIndex >= 0 ? Type.LESS_EQUAL_THAN :
                    gteIndex >= 0 ? Type.GREATER_EQUAL_THAN :
                    gtIndex  >= 0 ? Type.GREATER_THAN  :
                    ltIndex  >= 0 ? Type.LESS_THAN  :
                    eqIndex  >= 0 ? Type.EQUAL  :
                    neqIndex >= 0 ? Type.NOT_EQUAL :
                    Type.HUH;

        int operatorEndIndex = operatorIndex;
        if(src.charAt(operatorEndIndex + 1) == '=') operatorEndIndex++;

        this.a = Value.createProperValueType(src.substring(0, operatorIndex));
        this.b = Value.createProperValueType(src.substring(operatorEndIndex + 1));
    }

    public void loop() {
        a.loop();
        b.loop();
        switch(type) {
            case EQUAL:
                for(int i = a.returnValue.length - 1; i >= 0; i--) {
                    if(!(a.returnValue[i] == b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            case NOT_EQUAL:
                for(int i = Math.min(a.returnValue.length, b.returnValue.length) - 1; i >= 0; i--) {
                    if(!(a.returnValue[i] != b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            case LESS_THAN:
                for(int i =  Math.min(a.returnValue.length, b.returnValue.length)- 1; i >= 0; i--) {
                    if(!(a.returnValue[i] < b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            case GREATER_THAN:
                for(int i =  Math.min(a.returnValue.length, b.returnValue.length)- 1; i >= 0; i--) {
                    if(!(a.returnValue[i] > b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            case LESS_EQUAL_THAN:
                for(int i =  Math.min(a.returnValue.length, b.returnValue.length)- 1; i >= 0; i--) {
                    if(!(a.returnValue[i] <= b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            case GREATER_EQUAL_THAN:
                for(int i =  Math.min(a.returnValue.length, b.returnValue.length)- 1; i >= 0; i--) {
                    if(!(a.returnValue[i] >= b.returnValue[i])) {
                        this.returnValue = new float[] {0};
                        break;
                    }
                }
                this.returnValue = new float[] {1};
                break;
            default:
                this.returnValue = new float[] {0};
        }
    }
}