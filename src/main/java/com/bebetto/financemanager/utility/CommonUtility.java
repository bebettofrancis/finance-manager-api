package com.bebetto.financemanager.utility;

import java.util.UUID;

public class CommonUtility {

	public static String getUniqueId() {
		return UUID.randomUUID().toString();
	}

	private CommonUtility() {
		super();
	}

}
