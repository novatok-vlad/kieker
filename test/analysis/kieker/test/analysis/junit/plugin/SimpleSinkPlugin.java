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

package kieker.test.analysis.junit.plugin;

import java.util.ArrayList;
import java.util.List;

import kieker.analysis.plugin.annotation.InputPort;
import kieker.analysis.plugin.annotation.Plugin;
import kieker.analysis.plugin.filter.AbstractFilterPlugin;
import kieker.common.configuration.Configuration;

/**
 * 
 * @author Nils Ehmke, Jan Waller
 */
@Plugin
public class SimpleSinkPlugin<T> extends AbstractFilterPlugin {

	public static final String INPUT_PORT_NAME = "input";
	private final List<T> list = new ArrayList<T>();

	public SimpleSinkPlugin(final Configuration configuration) {
		super(configuration);
	}

	@InputPort(name = SimpleSinkPlugin.INPUT_PORT_NAME)
	// TODO: we could run into trouble here because the port accepts events of type Object
	public void input(final T data) {
		this.list.add(data);
	}

	public void clear() {
		this.list.clear();
	}

	public List<T> getList() {
		return this.list;
	}

	@Override
	protected Configuration getDefaultConfiguration() {
		return new Configuration();
	}

	public Configuration getCurrentConfiguration() {
		return new Configuration();
	}
}
