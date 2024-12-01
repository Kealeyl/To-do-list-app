package com.example.to_do.ui.navigaton

import com.example.to_do.R

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

object CreateTaskDestination : NavigationDestination {
    override val route = "create_task"
    override val titleRes = R.string.create_task_title
}

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = 0 // special case that will use plural that depends on num of tasks
}

object EditTaskDestination : NavigationDestination {
    override val route = "edit_task"
    override val titleRes = R.string.edit_task_title
    const val taskIdArg = "taskId" // the name of the argument
    val routeWithArgs = "$route/{$taskIdArg}"
}

fun getTitleResForRoute(route: String): Int {
    return when (route) {
        CreateTaskDestination.route -> CreateTaskDestination.titleRes
        HomeDestination.route -> HomeDestination.titleRes
        EditTaskDestination.routeWithArgs -> EditTaskDestination.titleRes
        else -> throw IllegalArgumentException("Route $route not found")
    }
}
