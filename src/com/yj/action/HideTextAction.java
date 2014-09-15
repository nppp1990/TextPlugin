package com.yj.action;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlRecursiveElementVisitor;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;

/**
 * TODO 在这里编写类的功能描述
 *
 * @author yuanjian
 * @version 1.0
 * @created 2014-09-15
 */
public class HideTextAction extends BaseAndroidGenerateCodeAction {
    public HideTextAction() {
        super(null);
    }

    @Override
    public void actionPerformedImpl(@NotNull final Project project, final Editor editor) {
        final PsiFile f = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (f == null) return;
        new WriteCommandAction.Simple(project, "test", f) {
            @Override
            protected void run() throws Throwable {
                if (f instanceof XmlFile) {
                    f.accept(new XmlRecursiveElementVisitor() {
                        @Override
                        public void visitXmlElement(XmlElement element) {
                            super.visitXmlElement(element);
                            if (element instanceof XmlTag) {
                                String name = ((XmlTag) element).getName();
                                if (names.contains(name)) {
                                    if (((XmlTag) element).getAttribute("android:text") != null &&
                                            TEXT.equals(((XmlTag) element).getAttributeValue("android:text"))) {
                                        // 怎样删除呢
                                        ((XmlTag) element).setAttribute("android:text", "");
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }.execute();

    }
}
