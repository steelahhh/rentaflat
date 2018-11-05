# Rent-A-Flat
- В приложении испольюзуется архитектура MVVM
- Простая авторизация, без возможности регистрации и восстановления пароля
- При первом запуске объекты парсятся из raw json и добавляются в БД

- При создании нового объекта используется placeholder изображение
- В детальном отображении только одна фотография
- Нет тестов

# Запуск
Для авторизации испольюзуются поля, помещенные в `local.properties`:
```
username=TESTNAME
password=TESTPASSWORD
```

# Библоитеки
- [Android Architecture Components (ViewModel, LiveData, Room, DataBinding)](https://developer.android.com/topic/libraries/architecture)
- [Dagger](https://google.github.io/dagger/)
- [RxJava](https://github.com/ReactiveX/RxJava), [RxKotlin](https://github.com/ReactiveX/RxKotlin)
- [GSON](https://github.com/google/gson)
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [FastAdapter](https://github.com/mikepenz/FastAdapter)
- [Glide](https://github.com/bumptech/glide)

