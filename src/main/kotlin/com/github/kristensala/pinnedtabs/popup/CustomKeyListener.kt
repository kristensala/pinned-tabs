package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBList
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.swing.DefaultListModel
import kotlin.io.path.Path

class CustomKeyListener(
    var project: Project,
    var list: JBList<VirtualFile>
) : KeyListener {
    override fun keyTyped(e: KeyEvent?) {
    }

    override fun keyPressed(e: KeyEvent?) {
        when (e?.keyCode) {
            KeyEvent.VK_J -> moveDown()
            KeyEvent.VK_K -> moveUp()
            KeyEvent.VK_D -> onRemove()
            KeyEvent.VK_S -> onSettingsPress()
        }

    }

    override fun keyReleased(e: KeyEvent?) {
    }

    private fun onSettingsPress(){
        val pluginFilePath = Path(project.basePath.toString(), ".idea/.pinned_tabs").toFile()
        val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(pluginFilePath)
        if (virtualFile != null) {
            FileEditorManager.getInstance(project).openFile(virtualFile)
            // todo: close the popup
        }
    }

    private fun onRemove() {
        val pluginFilePath = Path(project.basePath.toString(), ".idea/.pinned_tabs")
        var lines = File(pluginFilePath.toString()).readLines()
        var linesFinal = lines.filter {
                line -> line != list.selectedValue.path && line.isNotEmpty()}

        File(pluginFilePath.toString()).writeText(linesFinal.joinToString("\n"))
        val dataModel = DefaultListModel<VirtualFile>()

        File(pluginFilePath.toString()).readLines().forEach {
            if (it.isNotEmpty()) {
                val virtualFile = LocalFileSystem.getInstance().findFileByIoFile(File(it))
                if (virtualFile != null) {
                    dataModel.addElement(virtualFile)
                }
            }
        }
        this.list.model = dataModel
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
