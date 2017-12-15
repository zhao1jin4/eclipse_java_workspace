package jdbc.oracleadvance;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Struct;
import java.sql.Types;

import oracle.sql.Datum;
import oracle.sql.STRUCT;


/*

create or replace type o_tmp as object 
(
  userid varchar2(10),
  username varchar2(20)
);
create or replace type tmp_array is table of o_tmp
/
create or replace  procedure getObject(obj_arr out tmp_array) is
--declare obj_arr tmp_array;
  tmp_obj o_tmp;
  test_arr tmp_array := tmp_array();
  n number := 1;
begin
  loop  exit when n > 5;
    tmp_obj := o_tmp('test1' || n, 'fuyue' || n);
   test_arr.extend;--可以扩展
    test_arr(n) := tmp_obj;
    n := n + 1;
  end loop;
  obj_arr := test_arr;--return
  for n in obj_arr.first .. obj_arr.last loop  --这个n不同于上一个n,.firts ,.last
    tmp_obj := obj_arr(n);--以()仿问
    dbms_output.put_line(tmp_obj.userid || ' --- ' || tmp_obj.username);
  end loop;
end;
/
 */
public class NestTable
{
    public static void main(String[] args) {
        test();
    }

    private static void test()
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con =  DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "hr", "hr");
            
            CallableStatement stmt = con.prepareCall("{call getObject(?)}");
            stmt.registerOutParameter(1, Types.ARRAY, "TMP_ARRAY");
            stmt.execute();
            Array outparam = stmt.getArray(1);
            Object[] obj = (Object[]) outparam.getArray();

            for (int i = 0; i < obj.length; i++) 
            {
            	/*
            	//--------oracle self
                STRUCT struct = (STRUCT) obj[i];  //oracle  STRUCT
                Datum[] dt = struct.getOracleAttributes();//oracle   Datum
                for (int n = 0; n < dt.length; n++) 
                {
                    System.out.println(dt[n]);
                }*/
            	
            	//--------Java standard
	        	  Struct struct = (Struct) obj[i]; 
	        	  //System.out.println(struct.getSQLTypeName());//HR.O_TMP
	        	  Object[] dt = struct.getAttributes();
	        	  for (int n = 0; n < dt.length; n++) 
	              {
	                  System.out.println(dt[n]);
	              }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
