package net.coleh.autoautolanguageplugin;

import com.intellij.ide.actions.CreateFileAction;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;


public class AutoautoNewFileAction extends CreateFileAction {

    public AutoautoNewFileAction() {
        super("Autoauto File", "Create a new Autoauto file", AutoautoIcons.FILE);
    }

    @Override
    protected PsiElement[] create(@NotNull String newName, PsiDirectory directory) throws Exception {
        if(newName.endsWith(".autoauto")) return super.create(newName, directory);
        else return super.create(newName + ".autoauto", directory);
    }

}
