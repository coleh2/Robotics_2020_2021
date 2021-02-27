package org.firstinspires.ftc.teamcode.auxilary.dsls.autoauto.model.statements;

public class Statement {
    public String subject;
    public String predicate;

    public Statement(String src) {

    }

    public Statement() {}

    public void loop() {}
    public void stepInit() {}
    public void init() {}

    public static Statement createProperStatementType(String src) {
        String tr = src.trim();

        String firstWord = tr.substring(0,Math.max(0, tr.indexOf(' ')));
        switch (firstWord) {
            case "let": return new LetStatement(tr);
            case "next": return new NextStatement(tr);
            case "goto":  return new GotoStatement(tr);
            case "skip": return new SkipStatement(tr);
            case "after": return new AfterStatement(tr);
            default: return new FunctionCallStatement(tr);
        }
    }
}
