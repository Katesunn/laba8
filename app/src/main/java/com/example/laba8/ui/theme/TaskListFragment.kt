package com.example.laba8.ui.theme

import android.content.Intent                                       //Для запуска новых активностей
import android.os.Bundle
import androidx.fragment.app.Fragment                               //Основной класс для создания фрагментов интерфейса
import androidx.recyclerview.widget.ItemTouchHelper                 //Для отображения списка элементов
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View                                            //Базовый класс для всех элементов интерфейса.
import androidx.fragment.app.viewModels                             //Для управления данными и их жизненным циклом
import com.example.laba8.R                                          //Содержит ссылки на ресурсы проекта, такие как макеты, строки и изображения.
import com.example.laba8.TaskFormActivity
import com.example.laba8.TaskViewModel                              //Классы для управления данными задач
import com.example.laba8.TaskViewModelFactory                       //и создания ViewModel с параметрами.
import com.example.laba8.db.TaskDatabase                            //Класс для доступа к базе данных задач.



import com.google.android.material.floatingactionbutton.FloatingActionButton    //Для создания плавающей кнопки
import com.google.android.material.snackbar.Snackbar                            //Для отображения уведомлений

class TaskListFragment : Fragment(R.layout.fragment_task_list) {                //Объявляет класс, в конструктор передаётся ресурс разметки R.layout.fragment_task_list, определяющий внешний вид фрагмента.

    private lateinit var recyclerView: RecyclerView                             //Переменную типа RecyclerView, которая будет использоваться для отображения списка задач
    private lateinit var adapter: TaskAdapter                                   //Адаптер для управления данными списка задач. Предполагается, что класс TaskAdapter уже реализован отдельно.

    // Используем viewModels для создания ViewModel с параметрами
    private val taskViewModel: TaskViewModel by viewModels {                            //Создаёт экземпляр TaskViewModel с помощью делегата viewModels. Это позволяет управлять данными фрагмента и сохранять их при изменении конфигурации (например, при повороте экрана).
        TaskViewModelFactory(TaskDatabase.getDatabase(requireContext()).taskDao())      //Использует TaskViewModelFactory для создания ViewModel, передавая ей DAO из базы данных TaskDatabase. Это обеспечивает доступ к данным задач из базы данных.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {       //Переопределяет метод onViewCreated, который вызывается после создания представления фрагмента. Здесь происходит инициализация UI-элементов и настройка логики.
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)                    //Находит элемент RecyclerView в разметке фрагмента по его идентификатору recycler_view и присваивает его переменной recyclerView.
        recyclerView.layoutManager = LinearLayoutManager(context)               //Устанавливает менеджер компоновки LinearLayoutManager для RecyclerView, который отображает элементы в виде вертикального списка.

        // Инициализация адаптера с пустым списком задач
        adapter = TaskAdapter(mutableListOf(), context = requireContext())      //Создаёт новый экземпляр TaskAdapter, передавая ему пустой список задач и контекст приложения.
        recyclerView.adapter = adapter                                          //Устанавливает созданный адаптер для RecyclerView, чтобы он мог управлять отображением элементов списка.

        // Наблюдаем за изменениями в списке задач
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->              //Подписывается на изменения данных списка задач в ViewModel. Когда данные изменяются, вызывается лямбда-функция, которая обновляет адаптер.
            // Обновляем адаптер с новым списком задач
            adapter.updateTasks(tasks)                                          //Обновляет адаптер новым списком задач, что приводит к обновлению отображения списка на экране
        }

        // Пример добавления задачи (можно убрать или изменить по необходимости)
//        taskViewModel.addTask(content = "Сделать домашнее задание", priority = Priority.MEDIUM.level)

        taskViewModel.getTasks()                                                //Вызывает метод getTasks из ViewModel, который загружает задачи из базы данных.
        // Добавление функциональности свайпа для удаления элементов
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {         //Создаёт объект swipeHandler, который обрабатывает жесты свайпа (перетаскивания) на элементах списка. В данном случае настроен свайп влево для удаления задачи.
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false                                                    // Не обрабатываем перемещение (для этого переопределяет метод onMove)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {    //Переопределяет метод onSwiped, который вызывается при свайпе элемента.
                // Удаляем элемент из адаптера по позиции
                val position = viewHolder.adapterPosition               //Получает позицию свайпнутого элемента в списке.
                val taskToDelete = adapter.getTaskAt(position)          //Получает задачу по позиции из адаптера.
                taskViewModel.deleteTask(taskToDelete)                  //Удаляет задачу из базы данных через ViewModel.
                adapter.removeItem(position)                            //Удаляет элемент из адаптера, обновляя отображение списка.

                // Показываем Snackbar для отмены удаления (если нужно)
                Snackbar.make(view, "Задача удалена", Snackbar.LENGTH_LONG)
                    .setAction("Отменить") {
                        taskViewModel.addTask(taskToDelete.content, taskToDelete.priority) // Возвращаем задачу обратно в список
                    }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)         //Создаёт объект ItemTouchHelper, используя ранее созданный swipeHandler
        itemTouchHelper.attachToRecyclerView(recyclerView)          //Привязывает ItemTouchHelper к RecyclerView, чтобы обработка свайпов стала активной.

        // Обработка нажатия на FloatingActionButton (FAB)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)                 //Находит элемент FloatingActionButton в разметке по идентификатору fab и присваивает его переменной fab.
        fab.setOnClickListener {
            // Здесь можно открыть диалоговое окно или активность для добавления новой задачи.
            // Например, просто добавим новую задачу с фиксированным содержимым:
//            taskViewModel.addTask(content = "Новая задача", priority = Priority.MEDIUM.level)
            val intent = Intent(requireContext(), TaskFormActivity::class.java)     //Создаёт Intent для запуска новой активности TaskFormActivity, которая предназначена для добавления новой задачи.
            startActivity(intent)                                                   //Запускает активность TaskFormActivity
        }


    }

    override fun onResume() {           //Вызывается, когда фрагмент становится видимым для пользователя.
        super.onResume()
        // Обновляем список задач при возвращении к активности
        taskViewModel.getTasks() // Метод для получения задач из базы данных
    }

    companion object {
        fun newInstance() = TaskListFragment()  // Создание нового экземпляра фрагмента
    }
}

//   Этот файл отвечает за отображение списка задач в приложении.
//1. Отображение списка задач: Используя RecyclerView и TaskAdapter, фрагмент показывает список задач, полученных из базы данных через ViewModel.
//2. Добавление новых задач: При нажатии на плавающую кнопку действия (FAB) запускается новая активность (TaskFormActivity), где пользователь может добавить новую задачу.
//3. Удаление задач: Пользователь может удалить задачу, сделав свайп влево по элементу списка. После удаления отображается уведомление с возможностью отменить действие.
//4. Обновление списка задач: При возвращении к фрагменту список задач обновляется, чтобы отразить последние изменения.

// Этот файл отвечает за отображение списка задач в приложении, позволяя пользователю просматривать, добавлять и удалять задачи.