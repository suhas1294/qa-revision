package com.example.reporter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TableCreator {

    private String fillUpHeadDataForSummarryTable() {
        String tableData = "<table style=\"font-size: 12px;width: 100%;border-spacing: 2px;border-color:grey\">\r\n" +
                "				<thead style=\"width: 100%;border-spacing: 2px;border-color:grey\">\r\n" +
                "					<tr>\r\n" +
                "						<th colspan=\"8\" style=\"font-size: 14px;border: 1px #6ea1cc !important;text-align: center; padding: 8px;background-color: #508abb;color: #fff;\">SCENARIOS</th>\r\n" +
                "					</tr>\r\n" +
                "					<tr>";
        for (int i = 0; i < getHeadValuesForSummarryTable().length; i++) {
            tableData = tableData + "<th style=\"font-size: 14px;border: 1px #6ea1cc !important;text-align: center; padding: 8px;background-color: #508abb;color: #fff;\">" + getHeadValuesForSummarryTable()[i] + "</th>";
        }
        tableData = tableData + "</tr></thead><tbody style=\"font-size: 12px;\">";
        return tableData;
    }

    private String[] getHeadValuesForSummarryTable() {
        String[] headValues = new String[5];
        headValues[0] = "Features";
        headValues[1] = "Total";
        headValues[2] = "Passed";
        headValues[3] = "Failed";
        headValues[4] = "Skipped";
        return headValues;
    }

    private String fillUpSuiteData(List<String[]> suiteDetails) {
        String tableData = new String();
        for (int i = 0; i < suiteDetails.size(); i++) {
            tableData = tableData + "<tr style=\"width: 100%;border-bottom:1px solid #efefef;border-top:1px solid #ececec;background-color:#f4fbff;\">";
            for (int j = 0; j < suiteDetails.get(i).length; j++) {
                tableData = tableData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + suiteDetails.get(i)[j] + "</td>";
            }
            tableData = tableData + "</tr>";
        }
        tableData = tableData + "</tbody>";
        return tableData;
    }

    private String fillUpFootData(List<String[]> projectDetails) {
        String footData = "<tfoot><tr style=\"width: 100%;border-spacing: 2px;background-color:#fcffc9 !important\">";
        for (int i = 0; i < projectDetails.get(0).length; i++) {
            footData = footData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + projectDetails.get(0)[i] + "</td>";
        }
        footData = footData + "</tr></tfoot></table>";
        return footData;
    }

    public String generateFeatureTable(List<String[]> suiteDetails, List<String[]> projectDetails) {
        return (fillUpHeadDataForSummarryTable() + fillUpSuiteData(suiteDetails) + fillUpFootData(projectDetails));
    }

    private String fillUpHeadDataForTable(String[] columnArray, String heading) {
        String tableData = "</blockquote><h4>" + heading + "</h4><blockquote><table style=\"font-size: 12px;width: 100%;border-spacing: 2px;border-color:grey\">\r\n" +
                "				<thead style=\"width: 100%;border-spacing: 2px;border-color:grey\">\r\n" +
                "					<tr>\r\n" +
                "						<th colspan=\"6\" style=\"font-size: 14px;border: 1px #6ea1cc !important;text-align: center; padding: 8px;background-color: #508abb;color: #fff;\">SCENARIOS</th>\r\n" +
                "					</tr>\r\n" +
                "					<tr>";
        for (int i = 0; i < columnArray.length; i++) {
            tableData = tableData + "<th style=\"font-size: 14px;border: 1px #6ea1cc !important;text-align: center; padding: 8px;background-color: #508abb;color: #fff;\">" + columnArray[i] + "</th>";
        }
        tableData = tableData + "</tr></thead><tbody style=\"font-size: 12px;\">";
        return tableData;
    }

    private String[] getHeadValuesForFailureTable() {
        String[] headValues = new String[4];
        headValues[0] = "Class";
        headValues[1] = "Method";
        headValues[2] = "Failure Information";
        headValues[3] = "Console Error(s) (if any)";
        return headValues;
    }

    private String fillUpFailureData(Map suiteFailureDetails) {
        String tableData = "";
        Iterator<Entry<String, String>> itr = suiteFailureDetails.entrySet().iterator();
        while (itr.hasNext()) {
            Entry<String, String> entry = itr.next();
            String key = entry.getKey();
            String className = key.split("\\|")[0];
            String methodName = key.split("\\|")[1];
            String failureMessage = "";
            String consoleError = "";
            String value = entry.getValue();
            String[] split = value.split("\\|");
            if (split.length > 1) {
                failureMessage = split[0];
                consoleError = split[1];
            } else {
                failureMessage = split[0];
                consoleError = "-";
            }
            tableData = tableData + "<tr style=\"width: 100%;border-bottom:1px solid #efefef;border-top:1px solid #ececec;background-color:#f4fbff;\">";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + className + "</td>";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + methodName + "</td>";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + failureMessage + "</td>";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + consoleError + "</td>";
            tableData = tableData + "</tr>";
        }
        tableData = tableData + "</tbody></table>";
        return tableData;
    }

    public String generateFailureTable(Map suiteFailureDetails) {
        String tableData = fillUpHeadDataForTable(getHeadValuesForFailureTable(), "FAILURE SUMMARY") + fillUpFailureData(suiteFailureDetails);
        return tableData;
    }

    private String[] getHeadValuesForSkippedTable() {
        String[] headValues = new String[2];
        headValues[0] = "Class";
        headValues[1] = "Method";
        return headValues;
    }

    private String fillUpSkippedData(Set<String> suiteSkippedDetails) {
        String tableData = "";
        Iterator<String> itr = suiteSkippedDetails.iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            String className = key.split("\\|")[0];
            String methodName = key.split("\\|")[1];
            tableData = tableData + "<tr style=\"width: 100%;border-bottom:1px solid #efefef;border-top:1px solid #ececec;background-color:#f4fbff;\">";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + className + "</td>";
            tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + methodName + "</td>";
            tableData = tableData + "</tr>";
        }
        tableData = tableData + "</tbody></table>";
        return tableData;
    }

	public String generateSkippedTable(Set<String> suiteSkippedDetails) {
		String tableData = fillUpHeadDataForTable(getHeadValuesForSkippedTable(), "SKIPPED SUMMARY") + fillUpSkippedData(suiteSkippedDetails);
		return tableData;
	}

	private String[] getHeadValuesForPerformanceTable() {
		String[] headValues = new String[6];
		headValues[0] = "Description";
		headValues[1] = "Steps";
		headValues[2] = "Benchmark Values";
		headValues[3] = "Start Time";
		headValues[4] = "End Time";
		headValues[5] = "Actual Duration (ms)";
		return headValues;
	}

	private String fillUpPerformanceData(Map<String, Map<String, String>> performanceDetails) {
		String tableData = "";
		Iterator<Entry<String, Map<String, String>>> itr = performanceDetails.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, Map<String, String>> entry = itr.next();
			String description = entry.getKey();
			Map<String, String> detailsOfScenario = entry.getValue();
			String startTime = detailsOfScenario.get("start");
			String endTime = detailsOfScenario.get("end");
			String duration = detailsOfScenario.get("duration");
			String steps = detailsOfScenario.get("steps");
			tableData = tableData + "<tr style=\"width: 100%;border-bottom:1px solid #efefef;border-top:1px solid #ececec;background-color:#f4fbff;\">";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px\">" + description + "</td>";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: left; padding: 8px; white-space: pre-wrap\">" + steps + "</td>";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + "-" + "</td>";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + startTime + "</td>";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + endTime + "</td>";
			tableData = tableData + "<td style=\"border-collapse:collapse;text-align: center; padding: 8px\">" + duration + "</td>";
			tableData = tableData + "</tr>";
		}
		tableData = tableData + "</tbody></table>";
		return tableData;
	}

	public String generatePerformanceTable(Map<String, Map<String, String>> performanceDetails) {
		String tableData = fillUpHeadDataForTable(getHeadValuesForPerformanceTable(), "PERFORMANCE DETAILS")
				+ fillUpPerformanceData(performanceDetails);
		return tableData;
	}
}