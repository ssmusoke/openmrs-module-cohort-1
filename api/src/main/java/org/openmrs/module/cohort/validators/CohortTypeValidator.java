/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.validators;

import org.openmrs.module.cohort.CohortType;
import org.openmrs.module.cohort.api.CohortTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Qualifier("cohort.cohortTypeValidator")
public class CohortTypeValidator implements Validator {
	
	private final CohortTypeService cohortTypeService;
	
	@Autowired
	public CohortTypeValidator(CohortTypeService cohortTypeService) {
		this.cohortTypeService = cohortTypeService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CohortType.class);
	}
	
	@Override
	public void validate(Object command, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "required");
		
		CohortType currentType = (CohortType) command;
		CohortType type = cohortTypeService.getByName(currentType.getName());
		
		if (type != null) {
			errors.rejectValue("name", "A cohort type with the same name already exists");
		}
	}
}
