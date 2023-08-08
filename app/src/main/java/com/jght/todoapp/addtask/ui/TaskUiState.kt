package com.jght.todoapp.addtask.ui

import com.jght.todoapp.addtask.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Success(val tasks: List<TaskModel>) : TaskUiState
    data class Error(val throwable: Throwable) : TaskUiState
}