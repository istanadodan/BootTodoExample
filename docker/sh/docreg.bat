@echo off
kubectl create secret docker-registry  regcred ^
--docker-server=http://local-registry:5000/ ^
--docker-username=docker ^
--docker-password=password ^
--docker-email=c4now@naver.com