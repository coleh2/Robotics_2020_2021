package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.BooleanOperator;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.NumericValue;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.Value;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;

import java.util.Arrays;

public class IfStatement extends Statement {
    BooleanOperator conditional;
    Statement subject;

    public IfStatement(Value v, Statement s) {
        conditional = new BooleanOperator(v, new NumericValue(0), ">");
        subject = s;
    }
    public IfStatement(BooleanOperator b, Statement s) {
        conditional = b;
        subject = s;
    }

    public void setProgram(AutoautoProgram program) {
        super.setProgram(program);
        subject.setProgram(program);
    }

    public void setStatepath(Statepath statepath) {
        super.setStatepath(statepath);
        subject.setStatepath(statepath);
    }

    public void setState(State state) {
        super.setState(state);
        subject.setState(state);
    }

    public IfStatement(String src, AutoautoProgram program, Statepath statepath, State state) {
        super(program, statepath, state);
        src = src.substring("if".length());
        src = src.trim();

        int endingParen = ParserTools.groupAwareIndexOf(src, ')');
        int startingParen = src.indexOf('(');

        if(endingParen == -1 || startingParen == -1) FeatureManager.logger.log("[AUTOAUTO ERROR] Missing " + (endingParen == -1 ? "ending" : "starting") + " paren in `if` statement: " + src);

        String conditionalSrc = src.substring(startingParen + 1, endingParen);
        conditional = new BooleanOperator(conditionalSrc);

        this.subject = Statement.createProperStatementType(src.substring(endingParen + 1), program, statepath, state);
    }

    public void init() {
        conditional.setRuntimeReferences(program.autoautoRuntime.functions, program.autoautoRuntime.variables);
        conditional.init();

        subject.init();
    }

    public void loop() {
        conditional.loop();
//        FeatureManager.logger.log("[AuAu] if statement logs: " + Arrays.toString(conditional.getReturnValue()));
        if(conditional.getReturnValue()[0] > 0) subject.loop();
    }
    public String toString() {
        return "if (" + conditional.toString() + ") " + subject.toString();
    }
}
