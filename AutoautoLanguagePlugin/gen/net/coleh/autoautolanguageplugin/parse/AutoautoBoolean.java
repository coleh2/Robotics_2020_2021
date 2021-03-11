// This is a generated file. Not intended for manual editing.
package net.coleh.autoautolanguageplugin.parse;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface AutoautoBoolean extends PsiElement {

  @Nullable
  AutoautoComparisonOperator getComparisonOperator();

  @Nullable
  AutoautoFunctionCall getFunctionCall();

  @NotNull
  List<AutoautoNonBooleanValue> getNonBooleanValueList();

}
