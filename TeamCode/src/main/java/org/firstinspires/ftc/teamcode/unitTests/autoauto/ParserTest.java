package org.firstinspires.ftc.teamcode.unitTests.autoauto;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.junit.Test;

public class ParserTest {
    @Test
    public void runTest() {
        String testContent = "#init:     driveOmni(0, 1, 0), after 1s next;     log(4.0)";
        AutoautoProgram program = new AutoautoProgram(testContent);
        System.out.println(program.toString());
    }
}
