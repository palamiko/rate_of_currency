### Rate of currency

Приложение отображает архив курсов валют по 7 валютным парам.
Есть возможность выбрать дату и пару.

* [Скачать APK] (https://disk.yandex.kz/d/57bf7i_F2w2rGg)

### Техническая информация
* Приложение написано на Kotlin.
* Используется патерн MVVM и single activity.
* Приложение клиент, использует 2 api для авторизации и получения данных.
* Основной стек используемых технологий:
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
  * [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
  * [JetPack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started)
  * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
  * [Retrofit 2](https://square.github.io/retrofit/)
***

### Структура проекта
```
project
|   readme.md
|   build.gradle  # gradle уровня модуля.
|
└───app
    |   build.gradle  # gradle уровня приложения.
    |
    └─── ../android/bignerdranch/mobilecrm
        |
        └─── model
        |   |
        |   └─── entities  # Сущности используемые в сетевых запросах
        |   |
        |   └─── viewModels  # ViewModel используемые фрагментами.
        |       |
        |       └─── AuthViewModel  #  ViewModel фрагмента авторизации
        |       |
        |       └─── GeneralViewModel  #  ViewModel основного фрагмента
        |
        └─── network  # Retrofit
        |   └─── apiservice  # Api всех сетевых запросов.
        |   └─── modules  # Содержит 2 модуля Retrofit для разных api.
        |
        |
        └─── ui
        |   └─── MainActivity  # Single activity
        |   └─── fragments
        |      |
        |      └─── AuthorizationFragment # Фрагмент авторизации
        |      |
        |      └─── CurrencyInformation  # Фрагмент отображает архивы курсов валют
        |      |
        |      └─── UserProfile  # Отображает профиль пользователя
        |
        |
        └─── utilits  # Содержит константы приложения и адаптер для RecyclerView.
```
***
