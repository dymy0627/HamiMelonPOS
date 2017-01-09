package application;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.*;  
public class Task extends TimerTask {  
	Connection con = null; //Database objects 
    public void run() {  
        System.out.println("�������{");  
        String systemtime = getDateTime();
	    System.out.println("�{�b�ɶ�"+getDateTime());  //��X �{�b�ɶ�
	    String[] timeArray = systemtime.split(" ");
	    
	    String time = timeArray[0];
	    
		String insertdate = new String("insert into hamimelon.daily(teppanyaki_date)values('"+time+"')");
		
		String Turnover = new String("update hamimelon.daily set Turnover=(select sum(cost) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String Lunch_Turnover = new String("update hamimelon.daily set Lunch_Turnover=(select sum(cost) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"') where teppanyaki_date='"+time+"'");
		
		String L_Number_of_visitors = new String("update hamimelon.daily set L_Number_of_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"') where teppanyaki_date='"+time+"'");
		
		String L_Average_consumption = new String("update hamimelon.daily set L_Average_consumption=(select TRUNCATE(sum(cost)/sum(number_of_meals),0) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String L_Outsourcing = new String("update hamimelon.daily set L_Outsourcing=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"' and consumption_type='�~�a')where teppanyaki_date='"+time+"'");
		
		String L_delivery = new String("update hamimelon.daily set L_delivery=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')<'16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"' and consumption_type='�~�e')where teppanyaki_date='"+time+"'");
		
		String Dinner_Turnover = new String("update hamimelon.daily set Dinner_Turnover=(select sum(cost) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"') where teppanyaki_date='"+time+"'");
		
		String D_Number_of_visitors = new String("update hamimelon.daily set D_Number_of_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals  where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"') where teppanyaki_date='"+time+"'");
		
		String D_Average_consumption = new String("update hamimelon.daily set D_Average_consumption=(select TRUNCATE(sum(cost)/sum(number_of_meals),0) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String D_Outsourcing = new String("update hamimelon.daily set D_Outsourcing=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"' and consumption_type='�~�a')where teppanyaki_date='"+time+"'");
		
		String D_delivery = new String("update hamimelon.daily set D_delivery=(select count(consumption_type) from hamimelon.detail_list_meals where date_format(Ordering_time,'%H')>='16' and date_format(Ordering_time,'%Y-%m-%d')='"+time+"' and consumption_type='�~�e')where teppanyaki_date='"+time+"'");
		
		String Double_package = new String("update hamimelon.daily set Double_package=(select sum(Double_package) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"') where teppanyaki_date='"+time+"'");
		
		String Special_meals = new String("update hamimelon.daily set Special_meals=(select sum(Special_meals) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String wind_and_rain = new String("update hamimelon.daily set wind_and_rain=(select sum(wind_and_rain) from hamimelon.special_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String total_visitors = new String("update hamimelon.daily set total_visitors=(select sum(number_of_meals) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"')where teppanyaki_date='"+time+"'");
		
		String total_AVG_Turnover = new String("update hamimelon.daily set total_AVG_Turnover=(select sum(cost)/sum(number_of_meals) from hamimelon.detail_list_meals where date_format(Ordering_time,'%Y-%m-%d')='"+time+"' and Consumption_type='����')where teppanyaki_date='"+time+"'");
		
        try
        {
            //�s��MySQL
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("�s�����\MySQLToJava");
            //�إ�Ū����Ʈw (test ����Ʈw�W��; user ��MySQL�ϥΪ̦W��; passwrod ��MySQL�ϥΪ̱K�X)
            con = DriverManager.getConnection( 
            	      "jdbc:mysql://ap-cdbr-azure-southeast-b.cloudapp.net/?useUnicode=true&characterEncoding=Big5", 
            	      "b63738672a9134","555d06ab"); 
            System.out.println("�s�����\MySQL");
            Statement st = con.createStatement();
            //���X���s�W�����
            st.execute(insertdate);
            st.execute(Turnover);
            st.execute(Lunch_Turnover);
            st.execute(L_Number_of_visitors);
            st.execute(L_Average_consumption);
            st.execute(L_Outsourcing);
            st.execute(L_delivery);
            st.execute(Dinner_Turnover);
            st.execute(D_Number_of_visitors);
            st.execute(D_Average_consumption);
            st.execute(D_Outsourcing);
            st.execute(D_delivery);
            st.execute(Double_package);
            st.execute(Special_meals);
            st.execute(wind_and_rain);
            st.execute(total_visitors);
            st.execute(total_AVG_Turnover);
            /*
            ResultSet rs = st.getResultSet();
            while(rs.next())
            {
                System.out.println(rs.getString("ordering_time"));
            }
            */
        }catch(Exception e)
        {
            System.out.println("?");
        }
        
	    
    }  
    
    public static String getDateTime() {  //�L�Ѽ�=�Ǧ^�{�b�ɶ�
	    Calendar c=Calendar.getInstance();
	    return getYMDHMS(c);
	    }
	  public static String getDateTime(int Y, int M, int D, int H, int m, int S) { //���w�ɶ�
	    Calendar c=Calendar.getInstance();
	    c.set(Y, --M, D, H, m, S);  //�Ƕi�Ӫ���ڤ���n�� 1
	    return getYMDHMS(c);
	    }
	  public static String getYMDHMS(Calendar c) { //��X�榡�s�@
	    int[] a={c.get(Calendar.YEAR),
	             c.get(Calendar.MONTH),
	             c.get(Calendar.DAY_OF_MONTH),
	             c.get(Calendar.HOUR_OF_DAY),
	             c.get(Calendar.MINUTE),
	             c.get(Calendar.SECOND)
	             };
	    StringBuffer sb=new StringBuffer();
	    sb.append(a[0]);
	    if (a[1]<9) {sb.append("-0" + (a[1] + 1));}   //�[ 1 �~�|�o���ڤ��
	    else {sb.append("-" + (a[1] + 1));}
	    if (a[2]<10) {sb.append("-0" + (a[2]));}
	    else {sb.append("-" + (a[2]));}
	    if (a[3]<10) {sb.append(" 0" + (a[3]));}
	    else {sb.append(" " + (a[3]));}
	    if (a[4]<10) {sb.append(":0" + a[4]);}
	    else {sb.append(":" + a[4]);}
	    if (a[5]<10) {sb.append(":0" + a[5]);}
	    else {sb.append(":" + a[5]);}
	    return sb.toString();
	    }
	  
}  