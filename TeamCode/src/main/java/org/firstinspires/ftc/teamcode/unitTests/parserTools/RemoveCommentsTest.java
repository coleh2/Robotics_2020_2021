package org.firstinspires.ftc.teamcode.unitTests.parserTools;

import org.firstinspires.ftc.teamcode.auxilary.PaulMath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RemoveCommentsTest {
    @Test
    public void runTest() {
        final String input = "not-comment /* comment */ not-comment";

        final String expectedOutput = "not-comment  not-comment";

        String result = ParserTools.removeComments(input);

        System.out.println("Result: " + result);

        //exit with a non-zero code to indicate an error
        assertEquals(expectedOutput, result);
    }
}
