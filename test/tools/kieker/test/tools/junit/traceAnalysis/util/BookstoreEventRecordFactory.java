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

package kieker.test.tools.junit.traceAnalysis.util;

import java.util.ArrayList;
import java.util.List;

import kieker.common.record.flow.AfterOperationEvent;
import kieker.common.record.flow.BeforeOperationEvent;
import kieker.common.record.flow.CallOperationEvent;
import kieker.common.record.flow.TraceEvent;
import kieker.monitoring.core.controller.IMonitoringController;
import kieker.monitoring.core.controller.MonitoringController;

/**
 * 
 * @author Andre van Hoorn, Holger Knoche
 * 
 */
public class BookstoreEventRecordFactory {
	// private static final Log LOG = LogFactory.getLog(BookstoreEventRecordFactory.class);

	private static final String FQ_OP_BOOKSTORE_SEARCH_BOOK = "Bookstore.searchBook";
	private static final String FQ_OP_CATALOG_GET_BOOK = "Catalog.getBook";
	private static final String FQ_OP_CRM_GET_ORDERS = "CRM.getOrders";

	/**
	 * Returns "well-known" Bookstore trace as a list of {@link BeforeOperationEvent} and {@link AfterOperationEvent} events, ordered by its
	 * {@link TraceEvent#getOrderIndex()}es.
	 * 
	 * @param firstTimestamp
	 *            timestamp of the earliest event, incremented by 1 for each subsequent event
	 * @param traceId
	 * @return
	 */
	public static List<TraceEvent> validSyncTraceBeforeAfterEvents(final long firstTimestamp, final long traceId) {
		long curTime = firstTimestamp;

		int curOrderIndex = 0;

		final BeforeOperationEvent entry0_0__bookstore_searchBook; // NOCS
		final BeforeOperationEvent entry1_1__catalog_getBook; // NOCS
		final AfterOperationEvent exit1_1__catalog_getBook; // NOCS
		final BeforeOperationEvent entry2_1__crm_getOrders; // NOCS
		final BeforeOperationEvent entry3_2__catalog_getBook; // NOCS
		final AfterOperationEvent exit3_2__catalog_getBook; // NOCS
		final AfterOperationEvent exit2_1__crm_getOrders; // NOCS
		final AfterOperationEvent exit0_0__bookstore_searchBook; // NOCS

		entry0_0__bookstore_searchBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);
		entry1_1__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit1_1__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		entry2_1__crm_getOrders = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		entry3_2__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit3_2__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit2_1__crm_getOrders = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		exit0_0__bookstore_searchBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);

		final List<TraceEvent> retList = new ArrayList<TraceEvent>();
		retList.add(entry0_0__bookstore_searchBook);
		retList.add(entry1_1__catalog_getBook);
		retList.add(exit1_1__catalog_getBook);
		retList.add(entry2_1__crm_getOrders);
		retList.add(entry3_2__catalog_getBook);
		retList.add(exit3_2__catalog_getBook);
		retList.add(exit2_1__crm_getOrders);
		retList.add(exit0_0__bookstore_searchBook);

		return retList;
	}

	/**
	 * Returns "well-known" Bookstore trace as a list of {@link CallOperationEvent}, {@link BeforeOperationEvent} and {@link AfterOperationEvent} events, ordered by
	 * its {@link TraceEvent#getOrderIndex()}es.
	 * 
	 * @param firstTimestamp
	 *            timestamp of the earliest event, incremented by 1 for each subsequent event
	 * @param traceId
	 * @return
	 */
	public static List<TraceEvent> validSyncTraceAdditionalCallEvents(final long firstTimestamp, final long traceId) {
		long curTime = firstTimestamp;
		int curOrderIndex = 0;

		final BeforeOperationEvent entry0_0__bookstore_searchBook; // NOCS
		final CallOperationEvent call1_1__catalog_getBook; // NOCS
		final BeforeOperationEvent entry1_1__catalog_getBook; // NOCS
		final AfterOperationEvent exit1_1__catalog_getBook; // NOCS
		final CallOperationEvent call2_1__crm_getOrders; // NOCS
		final BeforeOperationEvent entry2_1__crm_getOrders; // NOCS
		final CallOperationEvent call3_2__catalog_getBook; // NOCS
		final BeforeOperationEvent entry3_2__catalog_getBook; // NOCS
		final AfterOperationEvent exit3_2__catalog_getBook; // NOCS
		final AfterOperationEvent exit2_1__crm_getOrders; // NOCS
		final AfterOperationEvent exit0_0__bookstore_searchBook; // NOCS

		entry0_0__bookstore_searchBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);
		call1_1__catalog_getBook = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		entry1_1__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit1_1__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		call2_1__crm_getOrders = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		entry2_1__crm_getOrders = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		call3_2__catalog_getBook = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		entry3_2__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit3_2__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit2_1__crm_getOrders = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		exit0_0__bookstore_searchBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);

		final List<TraceEvent> retList = new ArrayList<TraceEvent>();
		retList.add(entry0_0__bookstore_searchBook);
		retList.add(call1_1__catalog_getBook);
		retList.add(entry1_1__catalog_getBook);
		retList.add(exit1_1__catalog_getBook);
		retList.add(call2_1__crm_getOrders);
		retList.add(entry2_1__crm_getOrders);
		retList.add(call3_2__catalog_getBook);
		retList.add(entry3_2__catalog_getBook);
		retList.add(exit3_2__catalog_getBook);
		retList.add(exit2_1__crm_getOrders);
		retList.add(exit0_0__bookstore_searchBook);

		return retList;
	}

	/**
	 * Returns "well-known" Bookstore trace as a list of {@link CallOperationEvent}, {@link BeforeOperationEvent} and {@link AfterOperationEvent} events, ordered by
	 * its {@link TraceEvent#getOrderIndex()}es. In this trace, <i>CRM.getOrders</i> is assumed not to be instrumented.
	 * 
	 * @param firstTimestamp
	 *            timestamp of the earliest event, incremented by 1 for each subsequent event
	 * @param traceId
	 * @return
	 */
	public static List<TraceEvent> validSyncTraceAdditionalCallEventsGap(final long firstTimestamp, final long traceId) {
		long curTime = firstTimestamp;
		int curOrderIndex = 0;

		final BeforeOperationEvent entry0_0__bookstore_searchBook; // NOCS
		final CallOperationEvent call1_1__catalog_getBook; // NOCS
		final BeforeOperationEvent entry1_1__catalog_getBook; // NOCS
		final AfterOperationEvent exit1_1__catalog_getBook; // NOCS
		final CallOperationEvent call2_1__crm_getOrders; // NOCS
		// assumed to be uninstrumented: final BeforeOperationEvent entry2_1__crm_getOrders; // NOCS
		final CallOperationEvent call3_2__catalog_getBook; // NOCS
		final BeforeOperationEvent entry3_2__catalog_getBook; // NOCS
		final AfterOperationEvent exit3_2__catalog_getBook; // NOCS
		// assumed to be uninstrumented: final AfterOperationEvent exit2_1__crm_getOrders; // NOCS
		final AfterOperationEvent exit0_0__bookstore_searchBook; // NOCS

		entry0_0__bookstore_searchBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);
		call1_1__catalog_getBook = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		entry1_1__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit1_1__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		call2_1__crm_getOrders = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		// assumed to be uninstrumented:
		// entry2_1__crm_getOrders = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++, EventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		call3_2__catalog_getBook = new CallOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CRM_GET_ORDERS,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		entry3_2__catalog_getBook = new BeforeOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		exit3_2__catalog_getBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_CATALOG_GET_BOOK);
		// assumed to be uninstrumented:
		// exit2_1__crm_getOrders = new AfterOperationEvent(curTime++, traceId, curOrderIndex++, EventRecordFactory.FQ_OP_CRM_GET_ORDERS);
		exit0_0__bookstore_searchBook = new AfterOperationEvent(curTime++, traceId, curOrderIndex++,
				BookstoreEventRecordFactory.FQ_OP_BOOKSTORE_SEARCH_BOOK);

		final List<TraceEvent> retList = new ArrayList<TraceEvent>();
		retList.add(entry0_0__bookstore_searchBook);
		retList.add(call1_1__catalog_getBook);
		retList.add(entry1_1__catalog_getBook);
		retList.add(exit1_1__catalog_getBook);
		retList.add(call2_1__crm_getOrders);
		retList.add(call3_2__catalog_getBook);
		retList.add(entry3_2__catalog_getBook);
		retList.add(exit3_2__catalog_getBook);
		retList.add(exit0_0__bookstore_searchBook);

		return retList;
	}

	public static void main(final String[] args) {
		final IMonitoringController ctrl = MonitoringController.getInstance();

		long firstTimestamp = 7676876; // NOCS (MagicNumberCheck)
		final long firstTimestampDelta = 1000; // NOCS (MagicNumberCheck)
		long traceId = 688434; // NOCS (MagicNumberCheck)

		final List<TraceEvent> allRecords = new ArrayList<TraceEvent>();

		final List<TraceEvent> validSyncTraceBeforeAfterEvents = BookstoreEventRecordFactory.validSyncTraceAdditionalCallEventsGap(firstTimestamp, traceId++);
		allRecords.addAll(validSyncTraceBeforeAfterEvents);
		firstTimestamp += firstTimestampDelta;
		final List<TraceEvent> validSyncTraceAdditionalCallEvents = BookstoreEventRecordFactory.validSyncTraceAdditionalCallEvents(firstTimestamp, traceId++);
		allRecords.addAll(validSyncTraceAdditionalCallEvents);
		firstTimestamp += firstTimestampDelta;
		final List<TraceEvent> validSyncTraceAdditionalCallEventsGap = BookstoreEventRecordFactory.validSyncTraceAdditionalCallEventsGap(firstTimestamp, traceId++);
		allRecords.addAll(validSyncTraceAdditionalCallEventsGap);

		for (final TraceEvent r : allRecords) {
			ctrl.newMonitoringRecord(r);
		}

		ctrl.terminateMonitoring();
	}
}
