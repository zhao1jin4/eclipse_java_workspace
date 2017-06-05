 package javaee_batch;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

@Named("SimpleItemReader")
public class SimpleItemReader
    extends AbstractItemReader {

//    @EJB
//    private SampleDataHolderBean dataBean;

    @Inject
    private JobContext jobContext;

//    Iterator<PayrollInputRecord> payrollInputRecords;

    public void open(Serializable e) throws Exception
    {
        Properties jobParameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
        /*
        ConcurrentSkipListMap<Integer, PayrollInputRecord> records = dataBean.getPayrollInputRecords(
                (String) jobParameters.get("monthYear"));
        Integer fromKey = (Integer) jobParameters.get("startEmpID");
        Integer toKey = (Integer) jobParameters.get("endEmpID");
        payrollInputRecords = records.subMap(fromKey, true, toKey, false).values().iterator();
        */
    }

    public Object readItem() throws Exception 
    {
//        return payrollInputRecords.hasNext() ? payrollInputRecords.next() : null;
    	return new Object();
    }
    
}
