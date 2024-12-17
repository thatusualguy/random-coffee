package dev.suai.randomcoffee.ui.menu.navbar

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.slack.circuit.codegen.annotations.CircuitInject
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.ui.theme.Orange
import dev.suai.randomcoffee.ui.theme.OrangeLight
import dev.suai.randomcoffee.ui.theme.RandomCoffeeTheme



@CircuitInject(NavBarScreen::class, SingletonComponent::class)
@Composable
fun NavBar(
    state: NavBarScreen.State,
    modifier: Modifier = Modifier
) {
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .height(60.dp),
        cornerRadius = shapeCornerRadius(50.dp),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 13.dp,
            animationSpec = tween(
                1000,
                easing = { OvershootInterpolator().getInterpolation(it) })
        ),
        selectedIndex = state.selectedIndex,
        ballAnimation = Parabolic(tween(500, easing = LinearOutSlowInEasing)),
        barColor = MaterialTheme.colorScheme.surfaceVariant
    ) {

        for ((idx, item) in state.items.withIndex()) {

            DropletButton(
                isSelected = idx == state.selectedIndex,
                onClick = { state.eventSink(item.action) },
                dropletColor = Orange,
                icon = item.icon,
                animationSpec = tween(durationMillis = 500, easing = LinearEasing),
                modifier = Modifier.fillMaxSize(),
                size = 30.dp
            )
        }
    }
}

@Preview
@Composable
private fun NavBarPreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val state = NavBarScreen.State(
        selectedIndex = selectedIndex,
        items = navItems,
        eventSink = {
            selectedIndex = (selectedIndex + 1) % 3
        }
    )

    RandomCoffeeTheme {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            NavBar(state)
        }

    }
}