package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime.Function;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

enum Type {LESS_THAN, LESS_EQUAL_THAN, EQUAL, GREATER_THAN, GREATER_EQUAL_THAN, NOT_EQUAL, FUNC_CALL, HUH}

public class BooleanOperator extends Value {

    Type type;

    Value a;
    Value b;

    Statement statement;

    FunctionCall function;

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

        if(operatorIndex == -1) {
            FeatureManager.logger.log("[AUTOAUTO INFO] Boolean operator falling back to boolean function `" + src + "`");
            this.type = Type.FUNC_CALL;
            this.function = new FunctionCall(src);
            return;
        }

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

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }

    public void init() {
        a.setRuntimeReferences(runtimeFunctionStore, runtimeVariableStore);
        b.setRuntimeReferences(runtimeFunctionStore, runtimeVariableStore);
    }

    public void loop() {
        if(this.type == Type.FUNC_CALL) {
            this.function.loop();
            if(this.function.returnValue[0] > 0) this.returnValue = new float[] {1};
            else this.returnValue = new float[] {0};
            return;
        }
        a.loop();
        b.loop();
        switch(type) {
            case EQUAL:
                if(!(a.getReturnValue()[0] == b.getReturnValue()[0])) this.returnValue = new float[] {1};
                else this.returnValue = new float[] {0};
            break;
            case NOT_EQUAL:
                if(!(a.getReturnValue()[0] != b.getReturnValue()[0])) this.returnValue = new float[] {1};
                else this.returnValue = new float[] {0};
            break;
            case LESS_THAN:
                    FeatureManager.logger.log(a.getReturnValue().length);
                    if(!(a.getReturnValue()[0] < b.getReturnValue()[0])) this.returnValue = new float[] {1};
                    else this.returnValue = new float[] {0};
            break;
            case GREATER_THAN:
                if(!(a.getReturnValue()[0] > b.getReturnValue()[0])) this.returnValue = new float[] {1};
                else this.returnValue = new float[] {0};
            break;
            case LESS_EQUAL_THAN:
                if(!(a.getReturnValue()[0] <= b.getReturnValue()[0])) this.returnValue = new float[] {1};
                else this.returnValue = new float[] {0};
            break;
            case GREATER_EQUAL_THAN:
                if(!(a.getReturnValue()[0] >= b.getReturnValue()[0])) this.returnValue = new float[] {1};
                else this.returnValue = new float[] {0};
            break;
            default:
                this.returnValue = new float[] {0};
        }
    }

    public String toString() {
        if(this.type == Type.FUNC_CALL) return this.function.toString();
        else return this.a.toString() + " " + this.type.name() + " " + this.b.toString();
    }
}
