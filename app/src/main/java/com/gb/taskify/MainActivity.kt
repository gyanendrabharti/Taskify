@file:OptIn(ExperimentalMaterial3Api::class)


package com.gb.taskify

import androidx.compose.ui.tooling.preview.Preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.core.view.WindowCompat.enableEdgeToEdge
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isCompleted: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                TaskifyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskifyApp() {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task("1", "Complete Jetpack Compose tutorial"),
                Task("2", "Buy groceries for dinner", true),
                Task("3", "Call mom about weekend plans"),
                Task("4", "Finish reading that book"),
                Task("5", "Plan vacation itinerary", true)
            )
        )
    }
    var showAddDialog by remember { mutableStateOf(false) }
    var newTaskText by remember { mutableStateOf("") }

    val motivationalTexts = listOf(
        "Make today amazing! ✨",
        "You've got this! 💪",
        "Small steps, big dreams! 🌟",
        "Progress over perfection! 🚀",
        "Today is your day! 🌈"
    )

    val todayMotivation = remember { motivationalTexts.random() }
    val today = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
    val completedTasks = tasks.count { it.isCompleted }
    val totalTasks = tasks.size
    val progress = if (totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6B73FF),
            secondary = Color(0xFFFF9F43),
            tertiary = Color(0xFF5F27CD),
            background = Color(0xFFF8F9FE),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF2D3436),
            onSurface = Color(0xFF2D3436)
        )
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    modifier = Modifier
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                        .size(64.dp)
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Task",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Header Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "Taskify",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = today,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF636e72)
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = todayMotivation,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Medium
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Progress Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Progress",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                            Text(
                                text = "$completedTasks of $totalTasks tasks",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF636e72)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = Color(0xFFE0E6ED)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tasks Section
                if (tasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color(0xFFDDD6FE)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No tasks yet!",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color(0xFF636e72)
                                )
                            )
                            Text(
                                text = "Tap the + button to add your first task",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF636e72)
                                )
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = tasks,
                            key = { it.id }
                        ) { task ->
                            TaskItem(
                                task = task,
                                onTaskChecked = { updatedTask ->
                                    tasks = tasks.map {
                                        if (it.id == updatedTask.id) updatedTask else it
                                    }
                                },
                                onTaskDeleted = { taskToDelete ->
                                    tasks = tasks.filter { it.id != taskToDelete.id }
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }

        // Add Task Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = {
                    showAddDialog = false
                    newTaskText = ""
                },
                title = {
                    Text(
                        text = "Add New Task",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                text = {
                    OutlinedTextField(
                        value = newTaskText,
                        onValueChange = { newTaskText = it },
                        placeholder = { Text("What needs to be done?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newTaskText.isNotBlank()) {
                                tasks = tasks + Task(title = newTaskText.trim())
                                newTaskText = ""
                                showAddDialog = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Add Task")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAddDialog = false
                            newTaskText = ""
                        }
                    ) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskChecked: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible,
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(300)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(16.dp))
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            colors = CardDefaults.cardColors(
                containerColor = if (task.isCompleted) Color(0xFFF1F3F4) else Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox with animation
                val checkboxScale by animateFloatAsState(
                    targetValue = if (task.isCompleted) 1.2f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessHigh
                    ),
                    label = "checkbox_scale"
                )

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        onTaskChecked(task.copy(isCompleted = it))
                    },
                    modifier = Modifier
                        .scale(checkboxScale)
                        .size(24.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = Color(0xFFBDC3C7),
                        checkmarkColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Task title with strikethrough animation
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                        color = if (task.isCompleted) Color(0xFF95A5A6) else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Delete button with hover effect
                IconButton(
                    onClick = {
                        isVisible = false
                        // Delay deletion to allow exit animation
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                            kotlinx.coroutines.delay(300)
                            onTaskDeleted(task)
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color(0xFFE74C3C),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskifyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6B73FF),
            secondary = Color(0xFFFF9F43),
            tertiary = Color(0xFF5F27CD),
            background = Color(0xFFF8F9FE),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF2D3436),
            onSurface = Color(0xFF2D3436)
        ),
        typography = Typography(
            headlineLarge = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40.sp
            ),
            headlineSmall = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            ),
            titleLarge = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 28.sp
            ),
            titleMedium = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp
            ),
            bodyLarge = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            ),
            bodyMedium = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp
            )
        ),
        content = content
    )
}

// Main Composable for the app
@Composable
fun MainApp() {
    TaskifyTheme {
        TaskifyApp()
    }
}

// Preview
@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Taskify App Preview"
)
@Composable
fun TaskifyPreview() {
    TaskifyTheme {
        TaskifyApp()
    }
}

