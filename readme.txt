web приложение по управлению собственным туристическим телеграм ботом.
данные о городах хранятся в базе данных Postgresql. Добавление , редактирование и удаление данных происходит через  REST WebService.
Данные для соединения с базой данных и телеграм-ботом вносятся с помощью переменных окружения:
PG_CONN_STRING  - адрес базы данных
PG_USR  - имя пользователя базы данных
PG_PSSWRD - пароль базы данных
BT_NM  -имя телеграм-бота
BT_TKN  - токен телеграм-бота
Добавление и изменение данных происходит с помощью запросов POST и PUT . 
Удаление и получение информации происходит с помощью запросов DELETE и GET c параметром city.

web application for managing your own travel telegram bot.
city data is stored in the Postgresql database. Adding, editing and deleting data occurs through the REST WebService.
The data for connecting to the database and telegram bot are entered using environment variables:
PG_CONN_STRING - database address
PG_USR -
database username
PG_PSSWRD - database password
BT_NM - telegram bot name
BT_TKN - telegram bot token
Adding and changing data is done using POST and PUT requests.
Information is deleted and retrieved using DELETE and GET requests with the "city" parameter.
