/***************************************************************************
 * Copyright 2014 Kieker Project (http://kieker-monitoring.net)
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

package kieker.tools.opad.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import kieker.analysis.IProjectContext;
import kieker.analysis.plugin.annotation.InputPort;
import kieker.analysis.plugin.annotation.OutputPort;
import kieker.analysis.plugin.annotation.Plugin;
import kieker.analysis.plugin.filter.AbstractFilterPlugin;
import kieker.common.configuration.Configuration;
import kieker.tools.opad.record.ForecastMeasurementPair;
import kieker.tools.opad.record.IForecastMeasurementPair;
import kieker.tools.opad.record.NamedDoubleTimeSeriesPoint;

/**
 * This Filter brings a Measurement Point and a corresponding Forecasting value together.
 * 
 * @author Tom Frotscher
 * @since 1.9
 * 
 */
@Plugin(name = "VariateUniteForecastingMeasurementPair Filter", outputPorts = {
	@OutputPort(eventTypes = { IForecastMeasurementPair.class }, name = UniteMeasurementPairFilter.OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT) })
public class UniteMeasurementPairFilter extends AbstractFilterPlugin {

	/**
	 * Name of the input port receiving the measurements.
	 */
	public static final String INPUT_PORT_NAME_TSPOINT = "tspoint";

	/**
	 * Name of the input port receiving the forecasts.
	 */
	public static final String INPUT_PORT_NAME_FORECAST = "forecast";

	/**
	 * Name of the output port delivering the corresponding measurement-forecast-pairs.
	 */
	public static final String OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT = "forecastedcurrent";

	/**
	 * Stores incoming measurements and forecasts until a corresponding forecast or measurement is found.
	 */
	private final ConcurrentHashMap<String, NamedDoubleTimeSeriesPoint> tsPointMap;

	private final List<String> firstIncomingTSPointList;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param configuration
	 *            The configuration for this component.
	 * @param projectContext
	 *            The projectContext for this component.
	 */
	public UniteMeasurementPairFilter(final Configuration configuration, final IProjectContext projectContext) {
		super(configuration, projectContext);

		this.tsPointMap = new ConcurrentHashMap<String, NamedDoubleTimeSeriesPoint>();
		this.firstIncomingTSPointList = Collections.synchronizedList(new ArrayList<String>());
	}

	@Override
	public Configuration getCurrentConfiguration() {
		return new Configuration();
	}

	/**
	 * Method that represents the input port for incoming measurements.
	 * 
	 * @param input
	 *            The incoming measurement.
	 */
	@InputPort(eventTypes = { NamedDoubleTimeSeriesPoint.class }, name = UniteMeasurementPairFilter.INPUT_PORT_NAME_TSPOINT)
	public void inputTSPoint(final NamedDoubleTimeSeriesPoint input) {
		if (!this.checkCorrespondingForecast(input)) {
			// no matching point found --> add it to the waiting map
			this.addTsPoint(input);
		}
	}

	/**
	 * Method that represents the input port for incoming forecast.
	 * 
	 * @param input
	 *            The incoming forecast.
	 */
	@InputPort(eventTypes = { IForecastMeasurementPair.class }, name = UniteMeasurementPairFilter.INPUT_PORT_NAME_FORECAST)
	public void inputForecastValue(final IForecastMeasurementPair input) {
		final NamedDoubleTimeSeriesPoint pointFromForecast = new NamedDoubleTimeSeriesPoint(input.getTime(), input.getForecasted(), input.getName());
		if (!this.checkCorrespondingMeasurement(pointFromForecast)) {
			// no matching point found --> add it to the waiting map
			this.addTsPoint(pointFromForecast);
		}

	}

	/**
	 * This method adds a TimeSeriesPoint to this class's tsPointMap.
	 * 
	 * @param p
	 *            TimeSeriesPoint to add.
	 */
	private void addTsPoint(final NamedDoubleTimeSeriesPoint p) {
		this.tsPointMap.put(p.getTime() + "." + p.getName(), p);
	}

	/**
	 * This method checks the tsPointMap for a corresponding forecast to its measurement. If this check is successfully
	 * the measurement-forecast-pair is delivered.
	 * 
	 * @param p
	 *            Measurement
	 * @return
	 *         True, if corresponding forecast found
	 *         False, else
	 */
	private boolean checkCorrespondingForecast(final NamedDoubleTimeSeriesPoint p) {
		// The first measurement of each application has no corresponding forecast --> submit a dummy (the measurement itself as forecast replacement)
		if (!this.firstIncomingTSPointList.contains(p.getName())) {
			this.firstIncomingTSPointList.add(p.getName());
			final ForecastMeasurementPair fmp = new ForecastMeasurementPair(
					p.getName(),
					p.getValue(),
					p.getValue(),
					p.getTime());
			super.deliver(OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT, fmp);
			return true;
		} else {
			final String key = p.getTime() + "." + p.getName();
			if (this.tsPointMap.containsKey(key)) {
				final ForecastMeasurementPair fmp = new ForecastMeasurementPair(
						p.getName(),
						this.tsPointMap.get(key).getValue(),
						p.getValue(),
						p.getTime());
				super.deliver(OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT, fmp);
				this.tsPointMap.remove(key);
				return true;
			}
			return false;
		}
	}

	/**
	 * This method checks the tsPointMap for a corresponding measurement to its forecast. If this check is successfully
	 * the measurement-forecast-pair is delivered.
	 * 
	 * @param p
	 *            forecast
	 * @return
	 *         True, if corresponding measurement found
	 *         False, else
	 */
	private boolean checkCorrespondingMeasurement(final NamedDoubleTimeSeriesPoint p) {
		final String key = p.getTime() + "." + p.getName();
		if (this.tsPointMap.containsKey(key)) {
			final NamedDoubleTimeSeriesPoint m = this.tsPointMap.get(key);
			final ForecastMeasurementPair fmp = new ForecastMeasurementPair(
					m.getName(),
					p.getValue(),
					m.getValue(),
					m.getTime());
			super.deliver(OUTPUT_PORT_NAME_FORECASTED_AND_CURRENT, fmp);
			this.tsPointMap.remove(key);
			return true;
		}
		return false;
	}

}
