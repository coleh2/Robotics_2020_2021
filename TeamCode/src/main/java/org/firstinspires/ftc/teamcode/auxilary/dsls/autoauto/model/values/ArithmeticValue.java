package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values;

public class ArithmeticValue extends Value {
    String operator;
    Value right;
    Value left;

    //TODO: parsing!!
    public ArithmeticValue(Value left, String operator, Value right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public void init() {
        left.setRuntimeReferences(this.runtimeFunctionStore, this.runtimeVariableStore);
        left.init();

        right.setRuntimeReferences(this.runtimeFunctionStore, this.runtimeVariableStore);
        right.init();
    }

    public void loop() {
        left.loop();
        right.loop();
        //TODO: make it work on arrays
        //TODO: not just multiplication
        switch(operator) {
            case "*":
                this.returnValue = new float[] { left.getReturnValue()[0] * right.getReturnValue()[0] };
                break;
        }

    }

    @Override
    public float[] getReturnValue() {
        return this.returnValue;
    }
}
