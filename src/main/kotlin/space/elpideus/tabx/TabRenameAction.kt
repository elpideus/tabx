package space.elpideus.tabx

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages

class TabRenameAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val service = TabXService.getInstance(project)
        val currentCustomName = service.getCustomName(file.url)
        val originalName = file.name

        val newName = Messages.showInputDialog(
            project,
            "Enter new name for tab (leave empty to reset):",
            "Rename Tab",
            Messages.getQuestionIcon(),
            currentCustomName ?: originalName,
            null
        )

        if (newName != null) {
            service.setCustomName(file.url, newName)
            val fileEditorManager = FileEditorManager.getInstance(project)
            fileEditorManager.updateFilePresentation(file)
        }
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        e.presentation.isEnabledAndVisible = project != null && file != null
    }
}