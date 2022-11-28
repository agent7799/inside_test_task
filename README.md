# inside_test_task
test task for Inside
***
http://localhost:8091/auth/login
Этот эндпоинт проверяет пароль по БД и создает jwt токен , в токен записывает данные: name: "имя отправителя"
POST:
{
"username":"user001",
"password":"pass"
}
RESPONSE: (e.g.)
{
"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw"
}

CURL:
curl -X POST "http://localhost:8091/auth/login" -H "Content-Type: application/json" -H "Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205" --data-raw "{    \"username\":\"user001\",    \"password\":\"pass\"}"
***
http://localhost:8091/message
В этот эндпоинт на вход поступают данные в формате jsonзаголовках указан Bearer токен, полученный из эндпоинта выше (между Bearer и полученным токеном должно быть нижнее подчеркивание). В случае успешной проверки токена, полученное сообщение созраняется в БД.
POST:
{
"username": "user001",
"message": "meromAlex011"
}
RESPONSE: (e.g.)
{
"message saved",
"meromAlex011"
}

CURL:
curl -X POST "http://localhost:8091/message" -H "Authorization: Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw" -H "Content-Type: application/json" -H "Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205" --data-raw "{    \"username\": \"user001\",    \"message\": \"meromAlex011\"}"
***
Если пришло сообщение вида:
{    name:       "имя отправителя",    message:    "history 10" }
проверяется токен, в случае успешной проверки токена отправляется отправителю 10 последних сообщений из БД

POST:
{
"username": "user001",
"message":"history 10"
}

RESPONSE: (e.g.)
{
"message002",
"message002",
"message002",
"message003",
"last",
"message007",
"8",
"message010",
"message009",
"meromAlex011"
}

CURL:
curl -X POST "http://localhost:8091/message" -H "Authorization: Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw" -H "Content-Type: application/json" -H "Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205" --data-raw "{    \"username\": \"user001\",    \"message\":\"history 10\"}"POST "http://localhost:8091/message" -H "Authorization: Bearer_eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyRGV0YWlscyIsInVzZXJuYW1lIjoidXNlcjAwMSJ9.SYEtTDtux1qE5Cw00UCl5jeYmhsBxTPECor7ZXDG1Xw" -H "Content-Type: application/json" -H "Cookie: JSESSIONID=F13F4430D2D1CC157B637FBEE6A5C205" --data-raw "{    \"username\": \"user001\",    \"message\": \"meromAlex011\"}"
