package dev.suai.randomcoffee.ui.menu

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme

//@Composable
//fun CustomScaffold(
//    content: @Composable () -> Unit
//) {
//    Scaffold(bottomBar = { CustomBottomBar() }) { innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)) {
//            content()
//        }
//    }
//}
//
//@Preview
//@Composable
//fun CustomBottomAppBar() {
//    val selectedIndex by remember { mutableIntStateOf(0) }
//
//
//    val buttons = listOf(
//        Triple({ /*add action*/ }, "Интересы", Icons.Default.FavoriteBorder),
//        Triple({ /*add action*/ }, "Встречи", Icons.Default.Check),
//        Triple({ /*add action*/ }, "Профиль", Icons.Default.Check),
//    )
//    AnimatedNavigationBar(
//        selectedIndex = selectedIndex,
//        ballAnimation = Parabolic(tween(175)),
//    ) {
//        var index = 0
//        SecondaryMenuButton(
//            action = buttons[index].first,
//            text = buttons[index].second,
//            icon = buttons[index].third,
//            isSelected = index == selectedIndex
//        )
//        index++
//        PrimaryMenuButton(
//            action = buttons[index].first,
//            text = buttons[index].second,
//            icon = buttons[index].third,
//            isSelected = index == selectedIndex
//        )
//
//        index++
//        SecondaryMenuButton(
//            action = buttons[index].first,
//            text = buttons[index].second,
//            icon = buttons[index].third,
//            isSelected = index == selectedIndex
//        )
//    }
//}
//
//@Composable
//fun SecondaryMenuButton(action: () -> Unit, text: String, icon: ImageVector, isSelected: Boolean) {
//    Column(modifier = Modifier.clickable { }, horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(
//            imageVector = icon,
//            modifier = Modifier
//                .fillMaxHeight()
//                .aspectRatio(1f),
//            contentDescription = ""
//        )
//        Text(
//            text = text,
//            modifier = Modifier.alpha(if (isSelected) 1f else 0f),
//            style = MaterialTheme.typography.headlineSmall
//        )
//    }
//}
//
//@Composable
//fun PrimaryMenuButton(action: () -> Unit, text: String, icon: ImageVector, isSelected: Boolean) {
//    Column(modifier = Modifier.clickable { }, horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(icon, "")
//        Text(text = text, style = MaterialTheme.typography.headlineMedium)
//    }
//}
//
//@Composable
//private fun MenuButton(action: () -> Unit, icon: ImageVector, text: String) {
//
//    Column(modifier = Modifier.clickable { }, horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(icon, "")
//        Text(text = text)
//    }
//
//}


//------------------


//
//@Preview
//@Composable
//fun MenuButtonsPreview() {
//    RandomCoffeeTheme {
//        Box(
//            modifier = Modifier
//                .background(Color.White)
//                .fillMaxWidth(),
//        ) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//                SecondaryMenuButton(
//                    action = { /*TODO*/ },
//                    text = "Меню 1",
//                    icon = Icons.Default.Check
//                )
//                PrimaryMenuButton(
//                    action = { /*TODO*/ },
//                    text = "Меню 1",
//                    icon = Icons.Default.Check,
//                    isSelected = index == selectedIndex
//                )
//                SecondaryMenuButton(
//                    action = { /*TODO*/ },
//                    text = "Меню 1",
//                    icon = Icons.Default.Check
//                )
//            }
//        }
//    }
//}


//@Preview
//@Composable
//fun CustomBottomBar(): Unit {
//    return BottomAppBar(
//        actions = {
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                IconButton(onClick = { /* do something */ }) {
//                    Column {
//                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
//                        Text(text = "Меню")
//                    }
//                }
//                IconButton(onClick = { /* do something */ }) {
//                    Icon(
//                        Icons.Filled.Edit,
//                        contentDescription = "Localized description",
//                    )
//                }
//                IconButton(onClick = { /* do something */ }) {
//                    Icon(
//                        Icons.Filled.Build,
//                        contentDescription = "Localized description",
//                    )
//                }
//                IconButton(onClick = { /* do something */ }) {
//                    Icon(
//                        Icons.Filled.DateRange,
//                        contentDescription = "Localized description",
//                    )
//                }
//            }
//        }
//    )
//
//}