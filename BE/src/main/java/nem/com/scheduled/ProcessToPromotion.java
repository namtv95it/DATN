package nem.com.scheduled;

import nem.com.entity.Discounts;
import nem.com.utils.DiscountUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProcessToPromotion extends Thread {
    private Discounts discounts ;
    private DiscountUtils discountUtils ;

    public ProcessToPromotion( Discounts discounts ){
        this.discounts = discounts ;
    }

    @Override
    public void run() {
        Date newDate = new Date() ;
        Calendar calendar = Calendar.getInstance() ;
        try {
            Thread.sleep( this.changeTime( newDate , this.discounts.getStartDate()) );
            this.discountUtils.updateDiscountProductStart(this.discounts);

            calendar.setTime(this.discounts.getEndDate());
            calendar.add( Calendar.DATE , 1 );
            Thread.sleep( this.changeTime( this.discounts.getStartDate() , calendar.getTime() ) );
            this.discountUtils.updateDiscountProductEnd(this.discounts);
        }catch ( Exception e){
            throw new RuntimeException() ;
        }
    }

    private Long changeTime( Date startDate , Date endDate ){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY") ;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        if( sdf.format(startDate).equalsIgnoreCase( sdf.format(endDate) ) ){
            return 0L ;
        }else {
            c1.setTime(startDate);
            c2.setTime(endDate);
            return c2.getTime().getTime() - c1.getTime().getTime() ;
        }
    }
}
