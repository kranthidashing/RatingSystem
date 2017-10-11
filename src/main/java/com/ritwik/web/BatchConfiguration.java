package com.ritwik.web;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.ritwik.web.model.Person;
import com.ritwik.web.services.PersonItemProcessor;
import com.ritwik.web.services.ServiceRepo5;

@Configuration
@EnableBatchProcessing
@ComponentScan
@EnableAutoConfiguration
public class BatchConfiguration {
	    @Autowired
	    private JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	    
	    @Autowired
	    private ServiceRepo5 servicerepo5;
    @Bean
    public ItemReader<Person> reader() {
        // we read a flat file that will be used to fill a Person object
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        // we pass as parameter the flat file directory
        reader.setResource(new ClassPathResource("PersonData.csv"));
        // we use a default line mapper to assign the content of each line to the Person object
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            // we use a custom fixed line tokenizer
            setLineTokenizer(new DelimitedLineTokenizer() {{
            	setDelimiter(",");
                setNames(new String[]{"firstName", "familyName", "year"});
            }});
            // as field mapper we use the name of the fields in the Person class
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                // we create an object Person
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }
    @Bean
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }   
    @Bean
    RepositoryItemWriter<Person> servicerepo5ItemWriter(){

        RepositoryItemWriter<Person> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(servicerepo5);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;

    }
    @Bean
    public Job importPerson(JobBuilderFactory jobs, Step s1) {

        return jobs.get("import")
                .incrementer(new RunIdIncrementer()) // because a spring config bug, this incrementer is not really useful
                .flow(s1)
                .end()
                .build();
    }
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
    		RepositoryItemWriter<Person> servicerepo5ItemWriter, ItemProcessor<Person, Person> processor) {
        return stepBuilderFactory.get("step1")
                .<Person, Person>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(servicerepo5ItemWriter)
                .build();
    }
}
