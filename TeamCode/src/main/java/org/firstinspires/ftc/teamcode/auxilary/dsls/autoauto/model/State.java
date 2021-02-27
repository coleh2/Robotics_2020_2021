package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model;

import org.firstinspires.ftc.teamcode.auxilary.dsls.ParserTools;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements.Statement;
import org.jetbrains.annotations.NotNull;

public class State {
    public Statement[] statements;

    public State(String src) {
        String[] statementSources = ParserTools.groupAwareSplit(src, ',');

        this.statements = new Statement[statementSources.length];

        for(int i = statementSources.length - 1; i >= 0; i--) {
            statements[i] = Statement.createProperStatementType(statementSources[i]);
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
}
