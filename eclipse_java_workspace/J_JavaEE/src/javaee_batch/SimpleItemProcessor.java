 
package javaee_batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named("SimpleItemProcessor")
public class SimpleItemProcessor
    implements ItemProcessor {

    @Inject
    private JobContext jobContext;


    public Object processItem(Object obj) throws Exception {
        Properties jobParameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
/*
        PayrollInputRecord inputRecord = (PayrollInputRecord) obj;
        PayrollRecord payrollRecord = new PayrollRecord();
        payrollRecord.setMonthYear((String) jobParameters.get("monthYear"));

        int base = inputRecord.getBaseSalary();
        float tax = base * 27 / 100.0f;
        float bonus = base * 15 / 100.0f;

        payrollRecord.setEmpID(inputRecord.getId());
        payrollRecord.setBase(base);
        payrollRecord.setTax(tax);
        payrollRecord.setBonus(bonus);
        payrollRecord.setNet(base + bonus - tax);
       
        return payrollRecord;
      */ 
        return new Object();
    }
    
}
