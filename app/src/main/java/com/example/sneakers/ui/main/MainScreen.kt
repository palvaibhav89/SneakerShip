package com.example.sneakers.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sneakers.R
import com.example.sneakers.observers.GlobalObserver
import com.example.sneakers.observers.MainObserver
import com.example.sneakers.ui.cart.CartScreen
import com.example.sneakers.ui.home.HomeScreen
import com.example.sneakers.ui.productdetails.ProductDetailScreen
import com.example.sneakers.views.NavigationItemCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController
) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    val ctx = LocalContext.current
    val mainIOScope = CoroutineScope(Dispatchers.Main)
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true, block = {
        GlobalObserver.mainObserver.observeCartEvents(this) {
            when(it) {
                is MainObserver.MainEvent.AddToCart -> {
                    viewModel.handleCartEvents(it)
                    snackBarHostState.showSnackBar(
                        message = ctx.getString(R.string.product_added_to_cart),
                        coroutineScope = coroutineScope
                    )
                }
                is MainObserver.MainEvent.RemoveFromCart -> {
                    viewModel.handleCartEvents(it)
                    snackBarHostState.showSnackBar(
                        message = ctx.getString(R.string.product_removed_from_cart),
                        coroutineScope = coroutineScope
                    )
                }
                is MainObserver.MainEvent.OpenProductDetail -> {
                    viewModel.productDetailPageState.product.value = it.product
                    navController.navigateTo(NavigationScreens.ProductDetail.route, mainIOScope)
                }
                MainObserver.MainEvent.NavUp -> {
                    mainIOScope.launch {
                        navController.navigateUp()
                    }
                }
                MainObserver.MainEvent.ProceedToCheckout -> {
                    snackBarHostState.showSnackBar(
                        message = ctx.getString(R.string.proceed_to_checkout),
                        coroutineScope = coroutineScope
                    )
                }
            }
        }
    })

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                shadowElevation = 24.dp,
                color = Color.White
            ) {
                val bottomItem = viewModel.bottomNavScreens[1]
                val selected = bottomItem.route == backStackEntry.value?.destination?.route

                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    NavigationItemCustom(
                        modifier = Modifier
                            .padding(end = 36.dp)
                            .size(55.dp),
                        selected = selected,
                        onClick = {
                            navController.navigateTo(bottomItem.route, mainIOScope)
                        },
                        icon = bottomItem.icon,
                        contentDescription = stringResource(id = bottomItem.resourceId)
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .offset(y = 30.dp),
                hostState = snackBarHostState)
        },
        floatingActionButton = {
            val item = viewModel.bottomNavScreens[0]
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationItemCustom(
                modifier = Modifier
                    .offset(y = 20.dp)
                    .size(65.dp),
                selected = selected,
                onClick = {
                    navController.navigateTo(item.route, mainIOScope)
                },
                icon = item.icon,
                contentDescription = stringResource(id = item.resourceId)
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = { innerPadding ->
            MainScreenNavigationConfigurations(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding() - 20.dp),
                navController = navController,
                viewModel = viewModel
            )
        }
    )
}

@Composable
private fun MainScreenNavigationConfigurations(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationScreens.Home.route
    ) {
        composable(NavigationScreens.Home.route) {
            HomeScreen(viewModel.homePageState)
        }
        composable(NavigationScreens.Cart.route) {
            CartScreen(viewModel.cartPageState)
        }
        composable(NavigationScreens.ProductDetail.route) {
            ProductDetailScreen(viewModel.productDetailPageState)
        }
    }
}

private fun SnackbarHostState.showSnackBar(message: String, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        showSnackbar(message = message)
    }
}

private fun NavHostController.navigateTo(route: String, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        navigate(route = route) {
            popUpTo(NavigationScreens.Home.route)
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    val navController = rememberNavController()
    MainScreen(
        MainViewModel(MainRepository(null)),
        navController
    )
}