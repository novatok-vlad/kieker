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

package kicker.tools.tslib;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is a thread-safe buffer for time series points, which has or has not a fixed capacity.
 * 
 * @author Tom Frotscher, Nils Christian Ehmke
 * 
 * @since 1.9
 * 
 * @param <T>
 *            The type of the buffer.
 */
public class TimeSeriesPointsBuffer<T extends ITimeSeriesPoint<?>> extends ConcurrentLinkedQueue<T> implements ITimeSeriesPointsBuffer<T> {

	private static final long serialVersionUID = -7988633509408488397L;

	private final int capacity;
	private final boolean unbounded;

	/**
	 * Creates a new instance with infinite capacity.
	 */
	public TimeSeriesPointsBuffer() {
		this(-1);
	}

	/**
	 * Creates a new instance with the given capacity. A capacity of less or equal zero means that the capacity is infinite.
	 * 
	 * @param capacity
	 *            The capacity of the buffer
	 */
	public TimeSeriesPointsBuffer(final int capacity) {
		this.capacity = capacity;
		this.unbounded = capacity <= 0;
	}

	@Override
	public boolean add(final T o) {
		if (this.unbounded) {
			return super.add(o);
		} else {
			return this.addBounded(o);
		}
	}

	private boolean addBounded(final T o) {
		synchronized (this) {
			if (this.size() == this.capacity) {
				super.poll();
			}
			return super.add(o);
		}
	}

	@Override
	public T remove() {
		return super.poll();
	}

	@Override
	public int getSize() {
		return this.size();
	}

}