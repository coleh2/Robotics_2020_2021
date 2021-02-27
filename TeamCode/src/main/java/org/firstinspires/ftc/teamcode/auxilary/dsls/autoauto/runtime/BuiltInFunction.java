package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BuiltInFunction extends Function {
    public String name;
    public int argCount;

    FeatureManager caller;
    Method method;

    public BuiltInFunction(Method javaMethod, FeatureManager m) {
        this.name = javaMethod.getName();
        this.argCount = javaMethod.getTypeParameters().length;

        this.caller = m;
        this.method = method;
    }

    public float[] call(Float[] methodArgs) {
        try {
            return (float[]) method.invoke(caller, (Object[]) methodArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new float[0];
    }
}
