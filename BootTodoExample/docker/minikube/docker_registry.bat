@echo off
kubectl create secret docker-registry  docker-registry-secret ^
--docker-server=https://index.docker.io/v1/ ^
--docker-username=docker0now ^
--docker-password="asia1394!!" ^
--docker-email=c4now@naver.com ^
-n kaniko