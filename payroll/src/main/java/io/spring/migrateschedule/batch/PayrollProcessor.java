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

package io.spring.migrateschedule.batch;

import java.util.Date;

import io.spring.migrateschedule.Employee;
import io.spring.migrateschedule.Payroll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.cloud.deployer.spi.scheduler.ScheduleInfo;

/**
 * Generates the pay for the {@link Payroll} .
 * @author Glenn Renfro
 */
public class PayrollProcessor<T, C extends ScheduleInfo> implements ItemProcessor<Employee, Payroll>{

	private static final Logger logger = LoggerFactory.getLogger(PayrollProcessor.class);

	public PayrollProcessor() {

	}

	@Override
	public Payroll process(Employee employee){
		Payroll  payroll = new Payroll();
		payroll.setEmployeeId(employee.getEmployeeId());
		payroll.setEmployeeTypeId(employee.getEmployeeTypeId());
		payroll.setAmount(1000000);
		payroll.setCheckDate(new Date());
		payroll.setLastName(employee.getLastName());
		payroll.setFirstName(employee.getFirstName());
		payroll.setMiddleInitial(employee.getMiddleInitial());

		return payroll;
	}
}
