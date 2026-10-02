import com.android.build.api.dsl.LibraryExtension
import com.lihan.convention.ExtensionType
import com.lihan.convention.configureBuildTypes
import com.lihan.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            with(pluginManager) {
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension>{

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }
}