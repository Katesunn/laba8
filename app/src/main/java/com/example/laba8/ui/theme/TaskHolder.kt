package com.example.laba8.ui.theme                      //Определяет пакет, к которому принадлежит этот файл. Пакеты используются для организации кода в проекте, помогая структурировать его по функциональности или модулю.
import android.content.Context                          //Предоставляет доступ к ресурсам приложения и другим важным данным.
import android.content.res.ColorStateList               //Позволяет задавать цвета в зависимости от состояния элемента.
import android.graphics.Color                           //Содержит методы для работы с цветами
import android.view.View                                //Базовый класс для всех элементов интерфейса.
import android.widget.FrameLayout                       //Тип компоновки, используемый для размещения элементов интерфейса.
import android.widget.TextView                          //Элемент интерфейса для отображения текста.
import androidx.recyclerview.widget.RecyclerView        //Компонент для отображения списков элементов.
import com.example.laba8.R                              //Содержит ссылки на ресурсы проекта, такие как макеты, строки и изображения.
import com.example.laba8.model.Priority                 //Класс, представляющий приоритет задачи.
import com.example.laba8.model.Task                     //Класс, представляющий задачу.


class TaskHolder (itemView: View, private val context: Context, private val adapter: TaskAdapter) : RecyclerView.ViewHolder(itemView){      //Создание адаптера RecyclerView, где каждый элемент списка управляется отдельным ViewHolder.
    private val taskContent: TextView = itemView.findViewById(R.id.taskContent)             //Объявляет переменную taskContent типа TextView, которая отвечает за отображение содержания задачи.
    private val priorityCircle: FrameLayout = itemView.findViewById(R.id.priorityCircle)    //Объявляет переменную priorityCircle типа FrameLayout, которая используется для отображения цветного круга, указывающего на приоритет задачи.
    private val priorityNumber: TextView = itemView.findViewById(R.id.priorityNumber)       //Объявляет переменную priorityNumber типа TextView, которая отображает числовое значение приоритета задачи.

    fun bind(task: Task){                           //Этот метод отвечает за привязку данных задачи к элементам интерфейса. Он вызывается для каждой задачи при отображении её в списке. Объявляет функцию bind, которая принимает объект task типа Task. Этот объект содержит всю информацию о задаче, которую нужно отобразить.
        taskContent.text = task.content             //Устанавливает текстовое содержимое элемента taskContent равным содержимому задачи. task.content: Свойство объекта Task, содержащее описание или название задачи.
        when (task.priority) {                      //Использует конструкцию when, аналогичную оператору switch
            Priority.HIGH.level -> priorityCircle.backgroundTintList = ColorStateList.valueOf(Color.RED)        //устанавливает цвет фона priorityCircle в красный.
            Priority.MEDIUM.level -> priorityCircle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFA500"))        //станавливает цвет фона priorityCircle в оранжевый.
            else -> priorityCircle.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)        //Иначе устанавливает цвет фона priorityCircle в жёлтый
        }
        priorityNumber.text = task.priority.toString()      // Преобразует числовое значение приоритета задачи в строку для отображения.
    }
}

//Итоговая функциональность класса TaskHolder
//Этот класс отвечает за отображение одной задачи в списке.
//1. Инициализация элементов интерфейса: Найти и сохранить ссылки на элементы TextView и FrameLayout из макета элемента списка.
//2. Привязка данных задачи к UI: Устанавливает содержание задачи и визуально отображает приоритет задачи с помощью цвета и числового значения.
//3. Обработка отображения приоритета: В зависимости от уровня приоритета (HIGH, MEDIUM, LOW), устанавливает соответствующий цвет для отображения приоритета задачи.

// Класс TaskHolder отвечает за визуальное представление каждой задачи, устанавливая содержание и приоритет задачи с помощью текста и цветов. Он взаимодействует с адаптером RecyclerView, который управляет списком задач и обновляет пользовательский интерфейс при изменениях данных.
// Этот файл отвечает за отображение отдельных элементов списка задач в приложении, используя компонент RecyclerView. Он определяет, как каждая задача будет выглядеть и отображать свою информацию, такую как содержание задачи и её приоритет.