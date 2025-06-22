package com.project.SpringBatchDB2CSV.Configs;

import com.project.SpringBatchDB2CSV.Entity.Products;
import com.project.SpringBatchDB2CSV.Repository.ProductsRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
public class JobConfigs {

    @Autowired
    private ProductsRepository productRepository;

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("ExportData", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("export-data-csv",jobRepository)
                .<Products, Products>chunk(5,transactionManager)
                .reader(reader())
                .writer(writer())
                .build();
    }

    private RepositoryItemReader<Products> reader(){
        RepositoryItemReader<Products> readProduct = new RepositoryItemReader<>();
        readProduct.setRepository(productRepository);
        readProduct.setMethodName("findAll");
        readProduct.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return readProduct;
    }

    private FlatFileItemWriter<Products> writer(){
        FlatFileItemWriter<Products> writeProduct = new FlatFileItemWriter<>();
        writeProduct.setName("Export");
        writeProduct.setResource(new FileSystemResource("product_coffee.csv"));
        writeProduct.setHeaderCallback(writer -> writer.write("id, name, price, coffee_origin"));
        writeProduct.setLineAggregator(new DelimitedLineAggregator<>(){
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<>(){
                    {
                        setNames(new String[]{"id", "name", "price", "coffee_origin"});
                    }
                });
            }});

        return writeProduct;
    }


}
