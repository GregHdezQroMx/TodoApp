package com.jght.todoapp.addtask.domain

import com.jght.todoapp.addtask.data.TaskRepository
import com.jght.todoapp.addtask.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase@Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.addTask(taskModel)
    }
}