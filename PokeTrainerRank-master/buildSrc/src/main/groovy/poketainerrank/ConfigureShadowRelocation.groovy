package poketainerrank

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import java.util.jar.JarFile

/*
    From https://github.com/johnrengelman/shadow/blob/master/src/main/groovy/com/github/jengelman/gradle/plugins/shadow/tasks/ConfigureShadowRelocation.groovy
    Modify for add exclude
 */


class ConfigureShadowRelocation extends DefaultTask {

    @Input
    ShadowJar target

    @Input
    String prefix = "shadow"

    Set<String> excludes = [] as Set<String>

    def exclude(String... excludes) {
        this.excludes.addAll(excludes);
    }

    @InputFiles
    @Optional
    List<Configuration> getConfigurations() {
        return target.configurations
    }

    @TaskAction
    void configureRelocation() {
        def packages = [] as Set<String>
        configurations.each { configuration ->
            configuration.files.each { jar ->
                JarFile jf = new JarFile(jar)
                jf.entries().each { entry ->
                    if (entry.name.endsWith(".class")) {
                        packages << entry.name[0..entry.name.lastIndexOf('/') - 1].replaceAll('/', '.')
                    }
                }
                jf.close()
            }
        }
        packages.each { it->
            if(!excludes.stream().anyMatch{s->it.startsWith(s)}) {
                target.relocate(it, "${prefix}.${it}")
            }
        }

    }

    static String taskName(Task task) {
        return "configureRelocation${task.name.capitalize()}"
    }

}