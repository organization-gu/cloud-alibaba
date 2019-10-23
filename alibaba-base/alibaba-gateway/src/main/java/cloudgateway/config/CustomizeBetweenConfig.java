package cloudgateway.config;

import lombok.Data;

import java.time.LocalTime;

/**
 * @Author GU-YW
 * @Date 2019/10/22 10:41
 */
@Data
public class CustomizeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
