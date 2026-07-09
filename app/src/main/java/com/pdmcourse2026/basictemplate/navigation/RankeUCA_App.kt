package com.pdmcourse2026.basictemplate.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdmcourse2026.basictemplate.screens.home.HomeScreen
import com.pdmcourse2026.basictemplate.screens.results.ResultsScreen
import com.pdmcourse2026.basictemplate.screens.options.OptionsScreen

import com.pdmcourse2026.basictemplate.screens.home.MenuScreen
import com.pdmcourse2026.basictemplate.screens.questions.QuestionsScreen

@Composable
fun RankeUCA_App(
    userName: String?,
    onLogout: () -> Unit
) {
    val backStack = rememberNavBackStack(Routes.Menu)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Routes.Menu> {
                MenuScreen(
                    userName = userName,
                    onLogout = onLogout,
                    onNavigateToAdmin = { backStack.add(Routes.AdminQuestions) }
                )
            }
            entry<Routes.AdminQuestions> {
                QuestionsScreen(
                    onQuestionClick = { questionId -> 
                        backStack.add(Routes.AdminOptions(questionId))
                    }
                )
            }
            entry<Routes.AdminOptions> {
                OptionsScreen(questionId = it.questionId)
            }
            entry<Routes.Home> {
                HomeScreen(
                    onNavigateToResults = {
                        backStack.add(Routes.Results)
                    },
                    onNavigateToAdmin = {
                        backStack.add(Routes.AdminQuestions)
                    }
                )
            }
            entry<Routes.Results> {
                ResultsScreen(
                    onNavigateBack = {
                        backStack.removeLastOrNull()
                    }
                )
            }
        },
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(500)
            )
        },
        popTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(500)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(500)
            )
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(250)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(250)
            )
        }
    )
}