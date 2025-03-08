package com.github.kristensala.pinnedtabs.popup

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.FileEditorManager
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists

class PinFileAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProject = e.project;

        if (currentProject != null) {
            val fem = FileEditorManager.getInstance(currentProject);
            val projectBasePath = currentProject.basePath
            if (projectBasePath == null) {
                Logger.getInstance("PinnedTabs")
                    .warn("Could not find project base path")
                return
            }

            val projectLevelConfigDir = File(projectBasePath, ".idea")
            if (!projectLevelConfigDir.exists()) {
                Logger.getInstance("PinnedTabs")
                    .warn("Project level .idea folder does not exist")
                return
            }

            val pluginFilePath = File(projectBasePath, ".idea/.pinned_tabs")

            if (!pluginFilePath.exists()) {
                Logger.getInstance("PinnedTabs")
                    .info(".pinned_tabs file does not exist, creating one")

                try {
                    pluginFilePath.createNewFile();
                } catch (e: IOException) {
                    Logger.getInstance("PinnedTabs").error("Could not create .pinned_tabs file", e)
                    return
                }
            }

            val selectedFiles = fem.selectedFiles
            if (selectedFiles.isEmpty()) return

            val selectedFile = selectedFiles[0] ?: return

            if (!getPinnedFiles(pluginFilePath.toPath()).contains(selectedFile.path)) {
                File(pluginFilePath.toString()).appendText(System.lineSeparator() + selectedFile.path)
            }
        }
    }

    private fun getPinnedFiles(path: Path) : List<String> {
        return File(path.toString()).readLines()
    }
}
