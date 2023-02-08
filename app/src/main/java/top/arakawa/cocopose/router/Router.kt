package top.arakawa.cocopose.router

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import top.arakawa.cocopose.screen.Coordinator1Screen
import top.arakawa.cocopose.screen.OverviewScreen

/**
 * Created by JBY on 2023/2/8.
 */
interface Router {
    val route: String
    fun register(builder: NavGraphBuilder, controller: NavHostController)

    companion object {
        val routers = listOf<Router>(
            Overview,
            Coordinator1
        )
    }
}


object Overview : Router {
    override val route = "overview"
    override fun register(builder: NavGraphBuilder, controller: NavHostController) {
        val titles = arrayListOf<String>(
            "SimpleCoordinatorLayout"
        )
        builder.composable(route) {
            OverviewScreen(
                Modifier,
                titles
            ) {
                when (titles[it]) {
                    "SimpleCoordinatorLayout" -> controller.navigate(Coordinator1.route)
                }

            }
        }
    }
}

object Coordinator1 : Router {
    override val route: String
        get() = "Coordinator1"

    override fun register(builder: NavGraphBuilder, controller: NavHostController) {
        builder.composable(Coordinator1.route) {
            Coordinator1Screen(Modifier)
        }
    }

}
