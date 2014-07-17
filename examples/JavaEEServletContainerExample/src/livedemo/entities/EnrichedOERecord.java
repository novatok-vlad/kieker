/***************************************************************************
 * Copyright 2013 Kicker Project (http://kicker-monitoring.net)
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

package livedemo.entities;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import kicker.common.record.IMonitoringRecord;
import kicker.common.record.controlflow.OperationExecutionRecord;
import kicker.common.util.registry.IRegistry;

/**
 * @author Bjoern Weissenfels
 * 
 * @since 1.9
 */
public class EnrichedOERecord extends OperationExecutionRecord implements IMonitoringRecord {
	public static final int SIZE = (2 * TYPE_SIZE_STRING) + (3 * TYPE_SIZE_LONG) + TYPE_SIZE_STRING + (2 * TYPE_SIZE_INT) + TYPE_SIZE_LONG + (2 * TYPE_SIZE_STRING);
	public static final Class<?>[] TYPES = {
		String.class, // operationSignature
		String.class, // sessionId
		long.class, // traceId
		long.class, // tin
		long.class, // tout
		String.class, // hostname
		int.class, // eoi
		int.class, // ess
		double.class, // responseTime
		String.class, // shortSignature
		String.class, // commaSeperatedValues
	};

	private static final long serialVersionUID = 4652653198700953697L;

	private final double responseTime; // in milliseconds, rounded to one decimal
	private final String shortSignature; // should be ...class.method(...)
	private final String commaSeperatedValues;

	public EnrichedOERecord(final String operationSignature, final String sessionId, final long traceId, final long tin, final long tout,
			final String hostname, final int eoi, final int ess, final double responseTime, final String shortSignature, final String commaSeperatedValues) {
		super(operationSignature, sessionId, traceId, tin, tout, hostname, eoi, ess);
		this.responseTime = responseTime;
		this.shortSignature = shortSignature;
		this.commaSeperatedValues = commaSeperatedValues;
	}

	public double getResponseTime() {
		return this.responseTime;
	}

	public String getShortSignature() {
		return this.shortSignature;
	}

	public String getCommaSeperatedValues() {
		return this.commaSeperatedValues;
	}

	@Override
	public Object[] toArray() {
		return new Object[] {
			this.getOperationSignature(),
			this.getSessionId(),
			this.getTraceId(),
			this.getTin(),
			this.getTout(),
			this.getHostname(),
			this.getEoi(),
			this.getEss(),
			this.getResponseTime(),
			this.getShortSignature(),
			this.getCommaSeperatedValues(), };
	}

	@Override
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		super.writeBytes(buffer, stringRegistry);
		buffer.putDouble(this.getResponseTime());
		buffer.putInt(stringRegistry.get(this.getShortSignature()));
		buffer.putInt(stringRegistry.get(this.getCommaSeperatedValues()));
	}

	@Override
	public Class<?>[] getValueTypes() {
		return TYPES; // NOPMD
	}

	@Override
	public int getSize() {
		return SIZE;
	}

}