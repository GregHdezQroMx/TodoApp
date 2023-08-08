package com.jght.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.jght.todoapp.addtask.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {

    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifecycle,
        key2 = tasksViewModel
    ) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TaskUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                TaskList((uiState as TaskUiState.Success).tasks, tasksViewModel)
                FabDialog(modifier = Modifier.align(Alignment.BottomEnd), tasksViewModel = tasksViewModel)
                AddTasksDialog(
                    show = showDialog,
                    onDismiss = { tasksViewModel.onDialogClose() },
                    onTaskAdded = { tasksViewModel.onTaskCreated(it) })
            }
        }

        is TaskUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Error")
            }
        }

        is TaskUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun TaskList(myTasks: List<TaskModel>, tasksViewModel: TasksViewModel) {

    //val myTasks: List<TaskModel> = tasksViewModel.tasks

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(myTasks, key = { it.id }) { task ->
            TaskItem(taskModel = task, tasksViewModel = tasksViewModel)
        }
    }
}

@Composable
private fun TaskItem(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onTaskRemoved(taskModel)
                })
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = taskModel.task, Modifier.weight(1f))
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { tasksViewModel.onTaskSelected(taskModel) })
        }
    }
}

@Composable
private fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(
        onClick = {
            tasksViewModel.showDialogClick()
        }, modifier = modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add task",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTask by rememberSaveable {
        mutableStateOf("")
    }

    if (show) {
        Dialog(onDismissRequest = onDismiss) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Text(text = "Add Task", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = myTask, onValueChange = { myTask = it }, singleLine = true, maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onTaskAdded(myTask)
                        myTask = ""
                    },
                    Modifier.fillMaxWidth()
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
}