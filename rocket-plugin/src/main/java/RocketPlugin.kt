import detekt.configDetekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import publish.configPublish

@Suppress("unused")
class RocketPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.configDetekt()
        target.configPublish()
    }
}
