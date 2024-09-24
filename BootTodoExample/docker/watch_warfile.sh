#!/bin/bash

# 모니터링할 폴더와 파일 설정
WATCH_DIR=/tmp/libs
WATCH_FILE=BootTodoExample.war
#CONTAINER_NAME="your_container_name"  # 실제 컨테이너 이름으로 변경하세요

# 이전 수정 시간 초기화
LAST_MODIFIED=0

while true; do
    # 현재 파일의 수정 시간 확인
    CURRENT_MODIFIED=$(stat -c %Y "$WATCH_DIR/$WATCH_FILE" 2>/dev/null)

    # 파일이 존재하고 수정되었는지 확인
    if [ -f "$WATCH_DIR/$WATCH_FILE" ] && [ "$CURRENT_MODIFIED" != "$LAST_MODIFIED" ]; then
        #echo "파일 $WATCH_FILE 이 변경되었습니다. 컨테이너로 복사합니다."

        # 도커 컨테이너 내부로 파일 복사
        #docker cp "$WATCH_DIR/$WATCH_FILE" "$CONTAINER_NAME:/tmp/libs/$WATCH_FILE"

        # 컨테이너 내에서 파일을 /apps 폴더로 이동
        #docker exec "$CONTAINER_NAME" mv "/tmp/libs/$WATCH_FILE" "/apps/$WATCH_FILE"
		
		cp /tmp/libs/$WATCH_FILE /usr/local/tomcat/webapps/demo.war
        echo "\n파일이 성공적으로 복사되었습니다."

        # 마지막 수정 시간 업데이트
        LAST_MODIFIED=$CURRENT_MODIFIED
    fi

    # 10초 대기 후 다시 확인
    sleep 10
done