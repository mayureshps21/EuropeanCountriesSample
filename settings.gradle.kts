pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CountryListSample"
include(":app")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":features:home")
include(":features:detail")
