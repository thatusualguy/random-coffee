package dev.suai.randomcoffee.ui.menu.navbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import dagger.hilt.components.SingletonComponent
import dev.suai.randomcoffee.R
import dev.suai.randomcoffee.ui.interests.InterestsScreen
import dev.suai.randomcoffee.ui.meets.MeetScreen
import dev.suai.randomcoffee.ui.profile.ProfileScreen

val navItems: List<NavBarScreen.NavItem> = listOf(
    NavBarScreen.NavItem(
        action = NavBarScreen.Event.NavigateToInterests,
        text = "Интересы",
        icon = R.drawable.outline_interests_24
    ),
    NavBarScreen.NavItem(
        action = NavBarScreen.Event.NavigateToMeet,
        text = "Встречи",
        icon = R.drawable.outline_people_24
    ),
    NavBarScreen.NavItem(
        action = NavBarScreen.Event.NavigateToProfile,
        text = "Профиль",
        icon = R.drawable.outline_account_circle_24
    ),
)


@CircuitInject(NavBarScreen::class, SingletonComponent::class)
@Composable
fun navBarPresenter(navigator: Navigator): NavBarScreen.State {

    var selectedItem by remember { mutableIntStateOf(1) }

    LaunchedEffect(navigator.peek()) {
        val id = when (navigator.peek()) {
            InterestsScreen -> 0
            MeetScreen -> 1
            ProfileScreen -> 2
            else -> selectedItem
        }
        selectedItem = id
    }


    return NavBarScreen.State(selectedIndex = selectedItem, items = navItems) { event ->
        when (event) {
            NavBarScreen.Event.NavigateToInterests -> navigator.goTo(InterestsScreen)
            NavBarScreen.Event.NavigateToMeet -> navigator.goTo(MeetScreen)
            NavBarScreen.Event.NavigateToProfile -> navigator.goTo(ProfileScreen)
        }
    }
}