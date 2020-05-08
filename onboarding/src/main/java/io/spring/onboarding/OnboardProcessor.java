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

package io.spring.onboarding;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

public class OnboardProcessor implements ItemProcessor<EmployeeApplication, Employee> {
	private Map<String, String> hireMap = new HashMap<>();
	private Map<String, String> employeeType = new HashMap<>();

	public OnboardProcessor() {
		this.hireMap.put("1", "1");
		this.hireMap.put("4", "4");
		this.hireMap.put("7", "7");
		this.hireMap.put("10", "10");
		this.hireMap.put("14", "14");
		this.hireMap.put("17", "17");
		this.hireMap.put("21", "21");
		this.hireMap.put("24", "24");
		this.hireMap.put("27", "27");
		this.hireMap.put("15", "15");
		this.hireMap.put("19", "19");
		this.hireMap.put("20", "20");
		this.hireMap.put("8", "8");
		this.hireMap.put("12", "12");
		this.hireMap.put("12", "12");

		this.employeeType.put("1", "1");
		this.employeeType.put("4", "2");
		this.employeeType.put("7", "3");
		this.employeeType.put("10", "4");
		this.employeeType.put("14", "1");
		this.employeeType.put("17", "2");
		this.employeeType.put("21", "3");
		this.employeeType.put("24", "4");
		this.employeeType.put("27", "1");
		this.employeeType.put("15", "2");
		this.employeeType.put("19", "3");
		this.employeeType.put("20", "4");
		this.employeeType.put("8", "1");
		this.employeeType.put("12", "2");
		this.employeeType.put("12", "3");

	}

	@Override
	public Employee process(EmployeeApplication item) throws Exception {
		if(!this.hireMap.containsKey(item.getCandidateId())) {
			return null;
		}
		String employeeId = this.hireMap.get(item.getCandidateId());
		Employee employee = new Employee();
		employee.setFirstName(item.getFirstName());
		employee.setLastName(item.getLastName());
		employee.setAddress(item.getAddress());
		employee.setEmployee_id(employeeId);
		employee.setGovtId(item.getGovtId());
		employee.setMiddleInitial(item.getMiddleInitial());
		employee.setEmployeeTypeId(this.employeeType.get(employeeId));
		employee.setPostalCode(item.getPostalCode());
		employee.setStateAbbrev(item.getStateAbbrev());
		employee.setStartDate("2020-05-05");
		return employee;
	}

}
