package ksd.sto.ndm.domain.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static int fromTimestamp(double timestamp) {
        // 현재 시간의 타임스탬프 (초단위)
        long currentTimestamp = Instant.now().getEpochSecond();
        
        // 현재 시간 (서울 시간대 적용)
        ZonedDateTime seoulTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        // 서울 기준의 Unix 타임스탬프 (초 단위)
        long epochSecondsSeoul = seoulTime.toEpochSecond();

        long inputSeconds = (long) timestamp;
        long difference = Math.abs(currentTimestamp - inputSeconds);

        Instant c = Instant.ofEpochSecond(currentTimestamp);
        ZonedDateTime d = c.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println(d);
        System.out.println(d.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
        return (int) difference;
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtils.fromTimestamp(1740664700.266));
    }
}
