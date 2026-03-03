package es.fplumara.dam2.rinconessecretos.navegation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import es.fplumara.dam2.rinconessecretos.ui.screen.CrearRinconesRoute
import es.fplumara.dam2.rinconessecretos.ui.screen.RinconDetalleRoute
import es.fplumara.dam2.rinconessecretos.ui.screen.RinconesEditRoute
import es.fplumara.dam2.rinconessecretos.ui.screen.RinconesListRoute
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.DetalleRinconViewModel
import es.fplumara.dam2.rinconessecretos.ui.viewmodel.RinconViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    rinconViewModel: RinconViewModel = viewModel(),
    detalleViewModel: DetalleRinconViewModel = viewModel(),

    ) {
    NavHost(navController = navController, startDestination = Routes.LISTA) {

        composable(Routes.LISTA) {RinconesListRoute(navController = navController, rinconViewModel, )}
        composable (Routes.CREAR) { CrearRinconesRoute(navController, rinconVm = rinconViewModel)}
        composable(
            route = "rincon_edit/{rinconId}",
            arguments = listOf(navArgument("rinconId") { type = NavType.LongType })
        ) { backStackEntry ->

            val rinconId = backStackEntry.arguments?.getLong("rinconId") ?: return@composable

            RinconesEditRoute(
                rinconId = rinconId,
                navController = navController
            )
        }


        composable(
            route = "detalle/{rinconId}",
            arguments = listOf(navArgument("rinconId") { type = NavType.LongType })
        ) { backStackEntry ->

            val rinconId = backStackEntry.arguments!!.getLong("rinconId")

            RinconDetalleRoute(
                rinconId = rinconId,
                navController = navController
            )
        }



    }
}