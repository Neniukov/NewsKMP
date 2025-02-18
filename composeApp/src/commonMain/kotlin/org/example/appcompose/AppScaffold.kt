package org.example.appcompose

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.appcompose.articles.presentation.details.ArticleDetailsScreen
import org.example.appcompose.articles.presentation.list.ArticlesScreen
import org.example.appcompose.navigation.Screen
import org.example.appcompose.utils.ShareHelper

@Composable
fun AppScaffold(shareHelper: ShareHelper) {
    val navController: NavHostController = rememberNavController()
    Surface {
        NavHost(
            navController = navController,
            startDestination = Screen.Articles
        ) {
            composable<Screen.Articles> {
                ArticlesScreen(
                    onAboutButtonClick = { },
                    onArticlesDetailsClick = { articleId ->
                        navController.navigate(Screen.ArticleDetails(articleId))
                    }
                )
            }
            composable<Screen.ArticleDetails> { backStackEntry ->
                val articleId = backStackEntry.arguments?.getString("articleId")
                articleId?.let {
                    ArticleDetailsScreen(
                        articleId = it,
                        shareHelper = shareHelper,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }

    }
}