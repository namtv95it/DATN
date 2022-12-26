package nem.com.scheduled;

import lombok.AllArgsConstructor;
import nem.com.entity.Discounts;
import nem.com.entity.ProductDiscount;
import nem.com.repository.DiscountsRepository;
import nem.com.repository.ProductDiscountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class ProcessToPromotion extends Thread {
    private DiscountsRepository discountsRepository ;
    private ProductDiscountRepository productDiscountRepository ;

    public static void main(String[] args) {
        SpringApplication.run(ProcessToPromotion.class);
    }
    @Scheduled(cron = "59 04 13 * * ?")
    public void scheduledUpdateDiscountStatus() throws Exception{
        System.out.println("proceessssss");
        process();
    }

    public void process(){
        List<Discounts> listd = this.discountsRepository.findDiscountsByStatus() ;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy") ;
        String date1 = sdf.format(new Date() ) ;
        for ( Discounts x: listd ){
            String date2 = sdf.format( x.getStartDate() ) ;
            String date3 = sdf.format( x.getEndDate() ) ;
            if( date2.equalsIgnoreCase(date1) ){
                this.updateDiscountProductStart(x);
            }

            if (date3.equalsIgnoreCase(date1)) {
                this.updateDiscountProductEnd(x);
            }
        }
    }

    public void updateDiscountProductStart(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<Integer> listPro = new ArrayList<>();

        if( listPd.size() != 0 ){
            for( ProductDiscount x: listPd ){
                if( x.getProduct().getDiscount() < discounts.getDiscount() || x.getDiscount() == null ){
                    listPro.add(x.getProduct().getId() );
                }
            }
        }

        Integer[] arr = listPro.toArray(Integer[]::new) ;
        discounts.setStatus(2);
        this.discountsRepository.save( discounts) ;
        this.discountsRepository.updateDiscountProduct( discounts.getDiscount() , arr );
    }

    public void updateDiscountProductEnd(Discounts discounts ) {
        List<ProductDiscount> listPd = this.productDiscountRepository.findAllPd(discounts.getId()) ;
        List<Integer> listPro = new ArrayList<>();

        if( listPd.size() != 0 ){
            for( ProductDiscount x: listPd ){
                listPro.add(x.getProduct().getId() );
            }
        }

        discounts.setStatus(3);
        this.discountsRepository.save(discounts) ;
        this.discountsRepository.updateDiscountProduct( 0 , listPro.toArray(Integer[]::new)  );
    }
}
