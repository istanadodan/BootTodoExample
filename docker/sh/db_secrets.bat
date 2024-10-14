@echo off
kubectl create secret generic db-secrets ^
  --from-literal=MYSQL_DATABASE=mydb ^
  --from-literal=MYSQL_PASSWORD=secret ^
  --from-literal=MYSQL_ROOT_PASSWORD=verysecret ^
  --from-literal=MYSQL_USER=myuser