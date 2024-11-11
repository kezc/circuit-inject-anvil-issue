package com.example.kotlininjecttest

import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

/**
 * This component interface is added to the kotlin-inject component using the scope
 * [AppScope] automatically.
 */
@ContributesTo(AppScope::class)
interface ApplicationIdProviderComponent {
    /**
     * Provides the [ApplicationIdProvider] from the kotlin-inject object graph.
     */
    val applicationIdProvider: ApplicationIdProvider
}

/**
 * API to get the application ID. There are different implementations for Android and iOS and
 * their implementations are bound to this interface through code-gen.
 */
interface ApplicationIdProvider {
    /**
     * The application ID. This string is provided by the host platform.
     */
    val appId: String
}
