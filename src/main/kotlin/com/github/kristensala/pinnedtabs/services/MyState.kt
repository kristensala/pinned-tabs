package com.github.kristensala.pinnedtabs.services

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "customfile", storages = [Storage("customfile.xml")])
class MyService(private val project: Project) : SimplePersistentStateComponent<MyService.FileListState>(FileListState()) {
    private var state = FileListState()

    var list: ArrayList<String>
        get() = state.list
        set(value) {
            state.list = value

        }

    companion object {
        fun getInstance(project: Project) = project.service<MyService>()
    }

    class FileListState : BaseState() {
        var list: ArrayList<String> = arrayListOf()
        var id: String = "test"
    }
}

