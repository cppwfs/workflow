/*
 * Copyright 2020 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.spring.payroll.configuration;

import java.util.HashMap;

import io.spring.payroll.Employee;
import io.spring.payroll.EmployeeRowMapper;
import io.spring.payroll.Payroll;
import io.spring.payroll.PayrollProperties;
import io.spring.payroll.batch.PayrollProcessor;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.deployer.spi.scheduler.ScheduleInfo;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


/**
 * @author Glenn Renfro
 */
@Configuration
@EnableBatchProcessing
@EnableTask
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job importUserJob(Step migrationStep) {
		return this.jobBuilderFactory.get("migrationJob")
				.incrementer(new RunIdIncrementer())
				.start(migrationStep)
				.build();
	}

	@Bean
	public Step migrationStep(ItemReader itemReader,
			ItemProcessor itemProcessor, ItemWriter<Payroll> writer) {
		return this.stepBuilderFactory.get("migrationStep")
				.<ScheduleInfo, ScheduleInfo> chunk(1)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(writer)
				.build();
	}

	@Bean
	public ItemReader<Employee> itemReader(PayrollProperties payrollProperties, DataSource dataSource) {
		String sql = "select last_name, first_name, middle_initial, govt_id, employee_id," +
				" address, state_abbrev, postal_code, employee_type_id  FROM employee";
		JdbcCursorItemReader<Employee> jdbcCursorItemReader = new JdbcCursorItemReaderBuilder<>()
				.dataSource(dataSource)
				.name("employeeApplicationReader")
				.sql(sql)
				.rowMapper(new EmployeeRowMapper())
				.build();
		return jdbcCursorItemReader ;
	}

	@Bean
	public ItemWriter<Payroll> itemWriter(DataSource datasource) {
		return new JdbcBatchItemWriterBuilder<Payroll>()
				.dataSource(datasource)
				.sql("INSERT INTO payroll VALUES (:employee_id, :employee_type_id, " +
						":last_name, :first_name, :middle_initial, :amount, " +
						":check_date)")
				.itemSqlParameterSourceProvider(new ItemSqlParameterSourceProvider<Payroll>() {
					@Override
					public SqlParameterSource createSqlParameterSource(Payroll item) {
						return new MapSqlParameterSource(new HashMap<String, Object>() {
							{
								put("employee_id", item.getEmployeeId());
								put("employee_type_id", item.getEmployeeTypeId());
								put("last_name", item.getLastName());
								put("first_name", item.getFirstName());
								put("middle_initial", item.getMiddleInitial());
								put("amount", item.getAmount());
								put("check_date", item.getCheckDate());
							}
						});
					}
				})
				.build();
	}

	@Bean
	public ItemProcessor<Payroll, Employee> itemProcessor() {
		return new PayrollProcessor();
	}

	@Bean
	@ConfigurationProperties("io.spring")
	public PayrollProperties payrollProperties() {
		return new PayrollProperties();
	}
}
