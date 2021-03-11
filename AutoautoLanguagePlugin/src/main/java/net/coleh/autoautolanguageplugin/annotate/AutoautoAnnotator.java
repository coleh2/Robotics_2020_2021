package net.coleh.autoautolanguageplugin.annotate;

import com.android.tools.r8.position.TextRange;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;

import net.coleh.autoautolanguageplugin.AutoautoUtil;
import net.coleh.autoautolanguageplugin.parse.AutoautoGotoStatement;

import org.jetbrains.annotations.NotNull;

public class AutoautoAnnotator implements Annotator {
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
//TODO: MAKE THIS WORRK
//        if(element instanceof AutoautoGotoStatement) {
//            AutoautoGotoStatement gotoStatement = (AutoautoGotoStatement) element;
//            String statepathName = gotoStatement.getLastChild().getText();
//            if(AutoautoUtil.findStatepath(gotoStatement.getContainingFile(), statepathName) == null) {
//                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved statepath \"" + statepathName + "\"")
//                        .range(element)
//                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
//                        .create();
//            }
//        }
    }
}
