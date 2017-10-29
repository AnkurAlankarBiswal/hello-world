package nagen.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class CallYahooUrlExample {

	public static void main(String[] args) throws Exception {
		try{
			URIBuilder uribuilder = new URIBuilder("https://in.finance.yahoo.com/quote/SBIN.NS/financials?");	
			uribuilder.addParameter("p", "SBIN.NS");
			
			RestClient client = new RestClient();
			Resource resource = client.resource(uribuilder.build());
			
			ClientResponse response = resource.contentType("application/json").accept(new String[]{"application/json"}).get();
			String output = response.getEntity(String.class);
			
			String firstString = "\"financialsChart\":{\"yearly\"";
			String secondString = "},\"financialCurrency\"";
			
			String financialsChartData = StringUtils.substringBetween(output,firstString, secondString);
			
			financialsChartData = "{"+firstString+financialsChartData+"}"+"}";
			
			System.out.println("FinancialChart Data::"+financialsChartData);
			
			JSONObject jsonObj = new JSONObject(financialsChartData);
			JSONObject financialJsonObj = null;
			JSONArray yearlyJsonArray = null;
		    JSONArray quarterlyJsonArray=null;
		    
			if(null!=jsonObj){
				financialJsonObj = jsonObj.getJSONObject("financialsChart");
			}
			
			if(null!=financialJsonObj){
			    yearlyJsonArray = financialJsonObj.getJSONArray("yearly");
			    quarterlyJsonArray = financialJsonObj.getJSONArray("quarterly");
			}
		    
		    FinancialsChartData financialsChartDataObj = new FinancialsChartData();
		    List<YearlyData> yearlyDataList = null;
		    List<QuarterlyData> quarterDataList = null;
		    if(null!=yearlyJsonArray){
		    	yearlyDataList = new ArrayList<>();
			    for(int yearlyCount=0;yearlyCount<yearlyJsonArray.length();yearlyCount++){
			    	JSONObject yearlyJsonObject = yearlyJsonArray.getJSONObject(yearlyCount);
			    	YearlyData yearlyData = new YearlyData();
			    	
			    	yearlyData.setDate(yearlyJsonObject.get("date").toString());
			    	
			    	JSONObject yearlyEarningJsonObject =  yearlyJsonObject.getJSONObject("earnings");
			    	EarningData yearlyEarningObj = new EarningData();
			    	yearlyEarningObj.setFormat(yearlyEarningJsonObject.getString("fmt"));
			    	yearlyEarningObj.setLongFormat(yearlyEarningJsonObject.getString("longFmt"));
			    	yearlyEarningObj.setRaw(yearlyEarningJsonObject.getString("raw"));
			    	
			    	yearlyData.setEarning(yearlyEarningObj);
			    	
			    	JSONObject yearlyRevenueJsonObject =  yearlyJsonObject.getJSONObject("revenue");
			    	RevenueData yearlyRevenueObj = new RevenueData();
			    	yearlyRevenueObj.setFormat(yearlyRevenueJsonObject.getString("fmt"));
			    	yearlyRevenueObj.setLongFormat(yearlyRevenueJsonObject.getString("longFmt"));
			    	yearlyRevenueObj.setRaw(yearlyRevenueJsonObject.getString("raw"));
			    	
			    	yearlyData.setRevenue(yearlyRevenueObj);
			    	
			    	yearlyDataList.add(yearlyData);
			    }
		    }
		    if(null!=quarterlyJsonArray){
		    	quarterDataList = new ArrayList<>();
			    for(int quaterlyCount=0;quaterlyCount<quarterlyJsonArray.length();quaterlyCount++){
			    	JSONObject quarterlyJsonObject = quarterlyJsonArray.getJSONObject(quaterlyCount);
			    	QuarterlyData quarterlyData = new QuarterlyData();
			    	
			    	quarterlyData.setDate(quarterlyJsonObject.get("date").toString());
			    	
			    	JSONObject quarterlyEarningJsonObject =  quarterlyJsonObject.getJSONObject("earnings");
			    	EarningData quarterlyEarningObj = new EarningData();
			    	quarterlyEarningObj.setFormat(quarterlyEarningJsonObject.getString("fmt"));
			    	quarterlyEarningObj.setLongFormat(quarterlyEarningJsonObject.getString("longFmt"));
			    	quarterlyEarningObj.setRaw(quarterlyEarningJsonObject.getString("raw"));
			    	
			    	quarterlyData.setEarning(quarterlyEarningObj);
			    	
			    	JSONObject quarterlyRevenueJsonObject =  quarterlyJsonObject.getJSONObject("revenue");
			    	RevenueData quarterlyRevenueObj = new RevenueData();
			    	quarterlyRevenueObj.setFormat(quarterlyRevenueJsonObject.getString("fmt"));
			    	quarterlyRevenueObj.setLongFormat(quarterlyRevenueJsonObject.getString("longFmt"));
			    	quarterlyRevenueObj.setRaw(quarterlyRevenueJsonObject.getString("raw"));
			    	
			    	quarterlyData.setRevenue(quarterlyRevenueObj);
			    	
			    	quarterDataList.add(quarterlyData);
			    }
		    }
		    
		    financialsChartDataObj.setQuarterly(quarterDataList);
		    financialsChartDataObj.setYearly(yearlyDataList);
		    
		    System.out.println("Response in POJO::"+financialsChartDataObj);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

class FinancialsChartData {
	private List<YearlyData> yearly;
	private List<QuarterlyData> quarterly;
	
	
	public List<YearlyData> getYearly() {
		return yearly;
	}

	public void setYearly(List<YearlyData> yearly) {
		this.yearly = yearly;
	}

	public List<QuarterlyData> getQuarterly() {
		return quarterly;
	}

	public void setQuarterly(List<QuarterlyData> quarterly) {
		this.quarterly = quarterly;
	}

	@Override
	public String toString() {
		return "FinancialsChartData [yearly=" + yearly + ", quarterly=" + quarterly + "]";
	}

}

class YearlyData{
	private String date;
	private RevenueData revenue;
	private EarningData earning;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public RevenueData getRevenue() {
		return revenue;
	}
	public void setRevenue(RevenueData revenue) {
		this.revenue = revenue;
	}
	public EarningData getEarning() {
		return earning;
	}
	public void setEarning(EarningData earning) {
		this.earning = earning;
	}
	@Override
	public String toString() {
		return "YearlyData [date=" + date + ", revenue=" + revenue + ", earning=" + earning + "]";
	}
}

class QuarterlyData{
	private String date;
	private RevenueData revenue;
	private EarningData earning;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public RevenueData getRevenue() {
		return revenue;
	}
	public void setRevenue(RevenueData revenue) {
		this.revenue = revenue;
	}
	public EarningData getEarning() {
		return earning;
	}
	public void setEarning(EarningData earning) {
		this.earning = earning;
	}
	@Override
	public String toString() {
		return "QuarterlyData [date=" + date + ", revenue=" + revenue + ", earning=" + earning + "]";
	}
}

class RevenueData{
	private String raw;
	private String format;
	private String longFormat;
	public String getRaw() {
		return raw;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getLongFormat() {
		return longFormat;
	}
	public void setLongFormat(String longFormat) {
		this.longFormat = longFormat;
	}
	@Override
	public String toString() {
		return "RevenueData [raw=" + raw + ", format=" + format + ", longFormat=" + longFormat + "]";
	}
}
class EarningData{
	private String raw;
	private String format;
	private String longFormat;
	
	public String getRaw() {
		return raw;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getLongFormat() {
		return longFormat;
	}
	public void setLongFormat(String longFormat) {
		this.longFormat = longFormat;
	}
	@Override
	public String toString() {
		return "EarningData [raw=" + raw + ", format=" + format + ", longFormat=" + longFormat + "]";
	}
}