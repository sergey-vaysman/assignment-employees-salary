# assignment-employees-salary

## Сборка приложения ##

1. Для выполнения интеграционных тестов потребуется подключение к БД.
`$ docker run --name employee-db -p 5432:5432 -e POSTGRES_PASSWORD=qwerty -d postgres`

2. Находясь в папке проекта, из командной строки запустите скрипт сборки контейнера с приложением.
`$ mvnw install dockerfile:build`

## Запуск приложения ##

1. Поднимите контейнер с БД, если не был запущен ранее.
`$ docker run --name employee-db -p 5432:5432 -e POSTGRES_PASSWORD=qwerty -d postgres`

2. Запустите контейнер с приложением.
`$ docker run --name employee-app -p 8080:8080 --link employee-db:employee-db svaysman/employee-salary`

3. Зайдите в браузере на страницу по адресу http://localhost:8080

![скриншот страницы](https://i.ibb.co/tztBcKn/assignment-screen.png)
