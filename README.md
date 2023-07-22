# Финальный проект 7 спринта
## О проекте
Тестирование API учебного сервиса [Яндекс.Самокат](http://qa-scooter.praktikum-services.ru/).

Его документация: [Документация](https://qa-scooter.praktikum-services.ru/docs/)

## Технологии в проекте
|              | версия |
|--------------|--------|
| Java         | 11     |
| JUnit        | 4.13.2 |
| Maven        | 4.0.0  |
| Rest Assured | 5.3.1  |
| Allure       | 2.15.0 |
| Aspectj      | 1.9.7  |
| Gson         | 2.10.1 |

## Как запустить
<ol>
<li>Клонируй репозиторий</li>

```
git clone https://github.com/kirillpsl/Sprint_7
```
<li>Для запуска тестов</li>

```
mvn clean test
```
<li>Для просмотра отчета в Allure</li>

```
mvn allure:serve
```
</ol>

