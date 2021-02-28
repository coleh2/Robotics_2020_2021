package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.values.FunctionCall;
import org.firstinspires.ftc.teamcode.managers.FeatureManager;
import org.jetbrains.annotations.NotNull;

public class State {
    public Statement[] statements;
    public AutoautoProgram program;
    public Statepath statepath;

    public State(String src, AutoautoProgram program, Statepath statepath) {
        this.program = program;
        this.statepath = statepath;

        String[] statementSources = ParserTools.groupAwareSplit(src, ',', true);

        this.statements = new Statement[statementSources.length];

        for(int i = statementSources.length - 1; i >= 0; i--) {
            statements[i] = Statement.createProperStatementType(statementSources[i], program, statepath, this);
        }
    }

    @NotNull
    public String toString() {
        StringBuilder statementsStr = new StringBuilder();
        for(Statement statement : statements) {
            statementsStr.append(statement.toString() + ", ");
        }
        return statementsStr.toString();
    }

    public void stepInit() {
        for(Statement s : statements) {
            if(s != null) s.stepInit();
        }
        //stop when new steps start
        program.autoautoRuntime.functions.get(new FunctionCall("stopDrive()")).call(new float[0][0]);
    }

    public void init() {
        for(Statement s : statements) {
            if(s != null) s.init();
        }
    }

    public void loop() {
        for(Statement s : statements) {
            if(s != null) s.loop();
        }
    }
}
