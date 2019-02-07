/***************************************************************************
 * Copyright 2019 Kieker Project (http://kieker-monitoring.net)
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
package kieker.analysis.source;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the network access handler.
 *
 * @author Reiner Jung
 * @since 1.15
 */
public class TestNetworkAccessHandler {

	private static final String FIRST_ADDRESS = "134.10.5.231";
	private static final String SECOND_ADDRESS = "192.168.48.4";
	private static final String THIRD_ADDRESS = "192.168.48.5";

	private static final String FIRST_NETWORK = "134.10.5.0/24";
	private static final String SECOND_NETWORK = "134.10.4.0/23";

	@Test
	public void testPlainIP() throws UnknownHostException {
		final Collection<String> networkPatterns = new ArrayList<String>();
		networkPatterns.add(FIRST_ADDRESS);
		networkPatterns.add(SECOND_ADDRESS);
		final NetworkAccessHandler handler = new NetworkAccessHandler(networkPatterns);
		Assert.assertEquals("Address should be accepted.", true, handler.acceptRemoteIpAddress(FIRST_ADDRESS));
		Assert.assertEquals("Address should be accepted.", true, handler.acceptRemoteIpAddress(SECOND_ADDRESS));
		Assert.assertEquals("Address should be rejected.", false, handler.acceptRemoteIpAddress(THIRD_ADDRESS));
	}

	@Test
	public void testAlignedNetmask() throws UnknownHostException {
		final Collection<String> networkPatterns = new ArrayList<String>();
		networkPatterns.add(FIRST_NETWORK);
		final NetworkAccessHandler handler = new NetworkAccessHandler(networkPatterns);
		Assert.assertEquals("Address should be accepted.", true, handler.acceptRemoteIpAddress(FIRST_ADDRESS));
		Assert.assertEquals("Address should be accepted.", false, handler.acceptRemoteIpAddress(SECOND_ADDRESS));
		Assert.assertEquals("Address should be rejected.", false, handler.acceptRemoteIpAddress(THIRD_ADDRESS));
	}

	@Test
	public void testArbitraryNetmask() throws UnknownHostException {
		final Collection<String> networkPatterns = new ArrayList<String>();
		networkPatterns.add(SECOND_NETWORK);
		final NetworkAccessHandler handler = new NetworkAccessHandler(networkPatterns);
		Assert.assertEquals("Address should be accepted.", true, handler.acceptRemoteIpAddress(FIRST_ADDRESS));
		Assert.assertEquals("Address should be accepted.", false, handler.acceptRemoteIpAddress(SECOND_ADDRESS));
		Assert.assertEquals("Address should be rejected.", false, handler.acceptRemoteIpAddress(THIRD_ADDRESS));
	}

}
