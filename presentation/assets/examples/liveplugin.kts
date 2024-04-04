import com.intellij.openapi.actionSystem.AnActionEvent
import liveplugin.*

registerAction(id = "Insert New Line Above", keyStroke = "ctrl alt shift ENTER") { event ->
    val project = event.project ?: return@registerAction
    val editor = event.editor ?: return@registerAction
    executeCommand(editor.document, project) { document ->
        val caretModel = editor.caretModel
        val lineStartOffset = document.getLineStartOffset(caretModel.logicalPosition.line)
        document.insertString(lineStartOffset, "\n")
        caretModel.moveToOffset(caretModel.offset + 1)
    }
}
show("Loaded 'Insert New Line Above' action<br/>Use 'ctrl+alt+shift+Enter' to run it")