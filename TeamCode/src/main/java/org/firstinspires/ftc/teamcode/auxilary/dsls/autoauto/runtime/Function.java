package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

public class Function {
    public String name;
    public int argCount;

    public void setName(String n) {
        this.name = n;
    }

    public void setArgCount(int a) {
        this.argCount = a;
    }

    public float[] call(float[][] argsResolved) {
        return new float[] {0f};
    }
}
