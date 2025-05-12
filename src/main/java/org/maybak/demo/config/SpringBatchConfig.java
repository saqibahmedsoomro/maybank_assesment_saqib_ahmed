package org.maybak.demo.config;

import lombok.AllArgsConstructor;
import org.maybak.demo.entity.TransactionEntity;
import org.maybak.demo.repository.TransactionRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    @Autowired
    private TransactionRepository repository;

    @Bean
    public FlatFileItemReader<TransactionEntity> reader() {
        FlatFileItemReader<TransactionEntity> transactionReader = new FlatFileItemReader<>();
        transactionReader.setResource(new ClassPathResource("dataSource.txt"));
        transactionReader.setName("transactions");
        transactionReader.setLinesToSkip(1);
        transactionReader.setLineMapper(lineMapper());
        return transactionReader;
    }

    private LineMapper<TransactionEntity> lineMapper() {
        DefaultLineMapper<TransactionEntity> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("ACCOUNT_NUMBER","TRX_AMOUNT","DESCRIPTION","TRX_DATE","TRX_TIME","CUSTOMER_ID");


        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new CustomTransactionFieldSetMapper());
        return lineMapper;

    }

    @Bean
    public TransactionProcessor processor() {
        return new TransactionProcessor();
    }
    @Bean
    public RepositoryItemWriter<TransactionEntity> writer() {
        RepositoryItemWriter<TransactionEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(repository);
        writer.setMethodName("save");
        return writer;
    }
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, RepositoryItemWriter<TransactionEntity> writer) {
        return new StepBuilder("step1", jobRepository)
                .<TransactionEntity, TransactionEntity> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job importCustomerJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importCustomer", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }
}
