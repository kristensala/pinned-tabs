package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists

class PinFileAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project;
        if (currentProject != null) {
            //val bath =
            val fem = FileEditorManager.getInstance(currentProject);
            val projectBasePath = currentProject.basePath
            if (projectBasePath != null) {
                var pluginFilePath = Path(projectBasePath, ".idea/.pinned_tabs")
                if (!pluginFilePath.exists()) {
                    pluginFilePath.createFile()
                }

                val currentFile = fem.selectedFiles
                if (currentFile.isNotEmpty()) {
                    if (!getPinnedFiles(pluginFilePath).contains(currentFile[0].path)) {
                        File(pluginFilePath.toString()).appendText(currentFile[0].path + System.lineSeparator())
                    }
                }
            }

        }
    }

    private fun getPinnedFiles(path: Path) : List<String> {
        return File(path.toString()).readLines()
    }
}
