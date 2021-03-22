package net.coleh.autoautolanguageplugin.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;

import net.coleh.autoautolanguageplugin.parse.AutoautoTypes;

import org.jetbrains.annotations.NotNull;

public class AutoautoCompletionContributor extends CompletionContributor {
    public AutoautoCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(AutoautoTypes.FUNCTION_CALL),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               @NotNull ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {



                    }
                }
        );

    }
    @Override
    public void fillCompletionVariants(CompletionParameters parameters, CompletionResultSet resultSet) {
        if(!parameters.getCompletionType().equals(CompletionType.BASIC)) return;

        for(AutoautoBuiltinFunctionCompletions.AutoautoBuiltinFunctionRecord record  : AutoautoBuiltinFunctionCompletions.records) resultSet.addElement(new AutoautoFunctionLookupElement(record));

        resultSet.addElement(LookupElementBuilder.create(parameters.getOriginalPosition().getParent().toString()));
    }
}