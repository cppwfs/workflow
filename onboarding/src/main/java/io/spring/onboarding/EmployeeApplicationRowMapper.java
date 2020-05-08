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


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmployeeApplicationRowMapper implements RowMapper {
	public EmployeeApplication mapRow(ResultSet rs, int rowNum) {
		EmployeeApplication employeeApplication = new EmployeeApplication();
		try {
			employeeApplication.setPostalCode(rs.getString("postal_code"));
			employeeApplication.setStateAbbrev(rs.getString("state_abbrev"));
			employeeApplication.setCandidateId(rs.getString("candidate_id"));
			employeeApplication.setGovtId(rs.getString("govt_id"));
			employeeApplication.setFirstName(rs.getString("first_name"));
			employeeApplication.setLastName(rs.getString("last_name"));
			employeeApplication.setMiddleInitial(rs.getString("middle_initial"));
			employeeApplication.setAddress(rs.getString("address"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Unable to map data to EmployeeApplication", e);
		}
		return employeeApplication;
	}
}
