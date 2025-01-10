package com.example.laba8.db                //Определяет пакет, к которому принадлежит этот файл.
import android.content.Context              //Импортирует класс Context из Android SDK. Context предоставляет доступ к ресурсам приложения и является необходимым для создания экземпляра базы данных.
import androidx.room.Database               //Импортирует аннотацию @Database из библиотеки Room. Эта аннотация используется для определения конфигурации базы данных.
import androidx.room.Room                   //Импортирует класс Room, который содержит методы для создания экземпляра базы данных.
import androidx.room.RoomDatabase           //Импортирует абстрактный класс RoomDatabase, от которого наследуется класс базы данных. Этот класс предоставляет основные функции для работы с базой данных.
import com.example.laba8.model.Task         //Импортирует класс Task из пакета model. Этот класс представляет сущность (таблицу) в базе данных.

@Database(entities = [Task::class], version = 1)        //Это аннотация, которая указывает, что данный класс представляет базу данных Room
abstract class TaskDatabase : RoomDatabase() {          //Указывает, что TaskDatabase наследуется от RoomDatabase
    abstract fun taskDao(): TaskDao                     //Объявляет абстрактный метод taskDao(), который возвращает экземпляр интерфейса TaskDao.

    companion object {                                  //Объявляет компаньон-объект, который позволяет создавать статические методы и свойства, доступные без создания экземпляра класса. Э
        @Volatile                                       //Это аннотация, которая указывает, что переменная INSTANCE может быть изменена одновременно несколькими потоками.
        private var INSTANCE: TaskDatabase? = null      //Объявляет приватную изменяемую переменную INSTANCE типа TaskDatabase?, которая может хранить экземпляр базы данных или быть null.

        fun getDatabase(context: Context): TaskDatabase {       //Объявляет функцию getDatabase, которая принимает context типа Context и возвращает экземпляр TaskDatabase.
            return INSTANCE ?: synchronized(this) {        //INSTANCE ?:: Проверяет, существует ли уже экземпляр базы данных (INSTANCE). Если да, он возвращается немедленно.
                val instance = Room.databaseBuilder(            //Создаёт новый экземпляр базы данных с помощью билдера Room.databaseBuilder.
                    context.applicationContext,                 //Передаёт контекст приложения, который используется для создания базы данных.
                    TaskDatabase::class.java,                   //Указывает класс базы данных, который будет использоваться Room для создания экземпляра
                    "app_database" // Имя вашей базы данных
                ).build()
                INSTANCE = instance     //Присваивает созданный экземпляр базы данных переменной INSTANCE, чтобы он был доступен при последующих вызовах getDatabase.
                instance                //Возвращает созданный экземпляр базы данных.
            }
        }
    }
}


//Этот файл отвечает за настройку и управление базой данных приложения с помощью библиотеки Room
