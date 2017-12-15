package alibaba.dubbo.server;

public class DubboFacadeImpl implements DubboFacade {
	public QueryRes queryService(QueryReq req){
		
		System.out.println("得到请求数据:"+req.getQueryId());
		
		QueryRes res =new QueryRes();
		res.setData("服务返回数据");
		res.setResultCode("00");
		res.setResultDescription("成功");
		return res;
	}
}
