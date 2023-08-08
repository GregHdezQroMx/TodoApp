package com.jght.todoapp.addtask.domain

import com.jght.todoapp.addtask.data.TaskRepository
import com.jght.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}