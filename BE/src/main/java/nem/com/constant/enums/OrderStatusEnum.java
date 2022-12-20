package nem.com.constant.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    READY_TO_PICK(1,"ready_to_pick"),
    TRANSPORTING(2,"transporting"),
    DELIVERED(3,"delivered"),
    CANCEL(4,"cancel")
    ;

    private final int valueNumber;
    private final String valueString;

    public static OrderStatusEnum valueOfStatus(String status){
        if (Objects.nonNull(status)){
            for (OrderStatusEnum e: values()) {
                if (e.valueString.equalsIgnoreCase(status)){
                    return e;
                }
            }
        }
        return null;
    }
}
