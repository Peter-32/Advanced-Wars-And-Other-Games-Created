/**
 * Created by Peter on 8/24/2016.
 */
public class WorkWithClasses {
    public static void main(String[] args) {

       // find out if new tables on reppres or Impala

        // always add where.. the partition dates +1 / -1 day
        // always order by dates a certain way, otherwise sort by certain KPI then by all group by columns asc

        // use the original dashboards for now.

        SQL sql = new SQL(MONTH, ALL_KPI, CAM_MP, BASIC_DIMS, SUBAIDPARTITION, STANDARD); // STANDARD or ENTERPRISE

        // write the SQL to a new report.  Includes API enabling and certain column formats based on names
        // always make every column hidden if empty except the first column and certain KPI like action, revenue, Action Cost, Total Cost

        ReportWriter reportWriter = new ReportWriter();
        Report report = reportWriter.createReport(TOP_LEVEL_FAST, sql);
        reportWriter.setReportDrills("Actions", 1038);










    }
}
