package alibaba.dubbo.server;

public class DubboGroupVersionFacadeImpl implements DubboGroupVersionFacade {
	public QueryRes theGroupVersion(QueryReq req){
		
		System.out.println("theGroupVersion �õ���������:"+req.getQueryId());
		
		QueryRes res =new QueryRes();
		res.setData("���񷵻�����");
		res.setResultCode("00");
		res.setResultDescription("�ɹ�");
		return res;
	}

	 
}
