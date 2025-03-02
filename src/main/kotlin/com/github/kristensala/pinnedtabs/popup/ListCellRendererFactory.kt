package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.SimpleTextAttributes
import com.intellij.util.IconUtil
import javax.swing.JList

class ListCellRendererFactory(val project: Project) : ColoredListCellRenderer<VirtualFile>() {
    override fun customizeCellRenderer(
        p0: JList<out VirtualFile>,
        p1: VirtualFile?,
        p2: Int,
        p3: Boolean,
        p4: Boolean
    ) {
        if (p1 != null) {
            icon = (IconUtil.getIcon(p1, Iconable.ICON_FLAG_READ_STATUS, project))
            append(p1.name, SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES)
            append(" (${p1.path})", SimpleTextAttributes.GRAY_ATTRIBUTES)
        }
    }
}
