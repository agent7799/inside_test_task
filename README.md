# inside_test_task
test task for Inside

Этот эндпоинт проверяет пароль по БД и создает jwt токен , в токен записывает данные: name: "имя отправителя" 

curl --location --request POST 'http://localhost:8091/auth/login' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205' \
--data-raw '{
    "username": "user001",
    "password":"pass"
}'


В этот эндпоинт на вход поступают данные в формате jsonзаголовках указан Bearer токен, полученный из эндпоинта выше (между Bearer и полученным токеном должно быть нижнее подчеркивание). В случае успешной проверки токена, полученное сообщение созраняется в БД.

curl --location --request POST 'http://localhost:8091/message' \
--header 'Authorization: Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205' \
--data-raw '{
    "username": "user001",
    "message":"message003"
}'



Если пришло сообщение вида:
{
    name:       "имя отправителя",
    message:    "history 10"
}
проверить токен, в случае успешной проверки токена отправить отправителю 10 последних сообщений из БД

curl --location --request POST 'http://localhost:8091/message' \
--header 'Authorization: Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205' \
--data-raw '{
    "username": "user001",
    "message":"history 10"
}'
