package dbconnect;

import dbconnect.sql.SqlController;
import dbconnect.xml.XmlController;


public class DBManager {
	
	public static final String ENVIRONMENT_STRATEGY = "XMLERS_STRATEGY";
	public static final String XML_STRATEGY = "XML";
	public static final String SQL_STRATEGY = "SQL";
	public static final String DEBUG_STRATEGY = "DEBUG";
	
	/**
	 * The actual instance of a DB Controller that will have it's functionality passed through
	 */
	private static IDBController strategy;
	
	static {
		String strat = System.getenv(ENVIRONMENT_STRATEGY);
		if(null == strat)
		{
			strat = "none";
		}
		
		switch(strat)
		{
		default:
			System.err.println("Warning:\n\tEnvironment variable \"" + ENVIRONMENT_STRATEGY + "\" was not defined.\n\tAssuming default behavior.\n ");
		case DEBUG_STRATEGY:
			DBManager.strategy = new StubController();
			break;
		case XML_STRATEGY:
			DBManager.strategy = new XmlController();
			break;
		case SQL_STRATEGY:
			DBManager.strategy = new SqlController();
			break;
		}
	}
	
	/**
	 * Gets the currently employed strategy for serializing data.
	 * @return
	 */
	public static IDBController getInstance()
	{
		return strategy;
	}

}
