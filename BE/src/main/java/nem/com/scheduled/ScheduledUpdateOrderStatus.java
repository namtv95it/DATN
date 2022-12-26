package nem.com.scheduled;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nem.com.constant.GhnConstant;
import nem.com.constant.enums.OrderStatus;
import nem.com.constant.enums.OrderStatusEnum;
import nem.com.entity.Orders;
import nem.com.repository.OrdersRepository;
import nem.com.service.OrderService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.cloudinary.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class ScheduledUpdateOrderStatus {

    private final OrderService orderService;
    private final OrdersRepository ordersRepository;

    public static void main(String[] args) {
        SpringApplication.run(ScheduledUpdateOrderStatus.class, args);
    }

    @Scheduled(cron = "0 0 6-22 * * *")
    public void scheduledUpdateOrderStatus() throws IOException {
        log.info("scheduledUpdateOrderStatus");
        this.updateStatus();
    }
    private void updateStatus() throws IOException{
        log.info("updateStatus");
            List<Orders> ordersStatus = this.ordersRepository.getOrderGhnByStatus();
            if (!ordersStatus.isEmpty()){
                for (int i = 0; i < ordersStatus.size(); i++) {
                    String statusGhn = this.getStatusGhn(ordersStatus.get(i).getOrderCode());
                    if (OrderStatus.checkStatusOrder(statusGhn) && !statusGhn.equals(OrderStatus.READY_TO_PICK.getStatus())){
                        int statusGhnNumber = Objects.requireNonNull(OrderStatusEnum.valueOfStatus(statusGhn)).getValueNumber();
                        if (ordersStatus.get(i).getStatus() != statusGhnNumber){
                            Orders orders = ordersStatus.get(i);
                            orders.setStatus(statusGhnNumber);
                            orders.setShippedDate(new Date());
                            log.info("--Cập nhật trạng thái đơn hàng: {} --",ordersStatus.get(i).getOrderCode());
                            this.ordersRepository.save(orders);
                        }
                    }
                }
            }
    }
    private HttpPost setHeader(String orderCode) throws IOException{
        log.info("--Set Headers--");
        String uri = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";
        //Body
        StringBuilder json = new StringBuilder();
        json.append("{\"order_code\":\"");
        json.append(orderCode);
        json.append("\"}");
        StringEntity en = new StringEntity(json.toString());
        //Headers with body
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(en);
        httpPost.setHeader("ShopId", "120561");
        httpPost.setHeader("Token", "bd7afdaa-61da-11ed-889d-7acb4388671e");
        httpPost.setHeader("Content-type", "application/json");
        return httpPost;
    }

    private String getStatusGhn(String orderCode) throws IOException{
        log.info("getStatusGhn");
        try (CloseableHttpClient client = HttpClients.createDefault()){
            //Call api ghn
            CloseableHttpResponse response = client.execute(this.setHeader(orderCode));
            //Read result
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder resultJsonStr = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultJsonStr.append(line);
            }
            JSONObject result = new JSONObject(resultJsonStr.toString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jn = mapper.readTree(result.get("data").toString());
            return jn.get("status").toString().replace("\"","");
        }
    }
}
