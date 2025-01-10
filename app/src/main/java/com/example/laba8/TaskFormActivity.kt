package com.example.laba8                                       //Определяет пакет, к которому принадлежит этот файл.

import android.os.Bundle                                        //Bundle используется для передачи данных между компонентами приложения, например, для сохранения состояния активности при её пересоздании (например, при повороте экрана).
import androidx.appcompat.app.AppCompatActivity                 //является базовым классом для активностей, предоставляя совместимость с более старыми версиями Android и доступ к современным функциям пользовательского интерфейса.
import com.example.laba8.ui.theme.TaskFormFragment              //Этот фрагмент отвечает за отображение формы для добавления новой задачи.



class TaskFormActivity : AppCompatActivity() {                  //Этот класс отвечает за создание и управление активностью, в которой отображается форма для добавления новой задачи.
    override fun onCreate(savedInstanceState: Bundle?) {        //Параметр savedInstanceState представляет собой объект Bundle, содержащий сохранённое состояние активности, если оно существует. Если активность создаётся впервые, этот параметр будет null.
        super.onCreate(savedInstanceState)                      //Вызывает метод onCreate родительского класса (AppCompatActivity), передавая ему savedInstanceState. Это необходимо для корректной инициализации активности и обеспечения её правильного поведения.
        // Устанавливаем макет активности из ресурса XML
        setContentView(R.layout.activity_task_form)

        // Проверяем, пуст ли контейнер для фрагмента (savedInstanceState будет null при первом создании активности)
        val isFragmentContainerEmpty = savedInstanceState == null

        // Если контейнер пуст, добавляем новый фрагмент PhotoGalleryFragment
        if (isFragmentContainerEmpty) {
            supportFragmentManager // Получаем экземпляр FragmentManager для управления фрагментами
                .beginTransaction() // Начинаем транзакцию фрагментов
                .add(
                    R.id.fragmentContainer,
                    TaskFormFragment.newInstance()
                ) // Добавляем новый экземпляр фрагмента в контейнер
                .commit() // Подтверждаем транзакцию
        }

    }
}

//Этот файл отвечает за создание и управление активностью (Activity), которая отображает форму для добавления новой задачи. Активность использует фрагмент (Fragment) для отображения интерфейса формы