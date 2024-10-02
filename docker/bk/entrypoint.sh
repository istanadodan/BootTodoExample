#!/bin/sh
/usr/local/tomcat/watch_warfile.sh &  # 백그라운드에서 스크립트 실행
exec "$@"  # 도커 CMD 실행