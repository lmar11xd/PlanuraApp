package com.lmar.planuraapp.core.utils

import androidx.compose.ui.unit.dp

object Constants {

    const val USERS_DATABASE = "users"

    const val APP_DATABASE = "planura"

    const val NOTES_REFERENCE = "notes"

    const val TASKS_REFERENCE = "tasks"

    const val REMINDERS_REFERENCE = "reminders"

    const val STORAGE_REFERENCE = "users/img"

    val PHOTO_SIZE = 150.dp
    val PHOTO_ICON_SIZE = 30.dp

    const val ERROR_MESSAGE_AUTH = "The supplied auth credential is incorrect, malformed or has expired."
    const val ERROR_MESSAGE_ACCOUNT_EXISTS = "The email address is already in use by another account."

    const val PARAM_NOTEID = "noteId";
    const val PARAM_TASKID = "taskId";
    const val PARAM_REMINDERID = "reminderId";
}