 
package spring_jsp.annotation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.transaction.TransactionStatus; 
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

 
@Controller
@RequestMapping("/sqlWorkbench")
public class SQLWorkBeanchController
{

	private final Logger LOG=LoggerFactory.getLogger(SQLWorkBeanchController.class);
	
	@Autowired
	private DatabaseCommandDao databaseCommandDao;
	@Autowired
	private  TransactionTemplate transactionTemplate;
	
//    @RequestMapping("/init")
//    public ModelAndView init(HttpServletRequest request,HttpServletResponse response){
//    	ModelAndView mav = new ModelAndView();
//        mav.setViewName("/sqlWorkbench/sqlView");
//        return mav;
//    }
  
    @RequestMapping(value="/submitJson",method={RequestMethod.POST,RequestMethod.GET}	)
    @ResponseBody
	// @ResponseStatus(HttpStatus.OK)
    public EasyUIResult queryJsonResult(@RequestBody final EasyUIParam param){
		LOG.info("执行SQL 语句 {}",param.sql);
		final EasyUIResult res=new EasyUIResult();
		String sql= param.sql.trim().toLowerCase();
		try{
				if( sql.startsWith("select") )
				{
					List<Map<String,Object>>  resList = databaseCommandDao.query(param.sql + " limit " + param.skip +" , "+ param.pageSize);
					if(resList.size()==0)
					{
						 setOneMessage(res,"未查询到记录");
					}else
					{
						res.setRows(resList);
						res.setTotal(resList.size());
					}
					LOG.info("执行查询OK" );

				} else if( sql.startsWith("show") )
				{
					List<Map<String,Object>>  resList = databaseCommandDao.query(param.sql );
					if(resList.size()==0)
					{
						 setOneMessage(res,"未查询到记录");
					}else
					{
						res.setRows(resList);
						res.setTotal(resList.size());
					}
					LOG.info("执行show 查询OK" );
				} else 
				{
					transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
							Object firstPoit= status.createSavepoint();

							int effectRow=databaseCommandDao.execute(param.sql);
							 if(effectRow>1)
							 {
								 status.rollbackToSavepoint(firstPoit);
								 res.setErrorMsg("更新条数不能大于1条,请一条一条的更新");
							 }else 
							 {
								 status.releaseSavepoint(firstPoit);
								 setOneMessage(res,"更新成功");
							 }
						}
					}); 
					LOG.info("执行 非select 语句 OK" );
				}
		}catch(Exception e) //BadSqlGrammarException
		{
			res.setErrorMsg(e.getMessage());
			LOG.error("执行查询错误",e);
		}
		return res;
    }
    private void setOneMessage(EasyUIResult res,String str)
	{ 
		 Map<String,Object> row=new HashMap<String,Object>();
		 row.put("结果", str);
		List<Map<String,Object>>  resList = Collections.singletonList(row) ;
		 res.setRows(resList);
		 res.setTotal(1);
	 
	}
}
  class EasyUIParam {
	 
		String sql;
		int skip;
		int pageSize;

		public void setSql(String sql) {
			this.sql = sql;
		}

		public String getSql() {
			return sql;
		}

		public int getSkip() {
			return skip;
		}
		public void setSkip(int skip) {
			this.skip = skip;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		 
	}
	 class EasyUIResult 
	 {
		 int total;
		  
		 List<Map<String,Object>>  rows;
		String errorMsg;

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
 
		public List<Map<String, Object>> getRows() {
			return rows;
		}
		public void setRows(List<Map<String, Object>> rows) {
			this.rows = rows;
		}
 
		public void setTotal(int total) {
			this.total = total;
		}
		public int getTotal() {
			return total;
		}
	 }
		