/*
 * Copyright 2017 the original author or authors.
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

package io.spring.onboarding.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import io.spring.onboarding.Employee;
import io.spring.onboarding.EmployeeApplication;
import io.spring.onboarding.EmployeeApplicationRowMapper;
import io.spring.onboarding.OnboardProcessor;
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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.deployer.spi.scheduler.ScheduleInfo;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;

@Configuration
@EnableTask
@EnableBatchProcessing
public class OnboardingConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job OnboardingJob(Step migrationStep) {
		return this.jobBuilderFactory.get("onboarding")
				.incrementer(new RunIdIncrementer())
				.start(migrationStep)
				.build();
	}

	@Bean
	public Step onboarding(ItemReader itemReader,
			ItemProcessor itemProcessor, ItemWriter writer) {
		return this.stepBuilderFactory.get("migrationStep")
				.<ScheduleInfo, ScheduleInfo> chunk(1)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(writer)
				.build();
	}

	@Bean
	public ItemReader<EmployeeApplication> itemReader(DataSource dataSource, OnboardingProperties onboardingProperties) {
		String sql = "select last_name, first_name, middle_initial, govt_id, candidate_id," +
				" address, state_abbrev, postal_code  FROM employee_application";
		if(StringUtils.hasText(onboardingProperties.getStartLastNamePartition())) {
			sql += " WHERE last_name like '" + onboardingProperties.getStartLastNamePartition() + "%'";
		}
		JdbcCursorItemReader<EmployeeApplication> jdbcCursorItemReader = new JdbcCursorItemReaderBuilder<>()
				.dataSource(dataSource)
				.name("employeeApplicationReader")
				.sql(sql)
				.rowMapper(new EmployeeApplicationRowMapper())
				.build();
		return jdbcCursorItemReader ;
	}

	@Bean
	public ItemWriter<Employee> itemWriter(DataSource datasource) {
		return new JdbcBatchItemWriterBuilder<Employee>()
				.dataSource(datasource)
				.sql("INSERT INTO employee VALUES (:last_name, :first_name, " +
						":middle_initial, :govt_id, :employee_id, :address, :state_abbrev, " +
						":postal_code, :start_date, :employee_type_id)")
				.itemSqlParameterSourceProvider(new ItemSqlParameterSourceProvider<Employee>() {
					@Override
					public SqlParameterSource createSqlParameterSource(Employee item) {
						return new MapSqlParameterSource(new HashMap<String, Object>() {
							{
								put("last_name", item.getLastName());
								put("first_name", item.getFirstName());
								put("govt_id", item.getGovtId());
								put("middle_initial", item.getMiddleInitial());
								put("employee_id", item.getEmployee_id());
								put("address", item.getAddress());
								put("state_abbrev", item.getStateAbbrev());
								put("postal_code", item.getPostalCode());
								put("start_date", item.getStartDate());
								put("employee_type_id", item.getEmployeeTypeId());
							}
						});
					}
				})
				.build();
	}

	@Bean
	public ItemProcessor<EmployeeApplication, Employee> itemProcessor() {
		return new OnboardProcessor();
	}

	@Bean
	@ConfigurationProperties("io.spring")
	public OnboardingProperties onboardingProperties() {
		return new OnboardingProperties();
	}
}
