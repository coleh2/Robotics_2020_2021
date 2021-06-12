// This is a generated file. Not intended for manual editing.
package net.coleh.controlslanguageplugin.parse.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.coleh.controlslanguageplugin.parse.ControlsTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.coleh.controlslanguageplugin.parse.*;

public class ControlsSomethingImpl extends ASTWrapperPsiElement implements ControlsSomething {

  public ControlsSomethingImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull ControlsVisitor visitor) {
    visitor.visitSomething(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ControlsVisitor) accept((ControlsVisitor)visitor);
    else super.accept(visitor);
  }

}
