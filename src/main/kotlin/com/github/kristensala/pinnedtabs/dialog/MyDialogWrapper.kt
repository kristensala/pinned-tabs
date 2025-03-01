package com.github.kristensala.pinnedtabs.dialog

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.ui.components.JBList
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.KeyEventDispatcher
import java.awt.event.KeyEvent
import java.io.File
import javax.swing.DefaultListModel
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class ShowDialogSampleAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (e.project != null) {
            val p = e.project!!
            PinnedTabsPopupComponent(p).show();
        }
        /*if (PinnedTabs().isShowing) {
            PinnedTabs().close(0)
            return
        }
        PinnedTabs().show()*/
    }
}

class PinFileAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project;
        if (currentProject != null) {
            var path = currentProject.basePath;
            val fem = FileEditorManager.getInstance(currentProject);
            // we want to save current file into some sort of
            // a local project file under .idea/ folder
            val currentFile = fem.selectedFiles
            if (currentFile.isNotEmpty()) {
                //File(currentProject.projectFile).writeText(currentFile[0].path)
            }
        }

    }

}

//https://plugins.jetbrains.com/docs/intellij/settings-tutorial.html#creating-the-appsettingscomponent-implementation
//https://raw.githubusercontent.com/mustah/TabSwitch/refs/heads/master/src/org/intellij/ideaplugins/tabswitch/TabSwitchProjectComponent.java
/*@State(
    name = "org.intellij.sdk.settings.AppSettings",
    storages = @Storage("SdkSettingsPlugin.xml")
)*/

class PinnedTabsPopupComponent(project: Project) : KeyEventDispatcher {
    private var popup: JBPopup? = null
    private var list = JBList<String>()
    private var builder: PopupChooserBuilder<String> = PopupChooserBuilder(this.list)

    private var _project: Project = project;

    init {
        this.builder
            .setTitle("Pinned Tabs")
            .setMovable(true)
            .setResizable(true)

    }

    fun show() {
        if (popup != null) {
            return
        }

        val dataModel = DefaultListModel<String>()
        dataModel.addElement("asdf")
        dataModel.addElement("asdf")
        dataModel.addElement("asdf")
        this.list.model = dataModel

        //val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File("path"))

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

    // ex (fileName.cs) path grayed out
    // SubmitApplication.cs (src/test/asdf/asdf/submitApplication.cs)
    // SubmitApplication.cs (src/test/asdf/asdf/submitApplication.cs)
    private fun buildPinnedFileList(): String {
        return ""
    }

    override fun dispatchKeyEvent(e: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}

class PinnedTabs : DialogWrapper(true) {
    init {
        init()
        title = "Pinned Tabs"
        isModal = false
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(BorderLayout())
        val label = JLabel("Pinned tabs")
        label.preferredSize = Dimension(500, 200)
        panel.add(label, BorderLayout.CENTER)

        return panel
    }

}
