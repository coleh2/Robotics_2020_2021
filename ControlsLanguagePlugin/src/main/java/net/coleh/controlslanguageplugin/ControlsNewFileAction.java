package net.coleh.controlslanguageplugin;

import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;


public class ControlsNewFileAction extends CreateFileFromTemplateAction {

    public ControlsNewFileAction() {
        super("Controls File", "Create a new Controls file", ControlsIcons.FILE);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder
            .setTitle("New Controls file");
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return "Create Controls file " + newName;
    }

    @Override
    protected PsiFile createFile(String name, @NotNull String templateName, @NotNull PsiDirectory dir) {
        if(name.matches("[^\\w\\d_]")) {
            LOG.warn("Name must be alphanumeric!");
            return null;
        }
        return super.createFile(name, templateName, dir);
    }
}
