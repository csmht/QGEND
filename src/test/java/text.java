import java.sql.SQLException;

public class text {

    public static void main(String[] args) throws SQLException, InterruptedException {
        try{
          int a =  csmht.dao.JedisTool.FindLikesPost("20");
           System.out.println(a);
//            csmht.dao.JedisTool.unFollow("1","2");
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public void Pool(){

    }


}
