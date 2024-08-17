package io.writerme.app.navigation

import io.writerme.resources.common.NavigationGraphTree
import ru.alexgladkov.odyssey.compose.extensions.flow
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder

fun RootComposeBuilder.navigationLaunchGraph() {
    onboardingFlow()
    homeFlow()
}

private fun RootComposeBuilder.onboardingFlow() {
    // screen(NavigationGraphTree.CREATE_ACCOUNT.name) { CreateAccountScreen.Content() }
}

private fun RootComposeBuilder.homeFlow() {
    flow(NavigationGraphTree.HOME_FLOW.name) {
        // screen(NavigationGraphTree.HOME.name) { HomeScreen.Content() }
    }
}
