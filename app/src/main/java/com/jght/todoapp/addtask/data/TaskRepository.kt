package com.jght.todoapp.addtask.data

import com.jght.todoapp.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> = taskDao.getTasks().map { items ->
        items.map {
            TaskModel(
                id = it.id,
                task = it.task,
                selected = it.selected
            )
        }
    }

    suspend fun addTask(taskModel: TaskModel) {
        taskDao.addTask( taskModel.toEntity() )
    }

    suspend fun updateTask(taskModel: TaskModel) {
        taskDao.updateTask( taskModel.toEntity() )
    }

    suspend fun deleteTask(taskModel: TaskModel) {
        taskDao.deleteTask( taskModel.toEntity() )
    }
}

fun TaskModel.toEntity(): TaskEntity {
    return TaskEntity(
        id = this.id,
        task = this.task,
        selected = this.selected
    )
}