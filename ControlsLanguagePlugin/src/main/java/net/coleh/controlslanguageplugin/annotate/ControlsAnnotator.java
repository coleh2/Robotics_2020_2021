package net.coleh.controlslanguageplugin.annotate;

import com.android.tools.r8.position.TextRange;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

public class ControlsAnnotator implements Annotator {
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
//TODO: MAKE THIS WORRK
//        if(element instanceof ControlsGotoStatement) {
//            ControlsGotoStatement gotoStatement = (ControlsGotoStatement) element;
//            String statepathName = gotoStatement.getLastChild().getText();
//            if(ControlsUtil.findStatepath(gotoStatement.getContainingFile(), statepathName) == null) {
//                holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved statepath \"" + statepathName + "\"")
//                        .range(element)
//                        .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
//                        .create();
//            }
//        }
    }
}
