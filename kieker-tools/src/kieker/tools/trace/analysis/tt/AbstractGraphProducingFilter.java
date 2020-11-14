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
package kieker.tools.trace.analysis.tt;

import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.model.repository.SystemModelRepository;
import kieker.tools.trace.analysis.filter.IGraphOutputtingFilter;
import kieker.tools.trace.analysis.filter.IGraphProducingFilter;
import kieker.tools.trace.analysis.filter.visualization.graph.AbstractGraph;
import kieker.tools.trace.analysis.filter.visualization.graph.AbstractGraphElement;
import kieker.tools.trace.analysis.filter.visualization.graph.IOriginRetentionPolicy;
import kieker.tools.trace.analysis.filter.visualization.graph.NoOriginRetentionPolicy;

/**
 * Abstract superclass for graph-producing filters.
 *
 * @author Holger Knoche
 * @author Reiner Jung -- teetime port
 *
 * @param <G> The graph type created by this filter
 *
 * @since 1.6
 */
public abstract class AbstractGraphProducingFilter<G extends AbstractGraph<?, ?, ?>>
		extends AbstractMessageTraceProcessingFilter implements IGraphProducingFilter<G> {

	private static final String INCOMPATIBLE_RETENTION_ERROR_TEMPLATE = "%s: The current retention policy %s is incompatible with the requested retention policy %s.";

	private final G graph;
	private IOriginRetentionPolicy originRetentionPolicy = NoOriginRetentionPolicy.createInstance();

	/**
	 * Creates a new graph-producing filter using the given configuration and the
	 * given graph.
	 *
	 * @param configuration  The configuration to use
	 * @param projectContext The project context to use.
	 * @param graph          The (usually empty) graph to produce / extend
	 */
	public AbstractGraphProducingFilter(final SystemModelRepository repository, final G graph) {
		super(repository);
		this.graph = graph;
	}

	/**
	 * Returns this filter's configuration name.
	 *
	 * @return See above
	 */
	public abstract String getConfigurationName();

	// TODO this implies that all content is send on termination
//	@Override
//	public void terminate(final boolean error) {
//		if (!error) {
//			this.deliver(this.getGraphOutputPortName(), this.getGraph());
//		}
//	}

	@Override
	public String getGraphOutputPortName() {
		return IGraphOutputtingFilter.OUTPUT_PORT_NAME_GRAPH;
	}

	/**
	 * Delivers the graph stored in this filter.
	 *
	 * @return The graph.
	 */
	protected G getGraph() {
		return this.graph;
	}

	protected IOriginRetentionPolicy getOriginRetentionPolicy() {
		return this.originRetentionPolicy;
	}

	@Override
	public void requestOriginRetentionPolicy(final IOriginRetentionPolicy policy)
			throws AnalysisConfigurationException {
		if (!this.originRetentionPolicy.isCompatibleWith(policy)) {
			throw new AnalysisConfigurationException(
					String.format(AbstractGraphProducingFilter.INCOMPATIBLE_RETENTION_ERROR_TEMPLATE, this,
							this.originRetentionPolicy, policy));
		}

		this.originRetentionPolicy = this.originRetentionPolicy.uniteWith(policy);
	}

	protected <T> void handleOrigin(final AbstractGraphElement<T> element, final T origin) {
		this.getOriginRetentionPolicy().handleOrigin(element, origin);
	}

}
