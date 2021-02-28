package org.firstinspires.ftc.teamcode.unitTests.autoauto;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.junit.Test;

public class ParserTest {
    @Test
    public void runTest() {
        String testContent = "#test: doFunctionStep1(); doFunctionStepTwo(3); doFunctionStepThree(), after 30s skip -2";
        AutoautoProgram program = new AutoautoProgram(testContent);
        System.out.println(program.toString());
    }
}
