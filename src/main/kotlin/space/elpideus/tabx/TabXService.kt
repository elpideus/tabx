package space.elpideus.tabx

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "TabXState", storages = [Storage("tabx.xml")])
class TabXService : PersistentStateComponent<TabXService.State> {

    data class State(var customNames: MutableMap<String, String> = mutableMapOf())

    private var myState = State()

    override fun getState(): State = myState

    override fun loadState(state: State) { myState = state }

    fun getCustomName(fileUrl: String): String? { return myState.customNames[fileUrl] }

    fun setCustomName(fileUrl: String, name: String?) {
        if (name.isNullOrBlank()) myState.customNames.remove(fileUrl)
        else myState.customNames[fileUrl] = name
    }

    companion object {
        fun getInstance(project: Project): TabXService = project.service()
    }
}