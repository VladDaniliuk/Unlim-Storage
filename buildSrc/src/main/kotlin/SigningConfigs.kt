import org.gradle.api.NamedDomainObjectContainer
import org.gradle.plugin.use.PluginDependenciesSpec

private const val SIGNING_DEBUG = "debug"
private const val SIGNING_RELEASE = "release"

fun <T> NamedDomainObjectContainer<out T>.debug(apkSignInConfig: T.() -> Unit) {
	getByName(SIGNING_DEBUG) {
		apkSignInConfig()
	}
}

fun <T> NamedDomainObjectContainer<out T>.release(apkSignInConfig: T.() -> Unit) {
	create(SIGNING_RELEASE) {
		apkSignInConfig()
	}
}

val <T> NamedDomainObjectContainer<out T>.debug: T
	get() = getByName(SIGNING_DEBUG)

val <T> NamedDomainObjectContainer<out T>.release: T
	get() = getByName(SIGNING_RELEASE)

fun PluginDependenciesSpec.addAll() {
	id(Plugin.application)
	id(Plugin.hilt)
	id(Plugin.kotlinAndroid)
	id(Plugin.googleServices)
	id(Plugin.kotlinKapt)
}
