package com.github.kristensala.pinnedtabs.dialog

import com.github.kristensala.pinnedtabs.services.MyService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.ui.components.JBList
import java.awt.KeyEventDispatcher
import java.awt.KeyboardFocusManager
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.swing.DefaultListModel
import javax.swing.KeyStroke

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
            val fem = FileEditorManager.getInstance(currentProject);
            val currentFile = fem.selectedFiles
            if (currentFile.isNotEmpty()) {
                MyService.getInstance(currentProject).list.add(currentFile[0].path)
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

class PinnedTabsPopupComponent(project: Project) {
    private var popup: JBPopup? = null
    private var list = JBList<String>()
    private var builder: PopupChooserBuilder<String> = PopupChooserBuilder(this.list)

    private var _project: Project = project;

    init {
        this.builder
            .setTitle("Pinned Tabs")
            .setMovable(true)
            .setResizable(true)
            .setItemChosenCallback(Runnable {
                openSelectedFile(_project)
            })
    }

    fun show() {
        if (popup != null) {
            return
        }

        val dataModel = DefaultListModel<String>()
        MyService.getInstance(_project).list.forEach {
            dataModel.addElement(it)
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

    // ex (fileName.cs) path grayed out
    // SubmitApplication.cs (src/test/asdf/asdf/submitApplication.cs)
    // SubmitApplication.cs (src/test/asdf/asdf/submitApplication.cs)
    private fun buildPinnedFileList(): String {
        //state.state.list
        //state.list = JBList<String>();
        return ""
    }

    private fun openSelectedFile(project: Project){
        var selectedFile = list.selectedValue
        val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(selectedFile))
        if (virtualFile != null) {
            FileEditorManager.getInstance(project).openFile(virtualFile)
        }
    }
}

class CustomKeyListener(
    var project: Project,
    var list: JBList<String>
) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_J -> moveDown()
            KeyEvent.VK_K -> moveUp()
            KeyEvent.VK_D -> onRemove()
        }

    }

    override fun keyReleased(e: KeyEvent?) {
        TODO("Not yet implemented")
    }
    private fun onRemove() {
        val selectedIdx = list.selectedIndex
        MyService.getInstance(project).list.removeAt(selectedIdx)
    }

    private fun moveDown() {
        val listSize = list.itemsCount
        val selectedIndex = list.selectedIndex
        if (selectedIndex < listSize - 1) {
            list.selectedIndex = selectedIndex + 1
        }

        if (selectedIndex == listSize - 1) {
            list.selectedIndex = 0
        }

        list.ensureIndexIsVisible(list.selectedIndex)
    }

    private fun moveUp() {
        val listSize = list.itemsCount
        val selectedIndex = list.selectedIndex
        if (selectedIndex == 0) {
            list.selectedIndex = listSize - 1
        } else {
            list.selectedIndex = selectedIndex - 1
        }
        list.ensureIndexIsVisible(list.selectedIndex)
    }

}
