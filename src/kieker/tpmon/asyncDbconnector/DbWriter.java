/*
 * DbWriter.java
 *
 * Created on July 30, 2007, 9:21 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package kieker.tpmon.asyncDbconnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import kieker.tpmon.ExecutionData;
import kieker.tpmon.TpmonController;
import kieker.tpmon.annotations.TpmonInternal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author matthias
 */
public class DbWriter  implements Runnable, Worker{
    private static final Log log = LogFactory.getLog(DbWriter.class);
    
    private static final long pollingIntervallInMillisecs = 400L;
    
    private Connection conn;
    private BlockingQueue writeQueue;
    private PreparedStatement psInsertMonitoringData;
    private static boolean shutdown = false;
    private boolean finished = false;
    
    boolean statementChanged = true;
    String nextStatementText;
    
    public DbWriter(Connection initializedConnection, BlockingQueue writeQueue, String statementtext) {
        this.conn = initializedConnection;
        this.writeQueue = writeQueue;
        this.nextStatementText = statementtext;
        log.info("New Tpmon - DbWriter thread created");
    }
        
     @TpmonInternal()
    synchronized void changeStatement(String statementtext){
        nextStatementText = statementtext;
        statementChanged = true;
    }
    
    /**
     * May be called more often than required... but that doens't harm
     */
     @TpmonInternal()
    public void initShutdown() {
        DbWriter.shutdown = true;
    }           
    
      @TpmonInternal()
    public void run() {
        log.info("Dbwriter thread running");
        try {            
            while(!finished) { 
                Object data = writeQueue.poll(pollingIntervallInMillisecs, TimeUnit.MILLISECONDS); 
                if (data != null) {
                    consume(data); // throws SQLException
                } else {
                    // timeout ... 
                    if (shutdown && writeQueue.isEmpty()) {
                        finished = true;
                    }
                }
            }
            log.info("Dbwriter finished");
        } catch (Exception ex) {
            // e.g. Interrupted Exception or SQLException
            log.error("DB Writer will halt "+ex.getMessage());
            // TODO: This is a dirty hack!
            // What we need is a listener interface!
            log.error("Will disable monitoring!");
            TpmonController.getInstance().disableMonitoring();
        } finally{
            this.finished = true;
        }
    }
       
    /**
     * writes next item into database
     */
       @TpmonInternal()
    private void  consume(Object traceidObject) throws SQLException{
        //if (TpmonController.debug) System.out.println("DbWriter "+this+" Consuming "+traceidObject);
        try {
            if (statementChanged || psInsertMonitoringData == null) {                
                psInsertMonitoringData = conn.prepareStatement(nextStatementText);
                statementChanged = false;
            }
            
            ExecutionData execData = (ExecutionData) traceidObject;
            
            psInsertMonitoringData.setString(1, execData.componentName+"."+execData.opname);
            psInsertMonitoringData.setString(2, execData.sessionId);
            psInsertMonitoringData.setString(3, String.valueOf(execData.traceId));
            psInsertMonitoringData.setLong(4, execData.tin);
            psInsertMonitoringData.setLong(5, execData.tout);
            psInsertMonitoringData.setLong(6, execData.eoi);
            psInsertMonitoringData.setLong(7, execData.ess);
            psInsertMonitoringData.execute();
            
            
        } catch (SQLException ex) {
            log.error("Tpmon DbWriter Error during database statement preparation: ", ex);
            throw ex;
        }
    }
    
     @TpmonInternal()
    public boolean isFinished() {
        return finished;
    }
}
