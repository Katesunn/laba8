package com.example.laba8.ui.theme              //Определяет пакет, к которому принадлежит этот файл. Пакеты помогают организовать код в проекте, структурируя его по функциональным модулям.

import android.os.Bundle                        //Класс для передачи данных между компонентами приложения.
import android.view.View                        //Базовый класс для всех элементов интерфейса.
import android.widget.Button                    //Элементы пользовательского интерфейса для кнопок,
import android.widget.EditText                  //текстовых полей
import android.widget.RadioGroup                //и групп радиокнопок соответственно.
import androidx.fragment.app.Fragment           //Базовый класс для создания фрагментов в Android.
import androidx.fragment.app.viewModels         //Делегат для создания экземпляров ViewModel.
import com.example.laba8.R                      //Содержит ссылки на ресурсы проекта, такие как макеты, строки и изображения.
import com.example.laba8.TaskViewModel          //Классы для управления данными задач
import com.example.laba8.TaskViewModelFactory   //и создания ViewModel с параметрами.
import com.example.laba8.db.TaskDatabase        //Класс для доступа к базе данных задач.
import com.example.laba8.model.Priority         //Классы, представляющие приоритет задачи
import com.example.laba8.model.Task             //и саму задачу соответственно.



class TaskFormFragment : Fragment(R.layout.fragment_task_form) {                            //В конструктор передаётся ресурс разметки R.layout.fragment_task_form, определяющий внешний вид фрагмента. Это означает, что при создании этого фрагмента будет загружен соответствующий XML-файл разметки.

    // Используем viewModels для создания ViewModel с параметрами
    private val taskViewModel: TaskViewModel by viewModels {                                //Создаёт экземпляр TaskViewModel с помощью делегата viewModels. Это позволяет управлять данными фрагмента и сохранять их при изменении конфигурации (например, при повороте экрана).
        TaskViewModelFactory(TaskDatabase.getDatabase(requireContext()).taskDao())          //Использует фабрику TaskViewModelFactory для создания ViewModel, передавая ей DAO (Data Access Object) из базы данных TaskDatabase. Это обеспечивает доступ к данным задач из базы данных.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {                   // Переопределяет метод onViewCreated, который вызывается после создания представления фрагмента.
        super.onViewCreated(view, savedInstanceState)

        //Поиск и инициализация элементов интерфейса
        val buttonAddTask: Button = view.findViewById(R.id.buttonAddTask)                   //Находит элемент Button в разметке фрагмента по его идентификатору buttonAddTask и присваивает его переменной buttonAddTask.
        val editTextTaskContent: EditText = view.findViewById(R.id.editTextTaskContent)     //Находит элемент EditText по идентификатору editTextTaskContent и присваивает его переменной editTextTaskContent. (поле для ввода содержания задачи)
        val radioGroupPriority: RadioGroup = view.findViewById(R.id.radioGroupPriority)     //Находит элемент RadioGroup по идентификатору radioGroupPriority и присваивает его переменной radioGroupPriority. (группу радиокнопок для выбора приоритета задачи)

        // Обработка нажатия на кнопку "Добавить задачу"
        buttonAddTask.setOnClickListener {
            //Получение и проверка ввода пользователя
            val taskContent = editTextTaskContent.text.toString().trim()        //Получает текст, введённый пользователем в поле editTextTaskContent

            //Проверяет, пусто ли поле ввода.
            if (taskContent.isEmpty()) {
                // Устанавливаем ошибку, если поле пустое
                editTextTaskContent.error = "Поле не должно быть пустым"
                return@setOnClickListener       //Прерывает выполнение дальнейшего кода внутри слушателя нажатий,
            }

            //Определяет уровень приоритета задачи в зависимости от выбранной радиокнопки в группе radioGroupPriority.
            val priority = when (radioGroupPriority.checkedRadioButtonId) {     //Возвращает идентификатор выбранной радиокнопки.
                R.id.radioHigh -> Priority.HIGH.level                           //Если выбрана радиокнопка с идентификатором radioHigh, устанавливает приоритет задачи как высокий (Priority.HIGH.level).
                R.id.radioMedium -> Priority.MEDIUM.level
                R.id.radioLow -> Priority.LOW.level
                else -> Priority.LOW.level // Значение по умолчанию, если ничего не выбрано
            }

            // Создаём новую задачу и добавляем её через ViewModel
            val newTask = Task(content = taskContent, priority = priority)      //Создаёт новый объект Task с введённым содержанием и выбранным приоритетом.
            taskViewModel.addTask(newTask.content, newTask.priority)            //Вызывает метод addTask из ViewModel, чтобы добавить новую задачу в базу данных.

            // Закрывает текущую активность (экран), возвращая пользователя к предыдущему экрану (например, к списку задач).
            requireActivity().finish()
        }
    }

    companion object {
        fun newInstance() = TaskFormFragment() // Создание нового экземпляра фрагмента
    }
}


//Этот файл отвечает за создание формы, позволяющей пользователю добавлять новые задачи в приложение. Форма включает поле для ввода содержания задачи и выбор приоритета задачи. После заполнения формы пользователь может сохранить новую задачу, которая затем добавляется в список задач приложения.

//1. Отображение формы для ввода новой задачи
//1.1 Форма содержит поле для ввода содержания задачи и группу радиокнопок для выбора приоритета (высокий, средний, низкий).

//2. Обработка ввода пользователя
//2.1 Проверяет, что пользователь ввёл содержание задачи. Если поле пустое, отображает сообщение об ошибке.
//2.2 Определяет приоритет задачи на основе выбранной радиокнопки.

//3 Добавление новой задачи
//3.1 Создаёт новый объект задачи с введёнными данными и добавляет его в базу данных через ViewModel.
//3.2 После добавления задачи закрывает текущую активность, возвращая пользователя к списку задач.

//4 Управление состоянием приложения
//4.1 Использует ViewModel для управления данными, обеспечивая сохранение и доступ к данным задач при изменении конфигурации (например, поворот экрана).