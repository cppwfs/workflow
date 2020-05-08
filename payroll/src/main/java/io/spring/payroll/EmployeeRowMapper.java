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

package io.spring.payroll;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmployeeRowMapper implements RowMapper {
	public Employee mapRow(ResultSet rs, int rowNum) {
		Employee employee = new Employee();
		try {
			employee.setPostalCode(rs.getString("postal_code"));
			employee.setStateAbbrev(rs.getString("state_abbrev"));
			employee.setEmployeeId(rs.getString("employee_id"));
			employee.setGovtId(rs.getString("govt_id"));
			employee.setFirstName(rs.getString("first_name"));
			employee.setLastName(rs.getString("last_name"));
			employee.setMiddleInitial(rs.getString("middle_initial"));
			employee.setAddress(rs.getString("address"));
			employee.setEmployeeTypeId(rs.getString("employee_type_id"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Unable to map data to Employee", e);
		}
		return employee;
	}
}
