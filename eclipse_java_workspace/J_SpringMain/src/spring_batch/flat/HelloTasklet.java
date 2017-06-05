package spring_batch.flat;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class HelloTasklet implements Tasklet {
    private String message;
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public RepeatStatus execute(StepContribution step, ChunkContext context)
            throws Exception {
        System.out.println(message);
        return RepeatStatus.FINISHED;
    }
}