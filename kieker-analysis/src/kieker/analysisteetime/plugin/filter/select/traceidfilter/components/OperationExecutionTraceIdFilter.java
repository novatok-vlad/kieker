/***************************************************************************
 * Copyright 2020 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.analysisteetime.plugin.filter.select.traceidfilter.components;

import kieker.common.record.controlflow.OperationExecutionRecord;

/**
 * Concrete Implementation of {@link AbstractTraceIdFilter}. Allows to filter {@link OperationExecutionRecord} objects based on their given trace ids.
 *
 * @author Andre van Hoorn, Jan Waller, Lars Bluemke
 *
 * @since 1.2
 */
public class OperationExecutionTraceIdFilter extends AbstractTraceIdFilter<OperationExecutionRecord> {

	public OperationExecutionTraceIdFilter(final boolean acceptAllTraces, final Long[] selectedTraceIds) {
		super(acceptAllTraces, selectedTraceIds);
	}

	@Override
	protected long getRecordsTraceId(final OperationExecutionRecord record) {
		return record.getTraceId();
	}

}