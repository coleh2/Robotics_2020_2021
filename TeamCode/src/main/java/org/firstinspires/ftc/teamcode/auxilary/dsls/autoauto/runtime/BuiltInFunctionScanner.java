package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.runtime;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.firstinspires.ftc.teamcode.managers.ManipulationManager;
import org.firstinspires.ftc.teamcode.managers.MovementManager;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BuiltInFunctionScanner {


    public static BuiltInFunction[] scanAndLoadBuiltInFunctions(MovementManager driver, ManipulationManager manip) {
        ArrayList<BuiltInFunction> functions = new ArrayList<>();

        Method[] driverMethods = driver.getClass().getMethods();
        for(Method m : driverMethods) {
            if(!m.isVarArgs()) functions.add(new BuiltInFunction(m, driver));
        }

        Method[] manipMethods = manip.getClass().getMethods();
        for(Method m : manipMethods) {
            if(!m.isVarArgs()) functions.add(new BuiltInFunction(m, manip));
        }

        Method[] paulmathMethods = PaulMath.class.getMethods();
        for(Method m : paulmathMethods) {
            if(!m.isVarArgs()) functions.add(new BuiltInFunction(m, null));
        }

        return functions.toArray(new BuiltInFunction[0]);
    }
}
