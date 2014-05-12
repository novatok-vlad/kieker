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

package kieker.common.record.jvm;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.util.registry.IRegistry;

/**
 * @author Nils Christian Ehmke
 * 
 * @since 1.10
 */
public class ClassLoadingRecord extends AbstractMonitoringRecord {
	public static final int SIZE = TYPE_SIZE_LONG + (2 * TYPE_SIZE_STRING) + (3 * TYPE_SIZE_LONG);

	public static final Class<?>[] TYPES = {
		long.class,
		String.class,
		String.class,
		long.class,
		long.class,
		long.class,
	};

	private static final long serialVersionUID = 2989308154952301746L;

	private final long timestamp;
	private final String hostname;
	private final String vmName;
	private final long totalLoadedClassCount;
	private final long loadedClassCount;
	private final long unloadedClassCount;

	public ClassLoadingRecord(final long timestamp, final String hostname, final String vmName, final long totalLoadedClassCount, final int loadedClassCount,
			final long unloadedClassCount) {
		this.timestamp = timestamp;
		this.hostname = hostname;
		this.vmName = vmName;
		this.totalLoadedClassCount = totalLoadedClassCount;
		this.loadedClassCount = loadedClassCount;
		this.unloadedClassCount = unloadedClassCount;
	}

	public ClassLoadingRecord(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);

		this.timestamp = (Long) values[0];
		this.hostname = (String) values[1];
		this.vmName = (String) values[2];
		this.totalLoadedClassCount = (Long) values[3];
		this.loadedClassCount = (Long) values[4];
		this.unloadedClassCount = (Long) values[5];
	}

	public ClassLoadingRecord(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		this.timestamp = buffer.getLong();
		this.hostname = stringRegistry.get(buffer.getInt());
		this.vmName = stringRegistry.get(buffer.getInt());
		this.totalLoadedClassCount = buffer.getLong();
		this.loadedClassCount = buffer.getLong();
		this.unloadedClassCount = buffer.getLong();
	}

	@Override
	public Object[] toArray() {
		return new Object[] { this.getTimestamp(), this.getHostname(), this.getVmName(), this.getTotalLoadedClassCount(), this.getLoadedClassCount(),
			this.getUnloadedClassCount() };
	}

	@Override
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		buffer.putLong(this.getTimestamp());
		buffer.putInt(stringRegistry.get(this.getHostname()));
		buffer.putInt(stringRegistry.get(this.getVmName()));
		buffer.putLong(this.getTotalLoadedClassCount());
		buffer.putLong(this.getLoadedClassCount());
		buffer.putLong(this.getUnloadedClassCount());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.Factory} mechanism. Hence, this method is not implemented.
	 */
	@Override
	@Deprecated
	public final void initFromArray(final Object[] values) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.BinaryFactory} mechanism. Hence, this method is not implemented.
	 */
	@Override
	@Deprecated
	public final void initFromBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?>[] getValueTypes() {
		return TYPES; // NOPMD
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getSize() {
		return SIZE;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public String getHostname() {
		return this.hostname;
	}

	public String getVmName() {
		return this.vmName;
	}

	public long getTotalLoadedClassCount() {
		return this.totalLoadedClassCount;
	}

	public long getLoadedClassCount() {
		return this.loadedClassCount;
	}

	public long getUnloadedClassCount() {
		return this.unloadedClassCount;
	}

}