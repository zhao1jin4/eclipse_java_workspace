package alibaba.dubbo.server;

public class DubboFacadeImpl implements DubboFacade {
	public QueryRes queryService(QueryReq req){
		
		System.out.println("�õ���������:"+req.getQueryId());
		
		QueryRes res =new QueryRes();
		res.setData("���񷵻�����");
		res.setResultCode("00");
		res.setResultDescription("�ɹ�");
		return res;
	}
}
