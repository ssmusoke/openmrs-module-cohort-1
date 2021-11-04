/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.web.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;
import org.openmrs.module.cohort.api.CohortService;
import org.openmrs.module.cohort.web.resource.CohortMainRestController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
 * Allows searching for cohorts by patient fields i.e. UUID, patient-identifier
 */
@Slf4j
@Component
public class CohortPatientSearchHandler implements SearchHandler {
	
	@Autowired
	@Qualifier("cohort.cohortService")
	private CohortService cohortService;
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.api.SearchHandler#getSearchConfig()
	 */
	@Override
	public SearchConfig getSearchConfig() {
		return new SearchConfig("default", RestConstants.VERSION_1 + CohortMainRestController.COHORT_NAMESPACE + "/cohort",
		        Arrays.asList("1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*", "2.0.*", "2.1.*", "2.2.*", "2.3.*", "2.4.*",
		            "2.5.*"),
		        Collections.singleton(new SearchQuery.Builder("Allows you to find cohorts by patient uuid")
		                .withRequiredParameters("patient.uuid").build()));
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.api.SearchHandler#search(org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public PageableResult search(RequestContext context) throws ResponseException {
		String patientUuid = context.getParameter("patient.uuid");
		if (!patientUuid.isEmpty()) {
			return new NeedsPaging<>(new ArrayList<>(cohortService.findCohortsByPatientUuid(patientUuid)), context);
		}
		return new NeedsPaging<>(new ArrayList<>(), context);
	}
}
