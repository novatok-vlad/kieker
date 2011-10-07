/***************************************************************************
 * Copyright 2011 by
 *  + Christian-Albrechts-University of Kiel
 *    + Department of Computer Science
 *      + Software Engineering Group 
 *  and others.
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

package kieker.common.record;

/**
 * @author Andre van Hoorn
 */
public class DummyMonitoringRecord extends AbstractMonitoringRecord {

	private static final long serialVersionUID = 11767633L;

	/**
	 * Constructs a {@link DummyMonitoringRecord}.
	 */
	public DummyMonitoringRecord() {}

	@Override
	public void initFromArray(final Object[] values) {}

	@Override
	public Object[] toArray() {
		return new Object[] {};
	}

	@Override
	public Class<?>[] getValueTypes() {
		return new Class[] {};
	}
}
