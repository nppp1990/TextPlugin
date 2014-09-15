/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.yj.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuanjian
 * @version 1.0
 * @created 2014-09-15
 */
public class BaseAndroidGenerateCodeAction extends BaseGenerateAction {
    public static final String[] NAMES = {"EditText", "TextView", "Button"};
    protected static String TEXT = "test text";
    protected static List<String> names;

    static {
        names = Arrays.asList(NAMES);
    }

    public BaseAndroidGenerateCodeAction(final CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    protected boolean isValidForFile(@NotNull final Project project, @NotNull final Editor editor, @NotNull final PsiFile file) {
        if (!(file instanceof XmlFile)) return false;
        XmlTag contextTag = getContextTag(editor, file);
        return contextTag != null && contextTag.getDescriptor() != null;
//        return true;
    }

//    @Override
//    protected boolean isValidForClass(PsiClass targetClass) {
////        return super.isValidForClass(targetClass);
//        return true;
//    }

    @Nullable
    private static XmlTag getContextTag(Editor editor, PsiFile file) {
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        XmlTag tag = null;
        if (element != null) {
            tag = PsiTreeUtil.getParentOfType(element, XmlTag.class);
        }
        if (tag == null) {
            tag = ((XmlFile) file).getRootTag();
        }
        return tag;
    }
}
