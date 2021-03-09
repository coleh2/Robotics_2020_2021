package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.AutoautoProgram;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.State;
import org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.Statepath;

public class Statement {
    public String subject;
    public String predicate;

    public AutoautoProgram program;
    public Statepath statepath;
    public State state;

    public Statement(AutoautoProgram program, Statepath statepath, State state) {
        this.program = program;
        this.statepath = statepath;
        this.state = state;
    }

    public Statement(String src) {

    }

    public Statement() {}

    public void loop() {}
    public void stepInit() {}
    public void init() {}

    public static Statement createProperStatementType(String src, AutoautoProgram program, Statepath statepath, State state) {
        String tr = src.trim();

        int firstWordIdx = tr.indexOf(' ');
        if(tr.indexOf('(') > -1 && tr.indexOf('(') < firstWordIdx) firstWordIdx = tr.indexOf('(');
        firstWordIdx = firstWordIdx < 0 ? tr.length() : firstWordIdx;

        String firstWord = tr.substring(0,Math.max(0, firstWordIdx));
        switch (firstWord) {
            case "let": return new LetStatement(tr, program, statepath, state);
            case "next": return new NextStatement(tr, program, statepath, state);
            case "goto":  return new GotoStatement(tr, program, statepath, state);
            case "skip": return new SkipStatement(tr, program, statepath, state);
            case "after": return new AfterStatement(tr, program, statepath, state);
            case "if": return new IfStatement(tr, program, statepath, state);
            default: return new FunctionCallStatement(tr, program, statepath, state);
        }
    }
}
