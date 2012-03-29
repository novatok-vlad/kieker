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

package kieker.common.namedRecordPipe;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Andre van Hoorn, Jan Waller
 */
public enum Broker { // Singleton pattern (Effective Java #3)
	INSTANCE;

	/**
	 * Access synchronized through synchronized method {@link #acquirePipe(String)} !
	 */
	private final transient ConcurrentHashMap<String, Pipe> pipeMap = new ConcurrentHashMap<String, Pipe>();

	/**
	 * Returns a connection with name @a pipeName. If a connection with this
	 * name does not exist prior to the call, it is created.
	 */
	public Pipe acquirePipe(final String pipeName) throws IllegalArgumentException {
		Pipe conn;
		synchronized (this) {
			if ((pipeName == null) || (pipeName.length() == 0)) {
				// Broker.LOG.error(errorMsg); no need to log if thrown
				throw new IllegalArgumentException("pipeName must not be null or empty!  (Found: " + pipeName + ")");
			}
			final Pipe newPipe = new Pipe(pipeName);
			conn = this.pipeMap.putIfAbsent(pipeName, newPipe);
			if (conn == null) {
				return newPipe;
			} else {
				return conn;
			}
		}
	}
}
