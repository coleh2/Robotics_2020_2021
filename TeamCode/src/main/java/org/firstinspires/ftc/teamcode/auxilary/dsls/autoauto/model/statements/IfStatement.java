package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.BooleanOperator;

public class IfStatement extends Statement {
    BooleanOperator conditional;
    Statement subject;

    public IfStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        src = src.substring("if ".length());
        int endingParen = ParserTools.groupAwareIndexOf(src, ')');
        int startingParen = src.indexOf('(');

        String conditionalSrc = src.substring(startingParen + 1, endingParen);
        conditional = new BooleanOperator(conditionalSrc);

        this.subject = Statement.createProperStatementType(src.substring(endingParen + 1), program, statepath, state);
    }

    public void loop() {
        conditional.loop();
        if(conditional.returnValue[0] > 0) subject.loop();
    }
}
