pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name= "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "player_mover"
include("common")
include("velocity")
include("paper")
include("fabric")
