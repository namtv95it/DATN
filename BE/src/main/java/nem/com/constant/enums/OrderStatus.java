package nem.com.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    READY_TO_PICK("ready_to_pick"),
    TRANSPORTING("transporting"),
    DELIVERED("delivered"),
    CANCEL("cancel")
    ;
    private final String status;

    public static boolean checkStatusOrder(String status){
        if (Objects.nonNull(status)){
            for (OrderStatus e: values()) {
                if (e.status.equalsIgnoreCase(status)){
                    return true;
                }
            }
        }
        return false;
    }
}
