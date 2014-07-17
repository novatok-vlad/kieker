/***************************************************************************
 * Copyright 2014 Kicker Project (http://kicker-monitoring.net)
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

package kicker.analysis.analysisComponent;

import kicker.common.configuration.Configuration;

/**
 * This is the interface for an analysis component in Kicker.
 * 
 * @author Nils Christian Ehmke
 * 
 * @since 1.7
 */
public interface IAnalysisComponent {

	/**
	 * This method should deliver a {@code Configuration} object containing the current configuration of this instance. In other words: The constructor should be
	 * able to use the given object to initialize a new instance of this class with the same intern properties.
	 * 
	 * @return A completely filled configuration object.
	 * 
	 * @since 1.7
	 */
	public abstract Configuration getCurrentConfiguration();

	/**
	 * This method delivers the current name of this component instance. The name does not have to be unique.
	 * 
	 * @return The current name of the component instance.
	 * 
	 * @since 1.7
	 */
	public abstract String getName();

}