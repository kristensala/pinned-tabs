package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBList
import java.io.File
import javax.swing.DefaultListModel
import kotlin.io.path.Path
import kotlin.io.path.exists

class PinnedTabsPopupComponent(project: Project) {
    private var popup: JBPopup? = null
    private var list = JBList<VirtualFile>()
    private var builder: PopupChooserBuilder<VirtualFile> = PopupChooserBuilder(this.list)

    private var _project: Project = project;

    init {
        this.builder
            .setTitle("Pinned Tabs")
            .setMovable(true)
            .setResizable(true)
            .setItemChosenCallback(Runnable {
                openSelectedFile(_project)
            })

        list.cellRenderer = ListCellRendererFactory(_project)
    }

    fun show() {
        if (popup != null) {
            return
        }

        val dataModel = DefaultListModel<VirtualFile>()
        val pinnedTabsFile = Path(_project.basePath.toString(), ".idea/.pinned_tabs")

        if (pinnedTabsFile.exists()) {
            File(pinnedTabsFile.toString()).readLines().forEach {
                if (it.isNotEmpty()) {
                    val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(it))
                    if (virtualFile != null) {
                        dataModel.addElement(virtualFile)
                    }
                }
            }
        }

        this.list.model = dataModel
        this.list.addKeyListener(CustomKeyListener(_project, list))
        popup = builder.createPopup()

        popup?.showCenteredInCurrentWindow(_project)
    }

    fun close() {
        if (popup != null) {
            popup?.dispose()
            popup?.cancel()
            popup = null
        }
    }

    private fun openSelectedFile(project: Project){
        var selectedFile = list.selectedValue
        if (selectedFile != null) {
            FileEditorManager.getInstance(project).openFile(selectedFile, true)
        }
    }
}

