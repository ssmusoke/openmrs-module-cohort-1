/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.api.dao.search;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.cohort.CohortM;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@SuppressWarnings("unchecked")
public class CohortSearchHandler extends AbstractSearchHandler {
	
	public Collection<CohortM> findCohortsByPatientUuid(String patientUuid) {
		Criteria criteria = getCurrentSession().createCriteria(CohortM.class).createCriteria("cohortMembers", "cm")
		        .createCriteria("cm.patient", "cmp").add(Restrictions.eq("cmp.uuid", patientUuid));
		return criteria.list();
	}
	
}
