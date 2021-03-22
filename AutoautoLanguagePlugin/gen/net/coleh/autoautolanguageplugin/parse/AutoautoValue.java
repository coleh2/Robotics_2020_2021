// This is a generated file. Not intended for manual editing.
package net.coleh.autoautolanguageplugin.parse;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface AutoautoValue extends PsiElement {

  @Nullable
  AutoautoArrayLiteral getArrayLiteral();

  @Nullable
  AutoautoBoolean getBoolean();

  @NotNull
  List<AutoautoCommentOpportunity> getCommentOpportunityList();

  @Nullable
  AutoautoFunctionCall getFunctionCall();

  @Nullable
  AutoautoStringLiteral getStringLiteral();

  @Nullable
  AutoautoUnitValue getUnitValue();

  @Nullable
  AutoautoVariableReference getVariableReference();

}