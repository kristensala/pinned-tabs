package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class TogglePopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (e.project != null) {
            val p = e.project!!
            PinnedTabsPopupComponent(p).show();
        }
    }
}